package com.llopez.awallet.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llopez.awallet.R;

public class SplashFragment extends Fragment {
    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_splash, container, false);
        verifyLogin();
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void verifyLogin() {
        SharedPreferences preferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
        boolean isLogged = preferences.getBoolean("isLogged", false);

        if (isLogged) {
            NavHostFragment.findNavController(this).navigate(R.id.action_splashFragment_to_homeFragment);
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_splashFragment_to_loginFragment);
        }
    }
}