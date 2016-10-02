package com.example.nic_scm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ScmInterface.WSInterface;


public class closingBalance extends ActionBarActivity {

    private String fp_shop;
    Bundle bundle;

    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    TextView closingbalancereport;

    SoapObject response = null;
    WSInterface wsInterface = new WebService();
    private String district;
    ActionBar actionBar;
    NumberFormat format;

    private String commname, cbqty, commoditycode;
    double cbval;
    private TextView title;
    TableLayout table;
    TableRow tbrow;
    AndroidUtil util = new AndroidUtil(this);
    int margin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_closing_balance);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2378c8")));
            actionBar.setTitle("FP Shop Status");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(null);
            actionBar.setIcon(R.drawable.newlog);
            actionBar.setLogo(R.drawable.newlog);
        }
        int dpValue = 120; // margin in dips
        float d = closingBalance.this.getResources().getDisplayMetrics().density;
        margin = (int) (dpValue * d); // margin in pixels
        table = (TableLayout) findViewById(R.id.maintab);
        bundle = getIntent().getExtras();
        String monthname = (String) android.text.format.DateFormat.format("MMMM", new Date());
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (!util.isOnline()) {
            builder.setMessage("Please check your INTERNET CONNECTION").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    closingBalance.this.setResult(1);
                    finish();
                    startActivity(mainActivity);
                }
            }).create().show();
        }

        new SoapAccessTask().execute();
        setContentView(R.layout.activity_closing_balance);
        closingbalancereport = (TextView) findViewById(R.id.shopNoandName);
        closingbalancereport.setText("Closing Balance Report for Shop no: " + fp_shop + " in " + monthname + "-" + year);
        closingbalancereport.setSelected(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public class SoapAccessTask extends AsyncTask<String, SoapObject, SoapObject> {


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            fp_shop = bundle.getString("fp_shop");
            district = bundle.getString("district");

            builder = new AlertDialog.Builder(closingBalance.this);
            progressDialog = new ProgressDialog(closingBalance.this);
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

            if (soapresponse != null) {
                String message = "";
                String color = "#fffff0";
                SoapObject livereponse = null;
               // System.out.println("first resp msg iss---" + soapresponse.toString());

                table = (TableLayout) findViewById(R.id.maintab);
                for (int a = 0; a < soapresponse.getPropertyCount(); a++) {
                    livereponse = (SoapObject) soapresponse.getProperty(a);

                }
                if (livereponse != null) {
                    message = livereponse.getProperty("respMessage").toString();
                }
                if (message.equalsIgnoreCase("success")) {
                    for (int i = 0; i < soapresponse.getPropertyCount() - 1; i++) {
                        livereponse = (SoapObject) soapresponse.getProperty(i);

                        tbrow = new TableRow(closingBalance.this);
                        cbqty = livereponse.getProperty("closing_balance").toString();
                        commname = livereponse.getProperty("comm_name").toString();
                        commoditycode = livereponse.getProperty("commoditycode").toString();


                        if (commoditycode.equals("4") || commoditycode.equals("6")) {
                            format = new DecimalFormat("#0");
                        } else {
                            format = new DecimalFormat("#0.000");
                        }

                        //set color
                        if (i % 2 == 0) {
                            color = "#add8e6";
                        } else {
                            color = "#f0fff0";
                        }

                        TextView commodity = new TextView(closingBalance.this);
                        if (commname != null) {
                            commodity.setText(commname);
                        } else {
                            commodity.setText("NA");
                        }
                        commodity.setTextColor(Color.parseColor("#ff373fff"));
                        //commodity.setBackgroundResource(R.drawable.cell_shape);
                        commodity.setBackgroundColor(Color.parseColor(color));
                        commodity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        tbrow.addView(commodity);

                        TextView cb_qty = new TextView(closingBalance.this);
                        if (cbqty != null) {
                            cbval = Double.parseDouble(cbqty);
                            cb_qty.setText(String.valueOf(format.format(cbval)));
                        } else {
                            cb_qty.setText("NA");
                        }
                        cb_qty.setTextColor(Color.parseColor("#ff373fff"));
                        cb_qty.setGravity(Gravity.CENTER);
                        cb_qty.setBackgroundColor(Color.parseColor(color));
                        cb_qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                       // cb_qty.setBackgroundResource(R.drawable.cell_shape);
                        // commodity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        tbrow.addView(cb_qty);

                        if (tbrow.getParent() != null)
                            ((ViewGroup) tbrow.getParent()).removeView(tbrow);
                        table.addView(tbrow);

                    }
                } else {
                    builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                            mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainActivity);
                            closingBalance.this.setResult(1);
                            finish();
//                       closingBalance.this.finish();
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
                        closingBalance.this.setResult(1);
                        finish();
//                       closingBalance.this.finish();
                    }
                }).create().show();
            }
            progressDialog.dismiss();
        }
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
