package com.gureev.md_calculator.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

//    public MapsViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is share fragment");
//    }

    public LiveData<String> getText() {
        return mText;
    }
}