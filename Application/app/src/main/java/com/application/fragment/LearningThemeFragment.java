package com.application.fragment;

import android.os.Build;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.application.EWLApplication;
import com.application.R;
import com.application.activity.GameActivity;
import com.application.activity.LearningActivity;
import com.application.activity.MainActivity;
import com.application.database.EWLADbHelper;
import com.application.database.Point;
import com.application.database.Theme;

import java.util.HashMap;
import java.util.List;

public class LearningThemeFragment extends Fragment {

    Button fruit;
    Button veget;
    Button fish;
    Button meat;
    Button dairy;
    Button snack;
    Button previous;

    Point point;
    EWLApplication application = EWLApplication.getInstance();

    public static LearningThemeFragment newInstance() {
        return new LearningThemeFragment();
    }

    private HashMap<Integer, Integer> soundPoolMap;

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
        previous = (Button) view.findViewById(R.id.previous);

        //잠겼는지 아닌지 확인 후 이미지 바꾸기

        for (int i = 0; i < 6; i++) {

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

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.setNowThemeId(0);
                ((MainActivity) getActivity()).onThemeButtonClick(v, false);
            }
        });

        if (application.getThemeList().get(2).getIsLocked() == false) {
            fish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int isPoint = application.getPointValue();
                    int needPoint = application.getThemeList().get(2).getUnlockPoint();

                    if (isPoint >= needPoint) {
                        if (!application.getThemeList().get(2).getIsLocked()) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setMessage(needPoint + "포인트를 사용해서 생선 단어를 공부할 수 있어요.\n[테마: 생선]을(를) 열어볼까요?").setCancelable(
                                    false).setNegativeButton("아니요",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // Cancel 버튼 클릭시
                                            ((MainActivity) getActivity()).onLearningButtonClick(v);
                                        }
                                    }).setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Action for 'Yes' Button
                                            application.setPointValue(application.getPointValue() - needPoint);
                                            application.getThemeList().get(2).setIsLocked(true);
                                            application.setNowThemeId(2);
                                            ((MainActivity) getActivity()).setPointView();
                                            ((MainActivity) getActivity()).onThemeButtonClick(v, true);

                                            Toast.makeText(getContext(), "생선 교육을 시작한 걸 환영해요!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = dialog.create();
                            // Title for AlertDialog
                            alert.setTitle("생선 해금");
                            // Icon for AlertDialog
                            alert.setIcon(R.drawable.fish);
                            alert.show();
                        }
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setMessage(needPoint + "만큼의 포인트가 필요해요. \n포인트를 얻기 위해 게임을 하러 가볼까요??").setCancelable(
                                false).setNegativeButton("아니요",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Cancel 버튼 클릭시
                                        ((MainActivity) getActivity()).onLearningButtonClick(v);
                                    }
                                }).setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Action for 'Yes' Button
                                        ((MainActivity) getActivity()).onGameButtonClick(v);
                                    }
                                });
                        AlertDialog alert = dialog.create();
                        // Title for AlertDialog
                        alert.setTitle("생선 해금");
                        // Icon for AlertDialog
                        alert.setIcon(R.drawable.fish);
                        alert.show();
                    }
                }
            });
        }
        //아니면 화면 넘김
        else {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    application.setNowThemeId(1);
                    ((MainActivity) getActivity()).onThemeButtonClick(v, false);
                }
            });
        }

        if (application.getThemeList().get(3).getIsLocked() == false) {
            meat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int isPoint = application.getPointValue();
                    int needPoint = application.getThemeList().get(3).getUnlockPoint();

                    if (isPoint >= needPoint) {
                        if (!application.getThemeList().get(3).getIsLocked()) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setMessage(needPoint + "포인트를 사용해서 고기 단어를 공부할 수 있어요.\n[테마: 고기]을(를) 열어볼까요?").setCancelable(
                                    false).setNegativeButton("아니요",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // Cancel 버튼 클릭시
                                            ((MainActivity) getActivity()).onLearningButtonClick(v);
                                        }
                                    }).setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Action for 'Yes' Button
                                            application.setPointValue(application.getPointValue() - needPoint);
                                            application.getThemeList().get(3).setIsLocked(true);
                                            application.setNowThemeId(3);
                                            ((MainActivity) getActivity()).setPointView();
                                            ((MainActivity) getActivity()).onThemeButtonClick(v, true);

                                            Toast.makeText(getContext(), "고기 교육을 시작한 걸 환영해요!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = dialog.create();
                            // Title for AlertDialog
                            alert.setTitle("고기 해금");
                            // Icon for AlertDialog
                            alert.setIcon(R.drawable.meat);
                            alert.show();
                        }
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setMessage(needPoint + "만큼의 포인트가 필요해요. \n포인트를 얻기 위해 게임을 하러 가볼까요??").setCancelable(
                                false).setNegativeButton("아니요",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Cancel 버튼 클릭시
                                        ((MainActivity) getActivity()).onLearningButtonClick(v);
                                    }
                                }).setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Action for 'Yes' Button
                                        ((MainActivity) getActivity()).onGameButtonClick(v);
                                    }
                                });
                        AlertDialog alert = dialog.create();
                        // Title for AlertDialog
                        alert.setTitle("고기 해금");
                        // Icon for AlertDialog
                        alert.setIcon(R.drawable.meat);
                        alert.show();
                    }
                }
            });
        }
        //아니면 화면 넘김
        else {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    application.setNowThemeId(1);
                    ((MainActivity) getActivity()).onThemeButtonClick(v, false);
                }
            });
        }

        if (application.getThemeList().get(4).getIsLocked() == false) {
            dairy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int isPoint = application.getPointValue();
                    int needPoint = application.getThemeList().get(4).getUnlockPoint();

                    if (isPoint >= needPoint) {
                        if (!application.getThemeList().get(4).getIsLocked()) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setMessage(needPoint + "포인트를 사용해서 유제품 단어를 공부할 수 있어요.\n[테마: 유제품]을(를) 열어볼까요?").setCancelable(
                                    false).setNegativeButton("아니요",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // Cancel 버튼 클릭시
                                            ((MainActivity) getActivity()).onLearningButtonClick(v);
                                        }
                                    }).setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Action for 'Yes' Button
                                            application.setPointValue(application.getPointValue() - needPoint);
                                            application.getThemeList().get(4).setIsLocked(true);
                                            application.setNowThemeId(4);
                                            ((MainActivity) getActivity()).setPointView();
                                            ((MainActivity) getActivity()).onThemeButtonClick(v, true);

                                            Toast.makeText(getContext(), "유제품 교육을 시작한 걸 환영해요!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = dialog.create();
                            // Title for AlertDialog
                            alert.setTitle("유제품 해금");
                            // Icon for AlertDialog
                            alert.setIcon(R.drawable.dairy);
                            alert.show();
                        }
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setMessage(needPoint + "만큼의 포인트가 필요해요. \n포인트를 얻기 위해 게임을 하러 가볼까요??").setCancelable(
                                false).setNegativeButton("아니요",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Cancel 버튼 클릭시
                                        ((MainActivity) getActivity()).onLearningButtonClick(v);
                                    }
                                }).setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Action for 'Yes' Button
                                        ((MainActivity) getActivity()).onGameButtonClick(v);
                                    }
                                });
                        AlertDialog alert = dialog.create();
                        // Title for AlertDialog
                        alert.setTitle("유제품 해금");
                        // Icon for AlertDialog
                        alert.setIcon(R.drawable.dairy);
                        alert.show();
                    }
                }
            });
        }
        //아니면 화면 넘김
        else {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    application.setNowThemeId(1);
                    ((MainActivity) getActivity()).onThemeButtonClick(v, false);
                }
            });
        }

        if (application.getThemeList().get(5).getIsLocked() == false) {
            snack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int isPoint = application.getPointValue();
                    int needPoint = application.getThemeList().get(5).getUnlockPoint();

                    if (isPoint >= needPoint) {
                        if (!application.getThemeList().get(5).getIsLocked()) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setMessage(needPoint + "포인트를 사용해서 간식 단어를 공부할 수 있어요.\n[테마: 간식]을(를) 열어볼까요?").setCancelable(
                                    false).setNegativeButton("아니요",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // Cancel 버튼 클릭시
                                            ((MainActivity) getActivity()).onLearningButtonClick(v);
                                        }
                                    }).setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Action for 'Yes' Button
                                            application.setPointValue(application.getPointValue() - needPoint);
                                            application.getThemeList().get(5).setIsLocked(true);
                                            application.setNowThemeId(5);
                                            ((MainActivity) getActivity()).setPointView();
                                            ((MainActivity) getActivity()).onThemeButtonClick(v, true);

                                            Toast.makeText(getContext(), "간식 교육을 시작한 걸 환영해요!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = dialog.create();
                            // Title for AlertDialog
                            alert.setTitle("간식 해금");
                            // Icon for AlertDialog
                            alert.setIcon(R.drawable.snack);
                            alert.show();
                        }
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setMessage(needPoint + "만큼의 포인트가 필요해요. \n포인트를 얻기 위해 게임을 하러 가볼까요??").setCancelable(
                                false).setNegativeButton("아니요",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Cancel 버튼 클릭시
                                        ((MainActivity) getActivity()).onLearningButtonClick(v);
                                    }
                                }).setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Action for 'Yes' Button
                                        ((MainActivity) getActivity()).onGameButtonClick(v);
                                    }
                                });
                        AlertDialog alert = dialog.create();
                        // Title for AlertDialog
                        alert.setTitle("간식 해금");
                        // Icon for AlertDialog
                        alert.setIcon(R.drawable.snack);
                        alert.show();
                    }
                }


            });
        }
        //아니면 화면 넘김
        else {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    application.setNowThemeId(1);
                    ((MainActivity) getActivity()).onThemeButtonClick(v, false);
                }
            });
        }
        //잠겼으면 화면 유지
        if (application.getThemeList().get(1).getIsLocked() == false) {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int isPoint = application.getPointValue();
                    int needPoint = application.getThemeList().get(1).getUnlockPoint();

                    if (isPoint >= needPoint) {
                        if (!application.getThemeList().get(1).getIsLocked()) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setMessage(needPoint + "포인트를 사용해서 채소 단어를 공부할 수 있어요.\n[테마: 채소]을(를) 열어볼까요?").setCancelable(
                                    false).setNegativeButton("아니요",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // Cancel 버튼 클릭시
                                            ((MainActivity) getActivity()).onLearningButtonClick(v);
                                        }
                                    }).setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Action for 'Yes' Button
                                            application.setPointValue(application.getPointValue() - needPoint);
                                            application.getThemeList().get(1).setIsLocked(true);
                                            application.setNowThemeId(1);
                                            ((MainActivity) getActivity()).setPointView();
                                            ((MainActivity) getActivity()).onThemeButtonClick(v, true);

                                            Toast.makeText(getContext(), "채소 교육을 시작한 걸 환영해요!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = dialog.create();
                            // Title for AlertDialog
                            alert.setTitle("채소 해금");
                            // Icon for AlertDialog
                            alert.setIcon(R.drawable.veget);
                            alert.show();
                        }
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setMessage(needPoint + "만큼의 포인트가 필요해요. \n포인트를 얻기 위해 게임을 하러 가볼까요??").setCancelable(
                                false).setNegativeButton("아니요",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Cancel 버튼 클릭시
                                        ((MainActivity) getActivity()).onLearningButtonClick(v);
                                    }
                                }).setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Action for 'Yes' Button
                                        ((MainActivity) getActivity()).onGameButtonClick(v);
                                    }
                                });
                        AlertDialog alert = dialog.create();
                        // Title for AlertDialog
                        alert.setTitle("채소 해금");
                        // Icon for AlertDialog
                        alert.setIcon(R.drawable.veget);
                        alert.show();
                    }
                }

            });
        }
        //아니면 화면 넘김
        else {
            veget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    application.setNowThemeId(1);
                    ((MainActivity) getActivity()).onThemeButtonClick(v, false);
                }
            });
        }
        return view;
    }
}