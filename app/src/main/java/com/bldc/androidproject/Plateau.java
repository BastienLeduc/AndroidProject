package com.bldc.androidproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import static java.lang.Math.abs;
import static java.lang.Math.min;

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
    int selectedX;
    int selectedY;
    private Case[][] tabIm = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plateau, container, false);
        plateau = (GridLayout) view.findViewById(R.id.plateau);
        textViewScore = (TextView) view.findViewById(R.id.textViewScore);
        nbCoups = 0;
        nbBilles = 0;
        selectedX = -1;
        selectedY = -1;
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
                        imB = new Case(getContext(), false, false, i, j);
                    } else {
                        imB = new Case(getContext(), false, true, i, j);
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
                    imB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Case selected = (Case) view;
                            if (selected.getUse()) // si la case selectionnee contient une bille
                            {
                                if (selectedX != -1 && selectedY != -1) {
                                    tabIm[selectedX][selectedY].setState(false); // on deselectionne la case qui etait selectionnee
                                }
                                selectedX = (int) selected.getXc(); // on stocke X
                                selectedY = (int) selected.getYc(); //           Y
                                tabIm[selectedX][selectedY].setState(true); // on selectionne la case
                                updatePlateau(); // on met a jour le plateau
                            } else if (selectedX != -1 && selectedY != -1) // si la case est vide
                            {
                                int targetX = (int) selected.getXc(); // on stocke la valeur d'arrivee potentielle
                                int targetY = (int) selected.getYc();
                                // si on souhaite realiser un coup selon l'axe X
                                if (abs(selectedX - targetX) == 2 && selectedY == targetY && tabIm[min(selectedX, targetX) + 1][selectedY].getUse()) {
                                    tabIm[selectedX][selectedY].setUse(false); // on prend la bille
                                    tabIm[targetX][targetY].setUse(true); // on la met a la destination voulue
                                    tabIm[min(selectedX, targetX) + 1][selectedY].setUse(false); // on enleve la bille entre les deux
                                    nbCoups++;
                                    nbBilles--;
                                    // isEnded(); // verifie si le joueur a fini ou non sa partie
                                    updatePlateau(); // les lignes 28, 29, 30, 31 se repetent un peu avec la deuxieme condition mais bon
                                } // si on souhaite realiser un coup selon l'axe Y
                                else if (abs(selectedY - targetY) == 2 && selectedX == targetX && tabIm[selectedX][abs(selectedY - targetY) + 1].getUse()) {
                                    tabIm[selectedX][selectedY].setUse(false); // on prend la bille
                                    tabIm[targetX][targetY].setUse(true); // on la met a la destination voulue
                                    tabIm[selectedX][abs(selectedY - targetY) + 1].setUse(false); // on enleve la bille intermediaire
                                    nbCoups++;
                                    nbBilles--;
                                    // isEnded();
                                    updatePlateau();
                                }
                            }
                        }
                    });
                    tabIm[i][j] = imB;
                    plateau.addView(imB);
                }
            }
        }

    }

    private void updatePlateau() {
        plateau.removeAllViews(); // on vide tout le plateau
        for (int i = 0; i < nbLigneCol; i++) {
            for (int j = 0; j < nbLigneCol; j++) {
                if (noneActivCase.contains(i) && noneActivCase.contains(j)) {
                } else {
                    Case imB = tabIm[i][j];
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
                    imB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Case selected = (Case) view;
                            if (selected.getUse()) // si la case selectionnee contient une bille
                            {
                                if (selectedX != -1 && selectedY != -1) {
                                    tabIm[selectedX][selectedY].setState(false); // on deselectionne la case qui etait selectionnee
                                }
                                selectedX = (int) selected.getXc(); // on stocke X
                                selectedY = (int) selected.getYc(); //           Y
                                tabIm[selectedX][selectedY].setState(true); // on selectionne la case
                                updatePlateau(); // on met a jour le plateau
                            } else if (selectedX != -1 && selectedY != -1) // si la case est vide
                            {
                                int targetX = (int) selected.getXc(); // on stocke la valeur d'arrivee potentielle
                                int targetY = (int) selected.getYc();
                                // si on souhaite realiser un coup selon l'axe X
                                if (abs(selectedX - targetX) == 2 && selectedY == targetY && tabIm[min(selectedX, targetX) + 1][selectedY].getUse()) {
                                    tabIm[selectedX][selectedY].setUse(false); // on prend la bille
                                    tabIm[targetX][targetY].setUse(true); // on la met a la destination voulue
                                    tabIm[min(selectedX, targetX) + 1][selectedY].setUse(false); // on enleve la bille entre les deux
                                    nbCoups++;
                                    nbBilles--;
                                    // isEnded(); // verifie si le joueur a fini ou non sa partie
                                    updatePlateau(); // les lignes 28, 29, 30, 31 se repetent un peu avec la deuxieme condition mais bon
                                } // si on souhaite realiser un coup selon l'axe Y
                                else if (abs(selectedY - targetY) == 2 && selectedX == targetX && tabIm[selectedX][abs(selectedY - targetY) + 1].getUse()) {
                                    tabIm[selectedX][selectedY].setUse(false); // on prend la bille
                                    tabIm[targetX][targetY].setUse(true); // on la met a la destination voulue
                                    tabIm[selectedX][abs(selectedY - targetY) + 1].setUse(false); // on enleve la bille intermediaire
                                    nbCoups++;
                                    nbBilles--;
                                    // isEnded();
                                    updatePlateau();
                                }
                            }
                        }
                    });
                    plateau.addView(imB);
                }
            }
        }
    }

    public ArrayList<Case> verif(GridLayout plat, View v) {
        ArrayList<Case> lCase = new ArrayList<Case>();
        Case caseSel = (Case) v;

        if (((int) caseSel.getY() + 2) < nbLigneCol && (int) caseSel.getX() < nbLigneCol) {
            Log.e("testscol", "y+2");
            if (tabIm[(int) caseSel.getX()][(int) caseSel.getY() + 2] != null) {
                Log.e("testsnul", "y+2");
                for (int j = 0; j < plat.getChildCount(); j++) {
                    {
                        Case c = (Case) plat.getChildAt(j);
                        if (c.getY() == (int) caseSel.getY() + 2 && c.getX() == (int) caseSel.getX() && c.getState()) {
                            Log.e("tests", "y+2");
                            lCase.add(c);
                        }
                    }
                }
            }
        }
        if (((int) caseSel.getX() + 2) < nbLigneCol && (int) caseSel.getY() < nbLigneCol) {
            Log.e("testscol", "x+2");
            if (tabIm[((int) caseSel.getX() + 2)][(int) caseSel.getY()] != null) {
                Log.e("testsnull", "x+2");
                for (int j = 0; j < plat.getChildCount(); j++) {
                    {
                        Case c = (Case) plat.getChildAt(j);
                        if (c.getY() == (int) caseSel.getY() && c.getX() == (int) caseSel.getX() + 2 && c.getState()) {
                            Log.e("tests", "x+2");
                            lCase.add(c);
                        }
                    }
                }
            }
        }
        if (caseSel.getX() - 2 > 0 && (int) caseSel.getY() < nbLigneCol && (int) caseSel.getX() < nbLigneCol) {
            Log.e("testscol", "x-2");
            if (tabIm[(int) caseSel.getX() - 2][(int) caseSel.getY()] != null) {
                Log.e("testsnull", "x-2");
                for (int j = 0; j < plat.getChildCount(); j++) {
                    {
                        Case c = (Case) plat.getChildAt(j);
                        if (c.getY() == (int) caseSel.getY() && c.getX() == (int) caseSel.getX() - 2 && c.getState()) {
                            Log.e("tests", "x-2");
                            lCase.add(c);
                        }
                    }
                }
            }
        }
        if (caseSel.getY() - 2 > 0 && (int) caseSel.getX() < nbLigneCol && (int) caseSel.getY() < nbLigneCol) {
            Log.e("testscol", "y-2");
            if (tabIm[(int) caseSel.getX()][(int) caseSel.getY() - 2] != null) {
                Log.e("testsnull", "y-2");
                for (int j = 0; j < plat.getChildCount(); j++) {
                    {
                        Case c = (Case) plat.getChildAt(j);
                        if (c.getY() == (int) caseSel.getY() - 2 && c.getX() == (int) caseSel.getX() && c.getState()) {
                            Log.e("tests", "y-2");
                            lCase.add(c);
                        }
                    }
                }
            }

        }
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
        if ((x1 == x2 && abs(y1 - y2) == 2) || (y1 == y2 && abs(x1 - x2) == 2))
            for (int i = 0; i < plat.getChildCount(); i++) {
                Case c = (Case) plat.getChildAt(i);
                if (c.getX() == valMidX && c.getY() == valMidY) {
                    if (c.getUse()) {
                        nbCoups++;
                        nbBilles--;
                    }
                }
                if (c.getX() == x1 && c.getY() == y1) {
                    if (c.getUse()) c.setUse(false);
                }
                if (c.getX() == x2 && c.getY() == y2) {
                    if (!c.getUse()) c.setUse(true);
                }

            }
    }
}