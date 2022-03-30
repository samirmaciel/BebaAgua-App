package com.example.bebagua.feature.view.modules.registerwaterscreen;

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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

public class RegisterWaterViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public MutableLiveData<UserModel> currentUser = new MutableLiveData<>();
    public Integer currentDrink = 0;

    public RegisterWaterViewModel(FirebaseAuth mAuth, DatabaseReference mDatabase){
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
        updateUserAtUI();
    }

    private void updateUserAtUI() {
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
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(userDataListener);

    }

    public void updateUserData(){
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        UserModel user = dataSnapshot.getValue(UserModel.class);
                        Integer currentProgress = Integer.parseInt(user.getUserProgress());
                        Integer newProgress = currentProgress + currentDrink;
                        user.setUserProgress(newProgress.toString());
                        mDatabase.child("users").child(user.getUserUID()).setValue(user);
                    }
                });
            }
        });
    }

    static class RegisterWaterViewModelFactory implements ViewModelProvider.Factory{

        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase;

        public RegisterWaterViewModelFactory(FirebaseAuth mAuth, DatabaseReference mDatabase){
            this.mAuth = mAuth;
            this.mDatabase = mDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new RegisterWaterViewModel(mAuth, mDatabase);
        }
    }
}
