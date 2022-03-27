package com.example.bebagua.feature.view.modules.auth.signupscreen;

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

import com.example.bebagua.databinding.FragmentSignupBinding;
import com.example.bebagua.feature.domain.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment {

    private FragmentSignupBinding mBinding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSignupBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(requireContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        startDelayedMotionAnim();

        mBinding.btnSignup.setOnClickListener((View v) -> {
            signUp();
        });

        mBinding.btnArrowBack.setOnClickListener((View v) -> {
            goToBack();
        });
    }

    public void goToBack() {
        mBinding.constraintLayoutSignupScreen.transitionToStart();
        mBinding.constraintLayoutSignupScreen.addTransitionListener(new MotionLayout.TransitionListener() {
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

    private void signUp() {
        if (allfieldsValidation()) {
            String email = mBinding.edtEmail.getText().toString();
            String password = mBinding.edtPassword.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(getContext(), "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = authResult.getUser();
                                    createUserDate(user.getUid(), mBinding.edtNickname.getText().toString());
                                    showSnackbar("Cadastro efetuado com sucesso.");
                                }
                            });

                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showSnackbar("Não foi possivel concluir seu cadastro. " + e);
                                }
                            });
                        }
                    });
        }else{
            Snackbar.make(getView(), "É preciso preencher todos os campos!", Snackbar.LENGTH_SHORT ).show();
        }
    }

    private void createUserDate(String userUID, String nickName) {
        UserModel user = new UserModel(userUID, nickName);
        mDatabase.child("users").child(userUID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(getView()).navigateUp();
                    }
                });
            }
        });
    }

    private void showSnackbar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private Boolean allfieldsValidation() {
        String nickname = mBinding.edtNickname.getText().toString();
        String email = mBinding.edtEmail.getText().toString();
        String password = mBinding.edtPassword.getText().toString();
        return !nickname.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    public void startDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutSignupScreen.transitionToEnd();
            }
        };

        handler.postDelayed(runnable, 50);
    }
}
