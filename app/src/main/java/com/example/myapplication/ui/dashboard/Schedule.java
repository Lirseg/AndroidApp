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

public class Schedule extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;

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
        Query query = firebaseFirestore.collection("users");

        //RecOptv + adapter
        FirestoreRecyclerOptions<users> options = new FirestoreRecyclerOptions.Builder<users>().setQuery(query, users.class).build();
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
                holder.name.setText(model.getName());
            }
        };
        System.out.println(adapter);
        System.out.println(recyclerView);
        System.out.println(recyclerView2);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
