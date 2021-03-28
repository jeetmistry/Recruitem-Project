package com.example.rem.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rem.R;

public class RecruiterViewBranchesViewHolder extends RecyclerView.ViewHolder {
    public TextView branch;
    public View itemView;
    public RecruiterViewBranchesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView=itemView;
        branch = itemView.findViewById(R.id.recruiter_viewbranches_card_branches);
    }
}
