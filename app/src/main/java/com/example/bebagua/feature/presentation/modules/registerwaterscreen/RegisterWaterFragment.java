package com.example.bebagua.feature.presentation.modules.registerwaterscreen;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentRegisterwaterBinding;
import com.example.bebagua.feature.domain.model.UserModel;
import com.example.bebagua.feature.presentation.modules.settingsscreen.SettingsBottomSheet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterWaterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentRegisterwaterBinding mBinding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Integer currentDrink = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRegisterwaterBinding.bind(inflater.inflate(R.layout.fragment_registerwater, container, false));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        updateUserAtUI(mAuth.getCurrentUser().getUid());
    }

    @Override
    public void onResume() {
        super.onResume();
        starDelayedMotionAnim();

        initSpinner();

        mBinding.btnRegisterWater.setOnClickListener((View v) ->{
            updateUserData(mAuth.getCurrentUser().getUid());
            v.setEnabled(false);
        });

        mBinding.btnSettings.setOnClickListener((View v) -> {
            SettingsBottomSheet settingsBottomSheet = new SettingsBottomSheet();
            settingsBottomSheet.show(getChildFragmentManager(), "SettingsBottomSheet");
        } );

        mBinding.btnGoBack.setOnClickListener((View v) -> {
            goToBack();
        });

    }

    private void initSpinner() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.water_quantity, R.layout.item_text_spinner_default);
        adapter.setDropDownViewResource(R.layout.item_text_spinner_default);
        mBinding.spnWaterQuantity.setAdapter(adapter);
        mBinding.spnWaterQuantity.setOnItemSelectedListener(this);
    }

    private void goToBack() {
        mBinding.constraintLayoutRegisterWaterScreen.transitionToStart();

        mBinding.constraintLayoutRegisterWaterScreen.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                Navigation.findNavController(getView()).navigateUp();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
            }
        });
    }

    private void starDelayedMotionAnim() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.constraintLayoutRegisterWaterScreen.transitionToEnd();
            }
        };
        handler.postDelayed(runnable, 50);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String waterValueString = adapterView.getItemAtPosition(position).toString();
        this.currentDrink = Integer.parseInt(waterValueString.replace("ml", ""));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateUserAtUI(String currentUserUID) {
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

    private void updateUserData(String currentUserUID){
        mDatabase.child("users").child(currentUserUID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
}
