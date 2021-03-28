package com.example.rem.ui_recruiter.viewapplicationsbranches_recruiter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rem.Model.RecruiterViewApplicationStart;
import com.example.rem.R;
import com.example.rem.ViewHolders.RecruiterViewBranchesViewHolder;
import com.example.rem.ui_recruiter.RecruiterApplication;
import com.example.rem.ui_recruiter.RecruiterApplicationCardClickActivity;
import com.example.rem.ui_recruiter.RecruiterViewApplications;
import com.example.rem.ui_recruiter.recruiter_viewapplications.RecruiterViewApplicationsFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewapplicationsBranchesFragmentRecruiter extends Fragment {

    private RecyclerView applicationsRecycler;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef,userRef,userIdRef,appliedJobsRef;
    String userid,branch;
    private ViewapplicationsBranchesViewModelRecruiter viewapplicationsBranchesViewModelRecruiter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewapplicationsBranchesViewModelRecruiter =
                ViewModelProviders.of(this).get(ViewapplicationsBranchesViewModelRecruiter.class);
        View root = inflater.inflate(R.layout.fragment_viewbranches_recruiter, container, false);

        applicationsRecycler = root.findViewById(R.id.recyclerView_viewbranches_recruiter);
        layoutManager =new LinearLayoutManager(getContext());
        applicationsRecycler.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        userRef = rootRef.child("Job Applications");
       // userIdRef = userRef.child("KIuCq2YHdSgo353RAwZ4wR91Jpd2");
       // appliedJobsRef = userIdRef.child("Applied Applications");
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        final FirebaseRecyclerOptions<RecruiterViewApplicationStart> options = new FirebaseRecyclerOptions.Builder<RecruiterViewApplicationStart>()
                .setQuery(userRef, new SnapshotParser<RecruiterViewApplicationStart>() {
                    @NonNull
                    @Override
                    public RecruiterViewApplicationStart parseSnapshot(@NonNull DataSnapshot snapshot) {
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot root : dataSnapshot.getChildren()){
                                    for(DataSnapshot user : root.getChildren()){
                                        for(DataSnapshot job : user.child("Job Applications").getChildren()){
                                            branch=job.getKey().toString();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        return new RecruiterViewApplicationStart(snapshot.getKey().toString());
                    }
                })
                .build();

        final FirebaseRecyclerAdapter<RecruiterViewApplicationStart, RecruiterViewBranchesViewHolder> adapter = new FirebaseRecyclerAdapter<RecruiterViewApplicationStart, RecruiterViewBranchesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecruiterViewBranchesViewHolder recruiterViewApplicationViewHolder, final int i, @NonNull final RecruiterViewApplicationStart viewApplicationsStudent) {
                recruiterViewApplicationViewHolder.branch.setText(viewApplicationsStudent.getBranch());
                //recruiterViewApplicationViewHolder.jobPost.setText(viewApplicationsStudent.getJobpost());
//                recruiterViewApplicationViewHolder.companyName.setText("Company Name : "+viewApplicationsStudent.getCompanyname());
//                recruiterViewApplicationViewHolder.companyDescription.setText("Company Description : "+viewApplicationsStudent.getCompanydescription());
//                recruiterViewApplicationViewHolder.workingtype.setText("Working Type : "+viewApplicationsStudent.getWorkingtype());
//                recruiterViewApplicationViewHolder.status.setText("Status : "+viewApplicationsStudent.getStatus());

                recruiterViewApplicationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), RecruiterApplication.class);

                        intent.putExtra("branch",viewApplicationsStudent.getBranch());
//                        intent.putExtra("company name",viewApplicationsStudent.getCompanyname());
//                        intent.putExtra("company description",viewApplicationsStudent.getCompanydescription());
//                        intent.putExtra("working type",viewApplicationsStudent.getWorkingtype());
//                        intent.putExtra("status",viewApplicationsStudent.getStatus());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public RecruiterViewBranchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recruiter_viewbranches,parent,false);
                RecruiterViewBranchesViewHolder holder = new RecruiterViewBranchesViewHolder(view);
                return  holder;
            }
        };
        applicationsRecycler.setAdapter(adapter);
        adapter.startListening();
    }
}
