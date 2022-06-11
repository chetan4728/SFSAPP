package com.ingenioussys.microfinance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.GroupManager.DashboardActivity;
import com.ingenioussys.microfinance.Activites.FieldManager.VerficationDashboardActivity;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Role;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.UnsafeOkHttpClient;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class LoginActivity extends AppCompatActivity {

    EditText username,password,key;
    Button login;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    HintSpinner role_name;
    ArrayList<Role> roles;
    List<Role> List;
    int role_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        key = findViewById(R.id.key);
        role_name = findViewById(R.id.role_name);
        login = findViewById(R.id.login);
        progressDialog =  new ProgressDialog(this);
        prefManager =  new PrefManager(this);
        progressDialog.setMessage("Loading...");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty())
                {
                    username.setError("Enter Username");
                    username.requestFocus();
                }
                else if(password.getText().toString().isEmpty())
                {
                    password.setError("Enter Password");
                    password.requestFocus();
                }
                else if(key.getText().toString().isEmpty())
                {
                    key.setError("Enter Key");
                    key.requestFocus();
                }
                else
                {
                    _login();
                }
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        }else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        _GetRole();
        AsyncTask.execute(() -> {
            List<Role> list  =   AppDatabase.getDatabase(getApplicationContext()).roleDao().getAll();

            role_name.setAdapter(new HintSpinnerAdapter<Role>(getApplicationContext(), list, "Select Role") {
                @Override
                public String getLabelFor(Role role) {
                    return role.getRole_name();
                }

            });
            role_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    role_id = list.get(i).getRole_id();
                    //Toast.makeText(TakeSurveyActivity.this, ""+areas.get(i).getAreaId(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            }
        }
        else
        {
            finish();
        }
    }
    public void _login()
    {
       // Toast.makeText(this, ""+username.getText().toString(), Toast.LENGTH_SHORT).show();
        progressDialog.show();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<Result> call = service.Login(username.getText().toString(), password.getText().toString(), key.getText().toString(),"mobile",role_id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("response", String.valueOf(response.body().getMessage()));
                if (response.body().getError()) {
                   // Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                        if(jsonarray.length()>0) {
                            JSONObject jsonobject = jsonarray.getJSONObject(0);
                           // Log.d("Login", String.valueOf(jsonobject));
                            prefManager.setInt("Login", 1);
                            prefManager.setString("token", jsonobject.getString("token"));
                            prefManager.setString("employee_id", jsonobject.getString("employee_id"));
                            prefManager.setString("bank_id", jsonobject.getString("bank_id"));
                            prefManager.setString("area_id", jsonobject.getString("area_id"));
                            prefManager.setString("branch_id", jsonobject.getString("branch_id"));
                            prefManager.setString("employee_first_name", jsonobject.getString("employee_first_name"));
                            prefManager.setString("employee_last_name", jsonobject.getString("employee_last_name"));
                            prefManager.setString("employee_role_id", jsonobject.getString("employee_role_id"));
                            prefManager.setString("role_code", jsonobject.getString("role_code"));
                            prefManager.setString("profile", jsonobject.getString("profile"));
                            prefManager.setString("bank_prefix", jsonobject.getString("bank_prefix"));


                            if(jsonobject.getString("role_code").contains(getResources().getString(R.string.role_code_group_manager)))
                            {
                                Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(jsonobject.getString("role_code").contains(getResources().getString(R.string.role_code_field_manager)))
                            {
                                Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, VerficationDashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "No User Found For This Role" , Toast.LENGTH_SHORT).show();
                            }

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

    }

    public void _GetRole()
    {
        List =  new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<Result> call = service.GetRole();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("response", String.valueOf(response.body().getData()));
                if (response.body().getError()) {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {


                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());


                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            Role role =  new Role();


                            role.setRole_code(jsonarray.getJSONObject(i).getString("role_code"));
                            role.setRole_id(jsonarray.getJSONObject(i).getInt("role_id"));
                            role.setRole_name(jsonarray.getJSONObject(i).getString("role_name"));
                            List.add(role);
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(getApplicationContext()).roleDao().delete(role);
                                AppDatabase.getDatabase(getApplicationContext()).roleDao().insert(role);
                            });

                        }
                        role_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                role_id = List.get(i).getRole_id();
                                //Toast.makeText(TakeSurveyActivity.this, ""+areas.get(i).getAreaId(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        role_name.setAdapter(new HintSpinnerAdapter<Role>(getApplicationContext(), List, "Select Role") {
                            @Override
                            public String getLabelFor(Role role) {
                                return role.getRole_name();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}