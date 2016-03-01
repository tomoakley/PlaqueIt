package com.example.tom.plaqueit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Tom on 07/12/2015.
 */

class DashboardPagerAdapter extends FragmentPagerAdapter {

    private String fragments[] = {"List", "Map"};
    ArrayList<Plaque> plaques = new ArrayList<>();

    public DashboardPagerAdapter(FragmentManager fm, ArrayList<Plaque> plaques) {
        super(fm);
        this.plaques = plaques;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("plaques", plaques);
        switch (position) {
            case 0:
                ItemList list = new ItemList();
                list.setArguments(bundle);
                return list;
            case 1:
                Map map = new Map();
                map.setArguments(bundle);
                return map;
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