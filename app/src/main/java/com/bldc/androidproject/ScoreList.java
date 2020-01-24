package com.bldc.androidproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ScoreList extends Fragment {

    private LinearLayout listScore = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scorelist, container, false);
        listScore = (LinearLayout) view.findViewById(R.id.list_score);
        return view;
    }

    public void displayScore() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
