package com.example.rem.ui_recruiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rem.BuildConfig;
import com.example.rem.Model.ApplyJobStudent;
import com.example.rem.R;
import com.example.rem.ViewHolders.RecruiterViewApplicationViewHolder;
import com.example.rem.ui_recruiter.recruiter_viewapplications.RecruiterViewApplicationsViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class RecruiterApplication extends AppCompatActivity {
    TextView textView ;
    String branch,file1,file2;

    private RecruiterViewApplicationsViewModel recruiterViewApplicationsViewModel;
    private RecyclerView viewapplicationsRecycler;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    Button openExcel1, openExcel2;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef, userRef, userIdRef, appliedJobsRef, jobRef, branchRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recruiter_view_applications_activity);
        viewapplicationsRecycler = findViewById(R.id.recyclerView_viewapplications_recruiterr);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        viewapplicationsRecycler.setLayoutManager(layoutManager);
        openExcel1 = findViewById(R.id.button_download_excel);
        openExcel2 = findViewById(R.id.button_download_excel2);

        textView = findViewById(R.id.text_viewapplications_recruiterr);
        Intent intent = getIntent();
        branch = intent.getStringExtra("branch");
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        jobRef = rootRef.child("Job Applications");
        branchRef = jobRef.child(branch);
        textView.setText("Applications for : "+branch);
        openExcel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branch.equals("IT")) {
                    gotoUrl("https://docs.google.com/spreadsheets/d/1IH7N8FcUpL7aslOR2XsngaHAMLA5GKFjgeTwnO9tBl4/edit#gid=0");
                }
                if(branch.equals("CO")){
                    gotoUrl("https://docs.google.com/spreadsheets/d/1Ch2prIGM9IGxXPBIHPfLkec0dIuiOzMubFQgoZ36xYY/edit#gid=0");
                }
                if(branch.equals("ETRX")){
                    gotoUrl("https://docs.google.com/spreadsheets/d/1FcEaEhEYg0xBetGHu6ntQxZYiE_EhXtvBntMk5HmXac/edit#gid=0");
                }
                if(branch.equals("EXTC")){
                    gotoUrl("https://docs.google.com/spreadsheets/d/1A-kzbUMlcztOi1CdLux8Fm0WU_qAtULefxdHlDBs54g/edit#gid=0");
                }
            }
        });
        openExcel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branch.equals("IT")) {
                    gotoUrl("https://docs.google.com/spreadsheets/d/1PGFDoTb8TY6pBaZqTLtFhIcMAAwVc4IOvE2EVCxjMMQ/edit#gid=0");
                }
                if(branch.equals("CO")){
                    gotoUrl("https://docs.google.com/spreadsheets/d/1hbH99OteJM2Y-LAw-x6e-Ifg25vHt3eu9BIOj7Hp_DU/edit#gid=0");
                }
                if(branch.equals("ETRX")){
                    gotoUrl("https://docs.google.com/spreadsheets/d/1BoC1jN0L6eLSwOrDsDskYi_y0cCjshaD-SPMQkJ6m84/edit#gid=0");
                }
                if(branch.equals("EXTC")){
                    gotoUrl("https://docs.google.com/spreadsheets/d/1P7Ikv6ynsOZ8gWw0b3qSpd9nXHmGEgEcNCkHmMGZ0no/edit#gid=0");
                }
            }
        });

    }
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }


    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerOptions<ApplyJobStudent> options = new FirebaseRecyclerOptions.Builder<ApplyJobStudent>()
                .setQuery(branchRef, new SnapshotParser<ApplyJobStudent>() {
                    @NonNull
                    @Override
                    public ApplyJobStudent parseSnapshot(@NonNull DataSnapshot snapshot) {
                        branchRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot root : dataSnapshot.getChildren()){
                                    for(DataSnapshot user : root.getChildren()){
                                        for(DataSnapshot job : user.child("Job Applications").getChildren()){
                                            for(DataSnapshot branch : user.child(branch).getChildren()){

                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        return new ApplyJobStudent(snapshot.child("companyname").getValue().toString(),
                                snapshot.child("companydescription").getValue().toString(),
                                snapshot.child("jobpost").getValue().toString(),
                                snapshot.child("workingtype").getValue().toString(),
                                snapshot.child("name").getValue().toString(),
                                snapshot.child("email").getValue().toString(),
                                snapshot.child("qualification").getValue().toString(),
                                snapshot.child("studentfield").getValue().toString(),
                                snapshot.child("status").getValue().toString(),
                                snapshot.child("uid").getValue().toString());
                    }
                })
                .build();

        final FirebaseRecyclerAdapter<ApplyJobStudent, RecruiterViewApplicationViewHolder> adapter = new FirebaseRecyclerAdapter<ApplyJobStudent, RecruiterViewApplicationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecruiterViewApplicationViewHolder recruiterViewApplicationViewHolder, final int i, @NonNull final ApplyJobStudent applyJobStudent) {
                recruiterViewApplicationViewHolder.jobPost.setText(applyJobStudent.getJobpost());
                recruiterViewApplicationViewHolder.companyName.setText("Company Name : " + applyJobStudent.getCompanyname());
                recruiterViewApplicationViewHolder.companyDescription.setText("Company Description : " + applyJobStudent.getCompanydescription());
                recruiterViewApplicationViewHolder.workingtype.setText("Job Field : " + applyJobStudent.getWorkingtype());
                recruiterViewApplicationViewHolder.status.setText("Status : " + applyJobStudent.getStatus());
                recruiterViewApplicationViewHolder.name.setText("Name : " + applyJobStudent.getName());
                recruiterViewApplicationViewHolder.email.setText("Email : " + applyJobStudent.getEmail());
                recruiterViewApplicationViewHolder.qualification.setText("Qualification : " + applyJobStudent.getQualification());
                recruiterViewApplicationViewHolder.studentfield.setText("Student Field : " + applyJobStudent.getStudentfield());
                recruiterViewApplicationViewHolder.uid.setText("Uid : " + applyJobStudent.getUid());
                // recruiterViewApplicationViewHolder.uid.setVisibility(View.INVISIBLE);

                recruiterViewApplicationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RecruiterApplicationCardClickActivity.class);
                        intent.putExtra("job post",applyJobStudent.getJobpost());
                        intent.putExtra("company name",applyJobStudent.getCompanyname());
                        intent.putExtra("company description",applyJobStudent.getCompanydescription());
                        intent.putExtra("working type",applyJobStudent.getWorkingtype());
                        intent.putExtra("status",applyJobStudent.getStatus());
                        intent.putExtra("name",applyJobStudent.getName());
                        intent.putExtra("email",applyJobStudent.getEmail());
                        intent.putExtra("qualification",applyJobStudent.getQualification());
                        intent.putExtra("student field",applyJobStudent.getStudentfield());
                        intent.putExtra("uid",applyJobStudent.getUid());

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public RecruiterViewApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_viewapplications_recruiter, parent, false);
                RecruiterViewApplicationViewHolder holder = new RecruiterViewApplicationViewHolder(view);
                return holder;
            }
        };
        viewapplicationsRecycler.setAdapter(adapter);
        adapter.startListening();
    }
}

