package com.rcp.rcparking.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonLineStringStyle;
import com.rcp.rcparking.R;
import com.rcp.rcparking.Travel;
import com.rcp.rcparking.activities.MainActivity;

import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by gcarves on 13/04/2017.
 */

public class TravelInfoFragment extends Fragment{

    EditText txtViewLength, txtViewDuration;
    ImageView imgValide;

    MapView mMapView;
    private GoogleMap googleMap;
    GeoJsonLayer layerPath = null;
    JSONObject jsonObject = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_travel_info, null);


        jsonObject = ((MainActivity) getActivity()).getPathGoogle();

        txtViewLength = (EditText) view.findViewById(R.id.txtViewLength);
        txtViewDuration = (EditText) view.findViewById(R.id.txtViewDuration);

        txtViewLength.setFocusable(false);
        txtViewDuration.setFocusable(false);

        Double distance = Travel.getDistance();
        Double time = Travel.getDuration();

        Double hours = time / 3600;
        Double minutes = (time % 3600) / 60;
        Double seconds = time % 60;

        int intHour = hours.intValue();
        int intMin = minutes.intValue();
        int intSec = seconds.intValue();

        MainActivity.takenTimeTravel = time.intValue();


        String timeString = String.format("%02dh %02dmin %02ds", intHour, intMin, intSec);

        txtViewLength.setText(String.valueOf(distance) + " km");
        txtViewDuration.setText(String.valueOf(timeString));


        imgValide = (ImageView) view.findViewById(R.id.imgValide);
        imgValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCarFragment selectCarFragment = new SelectCarFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectCarFragment,"selectCarFragment");
            }
        });

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                try {
                    ((MainActivity)getActivity()). getBounds(googleMap);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                layerPath = new GeoJsonLayer(googleMap, jsonObject);

                addStylePath(layerPath);
                layerPath.addLayerToMap();


            }
        });
        return view;

    }

    public void addStylePath(GeoJsonLayer layer){

        for (GeoJsonFeature feature : layer.getFeatures()) {
            GeoJsonLineStringStyle geoJsonLineStringStyle = new GeoJsonLineStringStyle();
            geoJsonLineStringStyle.setColor(Color.RED);
            geoJsonLineStringStyle.setWidth(15);
            feature.setLineStringStyle(geoJsonLineStringStyle);

        }
    }

}
