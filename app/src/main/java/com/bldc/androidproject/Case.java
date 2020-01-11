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
    private boolean disable = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case, container, false);
        imgCase = (ImageButton) view.findViewById(R.id.imgcase);
        disable = true;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        imgCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCase.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorAccent));
            }
        });
    }


    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
        if (imgCase != null) {
            if (!disable)
                imgCase.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorBack));
        }
    }
}

