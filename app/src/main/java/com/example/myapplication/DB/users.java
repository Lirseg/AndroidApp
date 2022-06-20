package com.example.myapplication.DB;

import androidx.lifecycle.LiveData;

import java.util.List;

public class users {
    private String name;


    public users(){}

    public users(String name){
        this.name = name;
    }

    public String getName(){ return this.name;};
    public void setName(String name){this.name = name;};

    public void remove(int adapterPosition) {
        this.setName("");
    }
}
