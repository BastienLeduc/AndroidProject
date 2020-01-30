package com.bldc.androidproject.Entite;


import java.util.Comparator;

public class Score {

    private int position;
    private long chrono;
    private String name;
    private int score;

    /***
     * Score Constructor to save with a list easier
     * @param position
     * @param name
     * @param score
     * @param chrono
     */
    public Score(int position, String name, int score, long chrono) {
        this.position = position;
        this.name = name;
        this.score = score;
        this.chrono = chrono;
    }

    /**
     * Comparator to compare position of a score
     */
    public static Comparator<Score> ScoreComparator = new Comparator<Score>() {
        @Override
        public int compare(Score o1, Score o2) {
            return o1.getPosition() - o2.getPosition();
        }
    };

    //Getters & Setters
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getChrono() {
        return chrono;
    }

    public void setChrono(long chrono) {
        this.chrono = chrono;
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
