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
        ItemListHeader.OnFragmentInteractionListener, Map.OnFragmentInteractionListener {

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

        initializeData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void initializeData() {
        plaques = new ArrayList<>();
        plaques.add(new Plaque("Plaque 1", "Plaque 1 Description", "15 points", 51.5087972, -0.1249099));
        plaques.add(new Plaque("Plaque 2", "Plaque 2 Description", "25 points", 51.508680, -0.125500));
        plaques.add(new Plaque("Plaque 3", "Plaque 3 Description", "5 points", 51.509826, -0.123107));
        plaques.add(new Plaque("Plaque 4", "Plaque 4 Description", "20 points", 51.507616, -0.125177));
        plaques.add(new Plaque("Plaque 5", "Plaque 5 Description", "30 points", 51.507513, -0.125212));
    }

    public ArrayList<Plaque> getPlaques() {
        return plaques;
    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
}
