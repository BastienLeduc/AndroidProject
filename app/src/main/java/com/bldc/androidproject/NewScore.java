package com.bldc.androidproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewScore extends Fragment {

    private TextView posPlayer = null;
    private TextView namePlayer = null;
    private TextView scorePlayer = null;
    private TextView timePlayer = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newscore, container, false);
        posPlayer = (TextView) view.findViewById(R.id.positionPlayer);
        namePlayer = (TextView) view.findViewById(R.id.namePlayer);
        scorePlayer = (TextView) view.findViewById(R.id.scorePlayer);
        timePlayer = (TextView) view.findViewById(R.id.timePlayer);
        return view;
    }
}
