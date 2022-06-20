package com.example.myapplication.ui.home;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.myapplication.DB.Event;
import com.example.myapplication.DB.EventViewModel;
import com.example.myapplication.FireBaseFireStore.DocSnippets;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public List l;
    public EventViewModel mEventViewModel;
    public List<EventDay> events = new ArrayList<EventDay>();
    public LocalDate today = null;
    public Calendar calendar = Calendar.getInstance();
    private FirebaseAuth mAuth;






    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);




        List<Object> events2 = new ArrayList<Object>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now();


        }



//        events2=DocSnippets.getAllEvents(events2);
        DocSnippets ds= new DocSnippets();



        getAllEvents(events2);


        Log.d(TAG, "HEYA" + events2);
        Log.d(TAG, "HEYyA" + myList);
        System.out.println(ds.list2);





        CalendarView calendarView = (CalendarView) root.findViewById(R.id.calendarView);
        calendarView.setEvents(events);





        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public List getEventDate() {
        return this.l;
    }

    public void setEventDate(List list) {
        this.l = list;
    }
    public List myList = new ArrayList();

    public List<Object> getAllEvents(List<Object> list)  {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            List<Object> numbers = new ArrayList<>();
            OnCompleteListener<QuerySnapshot> myListener = new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (!currentUser.getUid().equals("j32McX8shng4EyBWVxImeu8sK6p2") ) {
                                if (document.getData().get("signedVolun").toString().contains(currentUser.getUid()))
                                    list.add(document.getData().get("date"));
                            }else{
                                list.add(document.getData().get("date"));
                            }
                            Log.d(TAG, document.getId() + " => " + document.getData().get("date"));
                        }
                        Log.w(TAG, "Here we are finished" + list);
                        final List l = list;
                        myList = list;
                        Log.w(TAG, "Here is main" + list);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            };
            db.collection("events")
                    .get()
                    .addOnCompleteListener(myListener).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.w(TAG, "now were finished" + list);
                    for (Object o : list) {
                        Calendar c = Calendar.getInstance();
                        String[] al = o.toString().split("\\\\");
                        c.set(Integer.valueOf((al[2])), Integer.valueOf(al[1]) - 1, Integer.valueOf(al[0]));
                        events.add(new EventDay(c, R.drawable.sample_icon, Color.parseColor("#228B22")));
                    }


                    CalendarView calendarView = (CalendarView) getActivity().findViewById(R.id.calendarView);
                    calendarView.setEvents(events);
                }
            });

            Log.w(TAG, "Error getting documents." + list);

            return list;

        }





}