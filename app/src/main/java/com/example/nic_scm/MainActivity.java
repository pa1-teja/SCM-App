package com.example.nic_scm;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("ShowToast")
public class MainActivity extends ActionBarActivity implements Serializable {


    Spinner List_of_Districts,reports1;
    EditText fp_shop;
    int dist_code;
    Bundle bundle = new Bundle();
    ActionBar actionBar;
    //int col = Color.parseColor("#ff6e77ff");

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2378c8")));
            actionBar.setTitle("FP Shop Status");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.drawable.newlog);
            actionBar.setLogo(R.drawable.newlog);
        }

        //if crashes,use this Build.VERSION.SDK_INT >=21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor((Color.parseColor("#2378c8")));
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        AndroidUtil and = new AndroidUtil(this);

        String text="Please Select your district";
        TextView select = (TextView) this.findViewById(R.id.select);
        final SpannableStringBuilder str = new SpannableStringBuilder(text);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        select.setText(str);
        select.setVisibility(View.GONE);
        select.setTypeface(null, Typeface.BOLD);
        //String dist = select.getText().toString();
        TextView textView = (TextView) this.findViewById(R.id.PDSRice);
        textView.setText("ePDS-Government Of Andhra Pradesh");
        textView.setSelected(true);
        if (!and.isOnline()) {
            builder.setMessage("Please check your INTERNET CONNECTION").setPositiveButton("OK",null).create().show();
            return;
        }
        List_of_Districts = (Spinner) findViewById(R.id.spinner);

        final ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<>();
        list.add(str.toString());
        list.add("Srikakulam");
        list.add("Vizianagaram");
        list.add("Visakhapatnam");
        list.add("East Godavari");
        list.add("West Godavari");
        list.add("Krishna");
        list.add("Guntur");
        list.add("Prakasam");
        list.add("Sri Potti Sriramulu Nellore");
        list.add("Y.S.R.");
        list.add("Kurnool");
        list.add("Anantapur");
        list.add("Chittoor");
        adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        List_of_Districts.setAdapter(adapter);
        List_of_Districts.setVisibility(View.VISIBLE);

        //List_of_Districts.setBackgroundColor(col);


        List_of_Districts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                bundle.putString("district", List_of_Districts.getSelectedItem().toString());
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.BLUE);
                switch (List_of_Districts.getSelectedItem().toString()) {
                    case "Please Select your district":
                        dist_code = 0;

                        Toast msg = Toast.makeText(getApplicationContext(), "Please Select your District", Toast.LENGTH_LONG);
                        msg.show();
                        break;
                    case "Srikakulam":
                        dist_code = 542;

                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");
                        break;
                    case "Vizianagaram":
                        dist_code = 543;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Visakhapatnam":
                        dist_code = 544;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "East Godavari":
                        dist_code = 545;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "West Godavari":
                        dist_code = 546;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Krishna":
                        dist_code = 547;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Guntur":
                        dist_code = 548;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Prakasam":
                        dist_code = 559;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Sri Potti Sriramulu Nellore":
                        dist_code = 550;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Y.S.R.":
                        dist_code = 551;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Kurnool":
                        dist_code = 552;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Anantapur":
                        dist_code = 553;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                    case "Chittoor":
                        dist_code = 554;
                        System.out.println(List_of_Districts.getSelectedItem().toString() + " is selected");

                        break;
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });



        final Button shoot = (Button) findViewById(R.id.button);
        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // shoot event handler
                System.out.println("dist_code in main activity : " + dist_code);
                fp_shop = (EditText) findViewById(R.id.fp_shop);
                System.out.println("fp_shop num after shooting : " + fp_shop.getText());
                if (dist_code == 0) {
                    builder.setMessage("PLEASE SELECT YOUR DISTRICT").setCancelable(true).create().show();
                    return;
                }
                if (fp_shop.getText().toString().isEmpty()) {

                    builder.setMessage("Please enter your FP SHOP number").setCancelable(true).create().show();
                    return;
                } else if (fp_shop.getText().toString().length() != 7) {
                    builder.setMessage("FP SHOP number should contain 7 numbers").setCancelable(true).create().show();
                    return;
                } else {
                    bundle.putString("dist_code", String.valueOf(dist_code));
                    bundle.putString("fp_shop", fp_shop.getText().toString());
                    Intent dashboard = new Intent(getBaseContext(), Dashboard.class);
                    dashboard.addCategory(Intent.CATEGORY_HOME);
                    dashboard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    dashboard.putExtras(bundle);
                    startActivity(dashboard);
                    MainActivity.this.finish();

                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure, you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}

