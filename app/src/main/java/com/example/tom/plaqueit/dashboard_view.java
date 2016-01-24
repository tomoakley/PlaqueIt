package com.example.tom.plaqueit;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;

public class dashboard_view extends AppCompatActivity
        implements ItemList.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener,
        Map.OnFragmentInteractionListener {

    static ArrayList<Plaque> plaques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_view);

        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        ViewPager vPager = (ViewPager) findViewById(R.id.pager);
        TabLayout mTabs = (TabLayout) findViewById(R.id.tabs);

        setSupportActionBar(appBar);
        vPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        mTabs.setupWithViewPager(vPager);

        // initializeData();

        Plaques plaqueList = new Plaques(this);
        plaques = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            plaques.add(plaqueList.getPlaqueByID(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public ArrayList<Plaque> getPlaques() {
        return plaques;
    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
}
