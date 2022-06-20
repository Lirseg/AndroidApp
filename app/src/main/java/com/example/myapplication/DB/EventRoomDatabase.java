package com.example.myapplication.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.FireBaseFireStore.DocSnippets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//@Database(entities = {Event.class}, version = 2, exportSchema = false)

abstract class EventRoomDatabase extends RoomDatabase {

    abstract EventDao eventDao();
    private static volatile EventRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static EventRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EventRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                EventDao dao = INSTANCE.eventDao();
                dao.deleteAll();
                ArrayList<String> list1 = new ArrayList<>();
                list1.add("id1");
                list1.add("id2");

                ArrayList<String> list2 = new ArrayList<>();
                list2.add("pP23Pb5J7nSryd4UCHevuSDENDh2");

                Event word = new Event("Shovrim Shigra","20/6/2022","10:00","12:00","2", list1);
                dao.insert(word);
                word = new Event("Yad La Lev","30/6/2022","12:00","15:00","2",list2);
                dao.insert(word);

            });
        }
    };

}
