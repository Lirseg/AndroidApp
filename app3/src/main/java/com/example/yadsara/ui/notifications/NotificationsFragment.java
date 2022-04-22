package com.example.yadsara.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yadsara.R;
import com.example.yadsara.databinding.FragmentNotificationsBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {




    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;


    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Find the ListView resource.
        mainListView = (ListView) root.findViewById( R.id.mainListView );

        // Create and populate a List of  data.
        ArrayList<String> dataList = new ArrayList<String>();
        LocalDateTime now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        int workweek =0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            workweek = (now.toLocalDate().getDayOfYear() / 7) +1;
        }

        for (int i =0; i<52 ;i++){
            if(workweek>52)
                workweek-=52;
            dataList.add(String.valueOf(workweek));
            workweek++;
        }



        // Create ArrayAdapter using the data list.
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.simplerow, dataList);

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}