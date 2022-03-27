package com.example.bebagua.feature.view.modules.settingsscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.bebagua.R;
import com.example.bebagua.databinding.BottomsheetSettingsBinding;
import com.example.bebagua.feature.domain.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class SettingsBottomSheet extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {

    private BottomsheetSettingsBinding mBinding;
    private Boolean isEditingNickName = false;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference storageRef;
    private String selectedGoal =  "4000";
    private ArrayAdapter mSpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = BottomsheetSettingsBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinner();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        updateUserAtUI(mAuth.getCurrentUser().getUid());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();

        mBinding.cvImageUser.setOnClickListener((View v) -> {
            imageCapture();
        });

        mBinding.btnSaveGoal.setOnClickListener((View v) -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("Com a troca de meta, seu progresso será zerado!");
            alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    updateUserGoal(mAuth.getCurrentUser().getUid(), selectedGoal.replace("lt", ""));
                }
            });
            alertDialog.setNegativeButton("Sair", null);
            alertDialog.create().show();
        });

        mBinding.btnEditUserNickName.setOnClickListener((View v) -> {
            changeEditNickNameStatus();
        });


    }

    public void imageCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            try {
                Bitmap userImageProfileCaptured = (Bitmap) data.getExtras().get("data");
                mBinding.ivUserImageProfile.setImageBitmap(userImageProfileCaptured);

                uploadImageProfile(mAuth.getCurrentUser().getUid(), userImageProfileCaptured);

            } catch (Exception e) {

            }
        }
    }

    private void initSpinner() {
        mSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.water_goal, R.layout.item_text_spinner_default);
        mSpinnerAdapter.setDropDownViewResource(R.layout.item_text_spinner_default);
        mBinding.spnWaterGoal.setAdapter(mSpinnerAdapter);
        mBinding.spnWaterGoal.setOnItemSelectedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void changeEditNickNameStatus() {
        if (this.isEditingNickName) {
            String newNickName = mBinding.edtUserNickName.getText().toString();

            if (!newNickName.isEmpty()) {
                updateUserNickName(mAuth.getCurrentUser().getUid(), newNickName);
                mBinding.tvUserNickName.setText(newNickName);
                mBinding.edtUserNickName.setVisibility(View.INVISIBLE);
                mBinding.tvUserNickName.setVisibility(View.VISIBLE);
                mBinding.btnEditUserNickName.setImageResource(R.drawable.ic_baseline_edit_24);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mBinding.edtUserNickName.getWindowToken(), 0);
                isEditingNickName = false;
            }

        } else {
            mBinding.edtUserNickName.setVisibility(View.VISIBLE);
            mBinding.tvUserNickName.setVisibility(View.INVISIBLE);
            mBinding.btnEditUserNickName.setImageResource(R.drawable.ic_baseline_save_24);
            mBinding.edtUserNickName.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mBinding.edtUserNickName, InputMethodManager.SHOW_IMPLICIT);
            isEditingNickName = true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String waterGoalString = adapterView.getItemAtPosition(position).toString();
        Integer waterGoalInteger = Integer.parseInt(waterGoalString.replace("lt", "")) * 1000;
        this.selectedGoal = waterGoalString.toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateUserNickName(String currentUserUID, String newNickName) {

        mDatabase.child("users").child(currentUserUID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            userModel.setUserNickName(newNickName);
                            mDatabase.child("users").child(currentUserUID).setValue(userModel);
                        }
                    }
                });
            }
        });

    }

    private void updateUserGoal(String currentUserUID, String goal) {

        mDatabase.child("users").child(currentUserUID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            userModel.setUserGoal(goal);
                            userModel.setUserProgress("0");
                            mDatabase.child("users").child(currentUserUID).setValue(userModel);
                            dismiss();
                            Snackbar.make(getParentFragment().getView(), "Meta atualizada com sucesso!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void uploadImageProfile(String currentUserUID, Bitmap userImage) {
        StorageReference mountainsRef = storageRef.child(currentUserUID + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                });

                task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.child(taskSnapshot.getMetadata().getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                updateUserImageProfile(mAuth.getCurrentUser().getUid(), uri.toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                });
            }
        });

    }

    private void updateUserImageProfile(String currentUserUID, String userImageURL){
        mDatabase.child("users").child(currentUserUID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            userModel.setUserImageURL(userImageURL);
                            mDatabase.child("users").child(currentUserUID).setValue(userModel);
                        }
                    }
                });
            }
        });
    }

    private void updateUserAtUI(String currentUserUID){
        ValueEventListener userDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);

                View view = getView();
                if(user != null){
                    if(view != null){
                        Glide.with(getView()).load(user.getUserImageURL()).placeholder(R.drawable.defaultperson)
                                .into(mBinding.ivUserImageProfile);
                        mBinding.spnWaterGoal.setSelection(mSpinnerAdapter.getPosition(user.getUserGoal() + "lt"), true);
                    }

                    mBinding.tvUserNickName.setText(user.getUserNickName());
                    mBinding.edtUserNickName.setText(user.getUserNickName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        };
        mDatabase.child("users").child(currentUserUID).addValueEventListener(userDataListener);

    }
}
