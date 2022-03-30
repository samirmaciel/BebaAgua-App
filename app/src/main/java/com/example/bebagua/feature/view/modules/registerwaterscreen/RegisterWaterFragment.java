package com.example.bebagua.feature.view.modules.registerwaterscreen;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentRegisterwaterBinding;
import com.example.bebagua.feature.data.FireBaseSource;
import com.example.bebagua.feature.domain.model.UserModel;
import com.example.bebagua.feature.view.modules.settingsscreen.SettingsBottomSheet;
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
    private RegisterWaterViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRegisterwaterBinding.bind(inflater.inflate(R.layout.fragment_registerwater, container, false));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mViewModel = new ViewModelProvider(this, new RegisterWaterViewModel.RegisterWaterViewModelFactory(
                FireBaseSource.getFirebaseAuth(), FireBaseSource.getDatabase()
        )).get(RegisterWaterViewModel.class);


    }

    @Override
    public void onResume() {
        super.onResume();
        starDelayedMotionAnim();

        initSpinner();

        mViewModel.currentUser.observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel.getUserImageURL() != null){
                    if(getView() != null){
                        Glide.with(getView()).load(userModel.getUserImageURL()).placeholder(R.drawable.defaultperson)
                                .into(mBinding.ivUserImageProfile);
                    }
                }
            }
        });

        mBinding.btnRegisterWater.setOnClickListener((View v) ->{
            mViewModel.updateUserData();
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
        mViewModel.currentDrink = Integer.parseInt(waterValueString.replace("ml", ""));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




}
