package com.example.rem.ui_student.jobs_student;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rem.Model.ViewJobsRecruiter;
import com.example.rem.Model.ViewJobsStudent;
import com.example.rem.R;
import com.example.rem.ViewHolders.StudentViewJobViewHolder;
import com.example.rem.ui_student.StudentJobCardClickActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.UserWriteRecord;

import java.util.Iterator;
import java.util.Objects;


public class JobsFragmentStudent extends Fragment {
    private RecyclerView viewjobRecycler;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef,userRef,fieldRef;
    private JobsViewModelStudent jobsViewModelStudent;
    private String cn,cl,jp,wt,uid,field;
    StudentViewJobViewHolder studentViewJobViewHolder ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jobsViewModelStudent =
                ViewModelProviders.of(this).get(JobsViewModelStudent.class);
        View root = inflater.inflate(R.layout.fragment_jobs_student, container, false);
        final TextView textView = root.findViewById(R.id.text_jobs_student);
        viewjobRecycler = root.findViewById(R.id.recyclerView_jobs_student);
        layoutManager =new LinearLayoutManager(getContext());
        viewjobRecycler.setLayoutManager(layoutManager);
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        fieldRef = rootRef.child("student").child(uid).child("profile").child("fields");
        //by entering manually userid
        userRef = rootRef.child("recruiter").child("32S2yattjRgpx2qraHstF5fQVH92").child("Jobs");

        fieldRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                field = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ViewJobsStudent> options = new FirebaseRecyclerOptions.Builder<ViewJobsStudent>()
                .setQuery(userRef, new SnapshotParser<ViewJobsStudent>() {
                    @NonNull
                    @Override
                    public ViewJobsStudent parseSnapshot(@NonNull DataSnapshot snapshot) {

                        rootRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot root : dataSnapshot.getChildren()){
                                    for(DataSnapshot user : root.getChildren()){
                                        for(DataSnapshot job : user.child("Jobs").getChildren()){

                                            // can be used later
                                            cn = job.child("companyname").getValue().toString();
                                            cl=job.child("companydescription").getValue().toString();
                                            jp = job.child("jobpost").getValue().toString();
                                            wt= job.child("workingtype").getValue().toString();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                            return new ViewJobsStudent(snapshot.child("companyname").getValue().toString(),
                                    snapshot.child("companydescription").getValue().toString(),
                                    snapshot.child("jobpost").getValue().toString(),
                                    snapshot.child("workingtype").getValue().toString());


                    }
                })
                .build();

        FirebaseRecyclerAdapter<ViewJobsStudent, StudentViewJobViewHolder> adapter = new FirebaseRecyclerAdapter<ViewJobsStudent, StudentViewJobViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StudentViewJobViewHolder studentViewJobViewHolder, int i, @NonNull final ViewJobsStudent viewJobsStudent) {
                if (viewJobsStudent.getWorkingtype().equals(field)) {
                    studentViewJobViewHolder.companyName.setText("Company Name : " + viewJobsStudent.getCompanyname());
                    studentViewJobViewHolder.jobPost.setText(viewJobsStudent.getJobpost());
                    studentViewJobViewHolder.companyDescription.setText("Company Description : " + viewJobsStudent.getCompanydescription());
                    studentViewJobViewHolder.workingtype.setText("Field : " + viewJobsStudent.getWorkingtype());

                    studentViewJobViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), StudentJobCardClickActivity.class);
                            intent.putExtra("job post", viewJobsStudent.getJobpost());
                            intent.putExtra("company name", viewJobsStudent.getCompanyname());
                            intent.putExtra("company description", viewJobsStudent.getCompanydescription());
                            intent.putExtra("working type", viewJobsStudent.getWorkingtype());
                            startActivity(intent);

                        }
                    });
                }
                else{
                    studentViewJobViewHolder.companyName.setText("Company Name : Hidden");
                    studentViewJobViewHolder.jobPost.setText("Hidden (Cannot Apply)");
                    studentViewJobViewHolder.companyDescription.setText("Company Description : Hidden ");
                    studentViewJobViewHolder.workingtype.setText("Field : Hidden");

                    studentViewJobViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(),"Cannot See or Apply job other than " + field +" field",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @NonNull
            @Override
            public StudentViewJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_student_viewjobs,parent,false);
                StudentViewJobViewHolder holder = new StudentViewJobViewHolder(view);
                return holder;
            }
        };
        viewjobRecycler.setAdapter(adapter);
        adapter.startListening();
    }
}