package com.example.tom.plaqueit;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Tom on 07/12/2015.
 */

class MyPagerAdapter extends FragmentPagerAdapter {

    private String fragments[] = {"List", "Map"};

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ItemList();
            case 1:
                return new Map(); // that will change to a Map view at some point
            default:
                return new Map();
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}
