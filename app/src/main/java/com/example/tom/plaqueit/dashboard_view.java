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

    private RecyclerView recyclerView;
    StickyRecyclerHeadersAdapter mAdapter;
    List<Plaque> within2kmPlaques;
    List<Plaque> within5kmPlaques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        ViewPager vPager = (ViewPager) findViewById(R.id.pager);
        TabLayout mTabs = (TabLayout) findViewById(R.id.tabs);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(appBar);
        vPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        mTabs.setupWithViewPager(vPager);

        initializeData();
        initializeAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void initializeData() {
        within2kmPlaques = new ArrayList<>();
        within2kmPlaques.add(new Plaque("Plaque 1", "Plaque 1 Description"));
        within2kmPlaques.add(new Plaque("Plaque 2", "Plaque 2 Description"));
        within2kmPlaques.add(new Plaque("Plaque 1", "Plaque 1 Description"));
        within2kmPlaques.add(new Plaque("Plaque 2", "Plaque 2 Description"));
        within2kmPlaques.add(new Plaque("Plaque 1", "Plaque 1 Description"));
        within2kmPlaques.add(new Plaque("Plaque 2", "Plaque 2 Description"));
        within2kmPlaques.add(new Plaque("Plaque 1", "Plaque 1 Description"));
        within2kmPlaques.add(new Plaque("Plaque 2", "Plaque 2 Description"));
        within2kmPlaques.add(new Plaque("Plaque 1", "Plaque 1 Description"));
        within2kmPlaques.add(new Plaque("Plaque 2", "Plaque 2 Description"));
        within2kmPlaques.add(new Plaque("Plaque 1", "Plaque 1 Description"));
        within2kmPlaques.add(new Plaque("Plaque 2", "Plaque 2 Description"));

    }

    private void initializeAdapter() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(within2kmPlaques);
        recyclerView.setAdapter(adapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);
    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
}
