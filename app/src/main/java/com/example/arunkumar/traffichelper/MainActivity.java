package com.example.arunkumar.traffichelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

public class MainActivity extends AppCompatActivity {

    static String appip="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login=(Button) findViewById(R.id.login);
        final EditText ip=(EditText) findViewById(R.id.ip);

        Intent i=new Intent(getBaseContext(), MyService.class);

          startService(i);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appip=ip.getText().toString();
                Intent i =new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });


    }


}
