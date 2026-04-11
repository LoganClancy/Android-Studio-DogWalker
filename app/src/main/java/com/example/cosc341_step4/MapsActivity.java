package com.example.cosc341_step4;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cosc341_step4.notifications.NotificationDB;
import com.example.cosc341_step4.notifications.NotificationEntry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cosc341_step4.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private boolean isAddMarkerMode = false;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button backBtn = findViewById(R.id.btnBack);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // closes MapsActivity so user can't go back with back button
        });



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


    private void addMark(LatLng location) {
        String name;
        EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Marker Title")
                .setMessage("Enter a name for this marker:")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {

                    String title = input.getText().toString();

                    if (title.isEmpty()) {
                        title = "Custom Marker";
                    }

                    mMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(title)
                    );

                    NotificationDB.addMapNotif(title, location.toString());
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Kelowna locations
        LatLng ubco = new LatLng(49.9406, -119.3950);
        LatLng downtown = new LatLng(49.8880, -119.4960);
        LatLng mission = new LatLng(49.8350, -119.4650);

        // Add markers
        mMap.addMarker(new MarkerOptions().position(ubco).title("UBCO"));
        mMap.addMarker(new MarkerOptions().position(downtown).title("Downtown Kelowna"));
        mMap.addMarker(new MarkerOptions().position(mission).title("Lower Mission"));

        // Move camera to Kelowna (zoom level 12 is nice for city view)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubco, 12));

        mMap.setOnMapClickListener(latLng -> {
            if (isAddMarkerMode) {
                addMark(latLng);
            }
        });

    }


    public void onClick(View view) {
        Button btn = findViewById(R.id.AddMarker);
        TextView txt = findViewById(R.id.txtInstruction);

            isAddMarkerMode = !isAddMarkerMode;

            if (isAddMarkerMode) {
                btn.setText("Cancel");
                txt.setVisibility(View.VISIBLE);
            } else {
                btn.setText("Add Marker");
                txt.setVisibility(View.GONE);
            }
    }


}