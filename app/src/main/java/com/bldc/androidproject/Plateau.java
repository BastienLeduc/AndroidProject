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
    private Integer[] listValue = {0, 1, 5, 6};
    private final ArrayList<Integer> noneActivCase = new ArrayList<Integer>(Arrays.asList(listValue));
    private int nbCoups;
    private int nbBilles;
    private int selectedX;
    private int selectedY;
    private Case[][] tabIm = null;
    private MediaPlayer pressBtn;
    private MediaPlayer pose;
    private MediaPlayer depose;
    private ArrayList<Score> listScore;
    private long valueChrono = 0;

    /***
     * Method to create fragment and affect element
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plateau, container, false);
        plateau = (GridLayout) view.findViewById(R.id.plateau);
        btn_replay = (Button) view.findViewById(R.id.btn_replay);
        textViewScore = (TextView) view.findViewById(R.id.textViewScore);
        chrono = (Chronometer) view.findViewById(R.id.chrono);
        initValue();
        createCase();
        return view;
    }

    /***
     * Intiate value at start
     */
    public void initValue() {

        nbCoups = 0;
        nbBilles = 32;
        selectedX = -1;
        selectedY = -1;
        tabIm = new Case[nbLigneCol][nbLigneCol];
    }

    /***
     * Create GridView and add Case with action
     */
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

    /***
     * Update GridView and Case state
     */
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
                                    chrono.stop();
                                    valueChrono = SystemClock.elapsedRealtime() - chrono.getBase();
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

    /***
     * Check end game
     * @return
     */
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

    /***
     *End action of a game
     */
    public void end() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText namePlayer = new EditText(getActivity());
        namePlayer.setHint(getResources().getString(R.string.your_name));
        builder.setView(namePlayer);
        builder.setCancelable(false);

        int score = nbBilles;

        // if win
        if (nbBilles == 1) {
            // if perfect win
            if (tabIm[3][3].getUse()) {
                builder.setMessage(getResources().getString(R.string.perfect_play));
                score = 0;
            } else {
                builder.setMessage(getResources().getString(R.string.win_play));
            }
        } else {
            builder.setMessage(getResources().getString(R.string.lose_play1) + " " + nbBilles + " " + getResources().getString(R.string.lose_play2));
        }

        final int finalScore = score;
        builder.setPositiveButton(getResources().getString(R.string.score), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //if nameplayer exist change activity & save
                if (!namePlayer.getText().toString().isEmpty()) {
                    getActivity().finish();
                    final Intent mainActivityIntent = new Intent(getActivity(), ScoreList.class);
                    startActivity(mainActivityIntent);
                    saveScore(finalScore, valueChrono, namePlayer.getText().toString());
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_palyer_name), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();


    }

    /***
     * Restart timer and add listener to button replay
     */
    @Override
    public void onResume() {
        super.onResume();

        updateScore();
        startChrono();

        btn_replay.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                replay();

            }
        });

    }

    /**
     * Start Chrono method
     */

    public void startChrono() {
        if (pauseTime != 0) {
            chrono.setBase(chrono.getBase() + SystemClock.elapsedRealtime() - pauseTime);
        } else {
            chrono.setBase(SystemClock.elapsedRealtime());
        }
        chrono.start();
    }

    /***
     * replay method
     */
    public void replay() {
        createCase();
        nbCoups = 0;
        nbBilles = 32;
        chrono.stop();
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        updateScore();
        playPress();

    }

    /***
     * Pause timer
     */
    @Override
    public void onPause() {
        super.onPause();

        pauseTime = SystemClock.elapsedRealtime();
        chrono.stop();

    }

    /***
     * Play pose sound
     */
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

    /***
     * Play depose sound
     */
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

    /***
     *Play press sound
     */
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

    /***
     * Save score to SharedPreferences with sort of them
     * @param score
     * @param chrono
     * @param name
     */
    private void saveScore(int score, long chrono, String name) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        //Get list score on shared preferences
        Gson gson = new Gson();
        String json = prefs.getString("ListScore", "");
        Type type = new TypeToken<ArrayList<Score>>() {
        }.getType();
        listScore = gson.fromJson(json, type);
        //Compare score to set rank
        Score sctemp = null;
        if (listScore != null) {
            for (int i = 0; i < listScore.size(); i++) {
                if (listScore.get(i).getScore() >= score && listScore.get(i).getChrono() > chrono) {
                    sctemp = new Score(listScore.get(i).getPosition(), name, score, chrono);
                    for (int j = i; j < listScore.size(); j++) {
                        listScore.get(j).setPosition(listScore.get(j).getPosition() + 1);
                    }
                    break;
                }
            }
        } else {
            listScore = new ArrayList<Score>();
        }
        if (sctemp == null) {
            sctemp = new Score(listScore.size() + 1, name, score, chrono);
        }

        //Add score to list
        listScore.add(sctemp);
        //sort list by rank
        Collections.sort(listScore, Score.ScoreComparator);
        if (listScore.size() > 10) {
            for (int i = 10; i < listScore.size(); i++) {
                listScore.remove(i);
            }
        }

        //Serialize and save list score
        gson = new Gson();
        json = gson.toJson(listScore);
        editor.putString("ListScore", json);
        editor.apply();
    }


    /***
     * Update score text view
     */
    public void updateScore() {
        textViewScore.setText(getResources().getString(R.string.score) + " : " + nbCoups);
    }

}