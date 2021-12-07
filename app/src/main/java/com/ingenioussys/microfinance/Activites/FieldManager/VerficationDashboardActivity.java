package com.ingenioussys.microfinance.Activites.FieldManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.ingenioussys.microfinance.Activites.FieldManager.LoanApplications.LoanVerificationActivity;
import com.ingenioussys.microfinance.Activites.ProfileActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.AttendanceActivity;
import com.ingenioussys.microfinance.Activites.FieldManager.Survey.ViewSurveyActivity;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.Service.GPS_Service;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.ingenioussys.microfinance.Activites.FieldManager.Center.CenterVerficationActivity;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
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

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class VerficationDashboardActivity extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    View headerView;
    ImageView navigationIcon;
    TextView profile_name;
    PrefManager prefManager;
    Geocoder geocoder;
    List<Address> addresses;
    GPS_Service gps;
    ImageView profile_image,profile;
    ProgressDialog progressDialog;
    public Double latitude, longitude;
    public String CurrentAddress = "";
    public String cityName= "";
    CardView pending_center,meeting,survey_verification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        headerView = findViewById(R.id.header);

        prefManager =  new PrefManager(this);
        meeting =  findViewById(R.id.meeting);
        survey_verification =  findViewById(R.id.survey_verification);
        progressDialog  =  new ProgressDialog(this);
        progressDialog.setMessage("Checking..");
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationIcon = headerView.findViewById(R.id.navigationIcon);
        profile = headerView.findViewById(R.id.profile);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().hide();
        navigationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(VerficationDashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        profile_image = headerView.findViewById(R.id.profile_image);

        View nav_view = navigationView.getHeaderView(0);
        profile_name = nav_view.findViewById(R.id.profile_name);
        profile_image = nav_view.findViewById(R.id.profile_image);
        Glide.with(this).load(prefManager.getString("profile")).into(profile_image);
        profile_name.setText(prefManager.getString("employee_first_name")+" "+prefManager.getString("employee_last_name"));
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        getsLocation();
        CheckAttendance();
        //new SyncFromServer(VerficationDashboardActivity.this);
        pending_center =  findViewById(R.id.pending_center);
        pending_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(VerficationDashboardActivity.this, LoanVerificationActivity.class);
                startActivity(intent);
            }
        });
        survey_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(VerficationDashboardActivity.this, ViewSurveyActivity.class);
                startActivity(intent);
            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent =  new Intent(VerficationDashboardActivity.this, MeetingActivity.class);
             //   startActivity(intent);
            }
        });

        profile_name = headerView.findViewById(R.id.profile_name);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.survey)


        {
            Intent intent = new Intent(VerficationDashboardActivity.this, ViewSurveyActivity.class);
            startActivity(intent);
        }

        return true;
    }

    public void CheckAttendance()
    {
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
        Call<Result> call = service.CheckAttendance(prefManager.getString("employee_id"),prefManager.getString("branch_id"),prefManager.getString("bank_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    //logout.setVisibility(View.GONE);
                    //attendance.setVisibility(View.VISIBLE);
                    Toast.makeText(VerficationDashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(VerficationDashboardActivity.this, AttendanceActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    try {
                        Toast.makeText(VerficationDashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                        prefManager.setString("login_time",jsonarray.getJSONObject(0).getString("login_time"));
                        prefManager.setString("created_at",jsonarray.getJSONObject(0).getString("created_at"));
                        //logout.setVisibility(View.VISIBLE);
                        //attendance.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
                progressDialog.dismiss();
                //progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




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