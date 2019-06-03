package com.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.application.EWLApplication;
import com.application.R;
import com.application.activity.MainActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Point;
import com.application.database.Theme;

import java.util.List;

public class LearningThemeFragment extends Fragment {

    Button fruit;
    Button veget;
    Button fish;
    Button meat;
    Button dairy;
    Button snack;

    Point point;
    EWLApplication application = EWLApplication.getInstance();

    public static LearningThemeFragment newInstance() {
        return new LearningThemeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_theme, container, false);
        point = EWLADbHelper.point;

        fruit = (Button) view.findViewById(R.id.fruit);
        veget = (Button) view.findViewById(R.id.veget);
        fish = (Button) view.findViewById(R.id.fish);
        meat = (Button) view.findViewById(R.id.meat);
        dairy = (Button) view.findViewById(R.id.dairy);
        snack = (Button) view.findViewById(R.id.snack);

        //잠겼는지 아닌지 확인 후 이미지 바꾸기

        for(int i = 0; i < 6; i++) {

            if (application.getThemeList().get(i).getIsLocked() == false) {
                if (i == 0)
                    fruit.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.locked_veget));
                if (i == 1)
                    veget.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.locked_veget));
                if (i == 2)
                    fish.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.locked_fish));
                if (i == 3)
                    meat.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.locked_meat));
                if (i == 4)
                    dairy.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.locked_dairy));
                if (i == 5)
                    snack.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.locked_snack));
            } else {
                if (i == 0)
                    fruit.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fruit));
                if (i == 1)
                    veget.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.veget));
                if (i == 2)
                    fish.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fish));
                if (i == 3)
                    meat.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.meat));
                if (i == 4)
                    dairy.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.dairy));
                if (i == 5)
                    snack.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.snack));
            }
        }

        fruit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                application.setNowThemeId(0);
                ((MainActivity) getActivity()).onThemeButtonClick(v, application.getThemeList().get(0).getId()); }
        });

        fish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity) getActivity()).onLearningButtonClick(v);
                Toast.makeText(getContext(), "포인트가 부족해요ㅠㅠ\n"+ application.getThemeList().get(2).getUnlockPoint() +"포인트가 필요해요!", Toast.LENGTH_SHORT).show();
            }
        });

        meat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity) getActivity()).onLearningButtonClick(v);
                Toast.makeText(getContext(), "포인트가 부족해요ㅠㅠ\n"+ application.getThemeList().get(3).getUnlockPoint() +"포인트가 필요해요!", Toast.LENGTH_SHORT).show();
            }
        });

        dairy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity) getActivity()).onLearningButtonClick(v);
                Toast.makeText(getContext(), "포인트가 부족해요ㅠㅠ\n"+ application.getThemeList().get(4).getUnlockPoint() +"포인트가 필요해요!", Toast.LENGTH_SHORT).show();
            }
        });

        snack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity) getActivity()).onLearningButtonClick(v);
                Toast.makeText(getContext(), "포인트가 부족해요ㅠㅠ\n"+ application.getThemeList().get(5).getUnlockPoint() +" 포인트가 필요해요!", Toast.LENGTH_SHORT).show();
            }
        });


        //잠겼으면 화면 유지
        if (application.getThemeList().get(1).getIsLocked() == false) {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int isPoint = application.getPointValue();
                    int needPoint = application.getThemeList().get(1).getUnlockPoint();

                    if (isPoint >= needPoint) {
                        point.setPoint(isPoint - needPoint);
                        application.getThemeList().get(1).setIsLocked(true);
                        application.setNowThemeId(1);
                        ((MainActivity) getActivity()).onThemeButtonClick(v, application.getThemeList().get(1).getId());
                        Toast.makeText(getContext(), "채소 교육을 시작한 걸 환영해요!", Toast.LENGTH_SHORT).show();
                    } else {

                        ((MainActivity) getActivity()).onLearningButtonClick(v);
                        Toast.makeText(getContext(), "포인트가 부족해요ㅠㅠ\n" + needPoint + "가 필요해요!", Toast.LENGTH_SHORT).show();

                    }
                }

            });
        }
        //아니면 화면 넘김
        else {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).onThemeButtonClick(v, application.getThemeList().get(1).getId());
                }
            });
        }
        return view;
    }
}