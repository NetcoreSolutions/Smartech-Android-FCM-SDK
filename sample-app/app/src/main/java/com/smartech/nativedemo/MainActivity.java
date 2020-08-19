package com.smartech.nativedemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.smartech.nativedemo.activity.NotificationCenterActivity;
import com.smartech.nativedemo.utils.Netcore;
import com.smartech.nativedemo.utils.SharedPreferencesManager;
import com.smartech.nativedemo.utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int OPT_IN = 1;
    public static final int OPT_OUT = 2;
    public static final int PAGE_BROWSE = 1;
    public static final int ADD_TO_CART = 2;
    public static final int CHECKOUT = 3;
    public static final int CART_EXPIRY = 4;
    public static final int REMOVE_FROM_CART = 5;
    String TAG = "LoginActivity";
    Switch switchEvent;
    boolean eventflag = false;
    private ActionBar actionBar;
    private Button btnGetNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddCart = findViewById(R.id.btn_add_cart);
        Button btnRemoveCart = findViewById(R.id.btn_remove_cart);
        Button btnCheckout = findViewById(R.id.btn_checkout);
        Button btnCartExpired = findViewById(R.id.btn_cart_expired);
        Button btnPageBrowse = findViewById(R.id.btn_page_browse);
        Button btnProfileUpdate = findViewById(R.id.btn_profile_update);
        Button btnOptIn = findViewById(R.id.btn_opt_in);
        Button btnOptOut = findViewById(R.id.btn_opt_out);
        Button btnCustomData = findViewById(R.id.btn_custom_data);
        Button btnOtherFunction = findViewById(R.id.btn_other_function);
        Button btnGuid = findViewById(R.id.btn_guid);
        btnGetNotification = findViewById(R.id.btn_get_notification);
        Button btnLogout = findViewById(R.id.btn_logout);
        switchEvent = findViewById(R.id.switch_event);

        trackEventFunc(getResources().getString(R.string.text_add_to_cart));

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_add_cart:
                        if (!eventflag) {
                            trackEventFunc(getResources().getString(R.string.text_add_to_cart));
                        } else {
                            trackEventFunc(ADD_TO_CART);
                        }
                        break;

                    case R.id.btn_remove_cart:
                        if (!eventflag) {
                            trackEventFunc(getResources().getString(R.string.text_remove_from_cart));
                        } else {
                            trackEventFunc(REMOVE_FROM_CART);
                        }
                        break;

                    case R.id.btn_checkout:
                        if (!eventflag) {
                            trackEventFunc(getResources().getString(R.string.text_checkout));
                        } else {
                            trackEventFunc(CHECKOUT);
                        }
                        break;

                    case R.id.btn_cart_expired:
                        if (!eventflag) {
                            trackEventFunc(getResources().getString(R.string.text_cart_expired));
                        } else {
                            trackEventFunc(CART_EXPIRY);
                        }
                        break;

                    case R.id.btn_page_browse:
                        if (!eventflag) {
                            trackEventFunc(getResources().getString(R.string.text_page_browse));
                        } else {
                            trackEventFunc(PAGE_BROWSE);
                        }
                        break;

                    case R.id.btn_profile_update:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;

                    case R.id.btn_opt_in:
                        Util.showAlert(MainActivity.this, OPT_IN, "Smartech Demo", "Do you want to Opt in?");
                        break;

                    case R.id.btn_opt_out:
                        Util.showAlert(MainActivity.this, OPT_OUT, "Smartech Demo", "Do you want to Opt in?");
                        break;

                    case R.id.btn_custom_data:
                        startActivity(new Intent(MainActivity.this, CustomActivity.class));
                        break;

                    case R.id.btn_other_function:
                        startActivity(new Intent(MainActivity.this, OtherFunctionsActivity.class));
                        break;

                    case R.id.btn_guid:
                        Util.showAlertWithMessage(MainActivity.this, "GUID", Netcore.getGUID(getApplicationContext()));
                        break;

                    case R.id.btn_get_notification:
                        startActivity(new Intent(MainActivity.this, NotificationCenterActivity.class));
                        break;

                    case R.id.btn_logout:
                        Netcore.logout(MainActivity.this);
                        SharedPreferencesManager.getInstance(getApplicationContext()).clearLogin();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;

                }
            }
        };
        btnAddCart.setOnClickListener(onClick);
        btnRemoveCart.setOnClickListener(onClick);
        btnCheckout.setOnClickListener(onClick);
        btnCartExpired.setOnClickListener(onClick);
        btnPageBrowse.setOnClickListener(onClick);
        btnProfileUpdate.setOnClickListener(onClick);
        btnOptIn.setOnClickListener(onClick);
        btnOptOut.setOnClickListener(onClick);
        btnCustomData.setOnClickListener(onClick);
        btnOtherFunction.setOnClickListener(onClick);
        btnGuid.setOnClickListener(onClick);

        btnGetNotification.setOnClickListener(onClick);
        btnLogout.setOnClickListener(onClick);

        switchEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                eventflag = isChecked;
                Log.d(TAG, "onCheckedChanged: " + eventflag);
            }
        });

        String status = SharedPreferencesManager.getInstance(getApplicationContext()).getLoginValue();
        if (status == null || !status.equals(SharedPreferencesManager.STATUS_LOGIN)) {

            btnLogout.setVisibility(View.GONE);

        }
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getResources().getText(R.string.text_main_title));
        }

        TextView txtCustomPayload = findViewById(R.id.txtCustomPayload);
        TextView txtDeeplink = findViewById(R.id.txtDeeplink);
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + bundle.get(key).toString());
                    if (key.equals("customPayload")) {
                        String data = key + " : " + bundle.get(key).toString();
                        txtCustomPayload.setText(data);
                        Toast.makeText(this, key + " : " + bundle.get(key).toString(), Toast.LENGTH_LONG).show();
                    }
                    if (key.equals("deeplink")) {
                        String data = key + " : " + bundle.get(key).toString();
                        txtDeeplink.setText(data);
                        Toast.makeText(this, key + " : " + bundle.get(key).toString(), Toast.LENGTH_LONG).show();

                        Uri uri = Uri.parse(bundle.get(key).toString());
                        String keyValue = uri.getQueryParameter("param1");

                        //Toast.makeText(this,  "param1 : " + keyValue, Toast.LENGTH_LONG).show();
                    }
                }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getText(R.string.text_main_title));
        }
        if (btnGetNotification != null) {
            if (Build.VERSION.SDK_INT >= 21) {
//                textNotificationCount.setText("" + Netcore.getUnreadNotificationsCount(this));
                btnGetNotification.setText("Notification center");
            } else {
//                btnGetNotification.setText("Notification center - unread(" + Netcore.getUnreadNotificationsCount(this) + ")");
            }

        }
    }

    private void trackEventFunc(String eventName) {

        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject payload = new JSONObject();
            payload.put("prid", 1);
            payload.put("name", "Nexus");
            payload.put("itemPrice", 2000);
            payload.put("prqt", 2);
            payload.put("currency", "INR");
            jsonObject.put("payload", payload);
            Netcore.track(MainActivity.this, eventName, jsonObject.toString());
            Toast.makeText(this, eventName + " Succesfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }

    }

    private void trackEventFunc(int eventId) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject newPayload = new JSONObject();
        try {

            jsonObject.put("s^name", "Nexus 5");
            jsonObject.put("i^prid", 2);
            jsonObject.put("f^price", 15000);
            jsonObject.put("i^prqt", 1);
            jsonObject.put("d^dateofpurchase", getCurrentDate());
            jsonArray.put(jsonObject);
            newPayload.put("payload", jsonArray);
            Netcore.track(MainActivity.this, eventId, newPayload.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }

    }

    private String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case R.id.english:
//                refreshLanguage(getBaseContext(), "en");
//                return true;
//            case R.id.vietnamese:
//                refreshLanguage(getBaseContext(), "vi");
//                return true;
////            case R.id.item3:
////                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
////                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    public void refreshLanguage(Context baseContext, String language) {
        Log.e(TAG, "refreshLanguage: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Locale locale = new Locale(language);
            Configuration conf = baseContext.getResources().getConfiguration();
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            conf.setLocales(localeList);
            Toast.makeText(baseContext, "Language changed.", Toast.LENGTH_SHORT).show();
            //return baseContext.createConfigurationContext(conf);
        } else {
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale(language));
            res.updateConfiguration(conf, dm);
            Toast.makeText(this, "Language changed.", Toast.LENGTH_SHORT).show();
        }
    }
}
