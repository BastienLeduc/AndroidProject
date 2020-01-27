package com.bldc.androidproject.Entite;


import java.util.Comparator;

public class Score {

    private int position;
    private double timer;
    private String name;
    private int score;

    public Score(int position, String name, int score, double timer) {
        this.position = position;
        this.name = name;
        this.score = score;
        this.timer = timer;
    }

    public static Comparator<Score> ScoreComparator = new Comparator<Score>() {
        @Override
        public int compare(Score o1, Score o2) {
            return o1.getPosition() - o2.getPosition();
        }
    };

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getTimer() {
        return timer;
    }

    public void setTimer(double timer) {
        this.timer = timer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
