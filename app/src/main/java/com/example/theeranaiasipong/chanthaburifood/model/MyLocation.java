package com.example.theeranaiasipong.chanthaburifood.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.example.theeranaiasipong.chanthaburifood.R;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

/**
 * Created by TheeranaiAsipong on 5/6/2559.
 */
public class MyLocation implements OnLocationUpdatedListener {

    Context context;
    getlisternerlocation getlisternerlocation;
    public double latltitude = 0;
    public double logtitude = 0;


    public MyLocation(Context context) {
        this.context = context;
        this.getlisternerlocation = (getlisternerlocation) this.context;
    }

    public double CalculationDistance(double myLat, double myLong, double foodLat, double foodLong) {
        double distace = 0;
        int R = 6371; //km
        double dlat = toRadians(foodLat - myLat);
        double dlog = toRadians(foodLong - myLong);

        myLat = toRadians(myLat);
        foodLat = toRadians(foodLat);

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.sin(dlog / 2) * Math.sin(dlog / 2) * Math.cos(myLat) * Math.cos(foodLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        distace = R * c;

        return distace;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI / 180);
    }

    @Override
    public void onLocationUpdated(Location location) {
        setLatltitude(location.getLatitude());
        setLogtitude(location.getLongitude());
        getlisternerlocation.getLatliogLocation(location);
    }

    public void StartLocation() {

        if (SmartLocation.with(context).location().state().locationServicesEnabled()) {
            LocationParams locationParams = new LocationParams.Builder()
                    .setAccuracy(LocationAccuracy.HIGH)
                    .setDistance(20)
                    .build();
            SmartLocation.with(context)
                    .location(new LocationGooglePlayServicesWithFallbackProvider(context))
                    .continuous()
                    .config(LocationParams.NAVIGATION)
                    .start(this);
        } else {
            locationServiceUnavailable();
        }

    }

    public void genLatlog(Location location) {

    }

    public void Stoplocation() {
        SmartLocation.with(context)
                .location()
                .stop();
    }

    public void FinalLocation() {
        Location location = SmartLocation.with(context)
                .location()
                .getLastLocation();

    }

    public void locationServiceUnavailable() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("เเจ้งเตือน!");
        builder.setMessage("บหริการหาตำเเหน่งมือถือของคุณถูกปิดอยู่!");
        builder.setPositiveButton("เปิด", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }

        });
        builder.setNegativeButton("ไม่ต้องการ", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    public double getLatltitude() {
        return latltitude;
    }

    public void setLatltitude(double latltitude) {
        this.latltitude = latltitude;
    }

    public double getLogtitude() {
        return logtitude;
    }

    public void setLogtitude(double logtitude) {
        this.logtitude = logtitude;
    }

}
