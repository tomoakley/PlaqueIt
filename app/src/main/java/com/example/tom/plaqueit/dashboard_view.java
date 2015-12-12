package com.example.tom.plaqueit;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.tom.plaqueit.StickyHeadersRecyclerView.StickyRecyclerHeadersAdapter;
import com.example.tom.plaqueit.StickyHeadersRecyclerView.StickyRecyclerHeadersDecoration;
import com.example.tom.plaqueit.StickyHeadersRecyclerView.StickyRecyclerHeadersTouchListener;

import java.util.ArrayList;
import java.util.List;

public class dashboard_view extends AppCompatActivity
        implements ItemList.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener,
        ItemListHeader.OnFragmentInteractionListener, Map.OnFragmentInteractionListener {

    // private RecyclerView recyclerView;
    StickyRecyclerHeadersAdapter mAdapter;
    static List<Plaque> plaques;

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
        plaques.add(new Plaque("Plaque 1", "Plaque 1 Description", "15 points"));
        plaques.add(new Plaque("Plaque 2", "Plaque 2 Description", "25 points"));
        plaques.add(new Plaque("Plaque 3", "Plaque 3 Description", "5 points"));
        plaques.add(new Plaque("Plaque 4", "Plaque 4 Description", "20 points"));
        plaques.add(new Plaque("Plaque 5", "Plaque 5 Description", "30 points"));
        plaques.add(new Plaque("Plaque 6", "Plaque 6 Description", "10 points"));
        plaques.add(new Plaque("Plaque 7", "Plaque 7 Description", "25 points"));
        plaques.add(new Plaque("Plaque 8", "Plaque 8 Description", "30 points"));
        plaques.add(new Plaque("Plaque 9", "Plaque 9 Description", "10 points"));
        plaques.add(new Plaque("Plaque 10", "Plaque 10 Description", "15 points"));
        plaques.add(new Plaque("Plaque 11", "Plaque 11 Description", "20 points"));
        plaques.add(new Plaque("Plaque 12", "Plaque 12 Description", "10 points"));
    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
}
