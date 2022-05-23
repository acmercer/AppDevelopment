package com.example.appdevelopment;


import java.io.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    TextView Title;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Title = (TextView) myToolbar.findViewById(R.id.toolbar_title);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.calendarNav);


    }



    FragmentCalendar firstFragment = new FragmentCalendar();
    FragmentNotes secondFragment = new FragmentNotes();
    FragmentTODO thirdFragment = new FragmentTODO();
    FragmentShopList fourthFragment = new FragmentShopList();
    FragmentFocus fifthFragment = new FragmentFocus();

    @Nullable

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.calendarNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, firstFragment).commit();
                Title.setText(getResources().getString(R.string.calendarTitle));
                return true;

            case R.id.notesNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, secondFragment).commit();
                Title.setText(getResources().getString(R.string.notesTitle));
                return true;

            case R.id.todoNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, thirdFragment).commit();
                Title.setText(getResources().getString(R.string.ToDoTitle));
                return true;
            case R.id.shoppinglistNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fourthFragment).commit();
                Title.setText(getResources().getString(R.string.shoppingTitle));
                return true;
            case R.id.focusNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fifthFragment).commit();
                Title.setText(getResources().getString(R.string.focusTitle));
                return true;
        }
        return false;
    }
}