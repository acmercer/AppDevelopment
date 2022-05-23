package com.example.appdevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

public class DetailActivity extends AppCompatActivity {
    Spinner repeatWhen;
    TextView displayDate, displayTime;
    EditText todoTitle;
    Switch remind;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.todo_display);
            todoTitle = findViewById(R.id.txtTodoTitle);
            repeatWhen = findViewById(R.id.drpRepeat);
            remind = findViewById(R.id.togReminder);
            displayDate = findViewById(R.id.txtDisplayDate);
            displayTime = findViewById(R.id.txtDisplayTime);
            Intent i = this.getIntent();
            todoTitle.setText(i.getExtras().getString("MSG_KEY"));
            remind.setChecked((i.getExtras().getString("REMIND_KEY")).equals("true"));
            displayDate.setText(i.getExtras().getString("DATE_KEY"));
            displayTime.setText(i.getExtras().getString("TIME_KEY"));
            repeatWhen.setSelection(getIndex(repeatWhen, i.getExtras().getString("REPEAT_KEY")));
        }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

}
