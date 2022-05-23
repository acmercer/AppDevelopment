package com.example.appdevelopment;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.text.BreakIterator;


public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Switch remind;
    ItemClickListener itemClickListener;
    Spinner repeatWhen;
    TextView displayDate, displayTime;
    EditText todoTitle;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        todoTitle = itemView.findViewById(R.id.txtTodoTitle);
        repeatWhen = itemView.findViewById(R.id.drpRepeat);
        remind = itemView.findViewById(R.id.togReminder);
        displayDate = itemView.findViewById(R.id.txtDisplayDate);
        displayTime = itemView.findViewById(R.id.txtDisplayTime);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }
}



