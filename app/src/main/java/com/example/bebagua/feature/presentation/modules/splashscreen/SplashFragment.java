package com.example.bebagua.feature.presentation.modules.splashscreen;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentSplashBinding;
import com.example.bebagua.feature.presentation.modules.MainActivity;

public class SplashFragment extends Fragment {

    private FragmentSplashBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false));
        return mBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();

        mBinding.loadLottieAnim.playAnimation();



        mBinding.loadLottieAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                mBinding.constraintLayoutSplashScreen.transitionToEnd(new Runnable() {
                    @Override
                    public void run() {
                        goToMainActivity();
                    }
                });

            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
    }

    private void goToMainActivity() {
        Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_homeFragment);
    }
}
