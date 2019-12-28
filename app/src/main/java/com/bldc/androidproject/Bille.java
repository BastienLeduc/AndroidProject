package com.bldc.androidproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Bille extends Fragment {
    private ArrayList<ImageButton> listImageBille = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bille, container, false);
        listImageBille = new ArrayList<>();
        listImageBille.add((ImageButton) view.findViewById(R.id.imgBille1));
        listImageBille.add((ImageButton) view.findViewById(R.id.imgBille2));
        listImageBille.add((ImageButton) view.findViewById(R.id.imgBille3));
        listImageBille.add((ImageButton) view.findViewById(R.id.imgBille4));
        listImageBille.add((ImageButton) view.findViewById(R.id.imgBille5));
        listImageBille.add((ImageButton) view.findViewById(R.id.imgBille6));
        listImageBille.add((ImageButton) view.findViewById(R.id.imgBille7));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
