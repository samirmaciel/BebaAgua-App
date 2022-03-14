package com.example.bebagua.feature.presentation.modules.auth.loginscreen;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        startDelayedMotionAnim();

        mBinding.btnSignin.setOnClickListener((View v) -> {
            goToScreen("SignIn");
        });

        mBinding.btnForgotPassword.setOnClickListener((View v) -> {
            goToScreen("ForgotPassword");
        });
    }

    private void goToScreen(String screenName) {
        mBinding.constraintLayoutLoginScreen.transitionToStart();

        mBinding.constraintLayoutLoginScreen.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                if(screenName.equals("SignIn")){
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
                }else if(screenName.equals("ForgotPassword")){
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
                }else {
                    Toast.makeText(getContext(), "Screen not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
    }

    private void startDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutLoginScreen.transitionToEnd();
            }
        };

        handler.postDelayed(runnable, 50);
    }
}
