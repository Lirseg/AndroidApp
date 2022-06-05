package com.example.myapplication.ui.dashboard;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DB.Event;
import com.example.myapplication.DB.EventListAdapter;
import com.example.myapplication.DB.EventViewModel;
import com.example.myapplication.DB.NewEventActivity;
import com.example.myapplication.FireBaseFireStore.DocSnippets;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardFragment extends Fragment {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    public EventViewModel mEventViewModel;

    private FragmentDashboardBinding binding;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final EventListAdapter adapter = new EventListAdapter(new EventListAdapter.EventDiff(),getActivity(),root);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        // Get a new or existing ViewModel from the ViewModelProvider.

        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        mEventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(events);
        });

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


        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Event event = new Event(data.getStringExtra("EVENTNAME"),data.getStringExtra("EVENTDATE"),data.getStringExtra("EVENTFROM"),data.getStringExtra("EVENTTO"),data.getStringExtra("EVENTNEEDED"));
            mEventViewModel.insert(event);
            DocSnippets.addEvent(data.getStringExtra("EVENTNAME"),data.getStringExtra("EVENTDATE"),data.getStringExtra("EVENTFROM"),data.getStringExtra("EVENTTO"),data.getStringExtra("EVENTNEEDED"));
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


}