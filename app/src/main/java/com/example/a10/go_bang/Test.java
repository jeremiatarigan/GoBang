package com.example.a10.go_bang;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class Test extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;
    CekLokasi cekLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mapView = findViewById(R.id.mapView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mapView = googleMap;
        cekLokasi = new CekLokasi();
        requestLocationUpdateFunction();

        // Add a marker in Sydney and move the camera
        LatLng medan = new LatLng(3.590293, 98.678576);
        //mMap.addMarker(new MarkerOptions().position(medan));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(medan));
    }


    class CekLokasi implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            LatLng posisi = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(posisi));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(posisi));
            //mMap.setMinZoomPreference(4f);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posisi, 15));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private void requestLocationUpdateFunction() {
        Log.i("xxxxx", "RequestLocation2");
        //Toast.makeText(context, "Request Lokasi", Toast.LENGTH_SHORT).show();

        if(locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //Toast.makeText(this, "Cek GPS", Toast.LENGTH_SHORT).show();

        locationManager.requestLocationUpdates(
                getProviderName(locationManager),
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                cekLokasi
        );

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                cekLokasi
        );
    }

    private String getProviderName(LocationManager locationManager) {

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
        criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
        criteria.setAltitudeRequired(false); // Choose if you use altitude.
        criteria.setBearingRequired(false); // Choose if you use bearing.
        criteria.setCostAllowed(false); // Choose if this provider can waste money :-)

        // Provide your criteria and flag enabledOnly that tells
        // LocationManager only to return active providers.
        return locationManager.getBestProvider(criteria, true);
    }

}
