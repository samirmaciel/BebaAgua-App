package com.example.bebagua.feature.data;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireBaseSource extends Application {

    public static FirebaseAuth mAuth;
    public static DatabaseReference mDatabase;
    public static StorageReference mStore;

    @Override
    public void onCreate() {
        super.onCreate();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStore = FirebaseStorage.getInstance().getReference();
    }

    public static FirebaseAuth getFirebaseAuth(){
        return mAuth;
    }

    public static DatabaseReference getDatabase(){
        return mDatabase;
    }

    public static StorageReference getStoreReference(){
        return mStore;
    }
}
