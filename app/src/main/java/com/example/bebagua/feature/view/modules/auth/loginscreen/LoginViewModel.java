package com.example.bebagua.feature.view.modules.auth.loginscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth mAuth;

    public LoginViewModel(FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }


    class LoginViewModelFactory implements ViewModelProvider.Factory{

        private FirebaseAuth mAuth;

        public LoginViewModelFactory(FirebaseAuth mAuth){
            this.mAuth = mAuth;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LoginViewModel(mAuth);
        }
    }


}
