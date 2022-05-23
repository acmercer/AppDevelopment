package com.example.appdevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TodoAdapter extends FirebaseRecyclerAdapter<Todo, TodoAdapter.TodoViewHolder> {

    ArrayList<Todo> todoList;
    Context context;
    Button saveTODO;
    Spinner repeatWhen;
    TextView displayDate, displayTime;
    DatabaseReference database, userData;
    EditText todoTitle;
    RecyclerView fullTodoList;
    ImageButton delete, edit;
    Switch remind;

    public TodoAdapter(@NonNull FirebaseRecyclerOptions<Todo> options) {
//        this.context = context;
//        this.todoList = todoList;
        super(options);
    }

    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_display, parent, false);
//        todoTitle = view.findViewById(R.id.txtTodoTitle);
//        repeatWhen = view.findViewById(R.id.drpRepeat);
//        remind = view.findViewById(R.id.togReminder);
//        displayDate = view.findViewById(R.id.txtDisplayDate);
//        displayTime = view.findViewById(R.id.txtDisplayTime);
//        saveTODO = view.findViewById(R.id.btnSaveToDo);
        return new TodoViewHolder(view);
    }
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final Todo todo = todoList.get(position);
//        //FragmentTODO todoFrag = new FragmentTODO();
//        holder.todoTitle.setText(todo.getMessage());
//        if (todo.getAlert().equals("true")){
//            holder.remind.setChecked(true);
//        }
//        else { holder.remind.setChecked(false);}
//        holder.repeatWhen.setSelection(getIndex(repeatWhen, todo.getRepeat()));
//        holder.displayTime.setText(todo.getAlertTime());
//        holder.displayDate.setText(todo.getAlertDate());
//        holder.setItemClickListener(pos -> openDetailActivity(todo.getMessage(),todo.getAlert(),todo.getAlertDate(),todo.getAlertTime(),todo.getRepeat()));
//
//    }

//    @Override
//    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Todo todo) {
//        //final Todo todo = todoList.get(position);
//        //FragmentTODO todoFrag = new FragmentTODO();
//        holder.todoTitle.setText(todo.getMessage());
//        if (todo.getAlert().equals("true")){
//            holder.remind.setChecked(true);
//        }
//        else { holder.remind.setChecked(false);}
//        holder.repeatWhen.setSelection(getIndex(repeatWhen, todo.getRepeat()));
//        holder.displayTime.setText(todo.getAlertTime());
//        holder.displayDate.setText(todo.getAlertDate());
//        holder.setItemClickListener(pos -> openDetailActivity(todo.getMessage(),todo.getAlert(),todo.getAlertDate(),todo.getAlertTime(),todo.getRepeat()));
//    }

    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(context,DetailActivity.class);

        i.putExtra("MSG_KEY",details[0]);
        i.putExtra("REMIND_KEY",details[1]);
        i.putExtra("DATE_KEY",details[2]);
        i.putExtra("TIME_KEY", details[3]);
        i.putExtra("REPEAT_KEY", details[4]);

        context.startActivity(i);
    }
//    public int getItemCount(){
//        return todoList.size();
//    }

    @Override
    protected void onBindViewHolder(@NonNull TodoViewHolder holder, int position, @NonNull Todo todo) {
        holder.todoTitle.setText(todo.getMessage());
        if (todo.getAlert().equals("true")){
            holder.remind.setChecked(true);
        }
        else { holder.remind.setChecked(false);}
        holder.repeatWhen.setSelection(getIndex(repeatWhen, todo.getRepeat()));
        holder.displayTime.setText(todo.getAlertTime());
        holder.displayDate.setText(todo.getAlertDate());
        //holder.setItemClickListener(pos -> openDetailActivity(todo.getMessage(),todo.getAlert(),todo.getAlertDate(),todo.getAlertTime(),todo.getRepeat()));
    }


    //private method of your class
        private int getIndex(Spinner spinner, String myString){
            for (int i=0;i<spinner.getCount();i++){
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                    return i;
                }
            }
            return 0;
        }
    class TodoViewHolder extends RecyclerView.ViewHolder {
        Switch remind;
        ItemClickListener itemClickListener;
        Spinner repeatWhen;
        TextView displayDate, displayTime;
        EditText todoTitle;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.txtTodoTitle);
            repeatWhen = itemView.findViewById(R.id.drpRepeat);
            remind = itemView.findViewById(R.id.togReminder);
            displayDate = itemView.findViewById(R.id.txtDisplayDate);
            displayTime = itemView.findViewById(R.id.txtDisplayTime);
            //itemView.setOnClickListener(this);
        }
    }
    }







