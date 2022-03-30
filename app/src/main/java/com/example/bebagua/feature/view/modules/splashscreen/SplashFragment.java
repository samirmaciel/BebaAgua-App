package com.example.bebagua.feature.view.modules.splashscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.bebagua.R;
import com.example.bebagua.databinding.FragmentSplashBinding;
import com.example.bebagua.feature.constants.NavigationEnum;
import com.example.bebagua.feature.data.FireBaseSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashFragment extends Fragment {

    private FragmentSplashBinding mBinding;
    private SplashViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       mViewModel = new ViewModelProvider(this, new SplashViewModel.SplashViewModelFactory(
               FireBaseSource.getFirebaseAuth()
       )).get(SplashViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.currentUser.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    goToHomeFragment();
                }else{
                    goToLoginFragment();
                }
            }
        });
    }


    private void checkFragment(NavigationEnum screenName){
        switch (screenName){

            case LOGIN_SCREEN: {
                goToLoginFragment();
                return;
            }

            case HOME_SCREEN: {
                goToHomeFragment();
                return;
            }
        }
    }

    private void goToHomeFragment() {
        Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_homeFragment);
    }

    private void goToLoginFragment(){
        Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
    }
}
