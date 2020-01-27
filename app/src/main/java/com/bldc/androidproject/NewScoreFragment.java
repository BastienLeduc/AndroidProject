package com.bldc.androidproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewScoreFragment extends Fragment {

    private TextView posPlayer = null;
    private TextView namePlayer = null;
    private TextView scorePlayer = null;
    private TextView timePlayer = null;

    private String name;
    private String position;
    private String score;
    private String timer;


    @SuppressLint("ValidFragment")
    public NewScoreFragment(String position, String name, String score, String timer) {
        this.position = position;
        this.name = name;
        this.score = score;
        this.timer = timer;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newscore, container, false);
        posPlayer = (TextView) view.findViewById(R.id.positionPlayer);
        namePlayer = (TextView) view.findViewById(R.id.namePlayer);
        scorePlayer = (TextView) view.findViewById(R.id.scorePlayer);
        timePlayer = (TextView) view.findViewById(R.id.timePlayer);
        setName(this.name);
        setPosition(this.position);
        setScore(this.score);
        setTimer(this.timer);
        return view;
    }

    public void setName(String name) {
        this.name = name;
        namePlayer.setText(this.name);
    }

    public void setPosition(String position) {
        this.position = position;
        posPlayer.setText(this.position);
    }

    public void setScore(String score) {
        this.score = score;
        scorePlayer.setText(this.score);
    }

    public void setTimer(String timer) {
        this.timer = timer;
        timePlayer.setText(this.timer);
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getScore() {
        return score;
    }

    public String getTimer() {
        return timer;
    }
}
