package com.application.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.R;
import com.application.activity.GameActivity;
import com.application.activity.MainActivity;
import com.application.databinding.FragmentMainMenuBinding;

public class MainMenuFragment extends Fragment {

    private FragmentMainMenuBinding binding;

    public String learningButton = "";
    public String gameButton = "";

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false);

        binding.setMainMenuFragment(this);
        // Fragment로 불러올 xml파일을 view로 가져옵니다.
        //View view = inflater.inflate(R.layout.fragment_main_menu, null);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}