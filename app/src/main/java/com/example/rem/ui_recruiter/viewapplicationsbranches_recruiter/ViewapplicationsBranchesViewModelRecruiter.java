package com.example.rem.ui_recruiter.viewapplicationsbranches_recruiter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewapplicationsBranchesViewModelRecruiter extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewapplicationsBranchesViewModelRecruiter() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recruiter View Applications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}