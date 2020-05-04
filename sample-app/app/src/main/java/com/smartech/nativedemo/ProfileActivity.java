package com.smartech.nativedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartech.nativedemo.utils.Netcore;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    Button btnSave;
    String TAG = "LoginActivity";
    EditText inputEmail, inputName, inputMobile, inputSalary, inputDOB, inputAge, inputWebsite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inputName = findViewById(R.id.activity_profile_name);
        inputEmail = findViewById(R.id.activity_profile_email);
        inputAge = findViewById(R.id.activity_profile_age);
        inputDOB = findViewById(R.id.activity_profile_dob);
        inputMobile = findViewById(R.id.activity_profile_mobile_no);
        inputSalary = findViewById(R.id.activity_profile_salary);
        inputWebsite = findViewById(R.id.activity_profile_website);
        btnSave = findViewById(R.id.activity_profile_btn_save);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getResources().getText(R.string.text_profile_title));

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputName.getText().toString().trim().isEmpty() && !inputEmail.getText().toString().trim().isEmpty() &&
                        !inputAge.getText().toString().trim().isEmpty() && !inputWebsite.getText().toString().trim().isEmpty() &&
                        !inputMobile.getText().toString().trim().isEmpty() && !inputSalary.getText().toString().trim().isEmpty() &&
                        !inputDOB.getText().toString().trim().isEmpty()) {
                    JSONObject profileDetails = new JSONObject();
                    try {
                        profileDetails.put("NAME", inputName.getText().toString());
                        profileDetails.put("AGE", inputAge.getText());
                        profileDetails.put("MOBILENO", inputMobile.getText().toString());
                        profileDetails.put("DOB", inputDOB.getText().toString());
                        profileDetails.put("SALARY", inputSalary.getText());
                        profileDetails.put("WEBSITE", inputWebsite.getText().toString());
                        profileDetails.put("EMAILID", inputEmail.getText().toString());
                        // Netcore.login(getApplicationContext(), inputEmail.getText().toString());
                        Netcore.profile(getApplicationContext(), profileDetails);

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("storedData", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.apply();
                        Toast.makeText(ProfileActivity.this, "Profile update succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "Error: " + e.getMessage());
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, getResources().getText(R.string.text_hint_mandatory_all_field), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
