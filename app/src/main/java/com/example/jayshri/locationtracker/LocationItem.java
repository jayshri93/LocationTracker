package com.example.jayshri.locationtracker;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;



public class LocationItem implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private final String mSnippet;
    private final double mLat;
    private final double mLng;

    public LocationItem(double lat, double lng){
        mPosition = new LatLng(lat,lng);
        mLat = lat;
        mLng = lng;
        mTitle = null;
        mSnippet = null;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public double getmLat() {
        return mLat;
    }

    public double getmLng() {
        return mLng;
    }
}
