package com.example.myapplication.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM event_tbl")
    LiveData<List<Event>> getEvents();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Query("DELETE FROM event_tbl")
    void deleteAll();

    @Query("DELETE FROM event_tbl WHERE eventName==:eventName ")
    void remove(String eventName);


}
