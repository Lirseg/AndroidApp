package com.example.myapplication.DB;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class EventListAdapter extends ListAdapter<Event, EventViewHolder> {
    private Activity a;
    private View root;

    public EventListAdapter(@NonNull DiffUtil.ItemCallback<Event> diffCallback, Activity a, View root) {
        super(diffCallback);
        this.a = a;
        this.root = root;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EventViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

        Event current = getItem(position);
        holder.bindName(current.getName());
        holder.bindDate(current.getDate());
        holder.bindFrom(current.getEndTime());
        holder.bindTo(current.getEndTime());
        holder.bindNeeded(current.getPeopleNeeded());
        holder.bindRmv(current.getName(), a );
//        holder.bindEdit(current.getEventName(),current.getEventDate(),current.getEventTimeFrom(),current.getEventTimeTo(),current.getNeeded(), a,root, current.getSignedVolun() );

//        holder.bindSignedVolun(current.getSignedVolun());

        holder.bindSchedule(root, a);

    }

    public static class EventDiff extends DiffUtil.ItemCallback<Event> {

        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }


}
