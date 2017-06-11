package com.adida.aka.testmap1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner mSpinnerType;
    ArrayList<String> mListType;
    ArrayAdapter<String> mArrayAdapter;
    ProgressDialog progressDialog;
    //10.862253, 106.619523
    //lang nghe vi tri hien tai cua thiet bi
    GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {

            LatLng loc = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if(mMap != null) {
                mMap.clear();
                    Marker mMarker = mMap.addMarker(new
                    MarkerOptions().position(loc));

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvent();
    }

    private void addEvent() {
        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xuLyCheDoHienThi(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void xuLyCheDoHienThi(int position) {
        switch (position) {
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 4:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;

        }
    }

    private void addControls() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(listener);

        mSpinnerType = (Spinner) findViewById(R.id.spinner);
        mListType    = new ArrayList<>();
        mListType.addAll(Arrays.asList(getResources().getStringArray(R.array.array_type)));
        mArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, mListType);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerType.setAdapter(mArrayAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Thong bao");
        progressDialog.setMessage("Dang tai map xin vui long cho");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //khi nao map load xong
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                progressDialog.dismiss();
                //lay vi tri theo vi do kinh do
                LatLng coc = new LatLng(10.862253, 106.619523);
                mMap.addMarker(new MarkerOptions().position(coc).title("Coc ")
                        .snippet("To hop anh hung vo lam"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coc, 14));
            }
        });
    }
}
