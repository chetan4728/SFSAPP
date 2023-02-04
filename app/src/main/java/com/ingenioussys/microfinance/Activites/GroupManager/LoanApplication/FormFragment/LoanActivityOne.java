package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.FormFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.ingenioussys.microfinance.Activites.GroupManager.Groups.CreateGroupActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.Groups.ViewGroupTransactionActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanTransactionActivity;
import com.ingenioussys.microfinance.Adapter.GroupTransactionsAdapter;
import com.ingenioussys.microfinance.Adapter.LoanApplicationAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.AadharCard;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.DataAttributes;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.QrCodeException;
import com.ingenioussys.microfinance.utility.SecureQrCode;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class LoanActivityOne extends AppCompatActivity {
    PrefManager prefManager;
    ImageView qr_scan;
    Button next;
    EditText voter_id,created_date,created_by,branch_office,applicant_name,applicant_father_name,dob,age,applicant_mob_no,loan_application_no;
    EditText address,tq,dist,state,pincode,uid_no;
    AadharCard aadharData;
    ProgressDialog progressDialog;
    Dialog dialog;
    List<Center> centerArrayList;
    List<Group> groupArrayList;
    int center_id = 0;
    int area_id = 0;
    final Calendar myCalendar = Calendar.getInstance();
    int group_id = 0;
    String gender_string ="Female";
    HintSpinner center_name,group_name;
    TextView loan_no;
    RadioButton male,female;
    private CodeScanner mCodeScanner;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    List<Center> List;
    List<Group> ListGroup;
    List<LoanApplication> loanActivityOnes;
    int group_id_get = 0;
    long loan_application_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_one);

        prefManager =  new PrefManager(this);
        checkCameraPermission();

        loan_no = findViewById(R.id.loan_no);
        getSupportActionBar().setTitle("Loan Application");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog =  new ProgressDialog(this);
        prefManager =  new PrefManager(this);
        progressDialog.setMessage("Submitting Data...");
        center_name =  findViewById(R.id.center_name);
        group_name =  findViewById(R.id.group_name);
        created_date =  findViewById(R.id.created_date);
        branch_office =  findViewById(R.id.branch_office);
        applicant_name =  findViewById(R.id.applicant_name);
        loan_application_no =  findViewById(R.id.loan_application_no);
        address =  findViewById(R.id.address);
        tq =  findViewById(R.id.tq);
        dist =  findViewById(R.id.dist);
        voter_id = findViewById(R.id.voter_id);
        state =  findViewById(R.id.state);
        pincode =  findViewById(R.id.pincode);
        uid_no =  findViewById(R.id.uid_no);
        age =  findViewById(R.id.age);
        next = findViewById(R.id.next);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        applicant_mob_no = findViewById(R.id.applicant_mob_no);
        applicant_father_name =  findViewById(R.id.applicant_father_name);
        dob =  findViewById(R.id.dob);
        qr_scan =  findViewById(R.id.qr_scan);

        branch_office.setText(prefManager.getString("branch_id"));
       // get_loan_application_no();
        created_by =  findViewById(R.id.created_by);
        created_by.setText(prefManager.getString("employee_first_name")+" "+prefManager.getString("employee_last_name"));

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        created_date.setText(formattedDate);

        RadioGroup approval   =findViewById(R.id.approval);
      //  Toast.makeText(this, ""+prefManager.getString("branch_id"), Toast.LENGTH_SHORT).show();


        approval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.male:
                        // do operations specific to this selection
                        gender_string = "Male";
                        break;
                    case R.id.female:
                        // do operations specific to this selection
                        gender_string = "Female";
                        break;

                }
            }
        });

        dialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("loan_application_number") != null)
                {
                    update_form();

                }
                else
                {

                    next_form();
                }
            }
        });

        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog   datePickerDialog = new DatePickerDialog(LoanActivityOne.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dob.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);
                                Date currentDate = new Date();

                                Calendar cal = Calendar.getInstance();
                                cal.setTime(currentDate);
                                int aged = cal.get(Calendar.YEAR) - year;
                                 age.setText(""+aged);
                                //Toast.makeText(LoanActivityOne.this, ""+age, Toast.LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });




        dialog.setContentView(R.layout.scanner_box);
        CodeScannerView scannerView = dialog.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(LoanActivityOne.this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull com.google.zxing.Result result) {
                processScannedData(result.getText());
            }


        });
        qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });

        load_centers();
      //  Toast.makeText(this, ""+getIntent().getStringExtra("loan_application_number"), Toast.LENGTH_SHORT).show();
        if(getIntent().getStringExtra("loan_application_number")!=null)
        {
            load_centers();
            load_data();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void updateLabel() {
        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
    public boolean checkCameraPermission (){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            return false;
        }
        return true;
    }
    protected void processScannedData(String scanData){
        // check if the scanned string is XML
        // This is to support old QR codes

        if(isXml(scanData)){
            XmlPullParserFactory pullParserFactory;

            try {
                // init the parserfactory
                pullParserFactory = XmlPullParserFactory.newInstance();
                // get the parser
                XmlPullParser parser = pullParserFactory.newPullParser();

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(new StringReader(scanData));
                aadharData = new AadharCard();

                // parse the XML
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        //Log.d("Rajdeol","Start document");
                    } else if(eventType == XmlPullParser.START_TAG && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                        // extract data from tag
                        //uid
                        aadharData.setUuid(parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR));
                        //name
                        aadharData.setName(parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR));
                        //gender
                        aadharData.setGender(parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR));
                        // year of birth
                        aadharData.setDateOfBirth(parser.getAttributeValue(null,DataAttributes.AADHAR_DOB_ATTR));
                        // care of
                        aadharData.setCareOf(parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR));
                        // village Tehsil
                        aadharData.setVtc(parser.getAttributeValue(null,DataAttributes.AADHAR_VTC_ATTR));
                        // Post Office
                        aadharData.setPostOffice(parser.getAttributeValue(null,DataAttributes.AADHAR_PO_ATTR));
                        // district
                        aadharData.setDistrict(parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR));
                        // state
                        aadharData.setState(parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR));
                        // Post Code
                        aadharData.setPinCode(parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR));

                    } else if(eventType == XmlPullParser.END_TAG) {
                        //  Log.d("Rajdeol","End tag "+parser.getName());

                    } else if(eventType == XmlPullParser.TEXT) {
                        //  Log.d("Rajdeol","Text "+parser.getText());

                    }
                    // update eventType
                    eventType = parser.next();
                }

                // display the data on screen
                // displayScannedData();
                return;
            } catch (XmlPullParserException e) {
                //  showErrorPrompt("Error in processing QRcode XML");
                Toast.makeText(LoanActivityOne.this, "Error in processing QRcode XML", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            } catch (IOException e) {
                Toast.makeText(LoanActivityOne.this, e.toString(), Toast.LENGTH_SHORT).show();
                // showErrorPrompt(e.toString());
                e.printStackTrace();
                return;
            }
        }

        // process secure QR code
        processEncodedScannedData(scanData);
    }// EO function

    /**
     * Function to process encoded aadhar data
     * @param scanData
     */
    protected void processEncodedScannedData(String scanData){
        try {
            SecureQrCode decodedData = new SecureQrCode(LoanActivityOne.this,scanData);
            aadharData = decodedData.getScannedAadharCard();
            // display the Aadhar Data
            //  showSuccessPrompt("Scanned Aadhar Card Successfully");
            displayScannedData();
        } catch (QrCodeException e) {
            // showErrorPrompt(e.toString());
            e.printStackTrace();
        }
    }
    public void displayScannedData(){

        Toast.makeText(LoanActivityOne.this, ""+aadharData.getName(), Toast.LENGTH_SHORT).show();
        applicant_name.setText(aadharData.getName());
        dob.setText(aadharData.getDateOfBirth());
        dialog.dismiss();
        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        try {
            Date birthdate = df.parse(aadharData.getDateOfBirth());
            age.setText(""+calculateAge(birthdate));
            // Toast.makeText(getActivity(), ""+calculateAge(birthdate), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //mCodeScanner.releaseResources();

    }

    public static int calculateAge(Date birthdate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();

        int yearDifference = today.get(Calendar.YEAR)
                - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth
                    .get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }

        }

        return yearDifference;
    }
    protected boolean isXml (String testString){
        Pattern pattern;
        Matcher matcher;
        boolean retBool = false;

        // REGULAR EXPRESSION TO SEE IF IT AT LEAST STARTS AND ENDS
        // WITH THE SAME ELEMENT
        final String XML_PATTERN_STR = "<(\\S+?)(.*?)>(.*?)</\\1>";

        // IF WE HAVE A STRING
        if (testString != null && testString.trim().length() > 0) {

            // IF WE EVEN RESEMBLE XML
            if (testString.trim().startsWith("<")) {

                pattern = Pattern.compile(XML_PATTERN_STR,
                        Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);

                // RETURN TRUE IF IT HAS PASSED BOTH TESTS
                matcher = pattern.matcher(testString);
                retBool = matcher.matches();
            }
            // ELSE WE ARE FALSE
        }

        return retBool;
    }

    public void load_data()
    {
      //  Toast.makeText(this, "dfdfdf"+getIntent().getStringExtra("loan_application_number"), Toast.LENGTH_SHORT).show();

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
        Call<Result> call = service.get_loan_details(getIntent().getStringExtra("loan_application_number"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    Toast.makeText(LoanActivityOne.this, "errror" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                        Log.d("loans",response.body().getData().toString());


                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                        Log.d("loansdd",jsonarray.toString());

                        loan_application_no.setText(jsonarray.getJSONObject(0).getString("loan_application_number"));
                        //loan_application_id = LoanApplications.get(0).getLoan_application_id();
                        applicant_name.setText(jsonarray.getJSONObject(0).getString("applicant_name"));
                        applicant_father_name.setText(jsonarray.getJSONObject(0).getString("applicant_father_name"));
                        dob.setText(jsonarray.getJSONObject(0).getString("dob"));
                        age.setText(jsonarray.getJSONObject(0).getString("age"));
                        applicant_mob_no.setText(jsonarray.getJSONObject(0).getString("applicant_mob_no"));
                        address.setText(jsonarray.getJSONObject(0).getString("address"));
                        tq.setText(jsonarray.getJSONObject(0).getString("tq"));
                        dist.setText(jsonarray.getJSONObject(0).getString("dist"));
                        state.setText(jsonarray.getJSONObject(0).getString("state"));
                        pincode.setText(jsonarray.getJSONObject(0).getString("pincode"));
                        uid_no.setText(jsonarray.getJSONObject(0).getString("uid_no"));
                        group_id_get = jsonarray.getJSONObject(0).getInt("group_id");
                        gender_string =jsonarray.getJSONObject(0).getString("gender");
                        created_date.setText(jsonarray.getJSONObject(0).getString("created_date"));
                        if(gender_string.equals("Male")) {
                            male.setChecked(true);
                        }
                        else  if(gender_string.equals("Female")) {
                            female.setChecked(true);
                        }
                                    for(int i=0;i<centerArrayList.size();i++)
                            {
                               // Toast.makeText(LoanActivityOne.this, ""+centerArrayList.get(i).getCenter_id()+"--"+jsonarray.getJSONObject(0).getInt("center_id"), Toast.LENGTH_SHORT).show();
                                if(centerArrayList.get(i).getCenter_id()==jsonarray.getJSONObject(0).getInt("center_id"))
                                {
                                     center_name.setSelection(i+1);
                                   // Toast.makeText(this, ""+ListGroup.size(), Toast.LENGTH_SHORT).show();
                                }
                            }

                                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {

                                for (int j = 0; j < groupArrayList.size(); j++) {
                                    // Toast.makeText(LoanActivityOne.this, ""+ListGroup.get(j).getGroup_id()+""+group_id_get, Toast.LENGTH_SHORT).show();
                                    if (groupArrayList.get(j).getGroup_id() == group_id_get) {
                                        group_name.setSelection(j+1);

                                    }
                                }

                        }
                    },
                    2000);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoanActivityOne.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //Toast.makeText(this, ""+getIntent().getStringExtra("loan_application_number"), Toast.LENGTH_SHORT).show();
//        AsyncTask.execute(() -> {
//
//            List<LoanApplication> LoanApplications =  new ArrayList<>();
//
//            LoanApplications =   AppDatabase.getDatabase(LoanActivityOne.this).loanApplicationDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),getIntent().getStringExtra("loan_application_number"));
//            loan_application_no.setText(LoanApplications.get(0).getLoan_application_number());
//            loan_application_id = LoanApplications.get(0).getLoan_application_id();
//            applicant_name.setText(LoanApplications.get(0).getApplicant_name());
//            applicant_father_name.setText(LoanApplications.get(0).getApplicant_father_name());
//            dob.setText(LoanApplications.get(0).getDob());
//            age.setText(LoanApplications.get(0).getAge());
//            applicant_mob_no.setText(LoanApplications.get(0).getApplicant_mob_no());
//            address.setText(LoanApplications.get(0).getAddress());
//            tq.setText(LoanApplications.get(0).getTq());
//            dist.setText(LoanApplications.get(0).getDist());
//            state.setText(LoanApplications.get(0).getState());
//            pincode.setText(LoanApplications.get(0).getPincode());
//            uid_no.setText(LoanApplications.get(0).getUid_no());
//            group_id_get = LoanApplications.get(0).getGroup_id();
//            gender_string = LoanApplications.get(0).getGender();
//            created_date.setText(LoanApplications.get(0).getCreated_date());
//
//            if(gender_string.equals("Male")) {
//                male.setChecked(true);
//            }
//            else  if(gender_string.equals("Female")) {
//                female.setChecked(true);
//            }
//
//
//            for(int i=0;i<List.size();i++)
//            {
//                if(List.get(i).getCenter_id()==LoanApplications.get(0).getCenter_id())
//                {
//                     center_name.setSelection(i+1);
//                   // Toast.makeText(this, ""+ListGroup.size(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//        });
//
//
//            new android.os.Handler(Looper.getMainLooper()).postDelayed(
//                    new Runnable() {
//                        public void run() {
//
//                                for (int j = 0; j < ListGroup.size(); j++) {
//                                    // Toast.makeText(LoanActivityOne.this, ""+ListGroup.get(j).getGroup_id()+""+group_id_get, Toast.LENGTH_SHORT).show();
//                                    if (ListGroup.get(j).getGroup_id() == group_id_get) {
//                                        group_name.setSelection(j+1);
//
//                                    }
//                                }
//
//                        }
//                    },
//                    2000);

    }
    public void  update_form()
    {
        if(applicant_name.getText().toString().isEmpty())
        {
            applicant_name.setError("Enter Applicant Name");
            applicant_name.requestFocus();
        }
        else if(applicant_father_name.getText().toString().isEmpty())
        {
            applicant_father_name.setError("Enter Father Name");
            applicant_father_name.requestFocus();
        }
        else if(dob.getText().toString().isEmpty())
        {
            dob.setError("Enter Dob");
            dob.requestFocus();
        }
        else if(gender_string.isEmpty())
        {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
        }
        else if(age.getText().toString().isEmpty())
        {
            age.setError("Enter Age");
            age.requestFocus();
        }

        else if( Integer.parseInt(age.getText().toString()) < 18 || Integer.parseInt(age.getText().toString()) > 60)
        {
            age.setError("Enter Age Between 18 to 59 ");
            age.requestFocus();
        }
        else if(applicant_mob_no.getText().toString().isEmpty())
        {
            applicant_mob_no.setError("Enter Mobile no");
            applicant_mob_no.requestFocus();
        }
        else if(applicant_mob_no.length() != 10)
        {
            applicant_mob_no.setError("Enter 10 Digit Mobile No");
            applicant_mob_no.requestFocus();
        }

        else if(address.getText().toString().isEmpty())
        {
            address.setError("Enter Address");
            address.requestFocus();
        }
        else if(tq.getText().toString().isEmpty())
        {
            tq.setError("Enter Taluka");
            tq.requestFocus();
        }
        else if(dist.getText().toString().isEmpty())
        {
            dist.setError("Enter District");
            dist.requestFocus();
        }
        else if(state.getText().toString().isEmpty())
        {
            state.setError("Enter State");
            state.requestFocus();
        }

        else if(pincode.getText().toString().isEmpty())
        {
            pincode.setError("Enter Pincode");
            pincode.requestFocus();
        }

        else if(uid_no.getText().toString().isEmpty())
        {
            uid_no.setError("Enter Uid No");
            uid_no.requestFocus();
        }
        else if(uid_no.length() != 12)
        {
            uid_no.setError("Enter 12 Digit Uid No");
            uid_no.requestFocus();
        }
        else if(center_id==0)
        {
            Toast.makeText(this, "Select Center", Toast.LENGTH_SHORT).show();
        }
        else if(group_id==0)
        {
            Toast.makeText(this, "Select Group", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(this, ""+loan_application_id, Toast.LENGTH_SHORT).show();
            LoanApplication loanApplication =  new LoanApplication();
            loanApplication.setLoan_application_id((int)loan_application_id);
            loanApplication.setApplicant_name(applicant_name.getText().toString());
            loanApplication.setLoan_application_number(getIntent().getStringExtra("loan_application_number"));
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setApplicant_father_name(applicant_father_name.getText().toString());
            loanApplication.setGender(gender_string);
            loanApplication.setDob(dob.getText().toString());
            loanApplication.setAge(age.getText().toString());
            loanApplication.setApplicant_mob_no(applicant_mob_no.getText().toString());
            loanApplication.setAddress(address.getText().toString());
            loanApplication.setTq(tq.getText().toString());
            loanApplication.setDist(dist.getText().toString());
            loanApplication.setState(state.getText().toString());
            loanApplication.setPincode(pincode.getText().toString());
            loanApplication.setUid_no(uid_no.getText().toString());
            loanApplication.setCenter_id(center_id);
            loanApplication.setVoter_id_no(voter_id.getText().toString());
            loanApplication.setGroup_id(group_id);
            loanApplication.setArea_id(Integer.parseInt(prefManager.getString("area_id")));
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplication.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
            Date c = Calendar.getInstance().getTime();


            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            loanApplication.setCreated_date(formattedDate);
            loanApplication.setCreated_date(formattedDate);

            loanActivityOnes =  new ArrayList<>();
            loanActivityOnes.add(loanApplication);
            UpdateToserver(loanActivityOnes);

           // progressDialog.show();

//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            AsyncTask.execute(() -> {
//                                AppDatabase.getDatabase(LoanActivityOne.this).loanApplicationDao().update(loanApplication);
//                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        new android.os.Handler().postDelayed(
//                                                new Runnable() {
//                                                    public void run() {
//                                                        progressDialog.dismiss();
//                                                           UpdateRowToServer(getIntent().getStringExtra("loan_application_number"));
//                                                           Intent intent =  new Intent(LoanActivityOne.this,LoanActivityTwo.class);
//                                                           Bundle bundle =  new Bundle();
//                                                           bundle.putLong("id", loan_application_id);
//                                                           bundle.putString("loan_application_no",loan_application_no.getText().toString());
//                                                           intent.putExtras(bundle);
//                                                           startActivity(intent);
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



            //((LoanApplicationForm) getActivity()).selectIndex(1);
        }
    }
    public void next_form()
    {
        if(applicant_name.getText().toString().isEmpty())
        {
            applicant_name.setError("Enter Applicant Name");
            applicant_name.requestFocus();
        }
        else if(applicant_father_name.getText().toString().isEmpty())
        {
            applicant_father_name.setError("Enter Father Name");
            applicant_father_name.requestFocus();
        }
        else if(dob.getText().toString().isEmpty())
        {
            dob.setError("Enter Dob");
            dob.requestFocus();
        }
        else if(gender_string.isEmpty())
        {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
        }
        else if(age.getText().toString().isEmpty())
        {
            age.setError("Enter Age");
            age.requestFocus();
        }
        else if( Integer.parseInt(age.getText().toString()) < 18 || Integer.parseInt(age.getText().toString()) > 60)
        {
            age.setError("Enter Age Between 18 to 59 ");
            age.requestFocus();
        }
        else if(applicant_mob_no.getText().toString().isEmpty())
        {
            applicant_mob_no.setError("Enter Mobile no");
            applicant_mob_no.requestFocus();
        }
        else if(applicant_mob_no.length() != 10)
        {
            applicant_mob_no.setError("Enter 10 Digit Mobile No");
            applicant_mob_no.requestFocus();
        }

        else if(address.getText().toString().isEmpty())
        {
            address.setError("Enter Address");
            address.requestFocus();
        }
        else if(tq.getText().toString().isEmpty())
        {
            tq.setError("Enter Taluka");
            tq.requestFocus();
        }
        else if(dist.getText().toString().isEmpty())
        {
            dist.setError("Enter District");
            dist.requestFocus();
        }
        else if(state.getText().toString().isEmpty())
        {
            state.setError("Enter State");
            state.requestFocus();
        }

        else if(pincode.getText().toString().isEmpty())
        {
            pincode.setError("Enter Pincode");
            pincode.requestFocus();
        }

        else if(uid_no.getText().toString().isEmpty())
        {
            uid_no.setError("Enter Uid No");
            uid_no.requestFocus();
        }
        else if(uid_no.length() != 12)
        {
            uid_no.setError("Enter 12 Digit Uid No");
            uid_no.requestFocus();
        }
        else if(center_id==0)
        {
            Toast.makeText(this, "Select Center", Toast.LENGTH_SHORT).show();
        }
        else if(group_id==0)
        {
            Toast.makeText(this, "Select Group", Toast.LENGTH_SHORT).show();
        }
        else {
            get_loan_application_no();
            progressDialog.show();
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {
                            ExecutorService executor = Executors.newSingleThreadExecutor();

                            executor.execute(() -> {

                                if(!loan_application_no.getText().toString().isEmpty()) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                          //  Toast.makeText(LoanActivityOne.this, ""+gender_string, Toast.LENGTH_SHORT).show();

                            LoanApplication loanApplication =  new LoanApplication();
                            loanApplication.setLoan_application_number(loan_application_no.getText().toString());
                            loanApplication.setApplicant_name(applicant_name.getText().toString());
                            loanApplication.setApplicant_father_name(applicant_father_name.getText().toString());
                            loanApplication.setGender(gender_string);
                            loanApplication.setDob(dob.getText().toString());
                            loanApplication.setAge(age.getText().toString());
                            loanApplication.setApplicant_mob_no(applicant_mob_no.getText().toString());
                            loanApplication.setAddress(address.getText().toString());
                            loanApplication.setTq(tq.getText().toString());
                            loanApplication.setDist(dist.getText().toString());
                            loanApplication.setState(state.getText().toString());
                            loanApplication.setPincode(pincode.getText().toString());
                            loanApplication.setUid_no(uid_no.getText().toString());
                            loanApplication.setArea_id(Integer.parseInt(prefManager.getString("area_id")));
                            loanApplication.setCenter_id(center_id);
                            loanApplication.setGroup_id(group_id);

                            loanApplication.setVoter_id_no(voter_id.getText().toString());
                            loanApplication.setActive(false);
                                            loanApplication.setArea_id(Integer.parseInt(prefManager.getString("area_id")));
                                            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
                                            loanApplication.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
                                            loanApplication.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
                            Date c = Calendar.getInstance().getTime();


                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            String formattedDate = df.format(c);
                            loanApplication.setCreated_date(formattedDate);

                                            loanActivityOnes =  new ArrayList<>();
                                            loanActivityOnes.add(loanApplication);
                                            SubmitSurveyServer(loanActivityOnes);
//                                            ExecutorService executor = Executors.newSingleThreadExecutor();
//
//                                            executor.execute(() -> {
//
//                                                   AppDatabase.getDatabase(LoanActivityOne.this).loanApplicationDao().insert(loanApplication);
//                                                    runOnUiThread(new Runnable() {
//                                                        @Override
//                                                        public void run() {
//                                                            insertRowToServer(loan_application_no.getText().toString());
//                                                            progressDialog.dismiss();
//                                                        }
//                                                    });
//
//
//
//
//                                            });





                                        }
                                    });
                                }



                            });
                        }
                    },
                    1000);

        }
    }


    public String get_loan_application_no()
    {
        String loanApplication_no = "";
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
        Call<Result> call = service.createLoanApplicationNo(prefManager.getString("bank_id"),area_id,prefManager.getString("branch_id"),center_id,group_id,prefManager.getString("bank_prefix"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                 Log.d("testadfasdf", response.body().getData().toString());
                //loan_application_no = response.body().getMessage();
                try {
                    JSONArray array =  new JSONArray(response.body().getData().toString());
                    loan_no.setText(array.getJSONObject(0).getString("loan_application_no"));
                    loan_application_no.setText(array.getJSONObject(0).getString("loan_application_no"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //  ((LoanApplicationForm) getActivity()).selectIndex(1,loanApplication.get(0).getLoan_application_id());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityOne.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return loanApplication_no;
    }

    public  void load_centers()
    {

        centerArrayList =  new ArrayList<>();
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
        Call<Result> call = service.LoadCenterTable(prefManager.getString("bank_id"),prefManager.getString("branch_id"),prefManager.getString("employee_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {

                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            Center center =  new Center();


                            center.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
                            //Toast.makeText(CreateGroupActivity.this, ""+jsonarray.getJSONObject(i).getInt("center_id"), Toast.LENGTH_SHORT).show();
                            center.setCenter_no(jsonarray.getJSONObject(i).getInt("center_no"));
                            center.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            center.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
                            center.setCenter_desc(jsonarray.getJSONObject(i).getString("center_desc"));
                            center.setArea_id(jsonarray.getJSONObject(i).getInt("area_id"));
                            center.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
                            center.setCenter_status(jsonarray.getJSONObject(i).getInt("center_status"));
                            center.setCreated_date(jsonarray.getJSONObject(i).getString("created_date"));
                            center.setFs_name(jsonarray.getJSONObject(i).getString("fs_name"));
                            center.setCreated_by(jsonarray.getJSONObject(i).getInt("created_by"));
                            centerArrayList.add(center);

                        }
                        center_name.setAdapter(new HintSpinnerAdapter<Center>(getApplicationContext(), centerArrayList, "Select Center") {
                            @Override
                            public String getLabelFor(Center center) {
                                return center.getCenter_name();
                            }

                        });

                        center_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                center_id = centerArrayList.get(i).getCenter_id();
                                area_id = centerArrayList.get(i).getArea_id();
                                load_groups(center_id);
                               // Toast.makeText(LoanActivityOne.this, ""+centerArrayList.get(i).getCenter_id(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

//                                        no_data.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();



                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                //Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }

    public  void load_groups(int center_id)
    {
        groupArrayList =  new ArrayList<>();
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
        Call<Result> call = service.LoadCenterGroupsTable(prefManager.getString("bank_id"),prefManager.getString("branch_id"), String.valueOf(center_id),prefManager.getString("employee_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("groups", String.valueOf(response.body().getData()));
                if (response.body().getError()) {
                    // Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());


                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            //Toast.makeText(LoanActivityOne.this, ""+jsonarray.getJSONObject(i).getString("group_name"), Toast.LENGTH_SHORT).show();
                            Group group =  new Group();
                            group.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
                            group.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            group.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
                            group.setGroup_id(jsonarray.getJSONObject(i).getInt("group_id"));
                            group.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
                            group.setMember_limit(jsonarray.getJSONObject(i).getInt("member_limit"));
                            group.setContact_number(jsonarray.getJSONObject(i).getString("contact_number"));
                            group.setGroup_name(jsonarray.getJSONObject(i).getString("group_name"));
                            group.setCreated_date(jsonarray.getJSONObject(i).getString("created_date"));
                            group.setCreated_by(jsonarray.getJSONObject(i).getInt("created_by"));
                            group.setLatitude(jsonarray.getJSONObject(i).getDouble("latitude"));
                            group.setLongitude(jsonarray.getJSONObject(i).getDouble("longitude"));
                            group.setGroupStatus(jsonarray.getJSONObject(i).getInt("GroupStatus"));
                            group.setVerified_by(jsonarray.getJSONObject(i).getInt("verified_by"));
                            group.setFs_name(jsonarray.getJSONObject(i).getString("fs_name"));

                            groupArrayList.add(group);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    group_name.setAdapter(new HintSpinnerAdapter<Group>(getApplicationContext(), groupArrayList, "Select Groups") {
                        @Override
                        public String getLabelFor(Group group) {
                            return group.getGroup_name();
                        }

                    });
                    group_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            group_id = groupArrayList.get(i).getGroup_id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }


    public void UpdateRowToServer(String loan_application_no)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<LoanApplication> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityOne.this).loanApplicationDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")), getIntent().getStringExtra("loan_application_number"));

            if(LoanApplications.size()>0) {
                UpdateToserver(LoanApplications);
            }

        });


    }
    public void insertRowToServer(String loan_application_no)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<LoanApplication> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityOne.this).loanApplicationDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")), loan_application_no);
            if(LoanApplications.size()>0) {
                SubmitSurveyServer(LoanApplications);
            }

        });


    }
    public void UpdateToserver(List<LoanApplication> loanApplication)
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
        Call<Result> call = service.UpdateLoandApplicationRow(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Intent intent =  new Intent(LoanActivityOne.this,LoanActivityTwo.class);
                Bundle bundle =  new Bundle();
                bundle.putLong("id", loan_application_id);
                bundle.putString("loan_application_no",loan_application_no.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
               // Toast.makeText(LoanActivityOne.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityOne.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    public void SubmitSurveyServer(List<LoanApplication> loanApplication)
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
        Call<Result> call = service.SubmitLoanApplication(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //  Log.d("testadfasdf", response.message());
                //Log.d("loanMe", String.valueOf(response.body().getMessage()));
                //Log.d("loanMe", String.valueOf(response.body().getData()));
                Intent intent =  new Intent(LoanActivityOne.this,LoanActivityTwo.class);
                Bundle bundle =  new Bundle();
                bundle.putLong("id", loan_application_id);
                bundle.putString("loan_application_no",loan_application_no.getText().toString());
                intent.putExtras(bundle);
               startActivity(intent);

              //  ((LoanApplicationForm) getActivity()).selectIndex(1,loanApplication.get(0).getLoan_application_id());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityOne.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}