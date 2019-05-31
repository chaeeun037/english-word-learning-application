package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.R;
import com.application.database.EWLADbHelper;
import com.application.database.Unit;

import java.util.List;

public class LearningUnitFragment extends Fragment {
    List<Unit> unitList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_unit, container, false);
        unitList = EWLADbHelper.UnitList;

        return view;
    }
}