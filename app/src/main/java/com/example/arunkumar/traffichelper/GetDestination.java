package com.example.arunkumar.traffichelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Arunkumar on 2/5/2017.
 */

public class GetDestination extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_destination);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                LatLng latLng=place.getLatLng();
               // Log.i(TAG, "Place: " + place.getName());
                Toast.makeText(GetDestination.this,"Place: " +latLng.latitude + " :" + latLng.longitude,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(GetDestination.this,"Error"+ status.getStatusMessage(),Toast.LENGTH_LONG).show();
                //Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}

