package com.example.bebagua.feature.view.modules.goalsscreen;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.bebagua.R;
import com.example.bebagua.feature.domain.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GoalsViewModel extends ViewModel {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public MutableLiveData<UserModel> currentUserData = new MutableLiveData();
    public MutableLiveData<List<UserModel>> usersGoalList = new MutableLiveData();

    public GoalsViewModel(FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
        updateUserAtUI(mAuth.getCurrentUser().getUid());
        loadItemList();
    }

    private void loadItemList() {
        mDatabase.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        List<UserModel> userList = new ArrayList<>();
                        for (DataSnapshot object : dataSnapshot.getChildren()) {
                            UserModel user = object.getValue(UserModel.class);
                            userList.add(user);
                        }

                        usersGoalList.postValue(userList);
                    }
                });
            }
        });

    }

    private void updateUserAtUI(String currentUserUID) {
        ValueEventListener userDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                if (user != null) {
                    currentUserData.postValue(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child("users").child(currentUserUID).addValueEventListener(userDataListener);
    }

    public static class GoalsViewModelFactory implements ViewModelProvider.Factory {

        private DatabaseReference mDatabase;
        private FirebaseAuth mAuth;

        public GoalsViewModelFactory(FirebaseAuth mAuth, DatabaseReference mDatabase) {
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
