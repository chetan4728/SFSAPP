package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.FormFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.LoanApplicationCashFlow;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class LoanActivityTwo extends AppCompatActivity {
    PrefManager prefManager;
    EditText opening_bal,sale_amount,asset_sale,deb_receipts,family_income,other_income,total_income,purchase,accountant_fees,
            solicitor_fees,advertisement_and_marketing,bank_charges,interest_paid,credit_card_fees,utilities,
            Loan,rents_rates,motor_vehicle_ex,repair_maintenance,stationary_printing,licensing,
            income_tax,wages,outgoing_amount,current_bal;
    Button back,next;
    TextView loan_no;

    List<LoanApplicationCashFlow> loanApplicationCashFlow;
    ProgressDialog progressDialog;
    Boolean update_flag = false;
    long cash_flow_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_two);
        getSupportActionBar().setTitle("Cash Flow");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Submitting Data...");

        prefManager =  new PrefManager(this);
        opening_bal = findViewById(R.id.opening_bal);
        loan_no = findViewById(R.id.loan_no);
        loan_no.setText(getIntent().getStringExtra("loan_no"));
        sale_amount = findViewById(R.id.sale_amount);
        asset_sale = findViewById(R.id.asset_sale);
        deb_receipts = findViewById(R.id.deb_receipts);
        family_income = findViewById(R.id.family_income);
        other_income = findViewById(R.id.other_income);
        total_income = findViewById(R.id.total_income);
        purchase = findViewById(R.id.purchase);
        accountant_fees = findViewById(R.id.accountant_fees);
        solicitor_fees = findViewById(R.id.solicitor_fees);

        advertisement_and_marketing = findViewById(R.id.advertisement_and_marketing);
        bank_charges = findViewById(R.id.bank_charges);
        interest_paid = findViewById(R.id.interest_paid);
        credit_card_fees = findViewById(R.id.credit_card_fees);
        utilities = findViewById(R.id.utilities);
        Loan = findViewById(R.id.Loan);
        rents_rates = findViewById(R.id.rents_rates);
        motor_vehicle_ex = findViewById(R.id.motor_vehicle_ex);
        repair_maintenance = findViewById(R.id.repair_maintenance);
        stationary_printing = findViewById(R.id.stationary_printing);
        licensing = findViewById(R.id.licensing);
        income_tax = findViewById(R.id.income_tax);

        wages = findViewById(R.id.wages);
        outgoing_amount = findViewById(R.id.outgoing_amount);
        current_bal = findViewById(R.id.current_bal);

        sale_amount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Salecalculation();
            }
        });

        deb_receipts.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Salecalculation();
            }
        });
        asset_sale.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Salecalculation();
            }
        });

        family_income.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Salecalculation();
            }
        });

        other_income.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Salecalculation();
            }
        });

        purchase.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        accountant_fees.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        advertisement_and_marketing.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });


        bank_charges.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        interest_paid.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        credit_card_fees.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        utilities.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        Loan.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        rents_rates.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });


        motor_vehicle_ex.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        repair_maintenance.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        stationary_printing.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });
        licensing.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });
        solicitor_fees.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });
        income_tax.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });

        wages.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Purchasecalculation();
            }
        });
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        if(getIntent().getStringExtra("loan_application_no")!=null)
        {
            load_form_two_data();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update_flag)
                {
                    UpdateForm();
                }
                else
                {
                    nextForm();
                }

            }
        });
    }

    public void Salecalculation()
    {
        int total_income_value = 0;
        if (sale_amount.getText().length() != 0)
        {
            int current = Integer.parseInt(sale_amount.getText().toString());
            total_income.setText("");
            total_income_value = total_income_value + current;
            total_income.setText("" + total_income_value);
            current_bal.setText("" + total_income_value);
        }
        else
        {
            total_income.setText("" + total_income_value);
        }

        if (deb_receipts.getText().length() != 0)
        {
            int current = Integer.parseInt(deb_receipts.getText().toString());
            total_income_value = total_income_value + current;
            total_income.setText("" + total_income_value);
            current_bal.setText("" + total_income_value);
        }

        if (asset_sale.getText().length() != 0)
        {
            int current = Integer.parseInt(asset_sale.getText().toString());
            total_income_value = total_income_value + current;
            total_income.setText("" + total_income_value);
            current_bal.setText("" + total_income_value);
        }

        if (family_income.getText().length() != 0)
        {
            int current = Integer.parseInt(family_income.getText().toString());
            total_income_value = total_income_value + current;
            total_income.setText("" + total_income_value);
            current_bal.setText("" + total_income_value);
        }

        if (other_income.getText().length() != 0)
        {
            int current = Integer.parseInt(other_income.getText().toString());
            total_income_value = total_income_value + current;
            total_income.setText("" + total_income_value);
            current_bal.setText("" + total_income_value);
        }

    }


    public void Purchasecalculation()
    {
        int total_purchase_value = 0;
        int total_sale_amount = 0;
        if(!total_income.getText().toString().isEmpty()) {
            total_sale_amount = Integer.parseInt(total_income.getText().toString());
        }
        if (purchase.getText().length() != 0)
        {
            int current = Integer.parseInt(purchase.getText().toString());
            total_purchase_value = total_purchase_value + current;

            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }


        if (accountant_fees.getText().length() != 0)
        {
            int current = Integer.parseInt(accountant_fees.getText().toString());
            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (solicitor_fees.getText().length() != 0)
        {
            int current = Integer.parseInt(solicitor_fees.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (advertisement_and_marketing.getText().length() != 0)
        {
            int current = Integer.parseInt(advertisement_and_marketing.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (bank_charges.getText().length() != 0)
        {
            int current = Integer.parseInt(bank_charges.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (interest_paid.getText().length() != 0)
        {
            int current = Integer.parseInt(interest_paid.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (credit_card_fees.getText().length() != 0)
        {
            int current = Integer.parseInt(credit_card_fees.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (utilities.getText().length() != 0)
        {
            int current = Integer.parseInt(utilities.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (Loan.getText().length() != 0)
        {
            int current = Integer.parseInt(Loan.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (motor_vehicle_ex.getText().length() != 0)
        {
            int current = Integer.parseInt(motor_vehicle_ex.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (repair_maintenance.getText().length() != 0)
        {
            int current = Integer.parseInt(repair_maintenance.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (stationary_printing.getText().length() != 0)
        {
            int current = Integer.parseInt(stationary_printing.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (licensing.getText().length() != 0)
        {
            int current = Integer.parseInt(licensing.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (income_tax.getText().length() != 0)
        {
            int current = Integer.parseInt(income_tax.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }

        if (wages.getText().length() != 0)
        {
            int current = Integer.parseInt(wages.getText().toString());

            total_purchase_value = total_purchase_value + current;
            outgoing_amount.setText("" + total_purchase_value);
            int total_cal = total_sale_amount - total_purchase_value;
            current_bal.setText("" + total_cal);
        }


    }



    public void load_form_two_data()
    {

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
        Call<Result> call = service.get_loan_details(getIntent().getStringExtra("loan_application_no"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    Toast.makeText(LoanActivityTwo.this, "errror" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    Log.d("loans",response.body().getData().toString());


                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                        update_flag = true;
                        loan_no.setText(jsonarray.getJSONObject(0).getString("loan_application_number"));
                        cash_flow_id = jsonarray.getJSONObject(0).getInt("loan_application_cash_flow_id");
                        opening_bal.setText(jsonarray.getJSONObject(0).getString("opening_bal"));
                        sale_amount.setText(jsonarray.getJSONObject(0).getString("sale_amount"));
                        asset_sale.setText(jsonarray.getJSONObject(0).getString("asset_sale"));
                        deb_receipts.setText(jsonarray.getJSONObject(0).getString("deb_receipts"));
                        family_income.setText(jsonarray.getJSONObject(0).getString("family_income"));
                        other_income.setText(jsonarray.getJSONObject(0).getString("other_income"));
                        total_income.setText(jsonarray.getJSONObject(0).getString("total_income"));
                        purchase.setText(jsonarray.getJSONObject(0).getString("purchase"));
                        accountant_fees.setText(jsonarray.getJSONObject(0).getString("accountant_fees"));
                        solicitor_fees.setText(jsonarray.getJSONObject(0).getString("solicitor_fees"));
                        advertisement_and_marketing.setText(jsonarray.getJSONObject(0).getString("advertisement_and_marketing"));
                        bank_charges.setText(jsonarray.getJSONObject(0).getString("bank_charges"));
                        interest_paid.setText(jsonarray.getJSONObject(0).getString("interest_paid"));
                        credit_card_fees.setText(jsonarray.getJSONObject(0).getString("credit_card_fees"));
                        utilities.setText(jsonarray.getJSONObject(0).getString("utilities"));
                        Loan.setText(jsonarray.getJSONObject(0).getString("Loan"));
                        rents_rates.setText(jsonarray.getJSONObject(0).getString("rents_rates"));
                        repair_maintenance.setText(jsonarray.getJSONObject(0).getString("repair_maintenance"));
                        stationary_printing.setText(jsonarray.getJSONObject(0).getString("stationary_printing"));
                        licensing.setText(jsonarray.getJSONObject(0).getString("licensing"));
                        income_tax.setText(jsonarray.getJSONObject(0).getString("income_tax"));
                        wages.setText(jsonarray.getJSONObject(0).getString("wages"));
                        outgoing_amount.setText(jsonarray.getJSONObject(0).getString("outgoing_amount"));
                        current_bal.setText(jsonarray.getJSONObject(0).getString("current_bal"));
                        motor_vehicle_ex.setText(jsonarray.getJSONObject(0).getString("motor_vehicle_ex"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoanActivityTwo.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
       // Toast.makeText(this, "sdfsdf"+ getIntent().getStringExtra("loan_application_no"), Toast.LENGTH_SHORT).show();

//        AsyncTask.execute(() -> {
//            List<LoanApplicationCashFlow> LoanApplications = new ArrayList<>();
//
//            LoanApplications = AppDatabase.getDatabase(LoanActivityTwo.this).loanApplicationCashFlowDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")), Integer.parseInt(prefManager.getString("branch_id")), getIntent().getStringExtra("loan_application_no"));
//
//            for(int i=0;i<LoanApplications.size();i++)
//            {
//                Log.d("total",LoanApplications.get(i).getLoan_application_number());
//            }
//            if(LoanApplications.size() > 0) {
//                update_flag = true;
//                loan_no.setText(LoanApplications.get(0).getLoan_application_number());
//                cash_flow_id = LoanApplications.get(0).getLoan_application_cash_flow_id();
//                opening_bal.setText(LoanApplications.get(0).getOpening_bal());
//                sale_amount.setText(LoanApplications.get(0).getSale_amount());
//                asset_sale.setText(LoanApplications.get(0).getAsset_sale());
//                deb_receipts.setText(LoanApplications.get(0).getDeb_receipts());
//                family_income.setText(LoanApplications.get(0).getFamily_income());
//                other_income.setText(LoanApplications.get(0).getOther_income());
//                total_income.setText(LoanApplications.get(0).getTotal_income());
//                purchase.setText(LoanApplications.get(0).getPurchase());
//                accountant_fees.setText(LoanApplications.get(0).getAccountant_fees());
//                solicitor_fees.setText(LoanApplications.get(0).getSolicitor_fees());
//                advertisement_and_marketing.setText(LoanApplications.get(0).getAdvertisement_and_marketing());
//                bank_charges.setText(LoanApplications.get(0).getBank_charges());
//                interest_paid.setText(LoanApplications.get(0).getInterest_paid());
//                credit_card_fees.setText(LoanApplications.get(0).getCredit_card_fees());
//                utilities.setText(LoanApplications.get(0).getUtilities());
//                Loan.setText(LoanApplications.get(0).getLoan());
//                rents_rates.setText(LoanApplications.get(0).getRents_rates());
//                repair_maintenance.setText(LoanApplications.get(0).getRepair_maintenance());
//                stationary_printing.setText(LoanApplications.get(0).getStationary_printing());
//                licensing.setText(LoanApplications.get(0).getLicensing());
//                income_tax.setText(LoanApplications.get(0).getIncome_tax());
//                wages.setText(LoanApplications.get(0).getWages());
//                outgoing_amount.setText(LoanApplications.get(0).getOutgoing_amount());
//                current_bal.setText(LoanApplications.get(0).getCurrent_bal());
//                motor_vehicle_ex.setText(LoanApplications.get(0).getMotor_vehicle_ex());
//            }
//        });


    }

    public void UpdateForm()
    {
        if(opening_bal.getText().toString().isEmpty())
        {
            opening_bal.setError("Enter opening Balance");
            opening_bal.requestFocus();
        }
        else if(sale_amount.getText().toString().isEmpty())
        {
            sale_amount.setError("Enter Sale Amount");
            sale_amount.requestFocus();
        }
        else if(asset_sale.getText().toString().isEmpty())
        {
            asset_sale.setError("Enter Asset Sale");
            asset_sale.requestFocus();
        }
        else if(deb_receipts.getText().toString().isEmpty())
        {
            deb_receipts.setError("Enter Deb Receipts");
            deb_receipts.requestFocus();
        }
        else if(family_income.getText().toString().isEmpty())
        {
            family_income.setError("Enter Family Income");
            family_income.requestFocus();
        }
        else if(other_income.getText().toString().isEmpty())
        {
            other_income.setError("Enter Other Income");
            other_income.requestFocus();
        }

        else if(total_income.getText().toString().isEmpty())
        {
            total_income.setError("Enter Total Income");
            total_income.requestFocus();
        }
        else if(purchase.getText().toString().isEmpty())
        {
            purchase.setError("Enter Purchase");
            purchase.requestFocus();
        }
        else if(accountant_fees.getText().toString().isEmpty())
        {
            accountant_fees.setError("Enter Accountant Fees");
            accountant_fees.requestFocus();
        }
        else if(solicitor_fees.getText().toString().isEmpty())
        {
            solicitor_fees.setError("Enter solicitor fees");
            solicitor_fees.requestFocus();
        }

        else if(advertisement_and_marketing.getText().toString().isEmpty())
        {
            advertisement_and_marketing.setError("Enter Advertisement and Marketing");
            advertisement_and_marketing.requestFocus();
        }

        else if(bank_charges.getText().toString().isEmpty())
        {
            bank_charges.setError("Enter Bank Charges");
            bank_charges.requestFocus();
        }
        else if(interest_paid.getText().toString().isEmpty())
        {
            interest_paid.setError("Enter Interest Paid");
            interest_paid.requestFocus();
        }
        else if(credit_card_fees.getText().toString().isEmpty())
        {
            credit_card_fees.setError("Enter Credit Card Fees");
            credit_card_fees.requestFocus();
        }
        else if(utilities.getText().toString().isEmpty())
        {
            utilities.setError("Enter utilities");
            utilities.requestFocus();
        }
        else if(Loan.getText().toString().isEmpty())
        {
            Loan.setError("Enter Loan");
            Loan.requestFocus();
        }
        else if(rents_rates.getText().toString().isEmpty())
        {
            rents_rates.setError("Enter Rents Rates");
            rents_rates.requestFocus();
        }
        else if(motor_vehicle_ex.getText().toString().isEmpty())
        {
            motor_vehicle_ex.setError("Enter Motor Vehicle Ex");
            motor_vehicle_ex.requestFocus();
        }
        else if(repair_maintenance.getText().toString().isEmpty())
        {
            repair_maintenance.setError("Enter Repair Maintenance");
            repair_maintenance.requestFocus();
        }
        else if(stationary_printing.getText().toString().isEmpty())
        {
            stationary_printing.setError("Enter Stationary Printing");
            stationary_printing.requestFocus();
        }
        else if(licensing.getText().toString().isEmpty())
        {
            licensing.setError("Enter Licensing");
            licensing.requestFocus();
        }
        else if(income_tax.getText().toString().isEmpty())
        {
            income_tax.setError("Enter Income Tax");
            income_tax.requestFocus();
        }
        else if(wages.getText().toString().isEmpty())
        {
            wages.setError("Enter Wages");
            wages.requestFocus();
        }
        else if(outgoing_amount.getText().toString().isEmpty())
        {
            outgoing_amount.setError("Enter Outgoing Amount");
            outgoing_amount.requestFocus();
        }
        else if(current_bal.getText().toString().isEmpty())
        {
            current_bal.setError("Enter Current Balance");
            current_bal.requestFocus();
        }

        else {

            LoanApplicationCashFlow loanApplication =  new LoanApplicationCashFlow();
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setLoan_application_cash_flow_id((int) cash_flow_id);
            loanApplication.setLoan_application_id((int) getIntent().getLongExtra("id",0));
            loanApplication.setLoan_application_number(getIntent().getStringExtra("loan_application_no"));
            loanApplication.setOpening_bal(opening_bal.getText().toString());
            loanApplication.setSale_amount(sale_amount.getText().toString());
            loanApplication.setAsset_sale(asset_sale.getText().toString());
            loanApplication.setDeb_receipts(deb_receipts.getText().toString());
            loanApplication.setFamily_income(family_income.getText().toString());
            loanApplication.setOther_income(other_income.getText().toString());
            loanApplication.setTotal_income(total_income.getText().toString());
            loanApplication.setPurchase(purchase.getText().toString());
            loanApplication.setAccountant_fees(accountant_fees.getText().toString());
            loanApplication.setSolicitor_fees(solicitor_fees.getText().toString());
            loanApplication.setAdvertisement_and_marketing(advertisement_and_marketing.getText().toString());
            loanApplication.setBank_charges(bank_charges.getText().toString());
            loanApplication.setInterest_paid(interest_paid.getText().toString());
            loanApplication.setCredit_card_fees(credit_card_fees.getText().toString());
            loanApplication.setUtilities(utilities.getText().toString());
            loanApplication.setLoan(Loan.getText().toString());
            loanApplication.setRents_rates(rents_rates.getText().toString());
            loanApplication.setRepair_maintenance(repair_maintenance.getText().toString());
            loanApplication.setStationary_printing(stationary_printing.getText().toString());
            loanApplication.setLicensing(licensing.getText().toString());
            loanApplication.setIncome_tax(income_tax.getText().toString());
            loanApplication.setWages(wages.getText().toString());
            loanApplication.setOutgoing_amount(outgoing_amount.getText().toString());
            loanApplication.setCurrent_bal(current_bal.getText().toString());
            loanApplication.setMotor_vehicle_ex(motor_vehicle_ex.getText().toString());
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplication.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
           // progressDialog.show();
            loanApplicationCashFlow =  new ArrayList<>();
            loanApplicationCashFlow.add(loanApplication);
            UpdateToserver(loanApplicationCashFlow);
       
//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            AsyncTask.execute(() -> {
//                                AppDatabase.getDatabase(LoanActivityTwo.this).loanApplicationCashFlowDao().update(loanApplication);
//                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        new android.os.Handler().postDelayed(
//                                                new Runnable() {
//                                                    public void run() {
//                                                        progressDialog.dismiss();
//                                                        UpdateRowToServer(getIntent().getLongExtra("id",0));
//                                                        Intent intent =  new Intent(LoanActivityTwo.this,LoanActivityFour.class);
//                                                        Bundle bundle =  new Bundle();
//                                                        bundle.putLong("id",getIntent().getLongExtra("id",0));
//                                                        bundle.putString("loan_application_no",getIntent().getStringExtra("loan_application_no"));
//                                                        intent.putExtras(bundle);
//                                                        startActivity(intent);
//                                                    }
//                                                },
//                                                1000);
//                                    }
//                                });
//
//
//
//                            });
//                        }
//                    },
//                    2000);

        }
    }

    public void UpdateRowToServer(long last_inserted_id)
    {
        AsyncTask.execute(() -> {
            List<LoanApplicationCashFlow> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityTwo.this).loanApplicationCashFlowDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),getIntent().getStringExtra("loan_application_no"));

            for(int i=0 ;i< LoanApplications.size();i++)
            {
                Log.d("sdfsdfsdfsd",LoanApplications.get(i).getOpening_bal());
            }

            if(LoanApplications.size()>0) {
                UpdateToserver(LoanApplications);
            }

        });


    }
    public void insertRowToServer(long last_inserted_id)
    {
        AsyncTask.execute(() -> {
            List<LoanApplicationCashFlow> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityTwo.this).loanApplicationCashFlowDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),getIntent().getStringExtra("loan_application_no"));
            //  Log.d("sdfsdfsdfsd",LoanApplications.get(0).getApplicant_name());
            if(LoanApplications.size()>0) {
                SubmitSurveyServer(LoanApplications);
            }

        });


    }

    public void SubmitSurveyServer(List<LoanApplicationCashFlow> loanApplication)
    {
        //progressDialog.show();
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
        Call<Result> call = service.UpdatecashflowRow(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //  Log.d("testadfasdf", response.message());
                // Log.d("loanMe", String.valueOf(response.body().getMessage()));

                Intent intent =  new Intent(LoanActivityTwo.this,LoanActivityFour.class);
                Bundle bundle =  new Bundle();
                bundle.putLong("id",getIntent().getLongExtra("id",0));
                bundle.putString("loan_application_no",getIntent().getStringExtra("loan_application_no"));
                intent.putExtras(bundle);
                startActivity(intent);

                //  ((LoanApplicationForm) getActivity()).selectIndex(1,loanApplication.get(0).getLoan_application_id());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityTwo.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }
    public void UpdateToserver(List<LoanApplicationCashFlow> loanApplication)
    {
        //progressDialog.show();
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
        Call<Result> call = service.UpdatecashflowRow(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Intent intent =  new Intent(LoanActivityTwo.this,LoanActivityFour.class);
                Bundle bundle =  new Bundle();
                bundle.putLong("id",getIntent().getLongExtra("id",0));
                bundle.putString("loan_application_no",getIntent().getStringExtra("loan_application_no"));
                intent.putExtras(bundle);
                startActivity(intent);
                  //Log.d("testadfasdf", response.body().getMessage());
                //Toast.makeText(LoanActivityTwo.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityTwo.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }

    public void nextForm()
    {
        if(opening_bal.getText().toString().isEmpty())
        {
            opening_bal.setError("Enter opening Balance");
            opening_bal.requestFocus();
        }
        else if(sale_amount.getText().toString().isEmpty())
        {
            sale_amount.setError("Enter Sale Amount");
            sale_amount.requestFocus();
        }
        else if(asset_sale.getText().toString().isEmpty())
        {
            asset_sale.setError("Enter Asset Sale");
            asset_sale.requestFocus();
        }
        else if(deb_receipts.getText().toString().isEmpty())
        {
            deb_receipts.setError("Enter Deb Receipts");
            deb_receipts.requestFocus();
        }
        else if(family_income.getText().toString().isEmpty())
        {
            family_income.setError("Enter Family Income");
            family_income.requestFocus();
        }
        else if(other_income.getText().toString().isEmpty())
        {
            other_income.setError("Enter Other Income");
            other_income.requestFocus();
        }

        else if(total_income.getText().toString().isEmpty())
        {
            total_income.setError("Enter Total Income");
            total_income.requestFocus();
        }
        else if(purchase.getText().toString().isEmpty())
        {
            purchase.setError("Enter Purchase");
            purchase.requestFocus();
        }
        else if(accountant_fees.getText().toString().isEmpty())
        {
            accountant_fees.setError("Enter Accountant Fees");
            accountant_fees.requestFocus();
        }
        else if(solicitor_fees.getText().toString().isEmpty())
        {
            solicitor_fees.setError("Enter solicitor fees");
            solicitor_fees.requestFocus();
        }

        else if(advertisement_and_marketing.getText().toString().isEmpty())
        {
            advertisement_and_marketing.setError("Enter Advertisement and Marketing");
            advertisement_and_marketing.requestFocus();
        }

        else if(bank_charges.getText().toString().isEmpty())
        {
            bank_charges.setError("Enter Bank Charges");
            bank_charges.requestFocus();
        }
        else if(interest_paid.getText().toString().isEmpty())
        {
            interest_paid.setError("Enter Interest Paid");
            interest_paid.requestFocus();
        }
        else if(credit_card_fees.getText().toString().isEmpty())
        {
            credit_card_fees.setError("Enter Credit Card Fees");
            credit_card_fees.requestFocus();
        }
        else if(utilities.getText().toString().isEmpty())
        {
            utilities.setError("Enter utilities");
            utilities.requestFocus();
        }
        else if(Loan.getText().toString().isEmpty())
        {
            Loan.setError("Enter Loan");
            Loan.requestFocus();
        }
        else if(rents_rates.getText().toString().isEmpty())
        {
            rents_rates.setError("Enter Rents Rates");
            rents_rates.requestFocus();
        }
        else if(motor_vehicle_ex.getText().toString().isEmpty())
        {
            motor_vehicle_ex.setError("Enter Motor Vehicle Ex");
            motor_vehicle_ex.requestFocus();
        }
        else if(repair_maintenance.getText().toString().isEmpty())
        {
            repair_maintenance.setError("Enter Repair Maintenance");
            repair_maintenance.requestFocus();
        }
        else if(stationary_printing.getText().toString().isEmpty())
        {
            stationary_printing.setError("Enter Stationary Printing");
            stationary_printing.requestFocus();
        }
        else if(licensing.getText().toString().isEmpty())
        {
            licensing.setError("Enter Licensing");
            licensing.requestFocus();
        }
        else if(income_tax.getText().toString().isEmpty())
        {
            income_tax.setError("Enter Income Tax");
            income_tax.requestFocus();
        }
        else if(wages.getText().toString().isEmpty())
        {
            wages.setError("Enter Wages");
            wages.requestFocus();
        }
        else if(outgoing_amount.getText().toString().isEmpty())
        {
            outgoing_amount.setError("Enter Outgoing Amount");
            outgoing_amount.requestFocus();
        }
        else if(current_bal.getText().toString().isEmpty())
        {
            current_bal.setError("Enter Current Balance");
            current_bal.requestFocus();
        }

        else {
            LoanApplicationCashFlow loanApplication =  new LoanApplicationCashFlow();
            loanApplication.setOpening_bal(opening_bal.getText().toString());
            loanApplication.setSale_amount(sale_amount.getText().toString());
            loanApplication.setLoan_application_number(getIntent().getStringExtra("loan_application_no"));
            loanApplication.setAsset_sale(asset_sale.getText().toString());
            loanApplication.setDeb_receipts(deb_receipts.getText().toString());
            loanApplication.setFamily_income(family_income.getText().toString());
            loanApplication.setOther_income(other_income.getText().toString());
            loanApplication.setTotal_income(total_income.getText().toString());
            loanApplication.setPurchase(purchase.getText().toString());
            loanApplication.setAccountant_fees(accountant_fees.getText().toString());
            loanApplication.setSolicitor_fees(solicitor_fees.getText().toString());
            loanApplication.setAdvertisement_and_marketing(advertisement_and_marketing.getText().toString());
            loanApplication.setBank_charges(bank_charges.getText().toString());
            loanApplication.setInterest_paid(interest_paid.getText().toString());
            loanApplication.setCredit_card_fees(credit_card_fees.getText().toString());
            loanApplication.setUtilities(utilities.getText().toString());
            loanApplication.setLoan(Loan.getText().toString());
            loanApplication.setRents_rates(rents_rates.getText().toString());
            loanApplication.setRepair_maintenance(repair_maintenance.getText().toString());
            loanApplication.setStationary_printing(stationary_printing.getText().toString());
            loanApplication.setLicensing(licensing.getText().toString());
            loanApplication.setIncome_tax(income_tax.getText().toString());
            loanApplication.setWages(wages.getText().toString());
            loanApplication.setOutgoing_amount(outgoing_amount.getText().toString());
            loanApplication.setCurrent_bal(current_bal.getText().toString());
            loanApplication.setMotor_vehicle_ex(motor_vehicle_ex.getText().toString());
            loanApplication.setLoan_application_id((int) getIntent().getLongExtra("id",0));
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplication.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
            loanApplicationCashFlow =  new ArrayList<>();
            loanApplicationCashFlow.add(loanApplication);
            SubmitSurveyServer(loanApplicationCashFlow);

//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            AsyncTask.execute(() -> {
//                               long last_inserted_id =    AppDatabase.getDatabase(LoanActivityTwo.this).loanApplicationCashFlowDao().insert(loanApplication);
//                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        new android.os.Handler().postDelayed(
//                                                new Runnable() {
//                                                    public void run() {
//                                                         //Toast.makeText(LoanActivityTwo.this, ""+last_inserted_id, Toast.LENGTH_SHORT).show();
//                                                        insertRowToServer(last_inserted_id);
//                                                        progressDialog.dismiss();
//                                                        Intent intent =  new Intent(LoanActivityTwo.this,LoanActivityFour.class);
//                                                        Bundle bundle =  new Bundle();
//                                                        bundle.putLong("id",getIntent().getLongExtra("id",0));
//                                                        bundle.putString("loan_application_no",getIntent().getStringExtra("loan_application_no"));
//                                                        intent.putExtras(bundle);
//                                                        startActivity(intent);
//                                                    }
//                                                },
//                                                1000);
//                                    }
//                                });
//
//
//
//                            });
//                        }
//                    },
//                    2000);


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}