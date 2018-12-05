package com.example.mju_mobile_computing_final.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPageAdapter extends FragmentStatePagerAdapter {
    private int currentTab;

    public TabPageAdapter(FragmentManager fm, int currentTab) {
        super(fm);
        this.currentTab = currentTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MapFragment mapFragment = new MapFragment();
                return mapFragment;
            case 1:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return currentTab;
    }
}
