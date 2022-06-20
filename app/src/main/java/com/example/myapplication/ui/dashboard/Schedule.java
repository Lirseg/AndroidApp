package com.example.myapplication.ui.dashboard;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DB.EventViewHolder;
import com.example.myapplication.DB.users;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

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

        RecyclerView recyclerView = findViewById(R.id.signView);
        RecyclerView recyclerView2 = findViewById(R.id.availView);


        //Query
        ArrayList<String> list = getIntent().getStringArrayListExtra("signed");

        String s = getIntent().getStringExtra("date");
        String name = getIntent().getStringExtra("name");

        //upper recycler
        Query query1 = firebaseFirestore.collection("events").whereEqualTo("name",name);
        FirestoreRecyclerOptions<users> options = new FirestoreRecyclerOptions.Builder<users>().setQuery(query1, users.class).build();
        adapter = new FirestoreRecyclerAdapter<users, usersHolder>(options) {
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
                holder.name.setText(list.toString());
            }
        };



        //bottom recycler
        Query query2 = firebaseFirestore.collection("users").whereNotIn("name",list).whereEqualTo("date", s);

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
