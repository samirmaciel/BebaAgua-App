package com.example.bebagua.feature.view.modules.goalsscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class GoalsViewModel extends ViewModel {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public GoalsViewModel(FirebaseAuth mAuth, DatabaseReference mDatabase){
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    class GoalsViewModelFactory implements ViewModelProvider.Factory{

        private DatabaseReference mDatabase;
        private FirebaseAuth mAuth;

        public GoalsViewModelFactory(FirebaseAuth mAuth, DatabaseReference mDatabase){
            this.mAuth = mAuth;
            this.mDatabase = mDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new GoalsViewModel(mAuth, mDatabase);
        }
    }
}
