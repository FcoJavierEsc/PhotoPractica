package com.utad.photopractica.modelo.geo;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

//TODO: hacerlo dependiente de si el usuario quiere o no el GPS

public class GpsLocalication implements LocationListener {

    private LocationManager mLocationManager = null;

    private Context mContext;


    private static GpsLocalication sInstance = null;

    private static double sLongitude;
    private static double sLatitude;


    private String mString;
    private int mEstado = -1;
    private boolean gpsEnabled = false;


    public static LatLng getLatLng() {
        return new LatLng(getLatitud(), getLongitud());
    }

    public static double getLongitud() {
        return sLongitude;
    }

    public static double getLatitud() {
        return sLatitude;
    }

    public static boolean getInstance(Context context) {
        if (sInstance == null) {
            synchronized (GpsLocalication.class) {
                if (sInstance == null) {
                    sInstance = new GpsLocalication(context);
                    Log.v("GPS", "Arranco");
                }
            }
        }
        return sInstance != null;
    }


    private GpsLocalication(Context context) {
        mContext = context;
        gpsAsOn();
        setLocation(mLocationManager.getLastKnownLocation(mString));
    }

    private void setLocation(Location location) {

        if (location == null) return;

        sLatitude = location.getLatitude();
        sLongitude = location.getLongitude();
        Log.v("GPS", "NO TE MUEVAS que me mareas" + String.format("[%6.4f %6.4f ]", sLongitude, sLatitude));

    }

    private void gpsAsOn() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mString = mLocationManager.getBestProvider(new Criteria(), false);
        mLocationManager.requestLocationUpdates(mString, 1000, 2, this);//un segundo dos metros
        // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        setLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        gpsEnabled = false;
    }

    @Override
    public void onProviderEnabled(String provider) {
        gpsEnabled = true;
    }
/*
    public int colorEstado() {
        switch (mEstado) {
            case android.location.LocationProvider.AVAILABLE:
                return Color.GREEN;
            case android.location.LocationProvider.OUT_OF_SERVICE:
                return Color.RED;
            case android.location.LocationProvider.TEMPORARILY_UNAVAILABLE:
                return Color.YELLOW;
        }
        return Color.BLACK;
    }
*/
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        mEstado = status;

    }

}
