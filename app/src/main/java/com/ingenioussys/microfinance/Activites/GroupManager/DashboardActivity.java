package com.ingenioussys.microfinance.Activites.GroupManager;

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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import com.ingenioussys.microfinance.Activites.ProfileActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanApplicationActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.Survey.ManageSurveyActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.Survey.ViewSurveyActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.collection.CollectionActivity;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.Service.GPS_Service;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Survey;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.ingenioussys.microfinance.utility.SyncToServer;

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


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    View headerView;
    ImageView navigationIcon;
    TextView profile_name;
    PrefManager prefManager;
    Geocoder geocoder;
    List<Address> addresses;
    GPS_Service gps;
    private InterstitialAd mInterstitialAd;
   ImageView profile_image,profile;
    ProgressDialog progressDialog;
    public Double latitude, longitude;
    public String CurrentAddress = "";
    public String cityName= "";
    CardView loanApplication,meeting,collection;
    List<Survey>  SurveyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        drawerLayout = findViewById(R.id.drawer_layout);
        headerView = findViewById(R.id.header);
        loanApplication = findViewById(R.id.loanApplication);
        prefManager =  new PrefManager(this);
        collection =  findViewById(R.id.collection);
        meeting =  findViewById(R.id.meeting);
        progressDialog  =  new ProgressDialog(this);
        progressDialog.setMessage("Checking..");
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationIcon = headerView.findViewById(R.id.navigationIcon);
        profile = headerView.findViewById(R.id.profile);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().hide();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

     /*   InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(DashboardActivity.this);
                Log.i("Loadedtest", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("Loadedtest", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });*/


        navigationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(DashboardActivity.this, CollectionActivity.class);
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
       // new SyncFromServer(DashboardActivity.this);
        loanApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(DashboardActivity.this, LoanApplicationActivity.class);
                startActivity(intent);
            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(DashboardActivity.this, MeetingActivity.class);
                startActivity(intent);
            }
        });

        profile_name = headerView.findViewById(R.id.profile_name);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.survey)
        {
            Intent intent = new Intent(DashboardActivity.this, ManageSurveyActivity.class);
            startActivity(intent);
        }

        else if(item.getItemId()==R.id.acq)
        {
            Intent intent = new Intent(DashboardActivity.this, LoanApplicationActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.meeting)
        {
            Intent intent = new Intent(DashboardActivity.this, LoanApplicationActivity.class);
            startActivity(intent);
        }

        else if(item.getItemId()==R.id.collection)
        {
            Intent intent = new Intent(DashboardActivity.this, LoanApplicationActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.center_location)
        {
            Intent intent = new Intent(DashboardActivity.this, LoanApplicationActivity.class);
            startActivity(intent);
        }


        else if(item.getItemId()==R.id.sync_from_server)
        {
           new SyncFromServer(DashboardActivity.this).getArea();
        }
        else if(item.getItemId()==R.id.sync_to_server)
        {
            new SyncToServer(DashboardActivity.this).UploadSurvey();
            new SyncToServer(DashboardActivity.this).UploadCenter();
            new SyncToServer(DashboardActivity.this).UploadGroup();
        }
        return true;
    }



    /*public void LogoutAttendance()
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
        Call<Result> call = service.SubmitAttendanceLogout(prefManager.getString("employee_id"),prefManager.getString("login_time"),prefManager.getString("created_at"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    logout.setVisibility(View.VISIBLE);
                    attendance.setVisibility(View.GONE);
                    Toast.makeText(DashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {

                    try {
                        Toast.makeText(DashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                        logout.setVisibility(View.GONE);
                        attendance.setVisibility(View.VISIBLE);
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




    }*/
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
                    Toast.makeText(DashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(DashboardActivity.this,AttendanceActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    try {
                        Toast.makeText(DashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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



    public void TakeSurvey(View view) {
        Intent intent =  new Intent(DashboardActivity.this, ViewSurveyActivity.class);
        startActivity(intent);
    }




}