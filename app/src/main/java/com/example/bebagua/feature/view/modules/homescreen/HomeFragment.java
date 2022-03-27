package com.example.bebagua.feature.view.modules.homescreen;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentHomeBinding;
import com.example.bebagua.feature.view.modules.settingsscreen.SettingsBottomSheet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onResume() {
        super.onResume();
        startDelayedMotionAnim();
        listenerCurrentUserData();

        mBinding.btnDrinkWater.setOnClickListener((View v) -> {
            goToScreen("RegisterWater");
        });

        mBinding.btnLogout.setOnClickListener((View v) -> {
            logout();
        });

        mBinding.btnGoToGoal.setOnClickListener((View v) -> {
            goToScreen("Goals");
        });

        mBinding.btnSettings.setOnClickListener((View v) -> {
            SettingsBottomSheet setttingsBottomSheet = new SettingsBottomSheet();
            setttingsBottomSheet.show(getChildFragmentManager(), "SettingsBottomSheet");
        });
    }

    private void logout() {
        mAuth.signOut();
        goToScreen("LoginScreen");
    }

    private void startDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutHomeScreen.transitionToEnd();
            }
        };
        handler.postDelayed(runnable, 50);
    }

    private void goToScreen(String screenName) {
        mBinding.constraintLayoutHomeScreen.transitionToStart();

        mBinding.constraintLayoutHomeScreen.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {

                if (screenName.equals("Goals")) {
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_goalsFragment);
                } else if (screenName.equals("RegisterWater")) {
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_registerWaterFragment);
                } else if (screenName.equals("LoginScreen")) {
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_loginFragment);
                }else{

                }

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
            }
        });
    }

    private void updateUserAtUI(String currentUserUID){
        ValueEventListener userDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageURL = dataSnapshot.getValue(String.class);

                View view = getView();
                if(imageURL != null){
                    if(view != null){
                        Glide.with(getView()).load(imageURL).placeholder(R.drawable.defaultperson)
                                .into(mBinding.ivUserImageProfile);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        };
        mDatabase.child("users").child(currentUserUID).child("userImageURL").addValueEventListener(userDataListener);

    }

    private void listenerCurrentUserData(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUserAtUI(currentUser.getUid());
        }
    }
}

