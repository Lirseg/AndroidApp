package com.example.myapplication.DB;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private NewEventActivity binding;
    private EditText mEditEventView1;
    private EditText mEditEventView2;
    private EditText mEditEventView3;
    private EditText mEditEventView4;
    private EditText mEditEventView5;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        mEditEventView1 = findViewById(R.id.plain_text_input11);
        mEditEventView2 = findViewById(R.id.plain_text_input12);
        mEditEventView3 = findViewById(R.id.plain_text_input16);
        mEditEventView4 = findViewById(R.id.plain_text_input17);
        mEditEventView5 = findViewById(R.id.plain_text_input18);
        Button btn = findViewById(R.id.button10);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NewEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mEditEventView2.setText(day + "/" + (month + 1) + "/" + year);
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
                timePickerDialog = new TimePickerDialog(NewEventActivity.this,
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
                timePickerDialog = new TimePickerDialog(NewEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                mEditEventView4.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        final Button button = findViewById(R.id.button11);
        button.setOnClickListener(view -> {

            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditEventView1.getText().toString())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {

                String eventName = mEditEventView1.getText().toString();
                String eventDate = mEditEventView2.getText().toString();
                String eventFrom = mEditEventView3.getText().toString();
                String eventTo = mEditEventView4.getText().toString();
                String eventNeeded = mEditEventView5.getText().toString();
                replyIntent.putExtra("EVENTNAME", eventName);
                replyIntent.putExtra("EVENTDATE", eventDate);
                replyIntent.putExtra("EVENTFROM", eventFrom);
                replyIntent.putExtra("EVENTTO", eventTo);
                replyIntent.putExtra("EVENTNEEDED", eventNeeded);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }





}
