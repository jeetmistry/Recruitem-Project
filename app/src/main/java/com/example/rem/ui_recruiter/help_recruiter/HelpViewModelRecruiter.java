package com.example.rem.ui_recruiter.help_recruiter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModelRecruiter extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpViewModelRecruiter() {
        mText = new MutableLiveData<>();
        mText.setValue("Help for TPO\n\n" +
                "1.TPO can login to the page by providing id and password on different devices.\n\n" +
                "2.Once you login as TPO , you did not need to login everytime in student unless you logout from module. \n\n" +
                "3.After you login as TPO and the home page appears you can slide in from left side moving towards right or simply click on the toggle button in the upper left corner to get more options and navigate through other functionalities.\n\n" +
                "4.The TPO has different functionalities like they can view jobs that they previously posted for students to enrol for, view notifications, add jobs that where there is vacancy ,and can view who has enrolled for the job in the view applications by.\n\n" +
                "5.The TPO has to first fill the profile and submit it. TPO will be able to see whether the information filled in the profile is correct or not in the home page and update the profile if anything is incorrect or in order to change something.\n\n" +
                "6.The TPO is able to write and submit the feedbacks and also see the previous feedback of the user. \n\n" +
                "7.If the TPO desires to go settings of the mobile he/she can directly navigate to the mobile settings by clicking the three dots at the top right corner instead of following the long procedure of closing app and starting the settings.\n\n");
    }

    public LiveData<String> getText() {
        return mText;
    }
}