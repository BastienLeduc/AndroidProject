package com.bldc.androidproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bldc.androidproject.Entite.Score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.min;

@SuppressLint("ValidFragment")
public class Plateau extends Fragment {

    private GridLayout plateau = null;
    private Chronometer chrono = null;
    private long pauseTime;
    private Button btn_replay = null;
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
    private MediaPlayer pressBtn;
    private MediaPlayer pose;
    private MediaPlayer depose;
    private ArrayList<Score> listScore;

    public Plateau() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plateau, container, false);
        plateau = (GridLayout) view.findViewById(R.id.plateau);
        btn_replay = (Button) view.findViewById(R.id.btn_replay);
        textViewScore = (TextView) view.findViewById(R.id.textViewScore);
        chrono = (Chronometer) view.findViewById(R.id.chrono);
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
                if (noneActivCase.contains(i) && noneActivCase.contains(j)) {
                    tabIm[i][j] = null;
                } else {
                    Case imB = null;
                    if (i == Math.round(nbLigneCol / 2) && j == Math.round(nbLigneCol / 2)) {
                        imB = new Case(getContext(), false, false, i, j);
                    } else {
                        imB = new Case(getContext(), false, true, i, j);
                    }
                    imB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Case selected = (Case) view;
                            if (selected.getUse()) // si la case selectionnee contient une bille
                            {
                                playPose();
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
                                    playDepose();
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
                                    playDepose();
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
                                playPose(); // joue le son quand on selectionne une case
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
                                    playDepose(); // joue le son de depot de la piece
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
                                    playDepose(); // joue le son de depot de la piece
                                    updatePlateau();
                                }
                                // si la partie est finie on envoie un pop up de fin
                                if (ended) {
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
        updateScore();
    }

    public Boolean isEnded() {

        // la partie est finie si il ne reste qu'une bille
        if (nbBilles == 1) {
            return true;
        }
        for (int i = 0; i < nbLigneCol; i++) {
            for (int j = 0; j < nbLigneCol; j++) {
                if (tabIm[i][j] != null) {
                    if (tabIm[i][j].getUse()) {
                        Case gauche = null;
                        Case droite = null;
                        Case bas = null;
                        Case haut = null;
                        Case gauche2 = null;
                        Case droite2 = null;
                        Case bas2 = null;
                        Case haut2 = null;

                        // prend les deux cases à gauche
                        if (i > 0) {
                            if (tabIm[i - 1][j] != null) {
                                gauche = tabIm[i - 1][j];
                                if (i > 1) {
                                    gauche2 = tabIm[i - 2][j];
                                }
                            }
                        }
                        // prend les deux cases à droite
                        if (i < 6) {
                            if (tabIm[i + 1][j] != null) {
                                droite = tabIm[i + 1][j];
                                if (i < 5) {
                                    droite2 = tabIm[i + 2][j];
                                }
                            }
                        }
                        // prend les deux cases en haut
                        if (j > 0) {
                            if (tabIm[i][j - 1] != null) {
                                haut = tabIm[i][j - 1];
                                if (j > 1) {
                                    haut2 = tabIm[i][j - 2];
                                }
                            }
                        }
                        // prend les deux cases en bas
                        if (j < 6) {
                            if (tabIm[i][j + 1] != null) {
                                bas = tabIm[i][j + 1];
                                if (j < 5) {
                                    bas2 = tabIm[i][j + 2];
                                }
                            }
                        }
                        // regarde si un coup est jouable en haut
                        if (haut != null && haut2 != null) {
                            if (haut.getUse() && !haut2.getUse()) {
                                return false;
                            }
                        }
                        // en bas
                        if (bas != null && bas2 != null) {
                            if (bas.getUse() && !bas2.getUse()) {
                                return false;
                            }
                        }
                        // à gauche
                        if (gauche != null && gauche2 != null) {
                            if (gauche.getUse() && !gauche2.getUse()) {
                                return false;
                            }
                        }
                        // à droite
                        if (droite != null && droite2 != null) {
                            if (droite.getUse() && !droite2.getUse()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void end() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        int score = nbBilles;

        // si la partie est gagnee
        if (nbBilles == 1) {
            // si c'est une perfect win
            if (tabIm[3][3].getUse()) {
                builder.setMessage("Wouah tu gères !");
                score = 0;
            } else {
                builder.setMessage("Bonne partie, mais tu peux faire mieux ;)");
            }
        } else {
            builder.setMessage("Il reste " + nbBilles + " Billes. Dommage, tu feras mieux la prochaine fois !");
        }

        final EditText namePlayer = new EditText(getActivity());
        namePlayer.setHint("Votre nom");
        builder.setView(namePlayer);
        builder.setCancelable(false);
        final int finalScore = score;
        builder.setPositiveButton("Score", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!namePlayer.getText().toString().isEmpty()) {
                    getActivity().finish();
                    final Intent mainActivityIntent = new Intent(getActivity(), ScoreList.class);
                    startActivity(mainActivityIntent);
                    saveScore(finalScore, SystemClock.elapsedRealtime() - chrono.getBase(), namePlayer.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Error nom player", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        updateScore();

        if (pauseTime != 0) {
            chrono.setBase(chrono.getBase() + SystemClock.elapsedRealtime() - pauseTime);
        } else {
            chrono.setBase(SystemClock.elapsedRealtime());
        }
        chrono.start();

        btn_replay.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                replay();

            }
        });

    }

    public void replay() {
        createCase();
        nbCoups = 0;
        nbBilles = 32;
        updateScore();
        playPress();

    }

    @Override
    public void onPause() {
        super.onPause();

        pauseTime = SystemClock.elapsedRealtime();
        chrono.stop();

    }

    private void playPose() {
        pose = (MediaPlayer) MediaPlayer.create(getActivity(), R.raw.pose);
        pose.start();
        pose.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pose.stop();
                pose.release();
            }
        });
    }

    private void playDepose() {
        depose = (MediaPlayer) MediaPlayer.create(getActivity(), R.raw.depose);
        depose.start();
        depose.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                depose.stop();
                depose.release();
            }
        });
    }

    private void playPress() {
        pressBtn = (MediaPlayer) MediaPlayer.create(getActivity(), R.raw.menusound);
        pressBtn.start();
        pressBtn.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pressBtn.stop();
                pressBtn.release();
            }
        });
    }

    private void saveScore(int score, long chrono, String name) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson;
        String json;
        Type type;
        gson = new Gson();
        json = prefs.getString("ListScore", "");
        type = new TypeToken<ArrayList<Score>>() {
        }.getType();
        listScore = gson.fromJson(json, type);
        Score sctemp = null;
        if (listScore != null) {
            for (int i = 0; i < listScore.size(); i++) {
                if (listScore.get(i).getScore() < score) {
                    if (listScore.get(i).getChrono() < chrono) {
                        sctemp = new Score(listScore.get(i).getPosition(), name, score, chrono);
                        for (int j = i; j < listScore.size(); j++) {
                            listScore.get(j).setPosition(listScore.get(j).getPosition() + 1);
                        }
                        break;
                    } else {
                        sctemp = new Score(listScore.get(i).getPosition() + 1, name, score, chrono);
                        for (int j = i+1; j < listScore.size(); j++) {
                            listScore.get(j).setPosition(listScore.get(j).getPosition() + 1);
                        }
                        break;
                    }
                }
            }
        } else {
            listScore = new ArrayList<Score>();
        }
        if (sctemp == null) {
            sctemp = new Score(listScore.size() + 1, name, score, chrono);
        }
        listScore.add(sctemp);
        Collections.sort(listScore, Score.ScoreComparator);
        if (listScore.size() > 10) {
            for (int i = 10; i < listScore.size(); i++) {
                listScore.remove(i);
            }
        }

        gson = new Gson();

        json = gson.toJson(listScore);
        editor.putString("ListScore", json);
        editor.apply();
    }

    public void updateScore() {
        textViewScore.setText("Score : " + nbCoups);
    }

}