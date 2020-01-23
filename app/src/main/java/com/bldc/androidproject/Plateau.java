package com.bldc.androidproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
        nbBilles = 32;
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
                if (noneActivCase.contains(i) && noneActivCase.contains(j)){
                    tabIm[i][j] = null;
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
                                    selectedX = -1;
                                    selectedY = -1;
                                    updatePlateau();
                                } // si on souhaite realiser un coup selon l'axe Y
                                else if (abs(selectedY - targetY) == 2 && selectedX == targetX && tabIm[selectedX][min(selectedY, targetY) + 1].getUse()) {
                                    tabIm[selectedX][selectedY].setUse(false); // on prend la bille
                                    tabIm[targetX][targetY].setUse(true); // on la met a la destination voulue
                                    tabIm[selectedX][min(selectedY, targetY) + 1].setUse(false); // on enleve la bille intermediaire
                                    nbCoups++;
                                    nbBilles--;
                                    selectedX = -1;
                                    selectedY = -1;
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
                    tabIm[i][j] = null;
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
                                Boolean ended = false;
                                int targetX = (int) selected.getXc(); // on stocke la valeur d'arrivee potentielle
                                int targetY = (int) selected.getYc();
                                // si on souhaite realiser un coup selon l'axe X
                                if (abs(selectedX - targetX) == 2 && selectedY == targetY && tabIm[min(selectedX, targetX) + 1][selectedY].getUse()) {
                                    tabIm[selectedX][selectedY].setUse(false); // on prend la bille
                                    tabIm[targetX][targetY].setUse(true); // on la met a la destination voulue
                                    tabIm[min(selectedX, targetX) + 1][selectedY].setUse(false); // on enleve la bille entre les deux
                                    nbCoups++;
                                    nbBilles--;
                                    selectedX = -1;
                                    selectedY = -1;
                                    ended = isEnded(); // verifie si le joueur a fini ou non sa partie
                                    updatePlateau(); // les lignes 28, 29, 30, 31 se repetent un peu avec la deuxieme condition mais bon
                                } // si on souhaite realiser un coup selon l'axe Y
                                else if (abs(selectedY - targetY) == 2 && selectedX == targetX && tabIm[selectedX][min(selectedY, targetY) + 1].getUse()) {
                                    tabIm[selectedX][selectedY].setUse(false); // on prend la bille
                                    tabIm[targetX][targetY].setUse(true); // on la met a la destination voulue
                                    tabIm[selectedX][min(selectedY, targetY) + 1].setUse(false); // on enleve la bille intermediaire
                                    nbCoups++;
                                    nbBilles--;
                                    selectedX = -1;
                                    selectedY = -1;
                                    ended = isEnded();
                                    updatePlateau();
                                }
                                // si la partie est finie on envoie un pop up de fin
                                if (ended)
                                {
                                    end();
                                }
                            }
                        }
                    });
                    tabIm[i][j] = imB;
                    plateau.addView(imB);
                }
            }
        }
        upDateBille();
    }

    public Boolean isEnded(){

        // la partie est finie si il ne reste qu'une bille
        if (nbBilles == 1){
            return true;
        }
        for (int i = 0; i < nbLigneCol; i++){
            for (int j = 0; j < nbLigneCol; j++){
                if (tabIm[i][j] != null)
                {
                    if (tabIm[i][j].getUse())
                    {
                        Case gauche = null;
                        Case droite = null;
                        Case bas = null;
                        Case haut = null;
                        Case gauche2 = null;
                        Case droite2 = null;
                        Case bas2 = null;
                        Case haut2 = null;

                        // prend les deux cases à gauche
                        if (i > 0){
                            if (tabIm[i-1][j] != null)
                            {
                                gauche = tabIm[i-1][j];
                                if (i > 1){
                                    gauche2 = tabIm[i-2][j];
                                }
                            }
                        }
                        // prend les deux cases à droite
                        if (i < 6){
                            if(tabIm[i+1][j] != null)
                            {
                                droite = tabIm[i+1][j];
                                if (i < 5)
                                {
                                    droite2 = tabIm[i+2][j];
                                }
                            }
                        }
                        // prend les deux cases en haut
                        if (j > 0){
                            if(tabIm[i][j-1] != null)
                            {
                                haut = tabIm[i][j-1];
                                if (j > 1){
                                    haut2 = tabIm[i][j-2];
                                }
                            }
                        }
                        // prend les deux cases en bas
                        if (j < 6){
                            if(tabIm[i][j+1] != null){
                                bas = tabIm[i][j+1];
                                if (j < 5){
                                    bas2 = tabIm[i][j+2];
                                }
                            }
                        }
                        // regarde si un coup est jouable en haut
                        if (haut != null && haut2 != null)
                        {
                            if (haut.getUse() && !haut2.getUse()){
                                return false;
                            }
                        }
                        // en bas
                        if (bas != null && bas2 != null){
                            if (bas.getUse() && !bas2.getUse()){
                                return false;
                            }
                        }
                        // à gauche
                        if (gauche != null && gauche2 != null){
                            if (gauche.getUse() && !gauche2.getUse()){
                                return false;
                            }
                        }
                        // à droite
                        if (droite != null && droite2 != null) {
                            if (droite.getUse() && !droite2.getUse()){
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void end(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // si la partie est gagnee
        if (nbBilles == 1){
            // si c'est une perfect win
            if (tabIm[3][3].getUse()){
                builder.setMessage("Wouah tu gères !");
            }else{
                builder.setMessage("Bonne partie, mais tu peux faire mieux ;)");
            }
        }else
        {
            builder.setMessage("Il reste "+ nbBilles +" Billes. Dommage, tu feras mieux la prochaine fois !");
        }
        builder.setCancelable(false);
        builder.setPositiveButton("Ok boomer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Ok zoomer", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }




    @Override
    public void onResume() {
        super.onResume();
        upDateBille();
    }

    public void upDateBille() {
        textViewScore.setText("Nb billes restantes : " + nbBilles);
    }
}