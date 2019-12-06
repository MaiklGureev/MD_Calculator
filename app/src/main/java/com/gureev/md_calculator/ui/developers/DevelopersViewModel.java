package com.gureev.md_calculator.ui.developers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevelopersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DevelopersViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}