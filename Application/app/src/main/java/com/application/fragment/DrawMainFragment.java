package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.R;

public class DrawMainFragment extends Fragment {

    public static DrawMainFragment newInstance() {
        return new DrawMainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_draw_main, container, false);
    }
}