package com.example.tom.plaqueit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Dashboard extends AppCompatActivity
        implements ItemList.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener,
        Map.OnFragmentInteractionListener {

    static ArrayList<Plaque> plaques;
    Context context;
    SessionManager session;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_view);

        context = getApplicationContext();
        session = new SessionManager(context);
        try {
            session.createUserSession(1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userId = session.getUserId();

        if (userId == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        ViewPager vPager = (ViewPager) findViewById(R.id.pager);
        TabLayout mTabs = (TabLayout) findViewById(R.id.tabs);

        Plaque.setDatabase(this);
        plaques = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            plaques.add(Plaque.getPlaqueByID(i));
        }

        setSupportActionBar(appBar);
        vPager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager(), plaques));
        mTabs.setupWithViewPager(vPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        if (userId > 0) {
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(true);
        } else {
            menu.getItem(2).setVisible(true);
            menu.getItem(3).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_profile:
                intent = new Intent(this, Profile.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<Plaque> getPlaques() {
        return plaques;
    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
}
