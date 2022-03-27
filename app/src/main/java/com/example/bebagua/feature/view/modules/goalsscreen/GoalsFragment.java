package com.example.bebagua.feature.view.modules.goalsscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentGoalsBinding;
import com.example.bebagua.feature.domain.model.UserModel;
import com.example.bebagua.feature.view.modules.adapter.GoalsRecyclerViewAdapter;
import com.example.bebagua.feature.view.modules.settingsscreen.SettingsBottomSheet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GoalsFragment extends Fragment {

    private FragmentGoalsBinding mBinding;
    private GoalsRecyclerViewAdapter mRecyclerAdapter;
    private GoalsViewModel mViewModel;


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
    }

    @Override
    public void onResume() {
        super.onResume();
        startDelayedMotionAnim();

        mBinding.btnSettings.setOnClickListener((View v) -> {
            SettingsBottomSheet settingsBottomSheet = new SettingsBottomSheet();
            settingsBottomSheet.show(getChildFragmentManager(), "SettingsBottomSheet");
        });

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




}


