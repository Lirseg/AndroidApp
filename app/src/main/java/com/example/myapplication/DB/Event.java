package com.example.myapplication.DB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_tbl")
public class Event {
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "eventName")
        private String mEventName;
        @NonNull
        public String getEventName() {
            return mEventName;
        }



        public void setEventName(@NonNull String mEventName) {
            this.mEventName = mEventName;
        }

        @ColumnInfo(name = "eventDate")
        private String mEventDate;

        public String getEventDate() {
            return mEventDate;
        }

        public void setEventDate(String mEventDate) {
            this.mEventDate = mEventDate;
        }

        @ColumnInfo(name = "eventTimeFrom")
        private String mEventTimeFrom;
        public String getEventTimeFrom() {
            return mEventTimeFrom;
        }
        public void setEventFrom(String mEventFrom) {
            this.mEventTimeFrom = mEventFrom;
        }
        @ColumnInfo(name = "eventTimeTo")
        private String mEventTimeTo;
        public String getEventTimeTo() {
            return mEventTimeTo;
        }

        public void setEventTo(String mEventTo) {
            this.mEventTimeTo = mEventTo;
        }
        @ColumnInfo(name = "needed")
        private String mNeeded;
        public String getNeeded() {
            return mNeeded;
        }

        public void setEventNeeded(String mEventNeeded) {
            this.mNeeded = mEventNeeded;
        }











    public Event(@NonNull String eventName, @NonNull String eventDate, @NonNull String eventTimeFrom, @NonNull String eventTimeTo, @NonNull String needed) {
            this.mEventName = eventName;
            this.mEventDate = eventDate;
            this.mEventTimeFrom = eventTimeFrom;
            this.mEventTimeTo = eventTimeTo;
            this.mNeeded = needed;
        }


    }

