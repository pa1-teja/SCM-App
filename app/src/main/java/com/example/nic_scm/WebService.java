package com.example.nic_scm;

import android.os.Build;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ScmInterface.WSInterface;

/**
 * Created by sai shanker on 5/21/2015.
 */
public class WebService implements WSInterface {


    private SoapObject object = null;

    public SoapObject getAllocationDetails(String dist_code, String fp_shop) {

        final String NAMESPACE = "http://ro.eseva.com/";
        final String METHOD_NAME = "getFPSAllotment";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //	  protected final String URL = "http://scm.ap.gov.in/robyddatmls/esevaro?wsdl";
        final String URL = "http://scm.ap.gov.in/robyddatmls1/esevaro?wsdl";
        String password = "2aa463727a0f50ab7914bdb72af73e26";
        String hts = "";


        PropertyInfo pack = new PropertyInfo();
        PropertyInfo pack1 = new PropertyInfo();
        PropertyInfo pack2 = new PropertyInfo();
        PropertyInfo pack3 = new PropertyInfo();

        pack.setName("distcode");
        pack.setType(String.class);
        pack.setValue(String.valueOf(dist_code));
        //System.out.println("pack : " + pack);
        pack1.setName("shopNo");
        pack1.setType(String.class);
        pack1.setValue(fp_shop);
        //System.out.println("pack1 : " + pack1);
        pack2.setName("password");
        pack2.setType(String.class);
        pack2.setValue(password);
        //System.out.println("pack2 : " + password);
        pack3.setName("hts");
        pack3.setType(String.class);
        pack3.setValue(hts);
        //System.out.println("pack3 : " + hts);


        request.addProperty(pack);
        request.addProperty(pack1);
        request.addProperty(pack2);
        request.addProperty(pack3);
        //System.out.println("request : " + request);
        SoapSerializationEnvelope soapPackage = new SoapSerializationEnvelope(12);
        //System.out.println("soapPackage : " + soapPackage);
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            soapPackage.setOutputSoapObject(request);
        }
        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call(NAMESPACE, soapPackage);
            http.getServiceConnection().disconnect();
            // app may FORCE CLOSE some times (yet to be fixed).
            request = (SoapObject) soapPackage.bodyOut;
            //System.out.println("request contents : " + request.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SoapObject response = null;

        try {
            response = (SoapObject) soapPackage.getResponse();
          //  System.out.println("allocation resp---"+response.toString());
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        //System.out.println("actual type===="+response.getClass());
        return response;

    }
    ////////////////////////////////////new services//////////////////////////report_type

    public SoapObject getStockReportDetailsInAndroid(String fp_shop) {

        final String NAMESPACE = "http://service.fetch.rationcard/";

        final String METHOD_NAME = "getStockReportMobile";
        SoapObject request1 = new SoapObject(NAMESPACE, METHOD_NAME);
        final String URL = "http://scm.ap.gov.in/ePosServiceApp/rationcardservice?wsdl";
        SoapSerializationEnvelope soapPackage1 = new SoapSerializationEnvelope(12);
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            PropertyInfo pack = new PropertyInfo();
            pack.setName("shop_no");
            pack.setValue(fp_shop);

            PropertyInfo pack1 = new PropertyInfo();
            pack1.setName("report_type");
            pack1.setValue("ALL");

            request1.addProperty(pack);
            request1.addProperty(pack1);
            if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                soapPackage1.setOutputSoapObject(request1);
            }
            try {
                http.call(NAMESPACE, soapPackage1);
                http.getServiceConnection().disconnect();
                // app may FORCE CLOSE some times (yet to be fixed).
                //System.out.println("soap package sent...");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        object = (SoapObject) soapPackage1.bodyIn;
        //System.out.println("stock register details---"+object.toString());
        //System.out.println("request contents : " + request.toString());
        return object;
    }

    public SoapObject getICDSDetails(String fp_shop) {

        final String NAMESPACE = "http://service.fetch.rationcard/";

        final String METHOD_NAME = "getStockReportMobile";
        SoapObject request1 = new SoapObject(NAMESPACE, METHOD_NAME);
        final String URL = "http://scm.ap.gov.in/ePosServiceApp/rationcardservice?wsdl";
        SoapSerializationEnvelope soapPackage1 = new SoapSerializationEnvelope(12);
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            PropertyInfo pack = new PropertyInfo();
            pack.setName("shop_no");
            pack.setValue(fp_shop);

            PropertyInfo pack1 = new PropertyInfo();
            pack1.setName("report_type");
            pack1.setValue("icds");

            request1.addProperty(pack);
            request1.addProperty(pack1);
            if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                soapPackage1.setOutputSoapObject(request1);
            }
            try {
                http.call(NAMESPACE, soapPackage1);
                http.getServiceConnection().disconnect();
                // app may FORCE CLOSE some times (yet to be fixed).
                //System.out.println("soap package sent...");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        object = (SoapObject) soapPackage1.bodyIn;
        //System.out.println("stock register details---"+object.toString());
        return object;
    }
}

