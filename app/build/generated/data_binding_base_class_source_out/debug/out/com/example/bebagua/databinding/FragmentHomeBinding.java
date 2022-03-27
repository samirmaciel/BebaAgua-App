// Generated by view binder compiler. Do not edit!
package com.example.bebagua.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.bebagua.R;
import com.google.android.material.button.MaterialButton;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final MotionLayout rootView;

  @NonNull
  public final ImageView bottomGeometry;

  @NonNull
  public final MaterialButton btnDrinkWater;

  @NonNull
  public final AppCompatButton btnGoToGoal;

  @NonNull
  public final ImageButton btnLogout;

  @NonNull
  public final ImageButton btnSettings;

  @NonNull
  public final MotionLayout constraintLayoutHomeScreen;

  @NonNull
  public final ImageView firstTopGeometry;

  @NonNull
  public final CircleImageView ivUserImageProfile;

  @NonNull
  public final ImageView secondTopGeometry;

  private FragmentHomeBinding(@NonNull MotionLayout rootView, @NonNull ImageView bottomGeometry,
      @NonNull MaterialButton btnDrinkWater, @NonNull AppCompatButton btnGoToGoal,
      @NonNull ImageButton btnLogout, @NonNull ImageButton btnSettings,
      @NonNull MotionLayout constraintLayoutHomeScreen, @NonNull ImageView firstTopGeometry,
      @NonNull CircleImageView ivUserImageProfile, @NonNull ImageView secondTopGeometry) {
    this.rootView = rootView;
    this.bottomGeometry = bottomGeometry;
    this.btnDrinkWater = btnDrinkWater;
    this.btnGoToGoal = btnGoToGoal;
    this.btnLogout = btnLogout;
    this.btnSettings = btnSettings;
    this.constraintLayoutHomeScreen = constraintLayoutHomeScreen;
    this.firstTopGeometry = firstTopGeometry;
    this.ivUserImageProfile = ivUserImageProfile;
    this.secondTopGeometry = secondTopGeometry;
  }

  @Override
  @NonNull
  public MotionLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomGeometry;
      ImageView bottomGeometry = ViewBindings.findChildViewById(rootView, id);
      if (bottomGeometry == null) {
        break missingId;
      }

      id = R.id.btnDrinkWater;
      MaterialButton btnDrinkWater = ViewBindings.findChildViewById(rootView, id);
      if (btnDrinkWater == null) {
        break missingId;
      }

      id = R.id.btnGoToGoal;
      AppCompatButton btnGoToGoal = ViewBindings.findChildViewById(rootView, id);
      if (btnGoToGoal == null) {
        break missingId;
      }

      id = R.id.btnLogout;
      ImageButton btnLogout = ViewBindings.findChildViewById(rootView, id);
      if (btnLogout == null) {
        break missingId;
      }

      id = R.id.btnSettings;
      ImageButton btnSettings = ViewBindings.findChildViewById(rootView, id);
      if (btnSettings == null) {
        break missingId;
      }

      MotionLayout constraintLayoutHomeScreen = (MotionLayout) rootView;

      id = R.id.firstTopGeometry;
      ImageView firstTopGeometry = ViewBindings.findChildViewById(rootView, id);
      if (firstTopGeometry == null) {
        break missingId;
      }

      id = R.id.ivUserImageProfile;
      CircleImageView ivUserImageProfile = ViewBindings.findChildViewById(rootView, id);
      if (ivUserImageProfile == null) {
        break missingId;
      }

      id = R.id.secondTopGeometry;
      ImageView secondTopGeometry = ViewBindings.findChildViewById(rootView, id);
      if (secondTopGeometry == null) {
        break missingId;
      }

      return new FragmentHomeBinding((MotionLayout) rootView, bottomGeometry, btnDrinkWater,
          btnGoToGoal, btnLogout, btnSettings, constraintLayoutHomeScreen, firstTopGeometry,
          ivUserImageProfile, secondTopGeometry);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}