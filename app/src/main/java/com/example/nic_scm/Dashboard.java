package com.example.nic_scm;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.Serializable;
import java.lang.reflect.Field;


public class Dashboard extends ActionBarActivity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2378c8")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeAsUpIndicator(null);
            actionBar.setIcon(R.drawable.newlog);
            actionBar.setLogo(R.drawable.newlog);
        }

        //if crashes,use this Build.VERSION.SDK_INT >=21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor((Color.parseColor("#2378c8")));
        }

        final Bundle bundle = getIntent().getExtras();

        Button allocationDetails = (Button) findViewById(R.id.allocation_details);
        Button closingReport = (Button) findViewById(R.id.closing_balance);
        Button stockReport = (Button) findViewById(R.id.stock_report_details);
        Button icdsReport = (Button) findViewById(R.id.icds_details);

        allocationDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AD = new Intent(getBaseContext(), Allocation_Details.class);
//                AD.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                AD.putExtras(bundle);
//                startActivity(AD);
                startActivityForResult(AD, 1);
            }
        });

        closingReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CB = new Intent(getBaseContext(), closingBalance.class);
//                CB.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                CB.putExtras(bundle);
//                startActivity(CB);
                startActivityForResult(CB, 1);
            }
        });
        stockReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stockReport = new Intent(getBaseContext(), StockReportDetails.class);
//                stockReport.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                stockReport.putExtras(bundle);
//                startActivity(stockReport);
                startActivityForResult(stockReport, 1);
            }
        });

        icdsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icdsReport = new Intent(getBaseContext(), ICDSActivity.class);
//                stockReport.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                icdsReport.putExtras(bundle);
//                startActivity(stockReport);
                startActivityForResult(icdsReport, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);

        // Associate searchable configuration with the SearchView
       /* SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));*/

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent refresh = new Intent(getBaseContext(), About.class);
                startActivity(refresh);
                return true;
          /*  case R.id.action_help:
                Intent help = new Intent(getBaseContext(), About.class);
                startActivity(help);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(Dashboard.this, MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);
//        Dashboard.this.finish();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            this.finish();
        }
    }
}
