package com.microsoft.azure.devices.dps.dpsqrscanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.microsoft.rest.*;
import com.microsoft.rest.credentials.BasicAuthenticationCredentials;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import com.microsoft.rest.serializer.JacksonAdapter;

import org.cryptacular.util.CertUtil;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.security.auth.x500.X500Principal;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import microsoft.azure.devices.registration.clients.service.v20180901.ProvisioningServiceClient;
import microsoft.azure.devices.registration.clients.service.v20180901.implementation.IndividualEnrollmentManager;
import microsoft.azure.devices.registration.clients.service.v20180901.implementation.ProvisioningServiceClientImpl;
import microsoft.azure.devices.registration.clients.service.v20180901.models.IndividualEnrollment;
import microsoft.azure.devices.registration.clients.service.v20180901.models.X509Attestation;
import microsoft.azure.devices.registration.clients.service.v20180901.models.X509CertificateWithInfo;
import microsoft.azure.devices.registration.clients.service.v20180901.models.X509Certificates;

public class QRScanningActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanning);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private ZXingScannerView mScannerView;

    @SuppressLint("NewApi")
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        // show the scanner result into dialog box.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Scan Result");
                builder.setMessage(rawResult.getText());
                AlertDialog alert1 = builder.create();
                alert1.show();

        try {
            enrollDevice(rawResult.getText());
        } catch (CertificateException e) {
            e.printStackTrace();
            Log.e("DPS", "Failed to call DPS Service for enrollment", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void enrollDevice(String qrText) throws CertificateException, IOException {
        Gson gson = new Gson();
        QRPayload dpsQRPayload = gson.fromJson(qrText, QRPayload.class);
        RestClient simpleClient = new RestClient.Builder()
                .withBaseUrl(dpsQRPayload.dpsUrl)
                .withResponseBuilderFactory(new ServiceResponseBuilder.Factory())
                //.withCredentials((ServiceClientCredentials) new ServiceAuthenticationWithSharedAccessPolicyKey("provisioningserviceowner", "KrvCJztso+7fG3QmHELgLIGLVg+qn8oLulA/yWOxDOc="))
                .withSerializerAdapter(new JacksonAdapter())
                .build();
        ProvisioningConnectionString connectionString = ProvisioningConnectionStringBuilder.createConnectionString("Type your host name here");
        ContractApiHttp contractHttp = ContractApiHttp.createFromConnectionString(connectionString);
        IndividualEnrollmentManager manager = IndividualEnrollmentManager.createFromContractApiHttp(contractHttp);
        java.security.cert.X509Certificate certificate = dpsQRPayload.GetDeviceCertificate();
        String registrationId = CertUtil.subjectCN(certificate);
        IndividualEnrollment enrollment =  new IndividualEnrollment();
        enrollment.withRegistrationId(registrationId);
        X509Attestation attestation = new X509Attestation();
        X509Certificates certificates = new X509Certificates();
        X509CertificateWithInfo certificateWithInfo = new X509CertificateWithInfo();
        certificateWithInfo.withCertificate(dpsQRPayload.deviceCertificate);
        certificates.withPrimary(certificateWithInfo);
        attestation.withClientCertificates(certificates);
        enrollment.attestation().withX509(attestation);
        manager.createOrUpdate(enrollment);
        // add pre authorize entry
    }

    public void QrScanner(View view){
        ActivityCompat.requestPermissions(QRScanningActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Begin scanning..");
        AlertDialog alert1 = builder.create();
        alert1.show();
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera(); // Start camera
    }

    @Override
    public void onPause() {
            super.onPause();
            mScannerView.stopCamera();   // Stop camera on pause
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Begin scanning..");
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
                    setContentView(mScannerView);
                    mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                    mScannerView.startCamera(); // Start camera
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(QRScanningActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
