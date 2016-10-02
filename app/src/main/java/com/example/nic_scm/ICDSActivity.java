package com.example.nic_scm;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.ksoap2.serialization.SoapObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import ScmInterface.WSInterface;

/**
 * Created by Shanker on 2/12/2016.
 */
public class ICDSActivity extends ActionBarActivity {

    private String fp_shop;
    private String cb;
    Bundle bundle;

    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    EditText Rice_cb, Kerosene_cb, Atta_cb, Wheat_cb, Sugar_cb, rdal, icdal, icrice, icoil;
    TextView closingbalancereport;

    SoapObject response = null;
    WSInterface wsInterface = new WebService();
    private String district;
    ActionBar actionBar;
    NumberFormat format;

    private String commname, cbqty, commoditycode;
    double  cbval;
    private TextView title;
    TableLayout table;
    TableRow tbrow;
    AndroidUtil util = new AndroidUtil(this);

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

        table = (TableLayout) findViewById(R.id.maintab);
        bundle = getIntent().getExtras();
        String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        int year= Calendar.getInstance().get(Calendar.YEAR);
        if (!util.isOnline()) {
            builder.setMessage("Please check your INTERNET CONNECTION").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ICDSActivity.this.setResult(1);
                    finish();
                    startActivity(mainActivity);
                }
            }).create().show();
        }

        new SoapAccessTask().execute();
        setContentView(R.layout.icds_details);
        closingbalancereport = (TextView) findViewById(R.id.shopNoandName);
        closingbalancereport.setText("ICDS Allocation Details for Shop no: " + fp_shop +" in "+monthname+"-"+year);
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

            builder = new AlertDialog.Builder(ICDSActivity.this);
            progressDialog = new ProgressDialog(ICDSActivity.this);
            progressDialog.setMessage("Processing Data...");
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Please Wait");

            progressDialog.show();

        }

        @Override
        protected SoapObject doInBackground(String... params) {
            response = wsInterface.getICDSDetails(fp_shop);
            return response;
        }

        @Override
        protected void onPostExecute(SoapObject soapresponse) {
            super.onPostExecute(soapresponse);

            if (soapresponse != null) {
                String message="";
                String color = "#fffff0";
                SoapObject livereponse = null;
                System.out.println("first resp msg iss---" + soapresponse.toString());

                table = (TableLayout) findViewById(R.id.maintab);
                for(int a = 0; a < soapresponse.getPropertyCount(); a++){
                    livereponse = (SoapObject) soapresponse.getProperty(a);

                }
                if(livereponse!=null) {
                    message = livereponse.getProperty("respMessage").toString();
                }
                if(message.equalsIgnoreCase("success")) {
                    for (int i = 0; i < soapresponse.getPropertyCount() - 1; i++) {
                        livereponse = (SoapObject) soapresponse.getProperty(i);

                        tbrow = new TableRow(ICDSActivity.this);
                        cbqty = livereponse.getProperty("allot_qty").toString();
                        commname = livereponse.getProperty("comm_name").toString();
                        commoditycode = livereponse.getProperty("commoditycode").toString();

                        if(commoditycode.equals("4") ||  commoditycode.equals("6")){
                            format = new DecimalFormat("#0");
                        }else{
                            format = new DecimalFormat("#0.000");
                        }
                        //set color
                        if (i % 2 == 0) {
                            color = "#add8e6";
                        } else {
                            color = "#f0fff0";
                        }

                        TextView commodity = new TextView(ICDSActivity.this);
                        if (commname != null) {
                            commodity.setText(commname.contains("ICDS")?commname.replace("ICDS",""):commname);
                        } else {
                            commodity.setText("NA");
                        }
                        commodity.setTextColor(Color.parseColor("#ff373fff"));
                        commodity.setBackgroundResource(R.drawable.cell_shape);
                        commodity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        commodity.setBackgroundColor(Color.parseColor(color));
                        //commodity.setMinHeight(20);
                        //tbrow.setPadding(0, 10, 0, 10);
                        tbrow.addView(commodity);


                        TextView cb_qty = new TextView(ICDSActivity.this);
                        if (cbqty != null) {
                            cbval = Double.parseDouble(cbqty);
                            cb_qty.setText(String.valueOf(format.format(cbval)));
                        } else {
                            cb_qty.setText("NA");
                        }
                        cb_qty.setBackgroundResource(R.drawable.cell_shape);
                        cb_qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        cb_qty.setBackgroundColor(Color.parseColor(color));
                        //commodity.setMinHeight(20);
                        cb_qty.setTextColor(Color.parseColor("#ff373fff"));
                        cb_qty.setGravity(Gravity.CENTER);
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
                            ICDSActivity.this.setResult(1);
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
                        ICDSActivity.this.setResult(1);
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
