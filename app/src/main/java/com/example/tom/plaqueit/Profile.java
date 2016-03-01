package com.example.tom.plaqueit;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile extends AppCompatActivity implements ItemList.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener,
        Map.OnFragmentInteractionListener {

    static ArrayList<Plaque> plaques = new ArrayList<>();
    TextView firstName;
    User user;
    ClientServerInterface serverInterface = new ClientServerInterface();
    HashMap<String, String> params = new HashMap<>();

    public static void setFavouritePlaques(ArrayList plaqueIds) {
        int id;
        System.out.println("plaqueIds: " + plaqueIds);
        for (int i = 0; i < plaqueIds.size(); i++) {
            id = (int) Math.floor((Double) plaqueIds.get(i));
            try {
                plaques.set(i, Plaque.getPlaqueByID(id));
            } catch (IndexOutOfBoundsException e) {
                plaques.add(Plaque.getPlaqueByID(id));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firstName = (TextView) findViewById(R.id.user_name);
        user = User.getUser();
        firstName.setText(user.firstName);
        params.put("userid", String.valueOf(user.id));
        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        ViewPager vPager = (ViewPager) findViewById(R.id.pager);
        TabLayout mTabs = (TabLayout) findViewById(R.id.tabs);
        try {
            serverInterface.getRequest("User", "getfavourites", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("plaques list: " + plaques);
        setSupportActionBar(appBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        vPager.setAdapter(new ProfilePagerAdapter(getSupportFragmentManager(), plaques));
        mTabs.setupWithViewPager(vPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
