package com.example.tom.plaqueit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class PlaquePage extends AppCompatActivity {

    Bundle bundle;
    int plaqueId;
    String title;
    String description;
    String points;
    Double latitude;
    Double longitude;
    TextView plaqueTitle;
    TextView plaqueDesc;
    TextView plaquePoints;
    Toolbar appBar;
    ImageButton favouriteBtn;
    SupportMapFragment map;
    int userId;
    static boolean favouriteStatus = false;
    SessionManager session;
    Context context;
    ClientServerInterface serverInstance;
    HashMap<String, String> requestParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaque_page);

        bundle = getIntent().getExtras();
        plaqueId = bundle.getInt("id");
        title = bundle.getString("title");
        description = bundle.getString("description");
        points = bundle.getString("points");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        context = getApplicationContext();
        session = new SessionManager(context);
        userId = session.getUserId();

        plaqueTitle = (TextView) findViewById(R.id.plaque_title);
        plaqueDesc = (TextView) findViewById(R.id.plaque_description);
        plaquePoints = (TextView) findViewById(R.id.plaque_points);
        appBar = (Toolbar) findViewById(R.id.app_bar);
        favouriteBtn = (ImageButton) findViewById(R.id.favourite_btn);
        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        serverInstance = new ClientServerInterface();
        requestParameters = new HashMap<>();
        requestParameters.put("userid", Integer.toString(userId));
        requestParameters.put("plaqueid", Integer.toString(plaqueId));

        setSupportActionBar(appBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        System.out.println("Lat: " + latitude + ", Long: " + longitude);

        plaqueTitle.setText(title);
        plaqueDesc.setText(description);
        plaquePoints.setText(points);

        try {
            serverInstance.getRequest("Plaque", "checkfavourite", requestParameters);
            if (favouriteStatus) {
                favouriteBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite, getTheme()));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng position = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(title));
                // Build camera position
                LatLng camera = new LatLng(latitude, longitude);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(camera)
                        .zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!favouriteStatus) {
                        serverInstance.getRequest("Plaque", "favourite", requestParameters);
                        favouriteBtn.setClickable(false);
                        if (favouriteStatus) {
                            Toast.makeText(PlaquePage.this, "Plaque favourited", Toast.LENGTH_SHORT).show();
                            favouriteBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite, getTheme()));
                        }
                        favouriteBtn.setClickable(true);
                    } else {
                        serverInstance.getRequest("Plaque", "unfavourite", requestParameters);
                        favouriteBtn.setClickable(false);
                        if (!favouriteStatus) {
                            favouriteBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_outline, getTheme()));
                        }
                        favouriteBtn.setClickable(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void setFavourite(boolean status) {
        favouriteStatus = status;
    }
}