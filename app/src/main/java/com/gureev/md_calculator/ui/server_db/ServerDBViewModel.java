package com.gureev.md_calculator.ui.server_db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServerDBViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ServerDBViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}