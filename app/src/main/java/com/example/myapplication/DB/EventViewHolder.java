package com.example.myapplication.DB;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class EventViewHolder extends RecyclerView.ViewHolder{
    private final TextView eventItemView;
    private EventViewHolder(View itemView) {
        super(itemView);
        eventItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        eventItemView.setText(text);
    }

    static EventViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new EventViewHolder(view);
    }
}
