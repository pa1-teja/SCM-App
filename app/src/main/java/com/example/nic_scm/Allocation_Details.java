package com.example.nic_scm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import ScmInterface.WSInterface;

public class Allocation_Details extends ActionBarActivity {


    ProgressDialog progressDialog;
    double pdsrice;
    TextView shop, allotment;
    String shopNo, shopName, aaprice, aayrice;
    String month, allocationYear = null;

    NumberFormat format = new DecimalFormat("#0.000");

    Bundle bundle;
    private String PDSRice;
    WSInterface wsInterface = new WebService();
    private String dist_code;
    private String fp_shop;
    ActionBar actionBar;
    EditText Sugar, Wheat, Atta, aayRice, aapRice, pdsRice, dal;
    AlertDialog.Builder builder;
    private Double wheatD;
    private Double sugarD;
    private Double aapriceD;

    private Double attaD;

    private Double aayriceD;
    private Object ad;
    private double rgdal;
    private String district;
    AndroidUtil util = new AndroidUtil(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation__details);
        actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2378c8")));
            //actionBar.setTitle("FP Shop Status");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(null);
            actionBar.setIcon(R.drawable.newlog);
            actionBar.setLogo(R.drawable.newlog);
        }

        progressDialog = new ProgressDialog(Allocation_Details.this);
        builder = new AlertDialog.Builder(this);

        allotment = (TextView) findViewById(R.id.allotmentMandY);
        shop = (TextView) findViewById(R.id.shopNoandName);
        Sugar = (EditText) findViewById(R.id.Sugar);
        Wheat = (EditText) findViewById(R.id.Wheat);
        Atta = (EditText) findViewById(R.id.atta);
        pdsRice = (EditText) findViewById(R.id.PDSRice);
        aapRice = (EditText) findViewById(R.id.aaprice);
        aayRice = (EditText) findViewById(R.id.aayrice);
        dal = (EditText) findViewById(R.id.dal);
        if (!util.isOnline()) {
            builder.setMessage("Please check your INTERNET CONNECTION").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Allocation_Details.this.setResult(1);
                    finish();
                    startActivity(mainActivity);
//                    Allocation_Details.this.finish();
                }
            }).create().show();
        }
        new SoapAccessTask().execute();
    }


    private class SoapAccessTask extends AsyncTask<String, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bundle = getIntent().getExtras();

            fp_shop = bundle.getString("fp_shop");
            dist_code = bundle.getString("dist_code");
            district = bundle.getString("district");

            progressDialog.setTitle("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Connecting to the Server....");
            progressDialog.show();

        }

        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject response = wsInterface.getAllocationDetails(dist_code, fp_shop);
            return response;
        }


        @Override
        protected void onPostExecute(SoapObject response) {

            final Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);

            progressDialog.dismiss();
            if (response != null) {
                for (int b = 0; b < response.getPropertyCount(); b++) {
                    PropertyInfo propertyInfo = new PropertyInfo();
                    response.getPropertyInfo(b, propertyInfo);
                    if (propertyInfo.name.contains("messageResponse") && propertyInfo.getValue().toString().contains("Invalid Shop")) {

                        builder.setMessage("INVALID SHOPNO. please check fp_shopno.").setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainActivity);
                                Allocation_Details.this.setResult(1);
                                finish();
//                                Allocation_Details.this.finish();
                            }
                        }).create().show();
                        return;
                    }
                    if (propertyInfo.name.contains("allocationMonth")) {
                        String allocationMonth = propertyInfo.getValue().toString();
                        switch (Integer.parseInt(allocationMonth)) {
                            case 1:
                                month = "January";
                                break;
                            case 2:
                                month = "February";
                                break;
                            case 3:
                                month = "March";
                                break;
                            case 4:
                                month = "April";
                                break;
                            case 5:
                                month = "May";
                                break;
                            case 6:
                                month = "June";
                                break;
                            case 7:
                                month = "July";
                                break;
                            case 8:
                                month = "August";
                                break;
                            case 9:
                                month = "September";
                                break;
                            case 10:
                                month = "October";
                                break;
                            case 11:
                                month = "November";
                                break;
                            case 12:
                                month = "December";
                                break;
                        }
                        continue;
                    }

                    if (propertyInfo.name.contains("allocationYear")) {
                        allocationYear = propertyInfo.getValue().toString();
                        continue;
                    }

                    if (propertyInfo.name.contains("shopName")) {
                        shopName = propertyInfo.getValue().toString();
                        continue;
                    }
                    if (propertyInfo.name.contains("shopNo")) {
                        shopNo = propertyInfo.getValue().toString();
                        continue;
                    }
                    shop.setText("ShopNo : " + shopNo + "; DealerName : " + shopName + "; District : " + district);
                    shop.setSelected(true);
                    allotment.setText("Allotment for " + month + " " + allocationYear);


                    System.out.println("property is " + propertyInfo.name + " & value is : " + propertyInfo.getValue());
                    if (propertyInfo.name.contains("otherCommodities")) {
                        SoapObject unpack = (SoapObject) propertyInfo.getValue();

                        for (int a = 0; a < unpack.getPropertyCount(); a++) {
                            PropertyInfo info = new PropertyInfo();
                            unpack.getPropertyInfo(a, info);
                            if (info.name.contains("allotment")) {
                                ad = info.getValue();
                            }
                            if (info.name.contains("commName") && info.getValue().toString().contains("Redgram Dal")){
                                continue;
                            }
                            if (info.name.contains("commName") && info.getValue().toString().contains("Palmolein Oil")){
                                continue;
                            }
                            if (info.name.contains("commName") && info.getValue().toString().contains("Sugar")){
                                continue;
                            }
                            if (info.name.contains("commName") && info.getValue().toString().contains("Turmeric Powder")){
                                continue;
                            }
                            if (info.name.contains("commName") && info.getValue().toString().contains("Tamarind")){
                                continue;
                            }

                            for (int c = 0; c < unpack.getPropertyCount(); c++) {
                                if (info.name.contains("commodityCode") && info.getValue().toString().contains("1")) {
                                    wheatD = Double.valueOf(ad.toString())*100;
                                    Wheat.setText(format.format(wheatD));
                                    continue;
                                }
                                if (info.name.contains("commodityCode") && info.getValue().toString().contains("3")) {
                                    sugarD = Double.valueOf(ad.toString())*100;
                                    Sugar.setText(format.format(sugarD));
                                    continue;
                                }
                                if (info.name.contains("commodityCode") && info.getValue().toString().contains("7")) {
                                    attaD = Double.valueOf(ad.toString())*100;
                                    Atta.setText(format.format(attaD));
                                    continue;
                                }
                                if (info.name.contains("commodityCode") && info.getValue().toString().contains("5")) {
                                    rgdal = Double.valueOf(ad.toString())*100;
                                    dal.setText(format.format(rgdal));
                                    continue;
                                }
                            }
                        }
                    }
                    if (propertyInfo.name.contains("riceBean")) {
                        SoapObject unpackObject = (SoapObject) propertyInfo.getValue();

                        for (int f = 0; f < unpackObject.getPropertyCount(); f++) {
                            PropertyInfo Info1 = new PropertyInfo();
                            unpackObject.getPropertyInfo(f, Info1);

                            if (Info1.name.contains("AAYAllotment")) {
                                aayrice = Info1.getValue().toString();
                                aayriceD = Double.valueOf(aayrice);
                                aayRice.setText(format.format(aayriceD));
                                continue;
                            }


                            if (Info1.name.contains("AAPAllotment")) {
                                aaprice = Info1.getValue().toString();
                                aapriceD = Double.valueOf(aaprice);
                                aapRice.setText(format.format(aapriceD));
                                continue;
                            }

                            if (Info1.name.contains("whiteAllotment")) {
                                PDSRice = Info1.getValue().toString();
                                pdsrice = Double.parseDouble(PDSRice);
                                pdsRice.setText(format.format(pdsrice));
                            }

                        }
                    }
                }
            }
            else {
                builder.setMessage("Could not reach the server. Please try again later").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                        startActivity(mainActivity);
                        Allocation_Details.this.setResult(1);
                        finish();
//                        Allocation_Details.this.finish();
                    }
                }).setCancelable(false).create().show();
                return;
            }
        }
    }
    public void onBackPressed(){
        finish();
//        Allocation_Details.this.finish();
    }
}
