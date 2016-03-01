package com.example.tom.plaqueit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Tom on 01/03/16.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {
    private String fragments[] = {"Favourites", "Visited"};
    ArrayList<Plaque> plaques = new ArrayList<>();

    public ProfilePagerAdapter(FragmentManager fm, ArrayList<Plaque> plaques) {
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
