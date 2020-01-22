package com.bldc.androidproject;

import android.content.Context;
import android.widget.ImageButton;

public class Case extends android.support.v7.widget.AppCompatImageButton {

    private Boolean state;
    private Boolean use;
    private int x;
    private int y;

    public Case(Context context, boolean state, boolean use, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
        setState(state);
        setUse(use);
    }

    public Boolean getUse() {
        return use;
    }

    public void setUse(Boolean use) {
        this.use = use;
        if (this.use) {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimaryDark));
        } else {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorBack));
        }

    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
        if (this.state)
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimary));
        else
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimaryDark));
    }

    public int getXc() {
        return x;
    }

    public void setXc(int x) {
        this.x = x;
    }

    public int getYc() {
        return y;
    }

    public void setYc(int y) {
        this.y = y;
    }
}
