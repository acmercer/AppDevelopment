package com.example.appdevelopment;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class todoWRDatabase {
    ArrayList<Todo> todoList = new ArrayList<Todo>();
    DatabaseReference userData;
    static Boolean saved=null;
    ArrayList<ShopItem> shopList = new ArrayList<>();
    DatabaseReference shopData;
    static Boolean savedShop=null;

    public todoWRDatabase(DatabaseReference userData){
        this.userData = userData;
    }
//    public Boolean save(Todo todoItem) {
//        if(todoItem==null) {
//            saved=false;
//        }else {
//            try {
//                userData.push().setValue(todoItem);
//                saved=true;
//            }catch (DatabaseException e) {
//                e.printStackTrace();
//                saved=false;
//            }
//        }
//        return saved;
//    }
    private void fetchData(DataSnapshot dataSnapshot) {
            todoList.clear();

//            String message=ds.child("message").getValue(String.class);
//            String date = ds.child("alertDate").getValue(String.class);
//            String time = ds.child("alertTime").getValue(String.class);
//            String alert = ds.child("alert").getValue(String.class);
//            String repeat = ds.child("repeat").getValue(String.class);
            Todo todoItem = dataSnapshot.getValue(Todo.class);
            todoList.add(todoItem);

    }
    public ArrayList<Todo> retrieveTodo(DatabaseReference userData) {
        userData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded (@NonNull DataSnapshot dataSnapshot, String todo){
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged (@NonNull DataSnapshot dataSnapshot, String todo){
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved (@NonNull DataSnapshot dataSnapshot){
            }
            @Override
            public void onChildMoved (@NonNull DataSnapshot dataSnapshot, String s){
            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){
            }
        });
        return todoList;
    }

    public void shopWRDatabase(DatabaseReference shopData){
        this.shopData = shopData;
    }
    public Boolean save(ShopItem shopItem) {
        if(shopItem==null) {
            savedShop=false;
        }else {
            try {
                userData.setValue(shopItem);
                savedShop=true;
            }catch (DatabaseException e) {
                e.printStackTrace();
                savedShop=false;
            }
        }
        return savedShop;
    }
    private void fetchShop(DataSnapshot shopSnapshot) {
        todoList.clear();

        for (DataSnapshot ds : shopSnapshot.getChildren()) {
            ShopItem shopItem=ds.getValue(ShopItem.class);
            shopList.add(shopItem);
        }
    }
    public ArrayList<ShopItem> retrieveShop(DatabaseReference shopData) {
        userData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded (@NonNull DataSnapshot dataSnapshot, String s){
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged (@NonNull DataSnapshot dataSnapshot, String s){
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved (@NonNull DataSnapshot dataSnapshot){
            }
            @Override
            public void onChildMoved (@NonNull DataSnapshot dataSnapshot, String s){
            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){
            }
        });
        return shopList;
    }
}
