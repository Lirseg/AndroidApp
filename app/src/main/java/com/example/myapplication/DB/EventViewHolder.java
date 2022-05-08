package com.example.myapplication.DB;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.dashboard.DashboardFragment;
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
        eventNeeded.setText(text);
    }
    public void bindRmv(String text,Activity a) {

        rmvBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                mEventViewModel = new ViewModelProvider((ViewModelStoreOwner) a).get(EventViewModel.class);
                mEventViewModel.remove(text);

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





    static EventViewHolder create(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new EventViewHolder(view);
    }



}
