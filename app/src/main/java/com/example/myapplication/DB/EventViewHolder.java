package com.example.myapplication.DB;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FireBaseFireStore.DocSnippets;
import com.example.myapplication.R;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.dashboard.Schedule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventViewHolder extends RecyclerView.ViewHolder{

    private final TextView eventName;
    private final TextView eventDate;
    private final TextView eventFrom;
    private final TextView eventTo;
    private final TextView eventNeeded;
    private final AppCompatImageButton rmvBtn ;
    private final AppCompatImageButton editBtn;
    private EventViewModel mEventViewModel;
    private final ConstraintLayout container;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;



    private EventViewHolder(View itemView) {

        super(itemView);
        eventName = itemView.findViewById(R.id.textName);
        eventDate = itemView.findViewById(R.id.textDate);
        eventFrom = itemView.findViewById(R.id.textFrom);
        eventTo = itemView.findViewById(R.id.textTo);
        eventNeeded = itemView.findViewById(R.id.textNeeded);
        rmvBtn = itemView.findViewById(R.id.rmvBtn);
        editBtn = itemView.findViewById(R.id.editBtn);
        container = itemView.findViewById(R.id.containerID);


    }

    public void bindName(String text) {
        eventName.setText(text);
    }
    public void bindDate(String text) {

        eventDate.setText(text);

    }
    public void bindFrom(String text) {
        eventFrom.setText(text);
    }
    public void bindTo(String text) {
        eventTo.setText(text);
    }
    public void bindNeeded(String text) {
        if (DocSnippets.isEventFull(eventName.getText().toString(),eventNeeded))
            eventNeeded.setTextColor(Color.GREEN);
        else
            eventNeeded.setTextColor(Color.RED);
        eventNeeded.setText(text);
    }


    public void bindRmv(String text,Activity a) {

        DialogInterface.OnClickListener ocl;
        ocl = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEventViewModel = new ViewModelProvider((ViewModelStoreOwner) a).get(EventViewModel.class);
                mEventViewModel.remove(text);

                DocSnippets.remove_event(text);
            }
        };

        rmvBtn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(a);
                builder.setTitle("Alert!");
                builder.setMessage("Would you like to remove this event?");
                // add the buttons
                builder.setPositiveButton("Continue",  ocl);
                builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();





            }
        });
    }
    public void bindEdit(String Name,String Date,String From,String To,String Needed,Activity a, View root) {

        editBtn.setOnClickListener(view -> {
            Intent intent = new Intent(root.getContext(), EditEventActivity.class);
            intent.putExtra("Name", Name);
            intent.putExtra("Date", Date);
            intent.putExtra("From", From);
            intent.putExtra("To", To);
            intent.putExtra("Needed", Needed);
            a.startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);

        });


    }
    public void bindSchedule(View root,Activity a){
        container.setOnClickListener(view -> {
            Intent intent = new Intent(root.getContext(), Schedule.class);
            a.startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);

        });
    }







    static EventViewHolder create(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new EventViewHolder(view);
    }



}
