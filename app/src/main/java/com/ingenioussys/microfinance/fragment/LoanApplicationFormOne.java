package com.ingenioussys.microfinance.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;

import com.ingenioussys.microfinance.model.Result;

import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanApplicationForm;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.AadharCard;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.DataAttributes;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.QrCodeException;
import com.ingenioussys.microfinance.utility.SecureQrCode;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

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


public class LoanApplicationFormOne extends Fragment {
    PrefManager prefManager;
    ImageView qr_scan;
    Button next;
    EditText created_date,created_by,branch_office,applicant_name,applicant_father_name,dob,age,applicant_mob_no;
    EditText address,tq,dist,state,pincode,uid_no;
    AadharCard aadharData;
    ProgressDialog progressDialog;
    Dialog dialog;
    int center_id = 0;
    final Calendar myCalendar = Calendar.getInstance();
    int group_id = 0;
    String gender_string ="";
    HintSpinner center_name,group_name;
    private CodeScanner mCodeScanner;
    private static final int MY_CAMERA_REQUEST_CODE = 100;




    public boolean checkCameraPermission (){
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            return false;
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loan_application_form_one, container, false);
        prefManager =  new PrefManager(getActivity());
        checkCameraPermission();




        progressDialog =  new ProgressDialog(getActivity());
        prefManager =  new PrefManager(getActivity());
        progressDialog.setMessage("Submitting Data...");
        center_name =  view.findViewById(R.id.center_name);
        group_name =  view.findViewById(R.id.group_name);
        created_date =  view.findViewById(R.id.created_date);
        branch_office =  view.findViewById(R.id.branch_office);
        applicant_name =  view.findViewById(R.id.applicant_name);
        address =  view.findViewById(R.id.address);
        tq =  view.findViewById(R.id.tq);
        dist =  view.findViewById(R.id.dist);
        state =  view.findViewById(R.id.state);
        pincode =  view.findViewById(R.id.pincode);
        uid_no =  view.findViewById(R.id.uid_no);
        age =  view.findViewById(R.id.age);
        next = view.findViewById(R.id.next);
        applicant_mob_no = view.findViewById(R.id.applicant_mob_no);
        applicant_father_name =  view.findViewById(R.id.applicant_father_name);
        dob =  view.findViewById(R.id.dob);
        qr_scan =  view.findViewById(R.id.qr_scan);
        String date = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        branch_office.setText(prefManager.getString("branch_id"));

        created_by =  view.findViewById(R.id.created_by);
        created_by.setText(prefManager.getString("employee_first_name")+" "+prefManager.getString("employee_last_name"));
        created_date.setText(date);

        RadioGroup approval   = view.findViewById(R.id.approval);



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

        dialog = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                next_form();
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
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dialog.setContentView(R.layout.scanner_box);
        CodeScannerView scannerView = dialog.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
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
        return view;
    }
    private void updateLabel() {
        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
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
            Toast.makeText(getActivity(), "Please select gender", Toast.LENGTH_SHORT).show();
        }
        else if(age.getText().toString().isEmpty())
        {
            age.setError("Enter Age");
            age.requestFocus();
        }
        else if(applicant_mob_no.getText().toString().isEmpty())
        {
            applicant_mob_no.setError("Enter Mobile no");
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
        else if(center_id==0)
        {
            Toast.makeText(getActivity(), "Select Center", Toast.LENGTH_SHORT).show();
        }
        else if(group_id==0)
        {
            Toast.makeText(getActivity(), "Select Group", Toast.LENGTH_SHORT).show();
        }
        else {


            LoanApplication loanApplication =  new LoanApplication();
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
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
            loanApplication.setCenter_id(center_id);
            loanApplication.setGroup_id(group_id);
            loanApplication.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplication.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
            String date = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            loanApplication.setCreated_date(date);


            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            AsyncTask.execute(() -> {
                                long last_inserted_id =   AppDatabase.getDatabase(getActivity()).loanApplicationDao().insert(loanApplication);
                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                       // Toast.makeText(getActivity(), ""+last_inserted_id, Toast.LENGTH_SHORT).show();
                                                        insertRowToServer(last_inserted_id);
                                                        progressDialog.dismiss();
                                                    }
                                                },
                                                1000);
                                    }
                                });



                            });
                        }
                    },
                    2000);



            //((LoanApplicationForm) getActivity()).selectIndex(1);
        }
    }

    public void insertRowToServer(long last_inserted_id)
    {
        AsyncTask.execute(() -> {
            List<LoanApplication> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(getActivity()).loanApplicationDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),null);
         //  Log.d("sdfsdfsdfsd",LoanApplications.get(0).getApplicant_name());
            if(LoanApplications.size()>0) {
                SubmitSurveyServer(LoanApplications);
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
                Log.d("loanMe", String.valueOf(response.body().getMessage()));
                ((LoanApplicationForm) getActivity()).selectIndex(1,loanApplication.get(0).getLoan_application_id());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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



    public  void load_centers()
    {
//        AsyncTask.execute(() -> {
////            List<Center> List =   AppDatabase.getDatabase(getActivity()).centerDao().getAll();
//
//            center_name.setAdapter(new HintSpinnerAdapter<Center>(getActivity(), List, "Select Center") {
//                @Override
//                public String getLabelFor(Center center) {
//                    return center.getCenter_name();
//                }
//
//            });
//
//            center_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    center_id = List.get(i).getCenter_id();
//                   /// loadGroupData(center_id);
//
//                    AsyncTask.execute(() -> {
//                        List<Group> ListGroup =   AppDatabase.getDatabase(getActivity()).groupDao().getAllByCenter(Integer.parseInt(prefManager.getString("bank_id")),center_id);
//
//                        //Log.d("centerData",""+centerList.get(i).getCenter_name());
//
//                        if(ListGroup.size() > 0) {
//
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    group_name.setAdapter(new HintSpinnerAdapter<Group>(getActivity(), ListGroup, "Select Group") {
//                                        @Override
//                                        public String getLabelFor(Group group) {
//                                            return group.getGroup_name();
//                                        }
//
//                                    });
//                                }
//                            });
//                        }
//
//
//                        group_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                group_id = ListGroup.get(i).getGroup_id();
//
//
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> adapterView) {
//
//                            }
//                        });
//                    });
//                    //Toast.makeText(CreateGroupActivity.this, ""+List.get(i).getAreaId(), Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//        });
    }

    public void loadGroupData(int center_id)
    {
//       // groupArrayList =  new ArrayList<>();
//        //progressDialog.show();
//        AsyncTask.execute(() -> {
//            List<Group> List =   AppDatabase.getDatabase(getActivity()).groupDao().getAllByCenter(Integer.parseInt(prefManager.getString("branch_id")),center_id);
//
//            group_name.setAdapter(new HintSpinnerAdapter<Group>(getActivity(), List, "Select Group") {
//                @Override
//                public String getLabelFor(Group group) {
//                    return group.getGroup_name();
//                }
//
//            });
//
//            group_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    group_id = List.get(i).getCenter_id();
//                    //Toast.makeText(CreateGroupActivity.this, ""+List.get(i).getAreaId(), Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//        });

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
                Toast.makeText(getActivity(), "Error in processing QRcode XML", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            } catch (IOException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
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
            SecureQrCode decodedData = new SecureQrCode(getActivity(),scanData);
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

       Toast.makeText(getActivity(), ""+aadharData.getName(), Toast.LENGTH_SHORT).show();
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

}