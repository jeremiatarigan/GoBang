package com.example.a10.go_bang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

class MyLocationListener implements LocationListener {

    private Double latitude = 0.0;
    private Double longitude = 0.0;
    private Boolean sending = false;
    private Context context;
    private DatabaseHelper databaseHelper;
    private LocationManager locationManager;
    private static final long
            MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0, // dalam Meters
            MINIMUM_TIME_BETWEEN_UPDATES = 60 * 60 * 1000; // dalam Milliseconds

    MyLocationListener(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.selectTable(Variabelglobal.driver);
        databaseHelper.hasRow();
        requestLocationUpdateFunction();
    }

    public void onLocationChanged(Location location) {

        longitude = location.getLongitude();
        latitude = location.getLatitude();
        try {
            //Log.i("xxxxxx", "Lokasi changed");
            //Toast.makeText(context, "Lokasi changed", Toast.LENGTH_SHORT).show();
            if (!sending) {
                Log.i("xxxxx", "Lat : " + String.valueOf(latitude));
                //Toast.makeText(context, "Simpan Lokasi", Toast.LENGTH_SHORT).show();
                new UpdateLokasiUser().execute();
            }
        } catch (Exception e) {
            //Variabelglobal.showAlertDialog(getContext(), e.toString());
            Log.i("xxxxx", e.toString());
        }
    }

    public void onStatusChanged(String s, int i, Bundle b) {
        //Toast.makeText(getContext(), "Status changed : " + s, Toast.LENGTH_SHORT).show();
    }

    public void onProviderDisabled(String s) {
        //Toast.makeText(getContext(), "Provider Disable", Toast.LENGTH_SHORT).show();
    }

    public void onProviderEnabled(String s) {
        //Toast.makeText(getContext(), "Provider Enable", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateLokasiUser extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            sending = true;
            JSONConnection jsonConnection = new JSONConnection();

            jsonConnection.addParameter(Variabelglobal.idDriver, databaseHelper.getIdDriver());
            jsonConnection.addParameter(Variabelglobal.latitude, latitude);
            jsonConnection.addParameter(Variabelglobal.longitude, longitude);

            String url = Variabelglobal.Url + "/myLocation.php";
            /*String url = Variabelglobal.getUrl() + Variabelglobal.PathApps + Variabelglobal.UrlProsesLokasi; */
            jsonConnection.proses(url);
            //Log.i("xxxxxx", jsonConnection.getResponString());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            sending = false;
        }
    }

    private void requestLocationUpdateFunction() {
        Log.i("xxxxx", "RequestLocation");
        //Toast.makeText(context, "Request Lokasi", Toast.LENGTH_SHORT).show();

        if(locationManager == null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                this
        );

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                this
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

    void stop() {
        if(locationManager != null)
            locationManager.removeUpdates(this);
        Variabelglobal.myLocationListener = null;
    }
}
