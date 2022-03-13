package com.example.bebagua.feature.presentation.modules.homescreen;

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

import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    FragmentHomeBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        startDelayedMotionAnim();

        mBinding.btnDrinkWater.setOnClickListener((View v) -> {
            goToScreen("RegisterWater");
        });

        mBinding.btnGoToGoal.setOnClickListener((View v) -> {
            goToScreen("Goals");
        });
    }

    private void startDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutHomeScreen.transitionToEnd();
            }
        };
        handler.postDelayed(runnable, 50);
    }

    private void goToScreen(String screenName) {
        mBinding.constraintLayoutHomeScreen.transitionToStart();

        mBinding.constraintLayoutHomeScreen.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {

                if (screenName.equals("Goals")) {
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_goalsFragment);
                } else if (screenName.equals("RegisterWater")) {
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_registerWaterFragment);
                }else{

                }

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
            }
        });
    }
}

