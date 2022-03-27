package com.example.bebagua.feature.view.modules.auth.signupscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpViewModel extends ViewModel {

    private FirebaseAuth mAuth;

    public SignUpViewModel(FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }

    class SignUpViewModelFactory implements ViewModelProvider.Factory{

        private FirebaseAuth mAuth;

        public SignUpViewModelFactory(FirebaseAuth mAuth){
            this.mAuth = mAuth;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SignUpViewModel(mAuth);
        }
    }
}
