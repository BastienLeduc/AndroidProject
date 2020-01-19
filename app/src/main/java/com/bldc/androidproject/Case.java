package com.bldc.androidproject;

import android.content.Context;
import android.widget.ImageButton;

public class Case extends android.support.v7.widget.AppCompatImageButton {

    private Boolean state;

    public Case(Context context, boolean state) {
        super(context);
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
