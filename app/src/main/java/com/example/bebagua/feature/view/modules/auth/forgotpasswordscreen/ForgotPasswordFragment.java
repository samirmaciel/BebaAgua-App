package com.example.bebagua.feature.view.modules.auth.forgotpasswordscreen;

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

import com.example.bebagua.databinding.FragmentFotgotpasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    private FragmentFotgotpasswordBinding mBinding;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFotgotpasswordBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        startDelayedMotionAnim();

        mBinding.btnSendEmail.setOnClickListener((View v) ->{
            if(validateFields()){
                sendEmailForResetPassword(mBinding.edtEmail.getText().toString());
            }else{
                Snackbar.make(getView(), "É preciso inserir um email válido!", Snackbar.LENGTH_SHORT).show();
            }
        });

        mBinding.btnGoBack.setOnClickListener((View v) -> {
            goToBack();
        });
    }

    private boolean validateFields(){
        return !mBinding.edtEmail.getText().toString().isEmpty();
    }

    private void sendEmailForResetPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        task.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                goToBack();
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void startDelayedMotionAnim(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutForgotPassowordScreen.transitionToEnd();
            }
        };
        handler.postDelayed(runnable, 50);
    }

    private void goToBack(){
        mBinding.constraintLayoutForgotPassowordScreen.transitionToStart();

        mBinding.constraintLayoutForgotPassowordScreen.addTransitionListener(new MotionLayout.TransitionListener() {
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
