package com.example.bebagua.feature.view.modules.auth.forgotpasswordscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends ViewModel {

    private FirebaseAuth mAuth;

    public ForgotPasswordViewModel(FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }

    class ForgotPasswordViewModelFactory implements ViewModelProvider.Factory{

        private FirebaseAuth mAuth;

        public ForgotPasswordViewModelFactory(FirebaseAuth mAuth){
            this.mAuth = mAuth;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ForgotPasswordViewModel(mAuth);
        }
    }
}
