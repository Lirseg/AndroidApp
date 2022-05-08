package com.example.myapplication.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {

    private EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    EventRepository(Application application) {
        EventRoomDatabase db = EventRoomDatabase.getDatabase(application);
        mEventDao = db.eventDao();
        mAllEvents = mEventDao.getEvents();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Event event) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            mEventDao.insert(event);
        });
    }

    void remove(String name) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            mEventDao.remove(name);
        });
    }
}
