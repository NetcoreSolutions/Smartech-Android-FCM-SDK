package com.smartpradeep.androidhivefcm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.smartpradeep.androidhivefcm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import in.netcore.smartechfcm.NetcoreSDK;


public class ActionActivity extends AppCompatActivity implements View.OnClickListener {
    private static String identity = "";
    private String TAG = "ActionActivity";
    Button btnaddtocart ,btncheckout, btnpagebrowse, btncartexpiry, btnremovefromcart, btncustom101, btncustom102, btninapp, btnGetNotifications, btnOptOut, btnOptIn, btnCustomPage, btnLocation, btnChangeToken;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        btnpagebrowse = (Button) findViewById(R.id.btnpagebrowse);
        btnaddtocart = (Button) findViewById(R.id.btnaddtocart);
        btncheckout = (Button) findViewById(R.id.btncheckout);
        btncartexpiry = (Button) findViewById(R.id.btncartexpiry);
        btnremovefromcart = (Button) findViewById(R.id.btnremovefromcart);
        btncustom101 = (Button) findViewById(R.id.btncustom101);
        btninapp = (Button) findViewById(R.id.btninapp);
        btncustom102 = (Button) findViewById(R.id.btncustom102);
        btnGetNotifications = (Button) findViewById(R.id.btnGetNotifications);
        btnOptOut = (Button) findViewById(R.id.btn_opt_out);
        btnOptIn = (Button) findViewById(R.id.btn_opt_in);
        btnCustomPage = (Button) findViewById(R.id.btn_custom_page);
        btnLocation = (Button) findViewById(R.id.btn_location);
        btnChangeToken = (Button) findViewById(R.id.btn_set_token);

        btnpagebrowse.setOnClickListener(this);
        btnaddtocart.setOnClickListener(this);
        btncheckout.setOnClickListener(this);
        btncartexpiry.setOnClickListener(this);
        btnremovefromcart.setOnClickListener(this);
        btncustom101.setOnClickListener(this);
        btncustom102.setOnClickListener(this);
        btnGetNotifications.setOnClickListener(this);
        btninapp.setOnClickListener(this);
        btnOptOut.setOnClickListener(this);
        btnOptIn.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnCustomPage.setOnClickListener(this);
        btnChangeToken.setOnClickListener(this);
        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null)
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key+" : "+bundle.get(key).toString());
                    Toast.makeText(ActionActivity.this,key+" : "+bundle.get(key).toString(),Toast.LENGTH_LONG).show();
                }
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        JSONObject jsonObject;
        JSONArray jsonArray;
        JSONObject newPayload;

        switch (v.getId()){
            case R.id.btn_location:
                NetcoreSDK.setUserLocation(this, 19.0760,72.8777);
                break;

            case R.id.btn_opt_in:
                NetcoreSDK.optOut(getApplicationContext(), false);
                break;

            case R.id.btn_opt_out:
                NetcoreSDK.optOut(getApplicationContext(), true);
                break;

            case R.id.btninapp:
                intent = new Intent(ActionActivity.this, InAppActivity.class);
                startActivity(intent);
                break;

            case R.id.btnGetNotifications:
                intent = new Intent(ActionActivity.this, NotificationCenterActivity.class);
                startActivity(intent);
                break;

            case R.id.btncustom102:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("storedData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("identity", "");
                editor.commit();
                NetcoreSDK.logout( this);
                NetcoreSDK.clearIdentity(this);
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.btncustom101:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.btnremovefromcart:
                jsonObject = new JSONObject();
                jsonArray = new JSONArray();
                newPayload = new JSONObject();
                try {
                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "f^price", 15000);
                    jsonObject.put( "i^prqt",  1);
                    jsonObject.put( "d^dateofpurchase",  getCurrentDate());
                    jsonArray.put( jsonObject );
                    newPayload.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 5, newPayload.toString());
                }
                catch ( JSONException e ) {
                   e.printStackTrace();
                }

            case R.id.btncartexpiry:
                jsonObject = new JSONObject();
                jsonArray = new JSONArray();
                newPayload = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "f^price", 15000);
                    jsonObject.put( "i^prqt",  1);
                    jsonObject.put( "d^dateofpurchase",  getCurrentDate());
                    jsonArray.put( jsonObject );
                    newPayload.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 4, newPayload.toString());
                }
                catch ( JSONException e ) {
                   e.printStackTrace();
                }
                break;

            case R.id.btncheckout:
                jsonObject = new JSONObject();
                jsonArray = new JSONArray();
                newPayload = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "f^price", 15000);
                    jsonObject.put( "i^prqt",  1);
                    jsonObject.put( "d^dateofpurchase",  getCurrentDate());
                    jsonArray.put( jsonObject );
                    newPayload.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 3, newPayload.toString());
                }
                catch ( JSONException e ) {
                   e.printStackTrace();
                }
                break;

            case R.id.btnpagebrowse:
                jsonObject = new JSONObject();
                jsonArray = new JSONArray();
                newPayload = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "f^price", 15000);
                    jsonObject.put( "i^prqt",  1);
                    jsonObject.put( "d^dateofpurchase",  getCurrentDate());
                    jsonArray.put( jsonObject );
                    newPayload.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 1, newPayload.toString());
                }
                catch ( JSONException e ) {
                   e.printStackTrace();
                }
                break;

            case R.id.btnaddtocart:
                jsonObject = new JSONObject();
                jsonArray = new JSONArray();
                newPayload = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "f^price", 15000);
                    jsonObject.put( "i^prqt",  1);
                    jsonObject.put( "d^dateofpurchase",  getCurrentDate());
                    jsonArray.put( jsonObject );
                    newPayload.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 2, newPayload.toString());
                }
                catch (JSONException e) {
                   e.printStackTrace();
                }
                break;

            case R.id.btn_custom_page:
                intent = new Intent(this, CustomActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_set_token:
                NetcoreSDK.setPushToken(this, generateRandomString());
                break;
        }
    }

    private static double getRandomLocation(double min, double max){
        return (Math.random()*((max-min)+1))+min;
    }

    private String getCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    private String generateRandomString(){
        int count = 10;
        String sourceString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {
            int character = (int)(Math.random() * sourceString.length());
            builder.append(sourceString.charAt(character));
        }

        return builder.toString();
    }

}