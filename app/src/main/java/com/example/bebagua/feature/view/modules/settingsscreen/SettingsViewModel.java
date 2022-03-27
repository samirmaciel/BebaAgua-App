package com.example.bebagua.feature.view.modules.settingsscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bebagua.feature.view.modules.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SettingsViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public SettingsViewModel(DatabaseReference mDatabase, FirebaseAuth mAuth){
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    class SettingsViewModelFactory implements ViewModelProvider.Factory{

        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase;

        public SettingsViewModelFactory(DatabaseReference mDatabase, FirebaseAuth mAuth){
            this.mAuth = mAuth;
            this.mDatabase = mDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SettingsViewModel(mDatabase, mAuth);
        }
    }
}
