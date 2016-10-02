package com.example.nic_scm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import ScmInterface.WSInterface;


public class StockReportDetails extends ActionBarActivity {

    AlertDialog.Builder builder;
    AndroidUtil util = new AndroidUtil();
    protected ProgressDialog progressDialog;
    Bundle bundle;
    ActionBar actionBar;
    WSInterface wsInterface = new WebService();
    private SoapObject response;
    private String fp_shop;
    NumberFormat format;

    private String commname, allotqty, recqty, issuedqty, obqty, cbqty, commoditycode;
    double allotval, recval, issuedval, obval, cbval;
     private TextView title;
    TableLayout table;
    TableRow tbrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_activity_stock_report_details);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2378c8")));
            //actionBar.setTitle("FP Shop Status");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(null);
            actionBar.setIcon(R.drawable.newlog);
            actionBar.setLogo(R.drawable.newlog);
        }
        //actionBar.hide();
        bundle = getIntent().getExtras();

        fp_shop = bundle.getString("fp_shop");

        table = (TableLayout) findViewById(R.id.maintab);

        new SoapAccessTask().execute();
        setContentView(R.layout.activity_activity_stock_report_details);
        title = (TextView) findViewById(R.id.title);
        title.setText("Stock Register for Shop No: " + fp_shop);
        title.setSelected(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public class SoapAccessTask extends AsyncTask<String, SoapObject, SoapObject> {

        @Override
        protected void onPreExecute() {

            builder = new AlertDialog.Builder(StockReportDetails.this);
            progressDialog = new ProgressDialog(StockReportDetails.this);
            progressDialog.setMessage("Processing Data...");
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Please Wait");
            progressDialog.show();
        }


        @Override
        protected SoapObject doInBackground(String... params) {

            response = wsInterface.getStockReportDetailsInAndroid(fp_shop);
            return response;
        }

        @Override
        protected void onPostExecute(SoapObject soapresponse) {
            super.onPostExecute(soapresponse);

            String message="";
            String color = "#fffff0";

            if (soapresponse != null) {
                SoapObject livereponse = null;
               // System.out.println("first resp msg iss---" + soapresponse.toString());

                table = (TableLayout) findViewById(R.id.maintab);
                for(int a = 0; a < soapresponse.getPropertyCount(); a++){
                    livereponse = (SoapObject) soapresponse.getProperty(a);

                }
                if(livereponse!=null) {
                    message = livereponse.getProperty("respMessage").toString();
                }
                    if(message.equalsIgnoreCase("success")) {

                    for (int b = 0; b < soapresponse.getPropertyCount() - 1; b++) {
                        livereponse = (SoapObject) soapresponse.getProperty(b);

                        tbrow = new TableRow(StockReportDetails.this);

                        allotqty = livereponse.getProperty("allot_qty").toString();
                        recqty = livereponse.getProperty("received_qty").toString();
                        issuedqty = livereponse.getProperty("issued_qty").toString();
                        obqty = livereponse.getProperty("opening_balance").toString();
                        cbqty = livereponse.getProperty("closing_balance").toString();
                        commname = livereponse.getProperty("comm_name").toString();
                        commoditycode = livereponse.getProperty("commoditycode").toString();

                        //set color
                        if (b % 2 == 0) {
                            color = "#add8e6";
                        } else {
                            color = "#f0fff0";
                        }


                        TextView commodity = new TextView(StockReportDetails.this);
                        if (commname != null) {
                            commodity.setText(commname);
                        } else {
                            commodity.setText("NA");
                        }
                        commodity.setTextColor(Color.parseColor("#000000"));
                        commodity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        commodity.setBackgroundColor(Color.parseColor(color));
                        commodity.setBackgroundResource(R.drawable.cell_shape);
                        tbrow.addView(commodity);

                        if(commoditycode.equals("4") ||  commoditycode.equals("6")){
                            format = new DecimalFormat("#0");
                        }else{
                            format = new DecimalFormat("#0.000");
                        }

                        TextView allot_qty = new TextView(StockReportDetails.this);
                        if (allotqty != null) {
                            allotval = Double.parseDouble(allotqty);
                            allot_qty.setText(String.valueOf(format.format(allotval)));
                        } else {
                            allot_qty.setText("NA");
                        }
                        allot_qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        allot_qty.setTextColor(Color.parseColor("#000000"));
                        allot_qty.setBackgroundColor(Color.parseColor(color));
                        allot_qty.setBackgroundResource(R.drawable.cell_shape);
                        allot_qty.setGravity(Gravity.END);
                        tbrow.addView(allot_qty);

                        TextView issueqty = new TextView(StockReportDetails.this);
                        if (issuedqty != null) {
                            issuedval = Double.parseDouble(issuedqty);
                            issueqty.setText(String.valueOf(format.format(issuedval)));
                        } else {
                            issueqty.setText("NA");
                        }
                        issueqty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        issueqty.setTextColor(Color.parseColor("#000000"));
                        issueqty.setGravity(Gravity.END);
                        issueqty.setBackgroundColor(Color.parseColor(color));
                        issueqty.setBackgroundResource(R.drawable.cell_shape);
                        tbrow.addView(issueqty);

                        TextView rec_qty = new TextView(StockReportDetails.this);
                        if (recqty != null) {
                            recval = Double.parseDouble(recqty);
                            rec_qty.setText(String.valueOf(format.format(recval)));
                        } else {
                            rec_qty.setText("NA");
                        }
                        rec_qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        rec_qty.setTextColor(Color.parseColor("#000000"));
                        rec_qty.setGravity(Gravity.END);
                        rec_qty.setBackgroundColor(Color.parseColor(color));
                        rec_qty.setBackgroundResource(R.drawable.cell_shape);
                        tbrow.addView(rec_qty);

                        TextView ob_qty = new TextView(StockReportDetails.this);
                        if (obqty != null) {
                            obval = Double.parseDouble(obqty);
                            ob_qty.setText(String.valueOf(format.format(obval)));
                        } else {
                            ob_qty.setText("NA");
                        }
                        ob_qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        ob_qty.setTextColor(Color.parseColor("#000000"));
                        ob_qty.setGravity(Gravity.END);
                        ob_qty.setBackgroundColor(Color.parseColor(color));
                        ob_qty.setBackgroundResource(R.drawable.cell_shape);
                        tbrow.addView(ob_qty);

                        TextView cb_qty = new TextView(StockReportDetails.this);
                        if (cbqty != null) {
                            cbval = Double.parseDouble(cbqty);
                            cb_qty.setText(String.valueOf(format.format(cbval)));
                        } else {
                            cb_qty.setText("NA");
                        }
                        cb_qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        cb_qty.setTextColor(Color.parseColor("#000000"));
                        cb_qty.setGravity(Gravity.END);
                        cb_qty.setBackgroundColor(Color.parseColor(color));
                        cb_qty.setBackgroundResource(R.drawable.cell_shape);
                        tbrow.addView(cb_qty);

                        if (tbrow.getParent() != null)
                            ((ViewGroup) tbrow.getParent()).removeView(tbrow);
                        table.addView(tbrow);

                    }
                }else{
                    builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                            mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainActivity);
                            StockReportDetails.this.setResult(1);
                            finish();
//                        StockReportDetails.this.finish();
                        }
                    }).create().show();
                }


            } else {
                builder.setMessage("Could not reach the Server. Please try again.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainActivity);
                        StockReportDetails.this.setResult(1);
                        finish();
//                        StockReportDetails.this.finish();
                    }
                }).create().show();
            }
            progressDialog.dismiss();
        }
    }

    public void onBackPressed() {
        finish();
//        StockReportDetails.this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}