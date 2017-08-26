package com.example.jayshri.locationtracker;


public class LocationEntity {

    private int _id;
    private double latitude;
    private double longitude;

    public LocationEntity(){

    }

    public LocationEntity(int _id,double latitude,double longitude){
        this._id = _id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationEntity(double latitude,double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
