package com.ingenioussys.microfinance.Activites.GroupManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.ingenioussys.microfinance.Activites.FieldManager.VerficationDashboardActivity;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.Service.GPS_Service;
import com.ingenioussys.microfinance.model.Branch;
import com.ingenioussys.microfinance.model.BranchArea;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class AttendanceActivity extends AppCompatActivity  implements OnMapReadyCallback {
    private GoogleMap mMap;
    GPS_Service gps;
    ArrayList<Branch> BranchList;

    PrefManager prefManager;
    ProgressDialog progressDialog;
    Button submitAttendance;
    List<String> attendace_type_list;
    Geocoder geocoder;
    List<Address> addresses;
    public Double latitude, longitude;
    String branch_id = "";
    String CurrentAddress="";
    List<BranchArea> areas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Mark Attendance");
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        submitAttendance = findViewById(R.id.submitAttendance);
        prefManager =  new PrefManager(this);

        attendace_type_list =  new ArrayList<>();
        attendace_type_list.add("Branch");
        attendace_type_list.add("Area");

        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        gps =  new GPS_Service(this);
        geocoder = new Geocoder(this, Locale.getDefault());


        submitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    addresses = geocoder. getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                    for (int i = 0; i < addresses.size(); i++) {
                        Address address = addresses.get(i);

                        for (int n = 0; n <= address.getMaxAddressLineIndex(); n++) {
                            CurrentAddress += " index n: " + n + ": " + address.getAddressLine(n) + ", ";
                        }
                    }

                    CurrentAddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                } catch (IOException e) {
                    e.printStackTrace();
                }

                progressDialog.show();
                OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
                okHttpClientBuilder
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                Request.Builder newRequest = request.newBuilder().header("Authorization", prefManager.getString("token"));
                                return chain.proceed(newRequest.build());
                            }
                        });
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_URL)
                        .client(okHttpClientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService service = retrofit.create(APIService.class);
                Call<Result> call = service.SubmitAttendance(prefManager.getString("employee_id"), prefManager.getString("branch_id"),  prefManager.getString("bank_id"),gps.getLatitude(),gps.getLongitude(),CurrentAddress);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("response", String.valueOf(response.body().getData()));
                        if (response.body().getError()) {
                            Toast.makeText(AttendanceActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                //prefManager.setString("branch_id",branch_id);
                               // Toast.makeText(AttendanceActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                                if(prefManager.getString("role_code").contains("TREX"))
                                {

                                    Intent intent = new Intent(AttendanceActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(prefManager.getString("role_code").contains("VFX"))
                                {

                                    Intent intent = new Intent(AttendanceActivity.this, VerficationDashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
            }
        } else {

            mMap.setMyLocationEnabled(true);
        }


        LatLng current = new LatLng(gps.getLatitude(), gps.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}