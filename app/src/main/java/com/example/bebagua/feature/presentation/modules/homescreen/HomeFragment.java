package com.example.bebagua.feature.presentation.modules.homescreen;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bebagua.databinding.FragmentHomeBinding;

import java.util.Timer;

public class HomeFragment extends Fragment {

    FragmentHomeBinding mDataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        starDelayedMotionAnim();

    }


    private void starDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDataBinding.constraintLayoutHomeScreen.transitionToEnd();
            }
        };
        handler.postDelayed(runnable, 50);
    }
}
