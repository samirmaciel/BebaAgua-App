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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding mBinding;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(inflater);
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

        mBinding.btnSignin.setOnClickListener((View v) -> {
            signIn();
        });

        mBinding.btnSignUp.setOnClickListener((View v) -> {
            goToScreen("SignUp");
            mBinding.btnSignUp.setEnabled(false);
        });

        mBinding.btnForgotPassword.setOnClickListener((View v) -> {
            goToScreen("ForgotPassword");
            mBinding.btnForgotPassword.setEnabled(false);
        });
    }

    private void signInFirebaseAuth(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                goToScreen("HomeScreen");
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showSnackbar("Não foi possivel entrar. " + e.getMessage());
                                mBinding.btnSignin.setEnabled(true);
                            }
                        });
                    }
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
                if(screenName.equals("HomeScreen")){
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
                }else if(screenName.equals("ForgotPassword")){
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
                }else if(screenName.equals("SignUp")){
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_signUpFragment);
                }else {
                    Toast.makeText(getContext(), "Screen not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
    }

    private void signIn(){
        String email = mBinding.edtEmail.getText().toString();
        String password = mBinding.edtPassword.getText().toString();
        mBinding.btnSignin.setEnabled(false);

        if(allFieldsValidation()){
            signInFirebaseAuth(email, password);
        }else{
            showSnackbar("Preencha todos os campos!");
            mBinding.btnSignin.setEnabled(true);
        }
    }

    private Boolean allFieldsValidation(){
        String email = mBinding.edtEmail.getText().toString();
        String password = mBinding.edtPassword.getText().toString();

        return !email.isEmpty() && !password.isEmpty();
    }

    private void showSnackbar(String message){
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
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
