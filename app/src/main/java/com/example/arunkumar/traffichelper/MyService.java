package com.example.arunkumar.traffichelper;

/**
 * Created by Arunkumar on 1/28/2017.
 */

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyService extends Service {

    private boolean isRunning  = false;
    double latitude=10,longitude=10;
    String sendlocation="http://192.168.1.6/reportLocation.php";
    GPSTracker gps;

    int test=0;


    @Override
    public void onCreate() {

        gps=new GPSTracker(MyService.this);
        isRunning = true;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        // bus=intent.getExtras().getString("bus");

        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                for (int i = 0; i < 20; i++) {
                    try {

                        Thread.sleep(30000);
                    } catch (Exception e) {
                    }

                    if(isRunning){
                        if(gps.canGetLocation()) {

                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            new sendlocation().execute();
                        }
                        test++;
                    }
                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning=false;
        Toast.makeText(this, "Stopped " + test, Toast.LENGTH_LONG).show();
    }


    private class sendlocation extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        //EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... args){


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("lat", "" + latitude));
            params2.add(new BasicNameValuePair("long", "" + longitude ));
            params2.add(new BasicNameValuePair("vid", "1" ));

            jsonobject = jParser2.makeHttpRequest(sendlocation, "GET", params2);

            try{

                String message = jsonobject.getString("success").toString();
                if( !(new String(message).equals("0"))){

                    return true;
                }

            }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == false){


                Toast.makeText(MyService.this,"fail",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MyService.this,"success",Toast.LENGTH_SHORT).show();

/*
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(MyService.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

*/

                //  temp.setText("");


            }
        }
    }



}