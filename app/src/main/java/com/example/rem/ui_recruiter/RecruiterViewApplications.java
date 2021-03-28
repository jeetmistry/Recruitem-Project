package com.example.rem.ui_recruiter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rem.R;
import com.example.rem.ui_recruiter.recruiter_viewapplications.RecruiterViewApplicationsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecruiterViewApplications extends AppCompatActivity {
    public TextView jobpost, companyname, companydescription, workingtype,status,tv;

    String job,compname,compdesc,worktype, userid,statuss,key;

    String branch;
    //Database variables
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference rootRef,userRef,userIdRef,appliedJobRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.recruiter_view_applications_activity);
        //receiving extras from card
        Intent intent = getIntent();
        branch = intent.getStringExtra("branch");
        tv = findViewById(R.id.text_viewapplications_recruiterr);
        tv.setText(branch);
//        Bundle bundle = new Bundle();
//        bundle.putString("branch", branch);
//        RecruiterViewApplicationsFragment myFrag = new RecruiterViewApplicationsFragment();
//        myFrag.setArguments(bundle);
    }


}
