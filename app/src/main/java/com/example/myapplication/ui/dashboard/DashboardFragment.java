package com.example.myapplication.ui.dashboard;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DB.EditEventActivity;
import com.example.myapplication.DB.Event;

import com.example.myapplication.DB.EventViewModel;
import com.example.myapplication.DB.NewEventActivity;
import com.example.myapplication.DB.users;
import com.example.myapplication.FireBaseFireStore.DocSnippets;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;


    public EventViewModel mEventViewModel;

    private FragmentDashboardBinding binding;










    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        System.out.println("this is test" + recyclerView);
//        final EventListAdapter adapter = new EventListAdapter(new EventListAdapter.EventDiff(),getActivity(),root);



        firebaseFirestore = FirebaseFirestore.getInstance();








        //Query
        Query query = firebaseFirestore.collection("events");

        //RecOptv + adapter
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>().setQuery(query, Event.class).build();
        adapter = new FirestoreRecyclerAdapter<Event, DashboardFragment.usersHolder>(options) {
            @NonNull
            @Override
            public DashboardFragment.usersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.recycler_view_item, viewGroup, false);

                return new DashboardFragment.usersHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DashboardFragment.usersHolder holder, int position, @NonNull Event model) {


                DialogInterface.OnClickListener ocl;
                ocl = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DocSnippets.remove_event(model.getName());
                    }
                };

                holder.eventNeeded.setTextColor(Color.RED);
                holder.eventName.setText(model.getName());
                holder.eventDate.setText(model.getDate());
                holder.eventFrom.setText(model.getStartTime());
                holder.eventNeeded.setText(model.getPeopleNeeded());
                holder.eventTo.setText(model.getEndTime());
                DocSnippets.isEventFull(model.getName().toString(),holder.eventNeeded);

                holder.rmvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Alert!");
                        builder.setMessage("Would you like to remove this event?");
                        // add the buttons
                        builder.setPositiveButton("Continue", ocl);
                        builder.setNegativeButton("Cancel", null);
                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
                holder.editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(root.getContext(), EditEventActivity.class);
                        intent.putExtra("Name", model.getName());
                        intent.putExtra("Date", model.getDate());
                        intent.putExtra("From", model.getStartTime());
                        intent.putExtra("To", model.getEndTime());
                        intent.putExtra("Needed", model.getPeopleNeeded());
                        intent.putExtra("signed", model.getSignedVolun());
                        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                    }
                });
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(root.getContext(), Schedule.class);
                        intent.putExtra("signed",model.getSignedVolun());
                        intent.putExtra("date",model.getDate());
                        intent.putExtra("name",model.getName());
                        intent.putExtra("startTime", model.getStartTime());
                        intent.putExtra("endTime", model.getEndTime());
                        intent.putExtra("needed", model.getPeopleNeeded());
                        intent.putExtra("more",String.valueOf(Integer.parseInt(model.getPeopleNeeded()) - model.getSignedVolun().size()+1));
                        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                    }
                });
            }
        };


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));





        // Get a new or existing ViewModel from the ViewModelProvider.

        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.



//        mEventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
//            // Update the cached copy of the words in the adapter.
//
//            adapter.submitList(events);
//        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            Intent intent = new Intent(root.getContext(), NewEventActivity.class);

            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });




        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<String> l = new ArrayList<String>();
        l.add("Eitan");
        l.add("Liran");
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            DocSnippets.addEvent(data.getStringExtra("EVENTNAME"),data.getStringExtra("EVENTDATE"),data.getStringExtra("EVENTFROM"),data.getStringExtra("EVENTTO"),data.getStringExtra("EVENTNEEDED"));
        } else {
            Snackbar.make(getView(),R.string.invalid_time2, Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(
//                    getContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
        }
    }



    public class usersHolder extends RecyclerView.ViewHolder {

        private final TextView eventName;
        private final TextView eventDate;
        private final TextView eventFrom;
        private final TextView eventTo;
        private final TextView eventNeeded;
        private final AppCompatImageButton rmvBtn ;
        private final AppCompatImageButton editBtn;
        private EventViewModel mEventViewModel;
        private final ConstraintLayout container;

        public usersHolder(@NonNull View itemView){
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
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


}