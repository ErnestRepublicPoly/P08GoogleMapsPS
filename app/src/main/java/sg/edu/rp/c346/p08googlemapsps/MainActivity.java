package sg.edu.rp.c346.p08googlemapsps;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng poi_north = new LatLng(1.461708, 103.813500);
                final Marker north = map.addMarker(new
                        MarkerOptions()
                        .position(poi_north)
                        .title("North - HQ")
                        .snippet("Block 333, Admiralty Ave 3, 765654")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                LatLng poi_central = new LatLng(1.300592, 103.841226);
                final Marker central = map.addMarker(new
                        MarkerOptions()
                        .position(poi_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng poi_east = new LatLng(1.350057, 103.939452);
                final Marker east = map.addMarker(new
                        MarkerOptions()
                        .position(poi_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker.equals(north)){
                            north.showInfoWindow();
                            Toast.makeText(getApplicationContext(), "Block 333, Admiralty Ave 3, 765654 \nOperating hours: 10am-5pm \nTel:65433456", Toast.LENGTH_LONG).show();
                            return true;
                        }else if(marker.equals(central)){
                            central.showInfoWindow();
                            Toast.makeText(getApplicationContext(), "Block 3A, Orchard Ave 3, 134542 \nOperating hours: 11am-8pm \nTel:67788652", Toast.LENGTH_LONG).show();
                            return true;
                        }else if(marker.equals(east)){
                            east.showInfoWindow();
                            Toast.makeText(getApplicationContext(), "Block 555, Tampines Ave 3, 287788 \nOperating hours: 9am-5pm \nTel:66776677", Toast.LENGTH_LONG).show();
                            return true;
                        }else{
                            return false;
                        }
                    }
                });

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                UiSettings ui = map.getUiSettings();
                ui.setZoomControlsEnabled(true);
                ui.setCompassEnabled(true);
            }
        });
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner.getSelectedItem().toString().equals("All")){
                    LatLng poi_center = new LatLng(1.349229, 103.798172);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_center, 10));
                }else if(spinner.getSelectedItem().toString().equals("North")){
                    LatLng poi_north_move = new LatLng(1.461708, 103.813500);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north_move, 15));
                }else if(spinner.getSelectedItem().toString().equals("Central")){
                    LatLng poi_central_move = new LatLng(1.300592, 103.841226);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central_move, 15));
                }else if(spinner.getSelectedItem().toString().equals("East")){
                    LatLng poi_east_move = new LatLng(1.350057, 103.939452);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east_move, 15));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
