package com.example.tom.plaqueit;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class plaquePage extends AppCompatActivity {

    Bundle bundle;
    String title;
    String description;
    String points;
    Double latitude;
    Double longitude;
    TextView plaqueTitle;
    TextView plaqueDesc;
    TextView plaquePoints;
    Toolbar appBar;
    SupportMapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaque_page);

        bundle = getIntent().getExtras();
        title = bundle.getString("title");
        description = bundle.getString("description");
        points = bundle.getString("points");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        plaqueTitle = (TextView) findViewById(R.id.plaque_title);
        plaqueDesc = (TextView) findViewById(R.id.plaque_description);
        plaquePoints = (TextView) findViewById(R.id.plaque_points);
        appBar = (Toolbar) findViewById(R.id.app_bar);
        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        setSupportActionBar(appBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        System.out.println("Lat: " + latitude + ", Long: " + longitude);

        plaqueTitle.setText(title);
        plaqueDesc.setText(description);
        plaquePoints.setText(points);

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
    }
}
