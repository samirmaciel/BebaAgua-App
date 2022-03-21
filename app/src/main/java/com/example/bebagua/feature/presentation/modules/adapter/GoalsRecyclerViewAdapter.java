package com.example.bebagua.feature.presentation.modules.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bebagua.R;
import com.example.bebagua.feature.domain.model.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GoalsRecyclerViewAdapter extends RecyclerView.Adapter<GoalsRecyclerViewAdapter.MyViewHolder> {

    private List<UserModel> itemList = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_usergoal, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView userImageProfile;
        private ProgressBar userCurrentProgress;
        private TextView userGoal;

        public MyViewHolder(View itemView) {
            super(itemView);

            userImageProfile = itemView.findViewById(R.id.ivUserImageProfile);
            userGoal = itemView.findViewById(R.id.tvTotalGoalValue);
            userCurrentProgress = itemView.findViewById(R.id.pbUserCurrentProgress);
        }

        public void bindItem(UserModel user) {
            Glide.with(itemView).load(user.getUserImageURL()).placeholder(R.drawable.defaultperson)
                    .into(userImageProfile);
            userGoal.setText(Integer.parseInt(user.getUserGoal()) / 1000 + "lts");
        }
    }

    public void setItemList(List<UserModel> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }
}
