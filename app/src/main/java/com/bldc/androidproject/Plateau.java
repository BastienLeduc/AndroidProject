package com.bldc.androidproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class Plateau extends Fragment {

    private GridLayout plateau = null;
    private TextView textViewScore = null;
    private final int nbLigneCol = 7;
    private final int nbCase = (int) Math.pow(nbLigneCol, 2);
    Integer[] listValue = {0, 1, 5, 6};
    private final ArrayList<Integer> noneActivCase = new ArrayList<Integer>(Arrays.asList(listValue));
    private int nbCoups;
    private int nbBilles;
    private Case[][] tabIm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plateau, container, false);
        plateau = (GridLayout) view.findViewById(R.id.plateau);
        textViewScore = (TextView) view.findViewById(R.id.textViewScore);
        nbCoups = 0;
        nbBilles = 0;
        tabIm = new Case[nbLigneCol][nbLigneCol];
        createCase();
        return view;
    }


    private void createCase() {
        plateau.setRowCount(nbLigneCol);
        plateau.setColumnCount(nbLigneCol);

        for (int i = 0; i < nbLigneCol; i++) {
            for (int j = 0; j < nbLigneCol; j++) {
                if (noneActivCase.contains(i) && noneActivCase.contains(j)) {
                } else {
                    Case imB = null;
                    if (i == Math.round(nbLigneCol / 2) && j == Math.round(nbLigneCol / 2)) {
                        imB = new Case(getContext(), false, false);
                    } else {
                        imB = new Case(getContext(), false, true);
                    }
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
                    params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
                    params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
                    params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
                    params.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
                    params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
                    params.columnSpec = GridLayout.spec(i);
                    params.rowSpec = GridLayout.spec(j);
                    imB.setLayoutParams(params);
                    final int finalI = i;
                    final int finalJ = j;
                    imB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ArrayList<Case> lCaseSt = verif(plateau, view);

                            if (lCaseSt != null) {
                                for (Case c : lCaseSt) {
                                    playOneBall(plateau, (int) c.getX(), (int) c.getY(), finalI, finalJ);
                                }
                            } else {
                                Case t = (Case) view;
                                t.setState(true);
                            }

                            /*for (int i = 0; i < plateau.getChildCount(); i++) {
                                if (plateau.getChildAt(i).isSelected() && finalImB.isSelected()) {
                                }
                            }*/

                        }
                    });
                    tabIm[i][j] = imB;
                    plateau.addView(imB);
                }
            }
        }
    }

    public ArrayList<Case> verif(GridLayout plat, View v) {
        ArrayList<Case> lCase = new ArrayList<Case>();
        for (int i = 0; i < plat.getChildCount(); i++) {
            if (((int)plat.getChildAt(i).getY() + 2) < nbLigneCol) {
                if (tabIm[(int) plat.getChildAt(i).getX()][(int) plat.getChildAt(i).getY() + 2] != null) {
                    Case c = (Case) tabIm[(int) plat.getChildAt(i).getX()][(int) plat.getChildAt(i).getY() + 2];
                    if (c != null && c.getState() == true) {
                        lCase.add(c);

                    }
                }
            }
            if (((int)plat.getChildAt(i).getX() + 2) < nbLigneCol) {
                if (tabIm[(int) plat.getChildAt(i).getX() + 2][(int) plat.getChildAt(i).getY()] != null) {
                    Case c = (Case) tabIm[(int) plat.getChildAt(i).getX() + 2][(int) plat.getChildAt(i).getY()];
                    if (c != null && c.getState() == true) {
                        lCase.add(c);

                    }
                }
            }
            if (plat.getChildAt(i).getX() - 2 > 0) {
                if (tabIm[(int) plat.getChildAt(i).getX() - 2][(int) plat.getChildAt(i).getY()] != null) {
                    Case c = (Case) tabIm[(int) plat.getChildAt(i).getX() - 2][(int) plat.getChildAt(i).getY()];
                    if (c != null && c.getState() == true) {
                        lCase.add(c);

                    }
                }
            }
            if (plat.getChildAt(i).getY() - 2 > 0) {
                if (tabIm[(int) plat.getChildAt(i).getX()][(int) plat.getChildAt(i).getY() - 2] != null) {
                    Case c = (Case) tabIm[(int) plat.getChildAt(i).getX()][(int) plat.getChildAt(i).getY() - 2];
                    if (c != null && c.getState() == true) {
                        lCase.add(c);

                    }
                }
            }
            /*if ((plat.getRowCount() < plat.getChildAt(i).getY() + 2 && plat.getColumnCount() < plat.getChildAt(i).getX()) &&
                    (plat.getRowCount() < plat.getChildAt(i).getY() - 2 && plat.getColumnCount() < plat.getChildAt(i).getX()) &&
                    (plat.getRowCount() < plat.getChildAt(i).getY() && plat.getColumnCount() < plat.getChildAt(i).getX() + 2) &&
                    (plat.getRowCount() < plat.getChildAt(i).getY() && plat.getColumnCount() < plat.getChildAt(i).getX() - 2)) {

            }*/
        }
        /*if ((plat.getRowCount() < v.getY() + 2 && plat.getColumnCount() < v.getX()) &&
                (plat.getRowCount() < v.getY() - 2 && plat.getColumnCount() < v.getX()) &&
                (plat.getRowCount() < v.getY() && plat.getColumnCount() < v.getX() + 2) &&
                (plat.getRowCount() < v.getY() && plat.getColumnCount() < v.getX() - 2)) {
            return true;
        } else return false;*/
        return lCase;

    }

    @Override
    public void onResume() {
        super.onResume();
        textViewScore.setText("Nb billes restantes : " + nbBilles);
    }

    private void playOneBall(GridLayout plat, int x1, int y1, int x2, int y2) {
        int valMidX = (x1 + x2) / 2;
        int valMidY = (y1 + y2) / 2;
        if ((x1 == x2 && Math.abs(y1 - y2) == 2) || (y1 == y2 && Math.abs(x1 - x2) == 2))
            for (int i = 0; i < plat.getChildCount(); i++) {
                if (plat.getChildAt(i).getX() == valMidX && plat.getChildAt(i).getY() == valMidY) {
                    if (!plat.getChildAt(i).isActivated()) {
                        nbCoups++;
                        nbBilles--;
                    }
                }
                if (plat.getChildAt(i).getX() == x1 && plat.getChildAt(i).getY() == y1) {
                    if (plat.getChildAt(i).isActivated()) plat.getChildAt(i).setActivated(false);
                }
                if (plat.getChildAt(i).getX() == x2 && plat.getChildAt(i).getY() == y2) {
                    if (plat.getChildAt(i).isActivated()) plat.getChildAt(i).setActivated(true);
                }

            }

    }
}

