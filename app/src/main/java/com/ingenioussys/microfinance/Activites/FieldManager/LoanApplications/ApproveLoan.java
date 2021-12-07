package com.ingenioussys.microfinance.Activites.FieldManager.LoanApplications;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ingenioussys.microfinance.Activites.FieldManager.Center.CenterVerficationActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.collection.CollectPayment;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.constant.Global;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class ApproveLoan extends AppCompatActivity {
    TextView applicant_name, applicant_email, applicant_no, applicant_add, loan_no, bank_name, area_name, branch_name, center_name, group_name;
    TextView loan_amount, monthly_emi, op_bal, closing_bal, emi_date;
    TextView   submit_emi, payment_status, payment_date;
    TextView opening_bal,sale_amount,asset_sale,deb_receipts,family_income,other_income,current_bal,outgoing_amount,wages;
    TextView income_tax,licensing,stationary_printing,repair_maintenance,motor_vehicle_ex,rents_rates,Loan,utilities;
    TextView credit_card_fees,interest_paid,bank_charges,advertisement_and_marketing,solicitor_fees,total_income;
    TextView purchase,accountant_fees,loan_cycle,marital_status,religion,cast,co_name,co_dob,ration_card_no,pan_card_no,nominee_name;
    TextView nominee_age,nominee_relation,saving_account_number,external_loan_account_number,loan_account_number;
    EditText get_emi;
    PrefManager prefManager;
    ImageView profile_image,profile_photo,pan_card,adhar_card,other_proof,bussiness_proof,signiture_proof;
    ProgressDialog progressDialog;
    String loan_distribution_emi_id;
    Button submit;
    RadioButton disapproved,approved;
     int approve_status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_loan);
        getSupportActionBar().setTitle(getIntent().getStringExtra("loan_application_no"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        get_emi = findViewById(R.id.get_emi);
        payment_date = findViewById(R.id.payment_date);

        /* Image object init */
        profile_photo = findViewById(R.id.profile_photo);
        pan_card = findViewById(R.id.pan_card);
        adhar_card = findViewById(R.id.adhar_card);
        other_proof = findViewById(R.id.other_proof);
        bussiness_proof = findViewById(R.id.bussiness_proof);
        signiture_proof = findViewById(R.id.signiture_proof);



        /* Textarea object init */
        sale_amount = findViewById(R.id.sale_amount);
        asset_sale = findViewById(R.id.asset_sale);
        deb_receipts = findViewById(R.id.deb_receipts);
        family_income = findViewById(R.id.family_income);
        other_income = findViewById(R.id.other_income);
        current_bal = findViewById(R.id.current_bal);
        outgoing_amount = findViewById(R.id.outgoing_amount);
        wages = findViewById(R.id.wages);
        income_tax = findViewById(R.id.income_tax);
        licensing = findViewById(R.id.licensing);
        stationary_printing = findViewById(R.id.stationary_printing);
        repair_maintenance = findViewById(R.id.repair_maintenance);
        motor_vehicle_ex = findViewById(R.id.motor_vehicle_ex);
        rents_rates = findViewById(R.id.rents_rates);
        Loan = findViewById(R.id.Loan);
        utilities = findViewById(R.id.utilities);
        credit_card_fees = findViewById(R.id.credit_card_fees);
        interest_paid = findViewById(R.id.interest_paid);
        bank_charges = findViewById(R.id.bank_charges);
        advertisement_and_marketing = findViewById(R.id.advertisement_and_marketing);
        solicitor_fees = findViewById(R.id.solicitor_fees);
        total_income = findViewById(R.id.total_income);
        purchase = findViewById(R.id.purchase);
        accountant_fees = findViewById(R.id.accountant_fees);
        loan_cycle = findViewById(R.id.loan_cycle);
        marital_status = findViewById(R.id.marital_status);
        religion = findViewById(R.id.religion);
        cast = findViewById(R.id.cast);
        co_name = findViewById(R.id.co_name);
        co_dob = findViewById(R.id.co_dob);
        ration_card_no = findViewById(R.id.ration_card_no);
        pan_card_no = findViewById(R.id.pan_card_no);
        nominee_name = findViewById(R.id.nominee_name);
        nominee_age = findViewById(R.id.nominee_age);
        nominee_relation = findViewById(R.id.nominee_relation);

        loan_amount = findViewById(R.id.loan_amount);
        saving_account_number = findViewById(R.id.saving_account_number);
        external_loan_account_number = findViewById(R.id.external_loan_account_number);
        loan_account_number = findViewById(R.id.loan_account_number);

        submit = findViewById(R.id.submit);
        payment_status = findViewById(R.id.payment_status);
        applicant_name = findViewById(R.id.applicant_name);
        disapproved = findViewById(R.id.disapproved);
        approved = findViewById(R.id.approved);
        applicant_email = findViewById(R.id.applicant_email);
        applicant_no = findViewById(R.id.applicant_no);
        applicant_add = findViewById(R.id.applicant_add);
        loan_no = findViewById(R.id.loan_no);
        bank_name = findViewById(R.id.bank_name);
        area_name = findViewById(R.id.area_name);
        branch_name = findViewById(R.id.branch_name);
        center_name = findViewById(R.id.center_name);
        group_name = findViewById(R.id.group_name);
        loan_amount = findViewById(R.id.loan_amount);
        monthly_emi = findViewById(R.id.monthly_emi);
        closing_bal = findViewById(R.id.closing_bal);
        op_bal = findViewById(R.id.op_bal);
        emi_date = findViewById(R.id.emi_date);
        profile_image = findViewById(R.id.profile_image);

        opening_bal = findViewById(R.id.opening_bal);

        getLoanInformation();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(ApproveLoan.this, ""+approve_status, Toast.LENGTH_SHORT).show();
                UpdateData();
            }
        });

        approved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    approve_status = 1;
                }
                else
                {
                    approve_status = 0;
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void UpdateData() {


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
        Call<Result> call = service.approve_loan_application_filed_manager(getIntent().getStringExtra("loan_application_no"), prefManager.getString("employee_id"),approve_status);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                progressDialog.dismiss();
                Toast.makeText(ApproveLoan.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                getLoanInformation();
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(ApproveLoan.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getLoanInformation() {
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
        Call<Result> call = service.get_loan_emi_employee_details(getIntent().getStringExtra("loan_application_no"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {


                try {
                    JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                    Log.d("loan_application", jsonarray.toString());
                    applicant_name.setText(jsonarray.getJSONObject(0).getString("applicant_name"));
                    applicant_no.setText(jsonarray.getJSONObject(0).getString("applicant_mob_no"));
                    applicant_email.setText(jsonarray.getJSONObject(0).getString("email_id"));
                    applicant_add.setText(jsonarray.getJSONObject(0).getString("address"));
                    loan_no.setText(jsonarray.getJSONObject(0).getString("loan_application_number"));
                    bank_name.setText(jsonarray.getJSONObject(0).getString("bank_name"));
                    area_name.setText(jsonarray.getJSONObject(0).getString("area_name"));
                    branch_name.setText(jsonarray.getJSONObject(0).getString("branch_name"));
                    center_name.setText(jsonarray.getJSONObject(0).getString("center_name"));
                    group_name.setText(jsonarray.getJSONObject(0).getString("group_name"));
                    opening_bal.setText(jsonarray.getJSONObject(0).getString("opening_bal"));
                    sale_amount.setText(jsonarray.getJSONObject(0).getString("sale_amount"));
                    asset_sale.setText(jsonarray.getJSONObject(0).getString("asset_sale"));
                    deb_receipts.setText(jsonarray.getJSONObject(0).getString("deb_receipts"));
                    family_income.setText(jsonarray.getJSONObject(0).getString("family_income"));
                    other_income.setText(jsonarray.getJSONObject(0).getString("other_income"));
                    current_bal.setText(jsonarray.getJSONObject(0).getString("current_bal"));
                    outgoing_amount.setText(jsonarray.getJSONObject(0).getString("outgoing_amount"));
                    wages.setText(jsonarray.getJSONObject(0).getString("wages"));
                    income_tax.setText(jsonarray.getJSONObject(0).getString("income_tax"));
                    licensing.setText(jsonarray.getJSONObject(0).getString("licensing"));
                    stationary_printing.setText(jsonarray.getJSONObject(0).getString("stationary_printing"));
                    repair_maintenance.setText(jsonarray.getJSONObject(0).getString("repair_maintenance"));
                    motor_vehicle_ex.setText(jsonarray.getJSONObject(0).getString("motor_vehicle_ex"));
                    rents_rates.setText(jsonarray.getJSONObject(0).getString("rents_rates"));
                    Loan.setText(jsonarray.getJSONObject(0).getString("Loan"));
                    utilities.setText(jsonarray.getJSONObject(0).getString("utilities"));
                    credit_card_fees.setText(jsonarray.getJSONObject(0).getString("credit_card_fees"));
                    interest_paid.setText(jsonarray.getJSONObject(0).getString("interest_paid"));
                    bank_charges.setText(jsonarray.getJSONObject(0).getString("bank_charges"));
                    advertisement_and_marketing.setText(jsonarray.getJSONObject(0).getString("advertisement_and_marketing"));
                    solicitor_fees.setText(jsonarray.getJSONObject(0).getString("solicitor_fees"));
                    total_income.setText(jsonarray.getJSONObject(0).getString("total_income"));
                    purchase.setText(jsonarray.getJSONObject(0).getString("purchase"));
                    accountant_fees.setText(jsonarray.getJSONObject(0).getString("accountant_fees"));
                    loan_cycle.setText(jsonarray.getJSONObject(0).getString("loan_cycle"));
                    marital_status.setText(jsonarray.getJSONObject(0).getString("marital_status"));
                    religion.setText(jsonarray.getJSONObject(0).getString("religion"));
                    cast.setText(jsonarray.getJSONObject(0).getString("cast"));
                    co_name.setText(jsonarray.getJSONObject(0).getString("co_name"));
                    co_dob.setText(jsonarray.getJSONObject(0).getString("co_dob"));
                    ration_card_no.setText(jsonarray.getJSONObject(0).getString("ration_card_no"));
                    pan_card_no.setText(jsonarray.getJSONObject(0).getString("pan_card_no"));
                    nominee_name.setText(jsonarray.getJSONObject(0).getString("nominee_name"));
                    nominee_age.setText(jsonarray.getJSONObject(0).getString("nominee_age"));
                    nominee_relation.setText(jsonarray.getJSONObject(0).getString("nominee_relation"));

                    Glide.with(getApplicationContext()).load(jsonarray.getJSONObject(0).getString("member_photo_pr")).into(profile_photo);

                    profile_photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(ApproveLoan.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            Window window = dialog.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.view_doc_diloge);


                            try {
                                ImageView document =  dialog.findViewById(R.id.document);
                                TextView  doc_name =  dialog.findViewById(R.id.doc_name);
                                //Toast.makeText(ApproveLoan.this, ""+jsonarray.getJSONObject(0).getString("member_photo_pr"), Toast.LENGTH_SHORT).show();
                                Glide.with(ApproveLoan.this).load(jsonarray.getJSONObject(0).getString("member_photo_pr")).into(document);
                                doc_name.setText("Profile Photo");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ImageView close =  dialog.findViewById(R.id.close);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });

                    Glide.with(getApplicationContext()).load(jsonarray.getJSONObject(0).getString("member_pan_card")).into(pan_card);

                    pan_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(ApproveLoan.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            Window window = dialog.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.view_doc_diloge);


                            try {
                                ImageView document =  dialog.findViewById(R.id.document);
                                TextView  doc_name =  dialog.findViewById(R.id.doc_name);
                                //Toast.makeText(ApproveLoan.this, ""+jsonarray.getJSONObject(0).getString("member_photo_pr"), Toast.LENGTH_SHORT).show();
                                Glide.with(ApproveLoan.this).load(jsonarray.getJSONObject(0).getString("member_pan_card")).into(document);
                                doc_name.setText("Pan Card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ImageView close =  dialog.findViewById(R.id.close);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    Glide.with(getApplicationContext()).load(jsonarray.getJSONObject(0).getString("member_adhar_card")).into(adhar_card);

                    adhar_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(ApproveLoan.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            Window window = dialog.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.view_doc_diloge);


                            try {
                                ImageView document =  dialog.findViewById(R.id.document);
                                TextView  doc_name =  dialog.findViewById(R.id.doc_name);
                                //Toast.makeText(ApproveLoan.this, ""+jsonarray.getJSONObject(0).getString("member_photo_pr"), Toast.LENGTH_SHORT).show();
                                Glide.with(ApproveLoan.this).load(jsonarray.getJSONObject(0).getString("member_adhar_card")).into(document);
                                doc_name.setText("Aadhar  Card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ImageView close =  dialog.findViewById(R.id.close);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    Glide.with(getApplicationContext()).load(jsonarray.getJSONObject(0).getString("member_other_proof")).into(other_proof);

                    other_proof.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(ApproveLoan.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            Window window = dialog.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.view_doc_diloge);


                            try {
                                ImageView document =  dialog.findViewById(R.id.document);
                                TextView  doc_name =  dialog.findViewById(R.id.doc_name);
                                //Toast.makeText(ApproveLoan.this, ""+jsonarray.getJSONObject(0).getString("member_photo_pr"), Toast.LENGTH_SHORT).show();
                                Glide.with(ApproveLoan.this).load(jsonarray.getJSONObject(0).getString("member_other_proof")).into(document);
                                doc_name.setText("Other Proof");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ImageView close =  dialog.findViewById(R.id.close);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    Glide.with(getApplicationContext()).load(jsonarray.getJSONObject(0).getString("member_new_business_activity")).into(bussiness_proof);


                    bussiness_proof.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(ApproveLoan.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            Window window = dialog.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.view_doc_diloge);


                            try {
                                ImageView document =  dialog.findViewById(R.id.document);
                                TextView  doc_name =  dialog.findViewById(R.id.doc_name);
                                //Toast.makeText(ApproveLoan.this, ""+jsonarray.getJSONObject(0).getString("member_photo_pr"), Toast.LENGTH_SHORT).show();
                                Glide.with(ApproveLoan.this).load(jsonarray.getJSONObject(0).getString("member_new_business_activity")).into(document);
                                doc_name.setText("Business Proof");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ImageView close =  dialog.findViewById(R.id.close);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    Glide.with(getApplicationContext()).load(jsonarray.getJSONObject(0).getString("member_photo_signature")).into(signiture_proof);

                    signiture_proof.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(ApproveLoan.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            Window window = dialog.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.view_doc_diloge);


                            try {
                                ImageView document =  dialog.findViewById(R.id.document);
                                TextView  doc_name =  dialog.findViewById(R.id.doc_name);
                                //Toast.makeText(ApproveLoan.this, ""+jsonarray.getJSONObject(0).getString("member_photo_pr"), Toast.LENGTH_SHORT).show();
                                Glide.with(ApproveLoan.this).load(jsonarray.getJSONObject(0).getString("member_photo_signature")).into(document);
                                doc_name.setText("Signature");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ImageView close =  dialog.findViewById(R.id.close);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    if (jsonarray.getJSONObject(0).getInt("approved_status") == 0) {
                        payment_status.setText("Pending");
                    } else {
                        payment_status.setText("Approved");
                    }

                    Glide.with(ApproveLoan.this).load(jsonarray.getJSONObject(0).getString("member_photo_pr")).into(profile_image);
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(ApproveLoan.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}