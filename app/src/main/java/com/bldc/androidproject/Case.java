package com.bldc.androidproject;

import android.content.Context;
import android.widget.ImageButton;

public class Case extends android.support.v7.widget.AppCompatImageButton {

    private Boolean state;
    private Boolean use;

    public Case(Context context, boolean state, boolean use) {
        super(context);
        setState(state);
        setUse(use);
    }

    public Boolean getUse() {
        return use;
    }

    public void setUse(Boolean use) {
        this.use = use;
        if (use) {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimaryDark));
            this.setActivated(true);
        } else {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorBack));
            this.setActivated(false);
        }

    }

    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        if (activated) {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimaryDark));
            this.state = false;
        } else {
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorBack));
        }
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
        if (state)
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimary));
        else
            this.setBackgroundColor(this.getContext().getResources().getColor(R.color.colorPrimaryDark));
    }
}
