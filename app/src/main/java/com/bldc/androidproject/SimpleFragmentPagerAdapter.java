package com.bldc.androidproject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String name;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, String name) {
        super(fm);
        this.context = context;
        this.name = name;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Plateau(name);
            case 1:
                return new ScoreList();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.onglet1);
            case 1:
                return context.getResources().getString(R.string.onglet2);
            default:
                return null;
        }
    }
}
