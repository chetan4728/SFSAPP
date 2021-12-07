package com.ingenioussys.microfinance.utility;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;

import com.ingenioussys.microfinance.Service.GPS_Service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BaseApplication extends Application {


    GPS_Service gps;
    Geocoder geocoder;
    List<Address> addresses;
    public Double latitude, longitude;
    public String CurrentAddress = "";
    public String cityName= "";
    @Override
    public void onCreate() {
        super.onCreate();
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        getsLocation();

    }
    public  void getsLocation()
    {
        gps =  new GPS_Service(this);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

        }




        try {
            addresses = geocoder. getFromLocation(latitude, longitude, 1);
            for (int i = 0; i < addresses.size(); i++) {
                Address address = addresses.get(i);

                for (int n = 0; n <= address.getMaxAddressLineIndex(); n++) {
                    CurrentAddress += " index n: " + n + ": " + address.getAddressLine(n) + ", ";
                }
            }

            CurrentAddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            cityName = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
