package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.EWLApplication;
import com.application.R;
import com.application.activity.MainActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Unit;

import java.util.List;

public class LearningUnitFragment extends Fragment {
    List<Unit> unitList;
    Button num1;
    Button num2;
    Button num3;

    EWLApplication application = EWLApplication.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_unit, container, false);
        unitList = EWLADbHelper.UnitList;

        num1 = (Button) view.findViewById(R.id.num1);
        num2 = (Button) view.findViewById(R.id.num2);
        num3 = (Button) view.findViewById(R.id.num3);


        //정적 바인딩 - BASKET
        if(application.getNowThemeId() == 0) {
            if (unitList.get(0).getHasCrown() == true)
                num1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
            else
                num1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));

            if (unitList.get(1).getHasCrown() == true)
                num2.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
            else
                num2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));

            if (unitList.get(2).getHasCrown() == true)
                num3.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
            else
                num3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));
        }
        else {
            if (unitList.get(3).getHasCrown() == true)
                num1.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
            else
                num1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));

            if (unitList.get(4).getHasCrown() == true)
                num2.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
            else
                num2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));

            if (unitList.get(5).getHasCrown() == true)
                num3.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.crown_basket));
            else
                num3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.basket));
        }


        num1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(application.getNowThemeId() == 0){
                    application.getUnitList().get(0).setHasCrown(true);
                    application.setNowWordId(0);
                    ((MainActivity) getActivity()).onUnitButtonClick(v, unitList.get(0).getId());
                }
                if(application.getNowThemeId() == 1){
                    application.getUnitList().get(3).setHasCrown(true);
                    application.setNowWordId(9);
                    ((MainActivity) getActivity()).onUnitButtonClick(v, unitList.get(3).getId());
                }
            }
        });
        num2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(application.getNowThemeId() == 0){
                    application.getUnitList().get(1).setHasCrown(true);
                    application.setNowWordId(3);
                    ((MainActivity) getActivity()).onUnitButtonClick(v, unitList.get(1).getId());

                }
                if(application.getNowThemeId() == 1){
                    application.getUnitList().get(4).setHasCrown(true);
                    application.setNowWordId(12);
                    ((MainActivity) getActivity()).onUnitButtonClick(v, unitList.get(4).getId());
                }
            }
        });
        num3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(application.getNowThemeId() == 0){
                    application.getUnitList().get(2).setHasCrown(true);
                    application.setNowWordId(6);
                    ((MainActivity) getActivity()).onUnitButtonClick(v, unitList.get(2).getId());
                }
                if(application.getNowThemeId() == 1){
                    application.getUnitList().get(5).setHasCrown(true);
                    application.setNowWordId(15);
                    ((MainActivity) getActivity()).onUnitButtonClick(v, unitList.get(5).getId());
                }
            }
        });

        return view;
    }
}