package com.gureev.md_calculator.ui.local_db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocalDBViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LocalDBViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}