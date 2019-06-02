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

public class LearningUnitFragmentVeget extends Fragment {
    List<Unit> unitList;
    Button num1;
    Button num2;
    Button num3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_unit_veget, container, false);
        unitList = EWLADbHelper.UnitList;

        num1 = (Button) view.findViewById(R.id.num1);
        num2 = (Button) view.findViewById(R.id.num2);
        num3 = (Button) view.findViewById(R.id.num3);

        if(unitList.get(3).getHasCrown()==true)
            num1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
        else
            num1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));

        if(unitList.get(4).getHasCrown()==true)
            num2.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
        else
            num2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));

        if(unitList.get(5).getHasCrown()==true)
            num3.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
        else
            num3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));

        return view;
    }
}