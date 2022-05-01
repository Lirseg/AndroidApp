package com.example.myapplication.ui.notifications;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class NotificationsFragment extends Fragment {


    private LocalDateTime showing;

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;


    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        TextView textView2 = root.findViewById(R.id.ScheduleTime );


        // Add a month
        View.OnClickListener plusMonth = new View.OnClickListener() {
            public void onClick(View v) {
                TextView textView3 = root.findViewById(R.id.ScheduleTime );

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    showing=showing.plusMonths(1);

                    textView3.setText(showing.getMonth().toString().substring(0,3)+showing.getYear()%100);
                }

            }
        };
        // reduce a month
        View.OnClickListener minusMonth = new View.OnClickListener() {
            public void onClick(View v) {
                TextView textView3 = root.findViewById(R.id.ScheduleTime );

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    showing=showing.minusMonths(1);

                    textView3.setText(showing.getMonth().toString().substring(0,3)+showing.getYear()%100);
                }

            }
        };

        Button btn1 = root.findViewById(R.id.week1);
        Button btn2 = root.findViewById(R.id.week2);
        Button btn3 = root.findViewById(R.id.week3);
        Button btn4 = root.findViewById(R.id.week4);
        Button btn5 = root.findViewById(R.id.week5);
        // Find the ListView resource.
//        mainListView = (ListView) root.findViewById( R.id.mainListView );
//
//        // Create and populate a List of  data.
//        ArrayList<String> dataList = new ArrayList<String>();
        LocalDateTime now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            showing=now;
            textView2.setText(now.getMonth().toString().substring(0,3)+now.getYear()%100);
            Calendar calendar = Calendar.getInstance();
//            btn1.setText(String.valueOf(calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)));

        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(root.getContext()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Alert message to be shown");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(plusMonth);
        FloatingActionButton fab2 = root.findViewById(R.id.floatingActionButton4);
        fab2.setOnClickListener(minusMonth);








//        int workweek =0;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            workweek = (now.toLocalDate().getDayOfYear() / 7) +1;
//        }
//
//        for (int i =0; i<52 ;i++){
//            if(workweek>52)
//                workweek-=52;
//            dataList.add(String.valueOf(workweek));
//            workweek++;
//        }
//
//
//
//        // Create ArrayAdapter using the data list.
//        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.simplerow, dataList);
//
//        // Set the ArrayAdapter as the ListView's adapter.
//        mainListView.setAdapter( listAdapter );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}