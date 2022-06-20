package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.DB.EditEventActivity;
import com.example.myapplication.DB.EventViewModel;
import com.example.myapplication.FireBaseFireStore.DocSnippets;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class NotificationsFragment extends Fragment {


    private LocalDateTime showing;

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;


    private FragmentNotificationsBinding binding;
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
    private FirebaseAuth mAuth;

    @SuppressLint("HandlerLeak")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mEditEventView3 = root.findViewById(R.id.userScheduleDateTxt);
        mEditEventView4 = root.findViewById(R.id.userScheduleSTimeTxt);
        mEditEventView5 = root.findViewById(R.id.userScheduleFTimeTxt);
        Button btn = root.findViewById(R.id.userScheduleDateBtn);
        Button btn2 = root.findViewById(R.id.userScheduleSTimeBtn);
        Button btn3 = root.findViewById(R.id.userScheduleFTimeBtn);
        Button btn4 = root.findViewById(R.id.userScheduleConfirmBtn);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(root.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mEditEventView3.setText(day + "\\" + (month + 1) + "\\" + year);
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
                timePickerDialog = new TimePickerDialog(root.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                if(hourOfDay<10)
                                    hourOfDay=0+hourOfDay;
                                if(minute<10)
                                    minute=0+minute;
                                mEditEventView4.setText(String.format("%02d:%02d", hourOfDay, minute));
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
                timePickerDialog = new TimePickerDialog(root.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                mEditEventView5.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener ocl;
                ocl = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth user = FirebaseAuth.getInstance();
                        System.out.println(( !mEditEventView4.getText().toString().isEmpty() && !mEditEventView3.getText().toString().isEmpty() && !mEditEventView5.getText().toString().isEmpty()));
                        if( !mEditEventView4.getText().toString().isEmpty() && !mEditEventView3.getText().toString().isEmpty() && !mEditEventView5.getText().toString().isEmpty()) {
                            System.out.println("in");
                            DocSnippets.userScheduleTime(user.getUid(), mEditEventView3.getText().toString(),
                                    mEditEventView4.getText().toString(), mEditEventView5.getText().toString());
                            mEditEventView3.setText("");
                            mEditEventView4.setText("");
                            mEditEventView5.setText("");
                            Snackbar.make(view,R.string.yes_vul_time, Snackbar.LENGTH_SHORT).show();
                        }else{
                            Snackbar.make(view,R.string.invalid_time, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("New Volunteer Time");
                builder.setMessage("Would you like to confirm these times?");
                // add the buttons
                builder.setPositiveButton("Continue",  ocl);
                builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });






        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}