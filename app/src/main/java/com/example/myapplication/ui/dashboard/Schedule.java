package com.example.myapplication.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DB.EventViewHolder;
import com.example.myapplication.DB.users;
import com.example.myapplication.FireBaseFireStore.DocSnippets;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerAdapter adapter2;

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

        firebaseFirestore = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btn = findViewById(R.id.confSchedule);

        RecyclerView recyclerView = findViewById(R.id.signView);
        RecyclerView recyclerView2 = findViewById(R.id.availView);
        TextView tv = findViewById(R.id.textView7);

        tv.setText(getIntent().getStringExtra("more"));



        //Query
        ArrayList<String> list = getIntent().getStringArrayListExtra("signed");

        String date = getIntent().getStringExtra("date");
        String name = getIntent().getStringExtra("name");
        String startTime = getIntent().getStringExtra("startTime");
        String finishTime = getIntent().getStringExtra("endTime");
        String needed = getIntent().getStringExtra("needed");


        //upper recycler
        Query query1 = firebaseFirestore.collection("events").whereEqualTo("name",name);
        FirestoreRecyclerOptions<users> options = new FirestoreRecyclerOptions.Builder<users>().setQuery(query1, users.class).build();
        adapter = new FirestoreRecyclerAdapter<users, usersHolder>(options) {

            public void deleteItem(int position){
                getSnapshots().getSnapshot(position).getReference().delete();
                notifyDataSetChanged();
            }

            @NonNull
            @Override
            public usersHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.mysimplerow, viewGroup, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("YUP");

                    }
                });
                return new usersHolder(view);
            }
            @Override
            protected void onBindViewHolder( @NonNull usersHolder holder, int position,  @NonNull users model) {

                System.out.println(holder.name.getText());

                holder.name.setText(getIntent().getStringExtra("number"));
//                holder.name.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        DocSnippets.signToEvent(name,date,startTime,finishTime,needed,holder.name.getText().toString());
////                        finish();
//                        holder.flag = !holder.flag;
//                        if(holder.flag)
//                            holder.name.setTextColor(Color.RED);
//                        else
//                            holder.name.setTextColor(Color.BLACK);
//                    }
//                });

            }
        };



        //bottom recycler
        Query query2 = firebaseFirestore.collection("users").whereNotIn("name",list).whereEqualTo("date", date);

        //RecOptv + adapter
        FirestoreRecyclerOptions<users> options2 = new FirestoreRecyclerOptions.Builder<users>().setQuery(query2, users.class).build();
        adapter2 = new FirestoreRecyclerAdapter<users, usersHolder>(options2) {


            @NonNull
            @Override
            public usersHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.mysimplerow, viewGroup, false);

                return new usersHolder(view);
            }

            @Override
            protected void onBindViewHolder( @NonNull usersHolder holder, int position,  @NonNull users model) {
                System.out.println(holder.name.getText());
                holder.name.setText(model.getName());
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DocSnippets.signToEvent(name,date,startTime,finishTime,needed,holder.name.getText().toString());
                        finish();
                        holder.flag = !holder.flag;
                        if(holder.flag)
                            holder.name.setTextColor(Color.GREEN);
                        else
                            holder.name.setTextColor(Color.BLACK);

                    }
                });
            }
        };



        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


    }



    //holder
    private class usersHolder extends RecyclerView.ViewHolder {

        private TextView name;
        public boolean flag=false;


        public usersHolder(@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.scheduleName);

        }
    }





    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }





}
