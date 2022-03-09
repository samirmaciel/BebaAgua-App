package com.example.bebagua.feature.presentation.modules.registerwaterscreen;

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
import com.example.bebagua.databinding.FragmentRegisterwaterBinding;

public class RegisterWaterFragment extends Fragment {

    private FragmentRegisterwaterBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRegisterwaterBinding.bind(inflater.inflate(R.layout.fragment_registerwater, container, false));
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        starDelayedMotionAnim();

        mBinding.btnGoBack.setOnClickListener((View v) -> {
            goToBack();
        });

    }

    private void goToBack() {
        mBinding.constraintLayoutRegisterWaterScreen.transitionToStart();

        mBinding.constraintLayoutRegisterWaterScreen.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) { }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) { }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                Navigation.findNavController(getView()).navigateUp();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) { }
        });
    }

    private void starDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutRegisterWaterScreen.transitionToEnd();
            }
        };
        handler.postDelayed(runnable, 50);
    }
}
