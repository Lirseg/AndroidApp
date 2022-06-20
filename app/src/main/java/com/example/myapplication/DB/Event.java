package com.example.myapplication.DB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;





public class Event {

        private String name;
        public String getName() {
            return name;
        }
        public void setName(@NonNull String EventName) { this.name = EventName; }


        private String date;
        public String getDate() { return date; }
        public void setDate(String mEventDate) {
            this.date = mEventDate;
        }

        private String startTime;
        public String getStartTime() {
            return startTime;
        }
        public void setStartTime(String EventFrom) {
            this.startTime = EventFrom;
        }
        @ColumnInfo(name = "eventTimeTo")
        private String endTime;
        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String mEventTo) {
            this.endTime = mEventTo;
        }
        @ColumnInfo(name = "needed")
        private String peopleNeeded;
        public String getPeopleNeeded() {
            return peopleNeeded;
        }

        public void setPeopleNeeded(String mEventNeeded) {
            this.peopleNeeded = mEventNeeded;
        }

//        @ColumnInfo(name = "signedVolun")
        private ArrayList<String> signedVolun;
        public ArrayList<String>  getSignedVolun() {
            return signedVolun;
        }

        public void setSignedVolun(ArrayList<String>  mSignedVolun) {
        this.signedVolun = mSignedVolun;
    }

    public Event(@NonNull String eventName, @NonNull String eventDate, @NonNull String eventTimeFrom, @NonNull String eventTimeTo, @NonNull String needed, @NonNull ArrayList<String>  signedVolun ) {
            this.name = eventName;
            this.date = eventDate;
            this.startTime = eventTimeFrom;
            this.endTime = eventTimeTo;
            this.peopleNeeded = needed;
            this.signedVolun = signedVolun;

        }

        public Event(){}



}

