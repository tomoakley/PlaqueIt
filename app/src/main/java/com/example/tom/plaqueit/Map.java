package com.example.tom.plaqueit;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Map.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

public class Map extends SupportMapFragment implements OnMapReadyCallback {

    private OnFragmentInteractionListener mListener;
    TextView plaqueMapTitle;
    TextView plaqueMapDesc;
    RelativeLayout plaqueMapData;
    ArrayList<Plaque> plaques = new ArrayList<>();
    String markerTitle;

    public Map() {
        // Required empty public constructor
    }

    public void createMapPlaques(GoogleMap map, ArrayList<Plaque> plaques) {
        Iterator<Plaque> iterator = plaques.iterator();
        Plaque plaque;
        while(iterator.hasNext()) {
            plaque = iterator.next();
            LatLng position = new LatLng(plaque.latitude, plaque.longtitude);
            map.addMarker(new MarkerOptions()
                    .position(position)
                    .title(plaque.title));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        plaqueMapTitle = (TextView) getView().findViewById(R.id.map_location_name);
        plaqueMapData = (RelativeLayout) getView().findViewById(R.id.plaque_map_data);
        plaqueMapDesc = (TextView) getView().findViewById(R.id.map_location_desc);

        // Get the data defined in the main activity (Dashboard.java) and create map markers from it
        Dashboard activity = (Dashboard) getActivity();
        plaques = activity.getPlaques();
        createMapPlaques(googleMap, plaques);

        // Build camera position
        LatLng camera = new LatLng(51.508357, -0.124365);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(camera)
                .zoom(17).build();

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                plaqueMapData.setVisibility(View.VISIBLE);
                markerTitle = marker.getTitle();
                plaqueMapTitle.setText(markerTitle);
                return false;
            }

        });

        // Zoom in and animate the camera
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
