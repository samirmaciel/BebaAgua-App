package com.example.bebagua.feature.presentation.modules.goalsscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentGoalsBinding;
import com.example.bebagua.feature.domain.model.UserModel;
import com.example.bebagua.feature.presentation.modules.adapter.GoalsRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoalsFragment extends Fragment {

    private FragmentGoalsBinding mBinding;
    private GoalsRecyclerViewAdapter mRecyclerAdapter;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGoalsBinding.bind(inflater.inflate(R.layout.fragment_goals, container, false));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onResume() {
        super.onResume();
        startDelayedMotionAnim();
        loadItemList();

        mBinding.btnGoBack.setOnClickListener((View v) -> {
            goToBack();
        });

    }

    private void initRecyclerView() {
        mRecyclerAdapter = new GoalsRecyclerViewAdapter();
        mBinding.rvGoals.setHasFixedSize(true);
        mBinding.rvGoals.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.rvGoals.setAdapter(mRecyclerAdapter);
    }

    private void startDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutGoalsScreen.transitionToEnd();
            }
        };
        handler.postDelayed(runnable, 50);
    }

    private void goToBack() {
        mBinding.constraintLayoutGoalsScreen.transitionToStart();

        mBinding.constraintLayoutGoalsScreen.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                Navigation.findNavController(getView()).navigateUp();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
            }
        });
    }

    private void loadItemList() {
        mDatabase.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        List<UserModel> userList = new ArrayList<>();
                        for (DataSnapshot object : dataSnapshot.getChildren()) {
                            UserModel user = object.getValue(UserModel.class);
                            userList.add(user);
                        }
                        Log.d("FireBaseTEST", "onSuccess: " + userList.get(0).getUserNickName());
                        mRecyclerAdapter.setItemList(userList);
                        mRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
}


