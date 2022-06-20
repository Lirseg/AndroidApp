package com.example.myapplication.DB;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.FireBaseFireStore.DocSnippets;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private final LiveData<List<Event>> mAllEvents;

    public EventViewModel(Application application) {

        super(application);
        mRepository = new EventRepository(application);
        mAllEvents = mRepository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }

    public void insert(Event event) {
//        mRepository.insert(event);
    }

    public void remove(String name) {
        mRepository.remove(name);
    }
}
