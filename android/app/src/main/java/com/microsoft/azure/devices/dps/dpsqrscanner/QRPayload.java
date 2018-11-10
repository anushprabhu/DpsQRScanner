package com.microsoft.azure.devices.dps.dpsqrscanner;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class QRPayload {

    public String dpsUrl;

    public String deviceCertificate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public java.security.cert.X509Certificate GetDeviceCertificate() throws CertificateException {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        String decodedCertificateString = new String(Base64.getDecoder().decode(this.deviceCertificate));

        InputStream certificateStream = new ByteArrayInputStream(decodedCertificateString.getBytes());
        java.security.cert.X509Certificate x509Cert = (java.security.cert.X509Certificate)cf.generateCertificate(certificateStream);
        return x509Cert;
    }
}
