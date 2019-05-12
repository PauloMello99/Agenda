package com.example.agenda.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.agenda.models.User;
import com.example.agenda.providers.UserDAO;
import com.example.agenda.utils.Locator;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = getCoordinatesFromAddress("R. Alexandre Herculano, 120 - T6 - Vila Monteiro, Piracicaba - SP, 13418-445");
        if(latLng!=null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,14);
            googleMap.moveCamera(update);
        }

        // SET USERS ADDRESS
        UserDAO userDAO = new UserDAO(getContext());
        for(User user: userDAO.searchAll()){
            LatLng position = getCoordinatesFromAddress(user.getAddress());
            if(position != null){
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(position);
                markerOptions.title(user.getName());
                markerOptions.snippet(String.valueOf(user.getScore()));
                googleMap.addMarker(markerOptions);
            }
        }

        //GPS
        new Locator(getContext(),googleMap);
    }

    private LatLng getCoordinatesFromAddress(String address){
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> results = geocoder.getFromLocationName(address, 1);
            if(!results.isEmpty())return new LatLng(results.get(0).getLatitude(),results.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
