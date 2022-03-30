package com.example.bebagua.feature.view.modules.homescreen;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bebagua.feature.domain.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class HomeViewModel extends ViewModel {

    public FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public MutableLiveData<UserModel> currentUser = new MutableLiveData<>();

    public HomeViewModel(FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
        updateUserAtUI(mAuth.getCurrentUser().getUid());
    }


    private void updateUserAtUI(String currentUserUID) {
        ValueEventListener userDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                currentUser.postValue(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        };
        mDatabase.child("users").child(currentUserUID).addValueEventListener(userDataListener);

    }

    static class HomeViewModelFactory implements ViewModelProvider.Factory {

        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase;

        public HomeViewModelFactory(FirebaseAuth mAuth, DatabaseReference mDatabase) {
            this.mAuth = mAuth;
            this.mDatabase = mDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HomeViewModel(mAuth, mDatabase);
        }
    }
}
