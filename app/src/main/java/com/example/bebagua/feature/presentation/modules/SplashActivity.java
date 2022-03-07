package com.example.bebagua.feature.presentation.modules;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.bebagua.R;
import com.example.bebagua.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding mDataBinging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinging = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(mDataBinging.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDataBinging.loadLottieAnim.playAnimation();

        mDataBinging.loadLottieAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                goToMainActivity();
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}