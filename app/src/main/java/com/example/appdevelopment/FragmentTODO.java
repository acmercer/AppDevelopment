package com.example.appdevelopment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.util.Log;

import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class FragmentTODO extends Fragment {
    public FragmentTODO(){
        // require a empty public constructor
    }
    Button setDate, setTime, saveTODO;
    EditText title;
    Spinner repeatWhen;
    ImageButton selectTime, confirmPick;
    CardView cardPickDate;
    TextView displayDate, displayTime;
    String timeTonotify, todoText, date, time, alert, repeat, name;
    FirebaseDatabase todoDatabase;
    DatabaseReference database, userData;
    RecyclerView fullTodoList;
    TodoAdapter todoAdapter, adapter;
    todoWRDatabase readWrite;
    LinearLayoutManager layout;
    Switch remind;
    ArrayList<Todo> todoList;
    Boolean saved=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_o_d_o, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDate = view.findViewById(R.id.btnSetDate);
        setTime = view.findViewById(R.id.btnSetTime);
        title = view.findViewById(R.id.txtTodoTitle);
        repeatWhen = view.findViewById(R.id.drpRepeat);
        remind = view.findViewById(R.id.togReminder);
        selectTime = view.findViewById(R.id.btnSelectDateTime);
        confirmPick = view.findViewById(R.id.btnConfirmDate);
        cardPickDate = view.findViewById(R.id.cardSelectDate);
        displayDate = view.findViewById(R.id.txtDisplayDate);
        displayTime = view.findViewById(R.id.txtDisplayTime);
        saveTODO = view.findViewById(R.id.btnSaveToDo);
        fullTodoList = view.findViewById(R.id.todoList);
        todoList = new ArrayList<>();
        layout = new LinearLayoutManager(getActivity());
        fullTodoList.setHasFixedSize(true);
        fullTodoList.setLayoutManager(layout);
        todoDatabase = FirebaseDatabase.getInstance("https://app-development-a9915-default-rtdb.europe-west1.firebasedatabase.app/");
        database = todoDatabase.getReference("ToDo");
        name = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!(snapshot.hasChild(name))) {
                    database.setValue(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Database issues", Toast.LENGTH_SHORT).show();
            }
        });
        userData = database.child(name);
        readWrite = new todoWRDatabase(userData);
        //todoAdapter = new TodoAdapter(getActivity(), todoList);
        //adapter = new TodoAdapter(getActivity(),readWrite.retrieveTodo(userData));
        //adapter = new TodoAdapter(getActivity(), todoAdapter.retrieveTodo(userData));
        FirebaseRecyclerOptions<Todo> options
                = new FirebaseRecyclerOptions.Builder<Todo>()
                .setQuery(userData, Todo.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new TodoAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        fullTodoList.setAdapter(adapter);
        selectTime.setOnClickListener(v -> {
            cardPickDate.setVisibility(View.VISIBLE);
        });

        remind.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectTime.setVisibility(View.VISIBLE);
            }
            else {
                selectTime.setVisibility(View.INVISIBLE);
                cardPickDate.setVisibility(View.INVISIBLE);
            }
        });

        setTime.setOnClickListener(v -> selectTime());
        setDate.setOnClickListener(v -> selectDate());
        confirmPick.setOnClickListener(view1 -> checkSelected());
        saveTODO.setOnClickListener(v -> {
            todoText = title.getText().toString();
            if (todoText.isEmpty()){
                Toast.makeText(getActivity(),"Please enter text for your to-do", Toast.LENGTH_SHORT).show();
            }
            else{
                if (remind.isChecked()){
                    alert = "true";
                    if(checkSelected()){
                        submitToDo();
                    }
                } else {
                    alert = "false";
                    date = "";
                    time = "";
                    submitToDo();
                }
            }
        });
    }

    private void submitToDo() {
        Todo todo = new Todo();
        todo.setMessage(todoText);
        todo.setAlert(alert);
        todo.setAlertDate(date);
        todo.setAlertTime(time);
        todo.setRepeat(repeat);

        if (todoText != null && todoText.length() > 0) {
            Map<String, Object> todoData = new HashMap<>();
            String key = userData.push().getKey();
            todoData.put(key, todo.toFirebaseObject());
            userData.updateChildren(todoData);
//            if(readWrite.save(todo)) {
//                Toast.makeText(getActivity(),"To-do submitted", Toast.LENGTH_SHORT).show();
            title.setText("");
            displayTime.setText("");
            displayDate.setText("");
            repeatWhen.setSelection(0);
            remind.setChecked(false);
//            }
//        }else {
//            Toast.makeText(getActivity(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
//        }
        }
    }

        private boolean checkSelected(){
        date = displayDate.getText().toString().trim();                                 //access the date from the choose date button
        time = displayTime.getText().toString().trim();                                 //access the time from the choose time button
        if (date.isEmpty()) {
            Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
            return false;//shows the toast if input field is empty
        } else {
            if (time.isEmpty()) {                                               //shows toast if date and time are not selected
                Toast.makeText(getActivity(), "Please select time", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                cardPickDate.setVisibility(View.INVISIBLE);
                return true;
            }
        }
    }

    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (timePicker, i, i1) -> {
                    timeTonotify = i + ":" + i1;                                                        //temp variable to store the time to set alarm
                    displayTime.setText(FormatTime(i, i1));                                                //sets the button text as selected time
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (datePicker, year1, month1, day1) -> {
            displayDate.setText(day1 + "-" + (month1 + 1) + "-" + year1);                             //sets the selected date as test for button
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr format and assigns am or pm
        String time;
        String formattedMinute;
        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }
        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;
    }
    public void editTodo(String...details) {

        String alert = details[0];
        String date = details[1];
        String time = details[2];
        String todoMsg = details[3];
        String repeat = details[4];
        remind.setChecked(alert.equals("true"));
        title.setText(todoMsg);
        displayDate.setText(date);
        displayTime.setText(time);
        repeatWhen.setSelection(getIndex(repeatWhen, repeat));
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    public void onDeleteClick(int position) {
        long itemID = todoAdapter.getItemId(position);
        Query queryById = userData.orderByChild("id").equalTo(itemID);
        queryById.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot idSnapshot: dataSnapshot.getChildren()) {
                    idSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}

