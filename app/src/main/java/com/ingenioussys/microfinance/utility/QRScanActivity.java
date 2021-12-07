package com.ingenioussys.microfinance.utility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.AadharCard;
import com.ingenioussys.microfinance.model.DataAttributes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRScanActivity extends AppCompatActivity {
    AadharCard aadharData;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scan);
        scanNow();
    }
    public boolean checkCameraPermission (){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void scanNow(){
        // we need to check if the user has granted the camera permissions
        // otherwise scanner will not work

        if(!checkCameraPermission()){return;}
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a Aadharcard QR Code");
        integrator.setResultDisplayDuration(500);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            Log.d("scannedd_data",scanContent);
            Log.d("scanFormat",scanFormat);
            // process received data
            if(scanContent != null && !scanContent.isEmpty()){
                processScannedData(scanContent);
            }else{
               // showWarningPrompt("Scan Cancelled");
            }

        }else{
            //showWarningPrompt("No scan data received!");
        }
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
                        Log.d("Rajdeol","Start document");
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
                        Log.d("Rajdeol","End tag "+parser.getName());

                    } else if(eventType == XmlPullParser.TEXT) {
                        Log.d("Rajdeol","Text "+parser.getText());

                    }
                    // update eventType
                    eventType = parser.next();
                }

                // display the data on screen
               // displayScannedData();
                return;
            } catch (XmlPullParserException e) {
              //  showErrorPrompt("Error in processing QRcode XML");
                e.printStackTrace();
                return;
            } catch (IOException e) {
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
            SecureQrCode decodedData = new SecureQrCode(this,scanData);
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

        Toast.makeText(this, ""+aadharData.getName(), Toast.LENGTH_SHORT).show();
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