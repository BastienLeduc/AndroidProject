package com.bldc.androidproject;

import android.content.Context;
import android.util.TypedValue;
import android.widget.GridLayout;
import android.widget.ImageButton;

import java.util.Objects;

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
        int width = getContext().getResources().getDisplayMetrics().widthPixels/8;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, width, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, width, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
        params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
        params.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
        params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, Objects.requireNonNull(getContext()).getResources().getDisplayMetrics());
        params.columnSpec = GridLayout.spec(x);
        params.rowSpec = GridLayout.spec(y);
        this.setLayoutParams(params);
    }

    public Boolean getUse() {
        return use;
    }

    public void setUse(Boolean use) {
        this.use = use;
        if (this.use) {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimaryDark));
        } else {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorBackCase));
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
