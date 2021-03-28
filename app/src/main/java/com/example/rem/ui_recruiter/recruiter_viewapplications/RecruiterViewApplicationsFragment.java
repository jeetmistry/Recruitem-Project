package com.example.rem.ui_recruiter.recruiter_viewapplications;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rem.Model.ApplyJobStudent;
import com.example.rem.Model.RecruiterViewApplicationStart;
import com.example.rem.R;
import com.example.rem.ViewHolders.RecruiterViewApplicationViewHolder;
import com.example.rem.ui_recruiter.RecruiterApplicationCardClickActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.getIntent;

public class RecruiterViewApplicationsFragment extends Fragment {

    private RecruiterViewApplicationsViewModel recruiterViewApplicationsViewModel;
    private RecyclerView viewapplicationsRecycler;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef, userRef, userIdRef, appliedJobsRef, jobRef, branchRef;
    String userid, branch;
    TextView tv ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        recruiterViewApplicationsViewModel = ViewModelProviders.of(this).get(RecruiterViewApplicationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_viewapplications_recruiter, container, false);

        branch = getArguments().getString("branch");
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        jobRef = rootRef.child("Job Applications");
        branchRef = jobRef.child(branch);
        viewapplicationsRecycler = root.findViewById(R.id.recyclerView_viewapplications_recruiter);
        layoutManager = new LinearLayoutManager(getContext());
        viewapplicationsRecycler.setLayoutManager(layoutManager);
        tv = root.findViewById(R.id.text_viewapplications_recruiter);
        tv.setText(branch);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

//        FirebaseRecyclerOptions<ApplyJobStudent> options = new
//                FirebaseRecyclerOptions.Builder<ApplyJobStudent>()
//                .setQuery(branchRef, ApplyJobStudent.class)
//                .build();
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
                          Intent intent = new Intent(getContext(), RecruiterApplicationCardClickActivity.class);
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



