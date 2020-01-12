package com.bldc.androidproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

@SuppressLint("ValidFragment")
public class Case extends Fragment {

    private ImageButton imgCase = null;
    private boolean use;
    private boolean state;

    public Case(boolean use, boolean state) {
        super();
        this.use = use;
        this.state = state;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case, container, false);
        imgCase = (ImageButton) view.findViewById(R.id.imgcase);
        if (!use && !state) {
            imgCase.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorBack));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(use) {
            imgCase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgCase.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorAccent));
                }
            });
        }
    }


}

