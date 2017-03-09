package com.example.theeranaiasipong.chanthaburifood;

import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.theeranaiasipong.chanthaburifood.model.MyLocation;
import com.example.theeranaiasipong.chanthaburifood.model.SharedPreferencesCheck;
import com.example.theeranaiasipong.chanthaburifood.model.getlisternerlocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, getlisternerlocation {

    ImageButton btnback;
    public MyLocation myLocation;
    public String serverkey = "AIzaSyADhgZNzP4jn99JgQax_ahRv2JsZ5ooPhM";
    public LatLng latLngStart, latLngStop;
    public GoogleMap gMap;
    public Boolean ststusLocation = false;
    public Double latfood, lngfood;
    public TextView textShowDiatacemap;
    public Marker myMarker, stopMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        latfood = bundle.getDouble("latfood");
        lngfood = bundle.getDouble("lngfood");

        latLngStop = new LatLng(latfood, lngfood);
        myLocation = new MyLocation(MapActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.viewmap);
        mapFragment.getMapAsync(this);

        gMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.viewmap)).getMap();


        btnback = (ImageButton) findViewById(R.id.btnback);
        textShowDiatacemap = (TextView) findViewById(R.id.textShowDiatacemap);

        btnback.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        myLocation.StartLocation();
        gMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.viewmap)).getMap();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocation.Stoplocation();
        gMap.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myLocation.StartLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocation.Stoplocation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SharedPreferencesCheck.Logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final LatLng coordinateDefult = new LatLng(15.391641, 101.5593225);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinateDefult, 4));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnback:
                finish();
                break;
        }
    }

    @Override
    public void getLatliogLocation(Location location) {

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        Log.e("lat", String.valueOf(location.getLatitude()));

        if (myMarker != null)
            myMarker.remove();

        myMarker = gMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.markstart)).title("ตำแหน่งของคุณ"));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16));

        CreateDirectionMap(location);
    }

    public void CreateDirectionMap(Location location) {

        Double lat = null;
        Double longt = null;

         lat = location.getLatitude();
         longt = location.getLongitude();

        if (lat == null)
            lat = 13.7335222;
        if (longt == null)
            longt = 100.5375236;

        latLngStart = new LatLng(lat, longt);

        if (latLngStart != null) {
            GoogleDirection.withServerKey(serverkey)
                    .from(latLngStart)
                    .to(latLngStop)
                    .transportMode(TransportMode.DRIVING)
                    .language(Language.THAI)
                    .unit(Unit.METRIC)
                    .transitMode(TransitMode.BUS)
                    .alternativeRoute(true)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            String status = direction.getStatus();
                            if (status.equals(RequestResult.OK)) {

                                Route route = direction.getRouteList().get(0);
                                Leg leg = route.getLegList().get(0);
                                ArrayList<LatLng> pointList = leg.getDirectionPoint();

                                Info distanceInfo = leg.getDistance();
                                Info durationInfo = leg.getDuration();

                                String distance = distanceInfo.getText();
                                String duration = durationInfo.getText();

                                textShowDiatacemap.setText("ระยะทาง " + distance);

                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();

                                PolylineOptions polygonOptions = DirectionConverter
                                        .createPolyline(MapActivity.this, directionPositionList, 5, Color.RED);

                                if (polygonOptions != null)
                                    gMap.addPolyline(polygonOptions).remove();
                                gMap.addPolyline(polygonOptions);
                                if (stopMarker != null)
                                    stopMarker.remove();
                                stopMarker = gMap.addMarker(new MarkerOptions().position(latLngStop).icon(BitmapDescriptorFactory.fromResource(R.drawable.markstop)).title("ร้านอาหาร"));

                            } else if (status.equals(RequestResult.REQUEST_DENIED)) {

                            } else if (status.equals(RequestResult.UNKNOWN_ERROR)) {

                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {

                        }
                    });
        }
    }


}
