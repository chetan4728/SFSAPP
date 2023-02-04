package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.FormFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanApplicationActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanTransactionActivity;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.LoanApplicationCashFlow;
import com.ingenioussys.microfinance.model.LoanApplicationDocument;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class LoanActivityFour extends AppCompatActivity {
    HintSpinner loan_cycle,marital_status,religion,cast,family_relation1,family_relation2,family_relation3,family_relation4,nominee_relation,loan_purpose;
    EditText co_name,co_dob,family_name1,family_name2,family_name3,family_name4,email_id,ration_card_no,pan_card_no,
            nominee_name,nominee_age,loan_amount;
    Button back,save;
    long loan_application_documents_id=0;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    Boolean update_flag = false;
    int req_code =0;
    ArrayList<String> loan_cycle_array = new ArrayList<>();;
    ArrayList<String> marital_status_array = new ArrayList<>();;
    ArrayList<String> religion_array = new ArrayList<>();;
    ArrayList<String> cast_array = new ArrayList<>();;
    ArrayList<String> relation_array = new ArrayList<>();;
    ArrayList<String> relation_array1 = new ArrayList<>();;
    ArrayList<String> loan_purpose_array = new ArrayList<>();;
    List<LoanApplicationDocument> loanApplicationDocuments;
    LoanApplicationDocument     loanApplicationsave =  new LoanApplicationDocument();
    LoanApplicationDocument     loanApplicationUpdate =  new LoanApplicationDocument();
    Map<String, RequestBody> map = new HashMap<>();
    List<LoanApplicationDocument> LoanApplications = new ArrayList<>();
    String loan_cycle_value="",marital_status_value,religion_value,cast_value,relation_value,loan_purpose_value,
            nominee_relation_value,family_relation1_value,family_relation2_value,family_relation3_value,family_relation4_value;

    String photo_upload_string="";
    String pan_upload_string="";
    String aadhar_upload_string="";
    String other_upload_string="";
    String bussiness_upload_string="";
    String sign_upload_string="";
    boolean photo_flag = false,pan_flag = false,adhar_flag = false,other_upload_flag =false,bussiness_flag = false,sing_flag = false;
    Uri photo_upload_uri,pan_upload_uri,aadhar_upload_uri,other_upload_uri,bussiness_upload_uri,sign_upload_uri;


    RequestBody photo_upload_fbody;
    RequestBody pan_upload_body;
    RequestBody aadhar_upload_body;
    RequestBody other_upload_body;
    RequestBody bussiness_upload_body;
    RequestBody sign_upload_body;

    boolean member_photo_pr_uploaded = false ,member_pan_card_uploaded = false,member_adhar_card_uploaded = false,member_other_proof_uploaded = false,member_photo_signature_uploaded = false,member_new_business_activity_uploaded = false;
    ImageView member_photo_pr,member_pan_card,member_adhar_card,member_other_proof,member_photo_signature,member_new_business_activity;
    Button upload_picture_member,upload_member_pan_card,upload_member_adhar_card,upload_member_other_proof,upload_member_new_business_activity,upload_member_photo_signature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_four);

        getSupportActionBar().setTitle("Document Uploading");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loan_cycle = findViewById(R.id.loan_cycle);
        marital_status = findViewById(R.id.marital_status);
        religion = findViewById(R.id.religion);
        cast = findViewById(R.id.cast);
        member_photo_pr = findViewById(R.id.member_photo_pr);
        member_pan_card = findViewById(R.id.member_pan_card);
        member_adhar_card = findViewById(R.id.member_adhar_card);
        member_photo_signature = findViewById(R.id.member_photo_signature);
        member_new_business_activity = findViewById(R.id.member_new_business_activity);
        member_other_proof = findViewById(R.id.member_other_proof);

        upload_picture_member = findViewById(R.id.upload_picture_member);
        upload_member_pan_card = findViewById(R.id.upload_member_pan_card);
        upload_member_adhar_card = findViewById(R.id.upload_member_adhar_card);
        upload_member_other_proof = findViewById(R.id.upload_member_other_proof);
        upload_member_new_business_activity = findViewById(R.id.upload_member_new_business_activity);
        upload_member_photo_signature = findViewById(R.id.upload_member_photo_signature);

        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        family_relation1 = findViewById(R.id.family_relation1);
        family_relation2 = findViewById(R.id.family_relation2);
        family_relation3 = findViewById(R.id.family_relation3);
        family_relation4 = findViewById(R.id.family_relation4);
        nominee_relation = findViewById(R.id.nominee_relation);
        loan_purpose  = findViewById(R.id.loan_purpose);
        prefManager =  new PrefManager(this);
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Submitting Data...");
        co_name = findViewById(R.id.co_name);
        co_dob = findViewById(R.id.co_dob);
        family_name1 = findViewById(R.id.family_name1);
        family_name2 = findViewById(R.id.family_name2);
        family_name3 = findViewById(R.id.family_name3);
        family_name4 = findViewById(R.id.family_name4);
        email_id = findViewById(R.id.email_id);
        ration_card_no = findViewById(R.id.ration_card_no);
        family_name3 = findViewById(R.id.family_name3);
        pan_card_no = findViewById(R.id.pan_card_no);
        nominee_name = findViewById(R.id.nominee_name);
        nominee_age = findViewById(R.id.nominee_age);
        loan_amount = findViewById(R.id.loan_amount);
        co_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(LoanActivityFour.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                co_dob.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);



                                //Toast.makeText(LoanActivityOne.this, ""+age, Toast.LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        load_form_two_data();

        upload_picture_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             ImagePicker imagePicker = new ImagePicker();

                imagePicker.Companion.with(LoanActivityFour.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)
                        .cameraOnly()//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                req_code = 1;
            }
        });

        upload_member_pan_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker imagePicker2 = new ImagePicker();
                imagePicker2.Companion.with(LoanActivityFour.this)
                        .crop()
                        .cameraOnly()//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                req_code = 2;
            }
        });

        upload_member_adhar_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker imagePicker3 = new ImagePicker();
                imagePicker3.Companion.with(LoanActivityFour.this)
                        .crop()
                        .cameraOnly()//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                req_code = 3;
            }
        });

        upload_member_other_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(LoanActivityFour.this)
                        .crop()
                        .cameraOnly()//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                req_code = 4;
            }
        });

        upload_member_new_business_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(LoanActivityFour.this)
                        .crop()
                        .cameraOnly()//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                req_code = 5;
            }
        });

        upload_member_photo_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(LoanActivityFour.this)
                        .crop()
                        .cameraOnly()//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                req_code = 6;
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update_flag)
                {
                    UpdateDocument();
                }
                else
                {
                    saveDocument();
                }

            }


        });



        loan_cycle_array.add("1");
        loan_cycle_array.add("2");
        loan_cycle_array.add("3");
        loan_cycle_array.add("4");
        loan_cycle_array.add("5");


        marital_status_array.add("Single");
        marital_status_array.add("Married");
        marital_status_array.add("Widowed");


        religion_array.add("Hindu");
        religion_array.add("Muslim");
        religion_array.add("Christan");
        religion_array.add("other");


        cast_array.add("SC");
        cast_array.add("ST");
        cast_array.add("OBC");
        cast_array.add("GENERAL");
        cast_array.add("OTHERS");


        relation_array.add("Aunt");
        relation_array.add("Brother");
        relation_array.add("Brother in Law");
        relation_array.add("Daughter");
        relation_array.add("Daughter in Law");
        relation_array.add("Father");
        relation_array.add("Father in Law");
        relation_array.add("Husband");
        relation_array.add("Uncle");
        relation_array.add("Mother");
        relation_array.add("Mother in Law");
        relation_array.add("Nephew");
        relation_array.add("Sister");
        relation_array.add("Sister in Law");
        relation_array.add("Son");
        relation_array.add("Son in Law");
        relation_array.add("Mother's sister");

        relation_array1.add("Aunt");
        relation_array1.add("Brother");
        relation_array1.add("Brother in Law");
        relation_array1.add("Daughter");
        relation_array1.add("Daughter in Law");
        relation_array1.add("Father");
        relation_array1.add("Father in Law");
        relation_array1.add("Husband");
        relation_array1.add("Uncle");
        relation_array1.add("Mother");
        relation_array1.add("Mother in Law");
        relation_array1.add("Nephew");
        relation_array1.add("Sister");
        relation_array1.add("Sister in Law");
        relation_array1.add("Son");
        relation_array1.add("Son in Law");
        relation_array1.add("Mother's sister");

        loan_purpose_array.add("Jewellery");
        loan_purpose_array.add("Mess");
        loan_purpose_array.add("Tailoring");
        loan_purpose_array.add("Vegetable");
        loan_purpose_array.add("Laundry");
        loan_purpose_array.add("Athari Sales");
        loan_purpose_array.add("Goat Rearing");
        loan_purpose_array.add("Milk Vending");
        loan_purpose_array.add("Grocery Stores");
        loan_purpose_array.add("General Stores");
        loan_purpose_array.add("Flower Selling");
        loan_purpose_array.add("Beauty Parlour");
        loan_purpose_array.add("Tent House");
        loan_purpose_array.add("Saree Selling");
        loan_purpose_array.add("Saree Work");
        loan_purpose_array.add("Ready Made");
        loan_purpose_array.add("Electric Shop");
        loan_purpose_array.add("Electric Repairing");
        loan_purpose_array.add("Vehicle Repairing or Garage");
        loan_purpose_array.add("Bicycle Stores");
        loan_purpose_array.add("Bangle Shop");
        loan_purpose_array.add("Footwear Shop");
        loan_purpose_array.add("Fish Shop");
        loan_purpose_array.add("Chicken Shop");
        loan_purpose_array.add("Mutton Shop");
        loan_purpose_array.add("Spices Selling");
        loan_purpose_array.add("Tea Powder Selling");
        loan_purpose_array.add("Tea Stall");
        loan_purpose_array.add("Hotel");
        loan_purpose_array.add("Animal Husbandry");
        loan_purpose_array.add("Scrap Business");
        loan_purpose_array.add("Pig Rearing");
        loan_purpose_array.add("Flour Mill");
        loan_purpose_array.add("Medical Stores");
        loan_purpose_array.add("Hair Saloon");
        loan_purpose_array.add("Snack Selling");
        loan_purpose_array.add("Papad Making Selling");
        loan_purpose_array.add("Incense Stick Selling");
        loan_purpose_array.add("Gold Smith");
        loan_purpose_array.add("Ladies Emporium");
        loan_purpose_array.add("Broom Making and Selling");
        loan_purpose_array.add("Catering");
        loan_purpose_array.add("Pan Shop");
        loan_purpose_array.add("House Made Jewellery");
        loan_purpose_array.add("Kirana Shop");
        loan_purpose_array.add("Construction Contractor");
        loan_purpose_array.add("Vegetables Producer");
        loan_purpose_array.add("Fruits Seller");
        loan_purpose_array.add("Fruits Producer");
        loan_purpose_array.add("Garment Shop");
        loan_purpose_array.add("Other Activities");

        loan_cycle.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, loan_cycle_array, "Loan Cycle"));
        marital_status.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, marital_status_array, "Marital Status"));
        religion.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, religion_array, "Religion"));
        cast.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, cast_array, "Cast"));
        family_relation1.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, relation_array1, "Family Relation 1"));
        family_relation2.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, relation_array, "Family Relation 2"));
        family_relation3.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, relation_array, "Family Relation 3"));
        family_relation4.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, relation_array, "Family Relation 4"));
        nominee_relation.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, relation_array, "Nominee Relation"));
        loan_purpose.setAdapter(new HintSpinnerAdapter<String>(LoanActivityFour.this, loan_purpose_array, "Loan Purpose"));

        loan_cycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loan_cycle_value = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        marital_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                marital_status_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                religion_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                cast_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nominee_relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                nominee_relation_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loan_purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                loan_purpose_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        family_relation1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                family_relation1_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        family_relation2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                family_relation2_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        family_relation3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                family_relation3_value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        family_relation4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                family_relation4_value  = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    private void UpdateDocument() {

       // Toast.makeText(this, ""+family_relation1_value, Toast.LENGTH_SHORT).show();
        if(co_name.getText().toString().isEmpty())
        {
            co_name.setError("Please Enter Co Applicant Name");
            co_name.requestFocus();
        }
        else if(co_dob.getText().toString().isEmpty())
        {
            co_dob.setError("Please Enter Co Applicant DOB");
            co_dob.requestFocus();
        }
        else if(family_name1.getText().toString().isEmpty())
        {
            family_name1.setError("Please Enter Co Applicant Family One");
            family_name1.requestFocus();
        }
        else if(family_name2.getText().toString().isEmpty())
        {
            family_name2.setError("Please Enter Co Applicant Family Two");
            family_name2.requestFocus();
        }
        else if(family_name3.getText().toString().isEmpty())
        {
            family_name3.setError("Please Enter Co Applicant Family Three");
            family_name3.requestFocus();
        }
        else if(family_name4.getText().toString().isEmpty())
        {
            family_name4.setError("Please Enter Co Applicant Family Four");
            family_name4.requestFocus();
        }
        else if(email_id.getText().toString().isEmpty())
        {
            email_id.setError("Please Enter Email Id");
            email_id.requestFocus();
        }
        else if(ration_card_no.getText().toString().isEmpty())
        {
            ration_card_no.setError("Please Enter Ration Card No ");
            ration_card_no.requestFocus();
        }
        else if(pan_card_no.getText().toString().isEmpty())
        {
            pan_card_no.setError("Please Enter Pan Card No ");
            pan_card_no.requestFocus();
        }

        else if(nominee_name.getText().toString().isEmpty())
        {
            nominee_name.setError("Please Enter Nominee Name");
            nominee_name.requestFocus();
        }

        else if(nominee_age.getText().toString().isEmpty())
        {
            nominee_age.setError("Please Enter Nominee Age");
            nominee_age.requestFocus();
        }
        else if(loan_amount.getText().toString().isEmpty())
        {
            loan_amount.setError("Please Enter Loan Amount");
            loan_amount.requestFocus();
        }
        else if(member_photo_pr_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Profile Photo", Toast.LENGTH_SHORT).show();
        }
        else if(member_pan_card_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Pan Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_adhar_card_uploaded == false)
        {
            Toast.makeText(this, "Please Upload Aadhar Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_new_business_activity_uploaded == false)
        {
            Toast.makeText(this, "Please Upload Bussiness Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_photo_signature_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Sign Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_other_proof_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Others proof ", Toast.LENGTH_SHORT).show();
        }

        else
        {


            loanApplicationUpdate.setLoan_application_document_id((int) loan_application_documents_id);
            loanApplicationUpdate.setLoan_application_id((int) getIntent().getLongExtra("id", 0));
            loanApplicationUpdate.setLoan_cycle(loan_cycle_value);
            loanApplicationUpdate.setLoan_application_number(getIntent().getStringExtra("loan_application_no"));
            loanApplicationUpdate.setMarital_status(marital_status_value);
            loanApplicationUpdate.setReligion(religion_value);
            loanApplicationUpdate.setCast(cast_value);
            loanApplicationUpdate.setCo_name(co_name.getText().toString());
            loanApplicationUpdate.setCo_dob(co_dob.getText().toString());
            loanApplicationUpdate.setFamily_name1(family_name1.getText().toString());
            loanApplicationUpdate.setFamily_name2(family_name2.getText().toString());
            loanApplicationUpdate.setFamily_name3(family_name3.getText().toString());
            loanApplicationUpdate.setFamily_name4(family_name4.getText().toString());
            loanApplicationUpdate.setFamily_relation1(family_relation1_value);
            loanApplicationUpdate.setFamily_relation2(family_relation2_value);
            loanApplicationUpdate.setFamily_relation3(family_relation3_value);
            loanApplicationUpdate.setFamily_relation4(family_relation4_value);
            loanApplicationUpdate.setLoan_purpose(loan_purpose_value);
            loanApplicationUpdate.setEmail_id(email_id.getText().toString());
            loanApplicationUpdate.setRation_card_no(ration_card_no.getText().toString());
            loanApplicationUpdate.setPan_card_no(pan_card_no.getText().toString());
            loanApplicationUpdate.setNominee_age(nominee_age.getText().toString());
            loanApplicationUpdate.setNominee_name(nominee_name.getText().toString());
            loanApplicationUpdate.setMember_photo_pr(photo_upload_string);
            loanApplicationUpdate.setMember_pan_card(pan_upload_string);
            loanApplicationUpdate.setMember_adhar_card(aadhar_upload_string);
            loanApplicationUpdate.setMember_other_proof(other_upload_string);
            loanApplicationUpdate.setMember_new_business_activity(bussiness_upload_string);
            loanApplicationUpdate.setMember_photo_signature(sign_upload_string);
            loanApplicationUpdate.setLoan_amount(loan_amount.getText().toString());
            loanApplicationUpdate.setNominee_relation(nominee_relation_value);
            loanApplicationUpdate.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplicationUpdate.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplicationUpdate.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));


            loanApplicationDocuments =  new ArrayList<>();
            loanApplicationDocuments.add(loanApplicationUpdate);
            SubmitSurveyServer(loanApplicationDocuments);
          //  Toast.makeText(this, ""+pan_upload_string, Toast.LENGTH_SHORT).show();

//            progressDialog.show();
//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            AsyncTask.execute(() -> {
//                                AppDatabase.getDatabase(LoanActivityFour.this).loanApplicationDocumentsDao().update(loanApplicationUpdate);
//                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        new android.os.Handler().postDelayed(
//                                                new Runnable() {
//                                                    public void run() {
//                                                        progressDialog.dismiss();
//                                                        UpdateRowToServer(getIntent().getLongExtra("id",0));
//                                                        Intent intent =  new Intent(LoanActivityFour.this, LoanTransactionActivity.class);
//                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                    Toast.makeText(LoanActivityFour.this, "errror" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    Log.d("loans",response.body().getData().toString());


                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                        update_flag = true;
                        loan_application_documents_id = jsonarray.getJSONObject(0).getInt("loan_application_document_id");
                        co_name.setText(jsonarray.getJSONObject(0).getString("co_name"));
                        co_dob.setText(jsonarray.getJSONObject(0).getString("co_dob"));
                        family_name1.setText(jsonarray.getJSONObject(0).getString("family_name1"));
                        family_name2.setText(jsonarray.getJSONObject(0).getString("family_name2"));
                        family_name3.setText(jsonarray.getJSONObject(0).getString("family_name3"));
                        family_name4.setText(jsonarray.getJSONObject(0).getString("family_name4"));
                        email_id.setText(jsonarray.getJSONObject(0).getString("email_id"));
                        pan_card_no.setText(jsonarray.getJSONObject(0).getString("pan_card_no"));
                        ration_card_no.setText(jsonarray.getJSONObject(0).getString("ration_card_no"));
                        nominee_age.setText(jsonarray.getJSONObject(0).getString("nominee_age"));
                        nominee_name.setText(jsonarray.getJSONObject(0).getString("nominee_name"));
                        loan_amount.setText(jsonarray.getJSONObject(0).getString("loan_amount"));
                        photo_upload_string= jsonarray.getJSONObject(0).getString("member_photo_pr");
                        pan_upload_string = jsonarray.getJSONObject(0).getString("member_pan_card");
                        aadhar_upload_string = jsonarray.getJSONObject(0).getString("member_adhar_card");
                        other_upload_string = jsonarray.getJSONObject(0).getString("member_other_proof");
                        bussiness_upload_string = jsonarray.getJSONObject(0).getString("member_new_business_activity");
                        sign_upload_string = jsonarray.getJSONObject(0).getString("member_photo_signature");

                        // Toast.makeText(LoanActivityFour.this, ""+pan_upload_string, Toast.LENGTH_SHORT).show();
                        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents/" +  photo_upload_string);
                        //Glide.with(LoanActivityFour.this).load(photo).placeholder(R.drawable.ic_survey).into(member_photo_pr);
                        Picasso.get().load(photo).into(member_photo_pr);

                        File pan_card = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + pan_upload_string);
                        // Glide.with(LoanActivityFour.this).load(pan_card).placeholder(R.drawable.ic_survey).into(member_pan_card);
                        Picasso.get().load(pan_card).into(member_pan_card);


                        File adhar_card = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/MicroFinance/Documents" + "/" + aadhar_upload_string);
                        // Glide.with(LoanActivityFour.this).load(adhar_card).placeholder(R.drawable.ic_survey).into(member_adhar_card);

                        Picasso.get().load(adhar_card).into(member_adhar_card);

                        File other_proof = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + other_upload_string);
                        //Glide.with(LoanActivityFour.this).load(other_proof).placeholder(R.drawable.ic_survey).into(member_other_proof);

                        Picasso.get().load(other_proof).into(member_other_proof);

                        File business_activity = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + bussiness_upload_string);
                        //Glide.with(LoanActivityFour.this).load(business_activity).placeholder(R.drawable.ic_survey).into(member_new_business_activity);
                        Picasso.get().load(business_activity).into(member_new_business_activity);

                        File sign_photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + sign_upload_string);
                        //Glide.with(LoanActivityFour.this).load(sign_photo).placeholder(R.drawable.ic_survey).into(member_photo_signature);

                        Picasso.get().load(sign_photo).into(member_photo_signature);

                        for(int m=0;m<relation_array1.size();m++)
                        {
                            //Log.d("dddddsd",LoanApplications.get(0).getFamily_name1()+"---"+ String.valueOf(relation_array1.get(m)));
                            if(relation_array1.get(m).equals(jsonarray.getJSONObject(0).getString("family_relation1")))
                            {
                                Log.d("checkded", String.valueOf(relation_array1.get(m)));
                                family_relation1.setSelection(m+1);
                            }
                        }


                        for(int i=0;i<loan_cycle_array.size();i++)
                        {
                            if(loan_cycle_array.get(i).equals(jsonarray.getJSONObject(0).getString("loan_cycle")))
                            {
                                loan_cycle.setSelection(i+1);
                            }
                        }

                        for(int i=0;i<marital_status_array.size();i++)
                        {
                            if(marital_status_array.get(i).equals(jsonarray.getJSONObject(0).getString("marital_status")))
                            {
                                marital_status.setSelection(i+1);
                            }
                        }

                        for(int i=0;i<religion_array.size();i++)
                        {
                            if(religion_array.get(i).equals(jsonarray.getJSONObject(0).getString("religion")))
                            {
                                religion.setSelection(i+1);
                            }
                        }

                        for(int i=0;i<cast_array.size();i++)
                        {
                            if(cast_array.get(i).equals(jsonarray.getJSONObject(0).getString("cast")))
                            {
                                cast.setSelection(i+1);
                            }
                        }



                        for(int i=0;i<relation_array1.size();i++)
                        {
                            if(relation_array1.get(i).equals(jsonarray.getJSONObject(0).getString("family_relation2")))
                            {

                                family_relation2.setSelection(1);
                            }
                        }

                        for(int i=0;i<relation_array1.size();i++)
                        {
                            if(relation_array1.get(i).equals(jsonarray.getJSONObject(0).getString("family_relation3")))
                            {
                                family_relation3.setSelection(i+1);
                            }
                        }
                        for(int i=0;i<relation_array1.size();i++)
                        {
                            if(relation_array1.get(i).equals(jsonarray.getJSONObject(0).getString("family_relation4")))
                            {
                                family_relation4.setSelection(i+1);
                            }
                        }
                        for(int i=0;i<loan_purpose_array.size();i++)
                        {
                            if(loan_purpose_array.get(i).equals(jsonarray.getJSONObject(0).getString("loan_purpose")))
                            {
                                loan_purpose.setSelection(i+1);
                            }
                        }

                        for(int i=0;i<relation_array.size();i++)
                        {
                            if(relation_array.get(i).equals(jsonarray.getJSONObject(0).getString("nominee_relation")))
                            {
                                nominee_relation.setSelection(i+1);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoanActivityFour.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                LoanApplications = AppDatabase.getDatabase(LoanActivityFour.this).loanApplicationDocumentsDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")), Integer.parseInt(prefManager.getString("branch_id")), getIntent().getStringExtra("loan_application_no"));
//                // add code which you want to run in background thread
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                        if(LoanApplications.size() > 0) {
//
//                            //Log.d("photo_added",LoanApplications.get(0).getMember_photo_pr());
//                            update_flag = true;
//                            loan_application_documents_id = LoanApplications.get(0).getLoan_application_document_id();
//                            co_name.setText( LoanApplications.get(0).getCo_name());
//                            co_dob.setText( LoanApplications.get(0).getCo_dob());
//                            family_name1.setText(LoanApplications.get(0).getFamily_name1());
//                            family_name2.setText(LoanApplications.get(0).getFamily_name2());
//                            family_name3.setText(LoanApplications.get(0).getFamily_name3());
//                            family_name4.setText(LoanApplications.get(0).getFamily_name4());
//                            email_id.setText(LoanApplications.get(0).getEmail_id());
//                            pan_card_no.setText(LoanApplications.get(0).getPan_card_no());
//                            ration_card_no.setText(LoanApplications.get(0).getRation_card_no());
//                            nominee_age.setText(LoanApplications.get(0).getNominee_age());
//                            nominee_name.setText(LoanApplications.get(0).getNominee_name());
//                            loan_amount.setText(LoanApplications.get(0).getLoan_amount());
//                             photo_upload_string= LoanApplications.get(0).getMember_photo_pr();
//                             pan_upload_string = LoanApplications.get(0).getMember_pan_card();
//                             aadhar_upload_string = LoanApplications.get(0).getMember_adhar_card();
//                             other_upload_string = LoanApplications.get(0).getMember_other_proof();
//                             bussiness_upload_string = LoanApplications.get(0).getMember_new_business_activity();
//                             sign_upload_string = LoanApplications.get(0).getMember_photo_signature();
//
//                           // Toast.makeText(LoanActivityFour.this, ""+pan_upload_string, Toast.LENGTH_SHORT).show();
//                           File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents/" +  photo_upload_string);
//                            //Glide.with(LoanActivityFour.this).load(photo).placeholder(R.drawable.ic_survey).into(member_photo_pr);
//                            Picasso.get().load(photo).into(member_photo_pr);
//
//                            File pan_card = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + pan_upload_string);
//                           // Glide.with(LoanActivityFour.this).load(pan_card).placeholder(R.drawable.ic_survey).into(member_pan_card);
//                            Picasso.get().load(pan_card).into(member_pan_card);
//
//
//                            File adhar_card = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/MicroFinance/Documents" + "/" + aadhar_upload_string);
//                           // Glide.with(LoanActivityFour.this).load(adhar_card).placeholder(R.drawable.ic_survey).into(member_adhar_card);
//
//                            Picasso.get().load(adhar_card).into(member_adhar_card);
//
//                            File other_proof = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + other_upload_string);
//                            //Glide.with(LoanActivityFour.this).load(other_proof).placeholder(R.drawable.ic_survey).into(member_other_proof);
//
//                            Picasso.get().load(other_proof).into(member_other_proof);
//
//                            File business_activity = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + bussiness_upload_string);
//                            //Glide.with(LoanActivityFour.this).load(business_activity).placeholder(R.drawable.ic_survey).into(member_new_business_activity);
//                            Picasso.get().load(business_activity).into(member_new_business_activity);
//
//                            File sign_photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents" + "/" + sign_upload_string);
//                            //Glide.with(LoanActivityFour.this).load(sign_photo).placeholder(R.drawable.ic_survey).into(member_photo_signature);
//
//                            Picasso.get().load(sign_photo).into(member_photo_signature);
//
//                            for(int m=0;m<relation_array1.size();m++)
//                            {
//                                //Log.d("dddddsd",LoanApplications.get(0).getFamily_name1()+"---"+ String.valueOf(relation_array1.get(m)));
//                                if(relation_array1.get(m).equals(LoanApplications.get(0).getFamily_relation1()))
//                                {
//                                    Log.d("checkded", String.valueOf(relation_array1.get(m)));
//                                    family_relation1.setSelection(m+1);
//                                }
//                            }
//
//
//                            for(int i=0;i<loan_cycle_array.size();i++)
//                            {
//                                if(loan_cycle_array.get(i).equals(LoanApplications.get(0).getLoan_cycle()))
//                                {
//                                    loan_cycle.setSelection(i+1);
//                                }
//                            }
//
//                            for(int i=0;i<marital_status_array.size();i++)
//                            {
//                                if(marital_status_array.get(i).equals(LoanApplications.get(0).getMarital_status()))
//                                {
//                                    marital_status.setSelection(i+1);
//                                }
//                            }
//
//                            for(int i=0;i<religion_array.size();i++)
//                            {
//                                if(religion_array.get(i).equals(LoanApplications.get(0).getReligion()))
//                                {
//                                    religion.setSelection(i+1);
//                                }
//                            }
//
//                            for(int i=0;i<cast_array.size();i++)
//                            {
//                                if(cast_array.get(i).equals(LoanApplications.get(0).getCast()))
//                                {
//                                    cast.setSelection(i+1);
//                                }
//                            }
//
//
//
//                            for(int i=0;i<relation_array1.size();i++)
//                            {
//                                if(relation_array1.get(i).equals(LoanApplications.get(0).getFamily_relation2()))
//                                {
//
//                                    family_relation2.setSelection(1);
//                                }
//                            }
//
//                            for(int i=0;i<relation_array1.size();i++)
//                            {
//                                if(relation_array1.get(i).equals(LoanApplications.get(0).getFamily_relation3()))
//                                {
//                                    family_relation3.setSelection(i+1);
//                                }
//                            }
//                            for(int i=0;i<relation_array1.size();i++)
//                            {
//                                if(relation_array1.get(i).equals(LoanApplications.get(0).getFamily_relation4()))
//                                {
//                                    family_relation4.setSelection(i+1);
//                                }
//                            }
//                            for(int i=0;i<loan_purpose_array.size();i++)
//                            {
//                                if(loan_purpose_array.get(i).equals(LoanApplications.get(0).getLoan_purpose()))
//                                {
//                                    loan_purpose.setSelection(i+1);
//                                }
//                            }
//
//                            for(int i=0;i<relation_array.size();i++)
//                            {
//                                if(relation_array.get(i).equals(LoanApplications.get(0).getNominee_relation()))
//                                {
//                                    nominee_relation.setSelection(i+1);
//                                }
//                            }
//
//                        }
//
//                        // add code which you want to run in main(UI) thread
//                    }
//                });
//            }
//        });

      //  File photo = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MicroFinance/Documents" + "/profile_6ad9e910-a970-401a-85a2-bd41c38d9c8a.jpg");
        //Glide.with(LoanActivityFour.this).load(photo).placeholder(R.drawable.ic_survey).into(member_photo_pr);







    }

    public void UploadDocuments()
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
        Call<Result> call = service.UploadDocuments(map);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("Uploaded", String.valueOf(response.body().getMessage()));
                Intent intent =  new Intent(LoanActivityFour.this, LoanTransactionActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityFour.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public  void saveDocument()
    {


        if(co_name.getText().toString().isEmpty())
        {
            co_name.setError("Please Enter Co Applicant Name");
            co_name.requestFocus();
        }
        else if(co_dob.getText().toString().isEmpty())
        {
            co_dob.setError("Please Enter Co Applicant DOB");
            co_dob.requestFocus();
        }
        else if(family_name1.getText().toString().isEmpty())
        {
            family_name1.setError("Please Enter Co Applicant Family One");
            family_name1.requestFocus();
        }
        else if(family_name2.getText().toString().isEmpty())
        {
            family_name2.setError("Please Enter Co Applicant Family Two");
            family_name2.requestFocus();
        }
        else if(family_name3.getText().toString().isEmpty())
        {
            family_name3.setError("Please Enter Co Applicant Family Three");
            family_name3.requestFocus();
        }
        else if(family_name4.getText().toString().isEmpty())
        {
            family_name4.setError("Please Enter Co Applicant Family Four");
            family_name4.requestFocus();
        }
        else if(email_id.getText().toString().isEmpty())
        {
            email_id.setError("Please Enter Email Id");
            email_id.requestFocus();
        }
        else if(ration_card_no.getText().toString().isEmpty())
        {
            ration_card_no.setError("Please Enter Ration Card No ");
            ration_card_no.requestFocus();
        }
        else if(pan_card_no.getText().toString().isEmpty())
        {
            pan_card_no.setError("Please Enter Pan Card No ");
            pan_card_no.requestFocus();
        }

        else if(nominee_name.getText().toString().isEmpty())
        {
            nominee_name.setError("Please Enter Nominee Name");
            nominee_name.requestFocus();
        }

        else if(nominee_age.getText().toString().isEmpty())
        {
            nominee_age.setError("Please Enter Nominee Age");
            nominee_age.requestFocus();
        }
        else if(loan_amount.getText().toString().isEmpty())
        {
            loan_amount.setError("Please Enter Loan Amount");
            loan_amount.requestFocus();
        }
        else if(member_photo_pr_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Profile Photo", Toast.LENGTH_SHORT).show();
        }
        else if(member_pan_card_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Pan Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_adhar_card_uploaded == false)
        {
            Toast.makeText(this, "Please Upload Aadhar Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_new_business_activity_uploaded == false)
        {
            Toast.makeText(this, "Please Upload Bussiness Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_photo_signature_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Sign Photo ", Toast.LENGTH_SHORT).show();
        }
        else if(member_other_proof_uploaded==false)
        {
            Toast.makeText(this, "Please Upload Others proof ", Toast.LENGTH_SHORT).show();
        }
        else
        {

            loanApplicationsave.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplicationsave.setLoan_application_document_id((int) loan_application_documents_id);
            loanApplicationsave.setLoan_application_id((int) getIntent().getLongExtra("id", 0));
            loanApplicationsave.setLoan_cycle(loan_cycle_value);
            loanApplicationsave.setMarital_status(marital_status_value);
            loanApplicationsave.setLoan_application_number(getIntent().getStringExtra("loan_application_no"));
            loanApplicationsave.setReligion(religion_value);
            loanApplicationsave.setCast(cast_value);
            loanApplicationsave.setCo_name(co_name.getText().toString());
            loanApplicationsave.setCo_dob(co_dob.getText().toString());
            loanApplicationsave.setFamily_name1(family_name1.getText().toString());
            loanApplicationsave.setFamily_name2(family_name2.getText().toString());
            loanApplicationsave.setFamily_name3(family_name3.getText().toString());
            loanApplicationsave.setFamily_name4(family_name4.getText().toString());
            loanApplicationsave.setFamily_relation1(family_relation1_value);
            loanApplicationsave.setFamily_relation2(family_relation2_value);
            loanApplicationsave.setFamily_relation3(family_relation3_value);
            loanApplicationsave.setFamily_relation4(family_relation4_value);
            loanApplicationsave.setLoan_purpose(loan_purpose_value);
            loanApplicationsave.setEmail_id(email_id.getText().toString());
            loanApplicationsave.setRation_card_no(ration_card_no.getText().toString());
            loanApplicationsave.setPan_card_no(pan_card_no.getText().toString());
            loanApplicationsave.setNominee_age(nominee_age.getText().toString());
            loanApplicationsave.setNominee_name(nominee_name.getText().toString());
            loanApplicationsave.setMember_photo_pr(photo_upload_string);
            loanApplicationsave.setMember_pan_card(pan_upload_string);
            loanApplicationsave.setMember_adhar_card(aadhar_upload_string);
            loanApplicationsave.setMember_other_proof(other_upload_string);
            loanApplicationsave.setMember_new_business_activity(bussiness_upload_string);
            loanApplicationsave.setMember_photo_signature(sign_upload_string);
            loanApplicationsave.setNominee_relation(nominee_relation_value);
            loanApplicationsave.setLoan_amount(loan_amount.getText().toString());
            loanApplicationsave.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplicationsave.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplicationsave.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));

            loanApplicationDocuments =  new ArrayList<>();
            loanApplicationDocuments.add(loanApplicationUpdate);
            UpdateToserver(loanApplicationDocuments);

//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            AsyncTask.execute(() -> {
//                                AppDatabase.getDatabase(LoanActivityFour.this).loanApplicationDocumentsDao().insert(loanApplicationsave);
//                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        new android.os.Handler().postDelayed(
//                                                new Runnable() {
//                                                    public void run() {
//                                                        progressDialog.dismiss();
//                                                        insertRowToServer(getIntent().getLongExtra("id",0));
//                                                        Intent intent =  new Intent(LoanActivityFour.this, LoanTransactionActivity.class);
//                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                        startActivity(intent);
//
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            //Toast.makeText(this, ""+req_code, Toast.LENGTH_SHORT).show();
            if(req_code==1)
            {
                Uri fileUri = data.getData();
                photo_upload_uri = data.getData();
                member_photo_pr.setImageURI(fileUri);
                member_photo_pr_uploaded = true;




                BitmapDrawable drawable = (BitmapDrawable) member_photo_pr.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                   String Image_name = "profile_"+UUID.randomUUID().toString()+".jpg";
                    photo_upload_string = Image_name;
                    File photo_upload_uri_path = new File(fileUri.getPath());
                    photo_upload_fbody = RequestBody.create(MediaType.parse("image/*"), photo_upload_uri_path);
                    map.put("photo\"; filename=\""+photo_upload_string+"\" ", photo_upload_fbody);
                    FileOutputStream out = new FileOutputStream(file+"/"+photo_upload_string);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                    out.flush();
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

              if(req_code==2)
            {
                Uri fileUri = data.getData();
                aadhar_upload_uri = data.getData();
                member_pan_card.setImageURI(fileUri);
                member_pan_card_uploaded = true;


                BitmapDrawable drawable = (BitmapDrawable) member_pan_card.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String Image_name = "pan_card_"+UUID.randomUUID().toString()+".jpg";
                    pan_upload_string= Image_name;
                    File pan_upload_uri_path = new File(fileUri.getPath());
                    pan_upload_body = RequestBody.create(MediaType.parse("image/*"), pan_upload_uri_path);
                    map.put("pan\"; filename=\""+pan_upload_string+"\" ", pan_upload_body);
                    FileOutputStream out = new FileOutputStream(file+"/"+pan_upload_string);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                    out.flush();
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

              if(req_code==3)
            {
                Uri fileUri = data.getData();
                aadhar_upload_uri = data.getData();
                member_adhar_card.setImageURI(fileUri);
                member_adhar_card_uploaded = true;

                BitmapDrawable drawable = (BitmapDrawable) member_adhar_card.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String Image_name = "adhar_card_"+UUID.randomUUID().toString()+".jpg";
                    aadhar_upload_string= Image_name;
                    File aadhar_upload_uri_path = new File(fileUri.getPath());
                    aadhar_upload_body = RequestBody.create(MediaType.parse("image/*"), aadhar_upload_uri_path);
                    map.put("adhar\"; filename=\""+aadhar_upload_string+"\" ", aadhar_upload_body);

                    FileOutputStream out = new FileOutputStream(file+"/"+aadhar_upload_string);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                    out.flush();
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

              if(req_code==4)
            {
                Uri fileUri = data.getData();
                other_upload_uri = data.getData();
                member_other_proof.setImageURI(fileUri);
                member_other_proof_uploaded = true;


                BitmapDrawable drawable = (BitmapDrawable) member_other_proof.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/MicroFinance/Documents");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String Image_name = "other_proof_"+UUID.randomUUID().toString()+".jpg";
                    other_upload_string= Image_name;
                    File other_upload_path = new File(fileUri.getPath());
                    other_upload_body = RequestBody.create(MediaType.parse("image/*"), other_upload_path);
                    map.put("other\"; filename=\""+other_upload_string+"\" ", other_upload_body);
                    FileOutputStream out = new FileOutputStream(file+"/"+other_upload_string);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                    out.flush();
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

              if(req_code==5)
            {
                Uri fileUri = data.getData();
                bussiness_upload_uri = data.getData();
                member_new_business_activity.setImageURI(fileUri);
                member_new_business_activity_uploaded = true;

                BitmapDrawable drawable = (BitmapDrawable) member_new_business_activity.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/MicroFinance/Documents");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String Image_name = "business_activity_"+UUID.randomUUID().toString()+".jpg";
                    bussiness_upload_string= Image_name;
                    File bussiness_upload_path = new File(fileUri.getPath());
                    bussiness_upload_body = RequestBody.create(MediaType.parse("image/*"), bussiness_upload_path);
                    map.put("business\"; filename=\""+bussiness_upload_string+"\" ", bussiness_upload_body);
                    FileOutputStream out = new FileOutputStream(file+"/"+bussiness_upload_string);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                    out.flush();
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

              if(req_code==6)
            {
                Uri fileUri = data.getData();
                sign_upload_uri = data.getData();
                member_photo_signature.setImageURI(fileUri);
                member_photo_signature_uploaded = true;


               BitmapDrawable drawable = (BitmapDrawable) member_photo_signature.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String Image_name = "signature_photo_"+UUID.randomUUID().toString()+".jpg";
                    sign_upload_string= Image_name;
                    File sign_upload_path = new File(fileUri.getPath());
                    sign_upload_body = RequestBody.create(MediaType.parse("image/*"), sign_upload_path);
                    map.put("sign_photo\"; filename=\""+sign_upload_string+"\" ", sign_upload_body);
                    FileOutputStream out = new FileOutputStream(file+"/"+sign_upload_string);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                    out.flush();
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }


    public void UpdateRowToServer(long last_inserted_id)
    {
        AsyncTask.execute(() -> {
            List<LoanApplicationDocument> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityFour.this).loanApplicationDocumentsDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),getIntent().getStringExtra("loan_application_no"));


            if(LoanApplications.size()>0) {
                UpdateToserver(LoanApplications);
            }

        });


    }
    public void insertRowToServer(long last_inserted_id)
    {
        AsyncTask.execute(() -> {
            List<LoanApplicationDocument> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityFour.this).loanApplicationDocumentsDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),getIntent().getStringExtra("loan_application_no"));
            //  Log.d("sdfsdfsdfsd",LoanApplications.get(0).getApplicant_name());
            if(LoanApplications.size()>0) {
                SubmitSurveyServer(LoanApplications);
            }

        });


    }
    public void SubmitSurveyServer(List<LoanApplicationDocument> loanApplication)
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
        Call<Result> call = service.UpdateLoanApplicationDocument(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //  Log.d("testadfasdf", response.message());
                // Log.d("loanMe", String.valueOf(response.body().getMessage()));
                UploadDocuments();
                Toast.makeText(LoanActivityFour.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                //  ((LoanApplicationForm) getActivity()).selectIndex(1,loanApplication.get(0).getLoan_application_id());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityFour.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }
    public void UpdateToserver(List<LoanApplicationDocument> loanApplication)
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
        Call<Result> call = service.UpdateLoanApplicationDocument(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
             //   Log.d("testadfasdf", response.body().getMessage());
                UploadDocuments();
                Toast.makeText(LoanActivityFour.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityFour.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }

}