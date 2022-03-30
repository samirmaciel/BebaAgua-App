package com.example.bebagua.feature.view.modules.splashscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bebagua.feature.domain.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    public MutableLiveData<Boolean> currentUser = new MutableLiveData<>(false);

    public SplashViewModel(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
        checkCurrentUser();
    }

    public void checkCurrentUser() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            currentUser.setValue(true);
        } else {
            currentUser.setValue(false);
        }

    }


    static class SplashViewModelFactory implements ViewModelProvider.Factory {

        private FirebaseAuth mAuth;

        public SplashViewModelFactory(FirebaseAuth mAuth) {
            this.mAuth = mAuth;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SplashViewModel(mAuth);
        }
    }
}
