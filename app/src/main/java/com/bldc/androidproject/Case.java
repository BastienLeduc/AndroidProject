package com.bldc.androidproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Case extends Fragment {

    private ImageButton imgCase = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case, container, false);
        imgCase = (ImageButton) view.findViewById(R.id.imgcase);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        imgCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCase.setImageResource(R.drawable.test);
            }
        });

    }
}

