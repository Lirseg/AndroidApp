package com.example.myapplication.DB;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myapplication.FireBaseFireStore.DocSnippets;
import com.example.myapplication.R;

import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditEventView1;
    private EditText mEditEventView2;
    private EditText mEditEventView3;
    private EditText mEditEventView4;
    private EditText mEditEventView5;
    private EventViewModel mEventViewModel;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        mEditEventView1 = findViewById(R.id.plain_text_input111);
        mEditEventView2 = findViewById(R.id.plain_text_input112);
        mEditEventView3 = findViewById(R.id.plain_text_input116);
        mEditEventView4 = findViewById(R.id.plain_text_input117);
        mEditEventView5 = findViewById(R.id.plain_text_input118);
        Button btn = findViewById(R.id.button101);
        Button btn2 = findViewById(R.id.button21);
        Button btn3 = findViewById(R.id.button31);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mEditEventView2.setText(day + "\\" + (month + 1) + "\\" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                timePickerDialog = new TimePickerDialog(EditEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                if(hourOfDay<10)
                                    hourOfDay=0+hourOfDay;
                                if(minute<10)
                                    minute=0+minute;
                                mEditEventView3.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                timePickerDialog = new TimePickerDialog(EditEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                mEditEventView4.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);



        mEditEventView1.setText(getIntent().getExtras().getString("Name"));
        mEditEventView2.setText(getIntent().getExtras().getString("Date"));
        mEditEventView3.setText(getIntent().getExtras().getString("From"));
        mEditEventView4.setText(getIntent().getExtras().getString("To"));
        mEditEventView5.setText(getIntent().getExtras().getString("Needed"));

        mEditEventView1.setEnabled(false);
        Activity activity = this;

        DialogInterface.OnClickListener ocl;
        ocl = new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String eventName = mEditEventView1.getText().toString();
                String eventDate = mEditEventView2.getText().toString();
                String eventFrom = mEditEventView3.getText().toString();
                String eventTo = mEditEventView4.getText().toString();
                String eventNeeded = mEditEventView5.getText().toString();
                mEventViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(EventViewModel.class);

                Event event = new Event(eventName,eventDate,eventFrom,eventTo,eventNeeded);
                mEventViewModel.insert(event);
                actionBar.setDisplayHomeAsUpEnabled(false);
                DocSnippets.editEvent(eventName,eventDate,eventFrom,eventTo,eventNeeded);
                finish();
            }
        };

        final Button button = findViewById(R.id.editBtn1);
        button.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setMessage("Would you like to edit this event?");
            // add the buttons
            builder.setPositiveButton("Continue",  ocl);
            builder.setNegativeButton("Cancel", null);
            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



