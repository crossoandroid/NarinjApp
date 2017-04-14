package com.orange_team.supplier.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service {
    double latService=0.0;
    double lngService=0.0;
    private LocationManager lm;
    Timer timer;
    final static long TIME = 1500;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        final Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                LocationUpdates();
                setupLocation();
                h.postDelayed(this, TIME);
            }
        });
    }

    public void LocationUpdates() {
        locListener locList = new locListener();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locList);
    }

    public void setupLocation()
    {
        FirebaseDatabase fbdb=FirebaseDatabase.getInstance();
        DatabaseReference ref=fbdb.getReference().getRef().child("Tracking");

        Log.d("kkkkkk",latService+":::::"+lngService);

        ref.child("latitude").setValue(latService);
        ref.child("longitude").setValue(lngService);
    }

    @Override
    public void onDestroy() {
        //lm.removeUpdates(this);
        timer.cancel();
    }

    public class locListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            latService = location.getLatitude();
            lngService = location.getLongitude();
            Log.d("llllll",latService+":::::"+ lngService);

        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}


    }
}
