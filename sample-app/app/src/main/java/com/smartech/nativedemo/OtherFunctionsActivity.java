package com.smartech.nativedemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.smartech.nativedemo.Utils.Netcore;
import com.smartech.nativedemo.Utils.SharedPreferencesManager;
import com.smartech.nativedemo.Utils.Util;

import java.text.DateFormat;
import java.util.Date;

public class OtherFunctionsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = OtherFunctionsActivity.class.getSimpleName();
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    public static int MY_PERMISSIONS_REQUEST_FOR_LOCATION = 0;
    private Button btnSetToken, btnSetLocation, btnGetToken, btnCurrentLocation, btnClearId, btnAppId;
    private EditText etLattitude, etLongitude, etSetToken, etappId;
    // location last updated time
    private String mLastUpdateTime;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    //private Boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_functions);

        etLattitude = findViewById(R.id.activity_other_function_et_lattitude);
        etLongitude = findViewById(R.id.activity_other_function_et_longitude);
        etSetToken = findViewById(R.id.activity_other_function_et_set_token);
        etappId = findViewById(R.id.activity_other_function_et_appid);

        btnSetLocation = findViewById(R.id.activity_other_function_btn_set_location);
        btnSetLocation.setOnClickListener(this);

        btnSetToken = findViewById(R.id.activity_other_function_btn_set_token);
        btnSetToken.setOnClickListener(this);

        btnGetToken = findViewById(R.id.activity_other_function_btn_get_token);
        btnGetToken.setOnClickListener(this);

        btnClearId = findViewById(R.id.activity_other_function_clear_id);
        btnClearId.setOnClickListener(this);

        btnCurrentLocation = findViewById(R.id.btn_current_location);
        btnCurrentLocation.setOnClickListener(this);

        btnAppId = findViewById(R.id.activity_other_function_change_app_id);
        btnAppId.setOnClickListener(this);

        //init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getResources().getText(R.string.text_other_function_title));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            etLattitude.setText(String.valueOf(mCurrentLocation.getLatitude()));
            etLongitude.setText(String.valueOf(mCurrentLocation.getLongitude()));
            etLattitude.setAlpha(0);
            etLongitude.setAlpha(0);
            etLattitude.animate().alpha(1).setDuration(300);
            etLongitude.animate().alpha(1).setDuration(300);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_FOR_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                if (!showRationale) {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Permission denied...!")
                            .setMessage("Location permission required for your current location access. \n Do you want to grant permission?")
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, 0);
                        }
                    }).create().show();
                }


                //mRequestingLocationUpdates = true;

            }
            return;

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_other_function_btn_set_location:
                if (!etLattitude.getText().toString().trim().isEmpty() && !etLongitude.getText().toString().trim().isEmpty()) {
                    Netcore.setUserLocation(getApplicationContext(), Double.parseDouble(etLattitude.getText().toString()),
                            Double.parseDouble(etLongitude.getText().toString()));
                    Toast.makeText(this, "Location submitted succesfully", Toast.LENGTH_SHORT).show();
                    etLattitude.setText("");
                    etLongitude.setText("");
                } else {
                    Toast.makeText(this, getResources().getText(R.string.text_hint_mandatory_all_field), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_other_function_btn_set_token:
                if (!etSetToken.getText().toString().trim().isEmpty()) {
                    Netcore.setPushToken(getApplicationContext(), etSetToken.getText().toString());
                    Toast.makeText(this, "Token submitted succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getText(R.string.text_hint_mandatory_field), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_other_function_btn_get_token:

                Util.showAlertWithMessage(OtherFunctionsActivity.this, "Token", Netcore.getPushToken(getApplicationContext()));
                break;
            case R.id.activity_other_function_clear_id:
                Netcore.clearIdentity(getApplicationContext());
                Toast.makeText(this, "Identity clear succesfully", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_current_location:

//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FOR_LOCATION);
//
//                } else {
//                    startLocationUpdates();
//                }
                break;
            case R.id.activity_other_function_change_app_id:
                if (!etappId.getText().toString().trim().isEmpty() && etappId.getText().toString().length() == 32) {
                    SharedPreferencesManager.getInstance(OtherFunctionsActivity.this).setSmartechId(etappId.getText().toString());
                    Util.showAlertWithMessage(OtherFunctionsActivity.this, getResources().getText(R.string.alert_title_appid).toString(), getResources().getText(R.string.alert_message_appid).toString());

                } else {
                    Toast.makeText(this, getResources().getText(R.string.text_hint_mandatory_field), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(OtherFunctionsActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(OtherFunctionsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }
}
