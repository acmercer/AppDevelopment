package com.example.appdevelopment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FragmentShopList extends Fragment {

    public FragmentShopList(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_list, container, false);
    }

    Button clearList, saveList, addItem, viewSaved;
    EditText item;
    String timeTonotify, shopText, date, time, alert, repeat, name;
    FirebaseDatabase shopDatabase;
    DatabaseReference database, shopData;
    RecyclerView fullShopList;
    ShopAdapter shopAdapter, adapter;
    todoWRDatabase readWrite;
    LinearLayoutManager layout;
    ArrayList<ShopItem> shopList;
    Boolean savedShop=null;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        item = view.findViewById(R.id.txtShopItem);
        clearList = view.findViewById(R.id.btnClearShopping);
        saveList = view.findViewById(R.id.btnSaveShop);
        addItem = view.findViewById(R.id.btnAddShopItem);
        viewSaved = view.findViewById(R.id.btnViewSavedShop);
        fullShopList = view.findViewById(R.id.viewShopList);
        shopList = new ArrayList<ShopItem>();
        layout = new LinearLayoutManager(getActivity());
        fullShopList.setHasFixedSize(true);
        fullShopList.setLayoutManager(layout);
        shopDatabase = FirebaseDatabase.getInstance("https://app-development-a9915-default-rtdb.europe-west1.firebasedatabase.app/");
        database = shopDatabase.getReference("ShopList");
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
                Toast.makeText(getActivity(), "Database issues", Toast.LENGTH_SHORT).show();
            }
        });
        shopData = database.child(name);
        readWrite = new todoWRDatabase(shopData);
        adapter = new ShopAdapter(getActivity(), readWrite.retrieveShop(shopData));
        fullShopList.setAdapter(adapter);
        saveList.setOnClickListener(v -> {
            shopText = item.getText().toString();
            if (shopText.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter text for your to-do", Toast.LENGTH_SHORT).show();
            } else {
                submitToDo();
            }
        });
    }

    private void submitToDo(){
        ShopItem shopItem=new ShopItem();
        shopItem.setShopitem(shopText);


        if(shopText != null && shopText.length()>0) {
//            Map<String, Object> todoData = new HashMap<>();
//            String key = userData.push().getKey();
//            todoData.put(key, todo.toFirebaseObject());
//            userData.updateChildren(todoData);
            if(readWrite.save(shopItem)) {
                Toast.makeText(getActivity(),"To-do submitted", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
        }
    }


    public void editTodo(String...details){

        String alert = details[0];
        String date=details[1];
        String time=details[2];
        String todoMsg=details[3];
        String repeat = details[4];

    }
    public void onDeleteClick(int position) {
        long itemID = shopAdapter.getItemId(position);
        Query queryById = shopData.orderByChild("id").equalTo(itemID);
        queryById.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idSnapshot: dataSnapshot.getChildren()) {
                    idSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
    }
}