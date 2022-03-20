package com.example.bebagua.feature.presentation.modules.splashscreen;

import android.animation.Animator;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashFragment extends Fragment {

    private FragmentSplashBinding mBinding;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false));
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
        checkCurrentUser();
    }

    private void checkCurrentUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            mBinding.constraintLayoutSplashScreen.transitionToEnd(new Runnable() {
                @Override
                public void run() {
                    goToHomeFragment();
                }
            });

        }else{
            mBinding.constraintLayoutSplashScreen.transitionToEnd(new Runnable() {
                @Override
                public void run() {
                    goToLoginFragment();
                }
            });
        }

    }

    private void goToHomeFragment() {
        Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
    }

    private void goToLoginFragment(){
        Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
    }
}
