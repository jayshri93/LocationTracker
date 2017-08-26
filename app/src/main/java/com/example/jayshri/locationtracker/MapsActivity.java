package com.example.jayshri.locationtracker;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ClusterManager.OnClusterItemClickListener {
    private List<Address> addresses ;
    private Geocoder geocoder;
    private GoogleMap mMap;
    private DBHelper dbHelper;
    private List<LocationEntity> locationEntities;
    private Handler mHandler;
    private int mInterval = 20000;
    private ClusterManager<LocationItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dbHelper = new DBHelper(this);
        addresses = new ArrayList<>();
        mHandler = new Handler();
        geocoder = new Geocoder(this, Locale.getDefault());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private void fetchLocation(){

        locationEntities = dbHelper.getAllLocation();
        mMap.clear();
        for(LocationEntity l : locationEntities){
            LatLng latLng = new LatLng(l.getLatitude(),l.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(" "));
            Log.v("-----------","fetchLocation");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpCluster();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
//                updateStatus(); //this function can change value of mInterval.
                addItems();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void setUpCluster() {
        mClusterManager = new ClusterManager<LocationItem>(this, mMap);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnCameraIdleListener(mClusterManager);
        addItems();
        startRepeatingTask();
        mClusterManager.setOnClusterItemClickListener(this);
    }

    private void addItems() {
        locationEntities = dbHelper.getAllLocation();
        int index = 0;
        for(LocationEntity l : locationEntities){
//            if( index ==0 ) {
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(l.getLatitude(),l.getLongitude()), 10));
//            }
            LocationItem item = new LocationItem(l.getLatitude(),l.getLongitude());
            mClusterManager.addItem(item);
        }
    }

    @Override
    public boolean onClusterItemClick(ClusterItem clusterItem) {
        LocationItem item = (LocationItem) clusterItem;
        Log.d("L-------------", ((LocationItem) clusterItem).getmLat()+"");
        try {
            addresses = geocoder.getFromLocation(item.getmLat(), item.getmLng(), 1);
            Log.d("------------address" , addresses.toString());
            Toast.makeText(getApplicationContext() ,addresses.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
