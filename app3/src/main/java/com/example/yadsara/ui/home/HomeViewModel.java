package com.example.yadsara.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("These are your scheduled events!");

        MutableLiveData<Object> instructions = new MutableLiveData<>();
        instructions.setValue("These are your scheduled events!");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getInstructions() {
        mText.setValue("instructions.....");
        return mText;
    }
}