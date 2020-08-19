package com.smartech.nativedemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartech.nativedemo.utils.Netcore;
import com.smartech.nativedemo.utils.SharedPreferencesManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailView, mPasswordView;
    private String TAG = "LoginActivity";
    private Button btnLogin;
    private TextView textSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.edit_email);
        mPasswordView = findViewById(R.id.edit_pass);
        btnLogin = findViewById(R.id.btn_login);
        textSkip = findViewById(R.id.text_skip);
        String status = SharedPreferencesManager.getInstance(getApplicationContext()).getLoginValue();
        if (status != null && status.equals("login")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + bundle.get(key).toString());
                    if (key.equals("deeplink")) {
                        Uri uri = Uri.parse(bundle.get(key).toString());
                        String keyValue = uri.getQueryParameter("param1");
                        Log.e(TAG, "onCreate: " + keyValue);
                    }
                    if (key.equals("customPayload")) {
                        Log.e(TAG, key + " : " + bundle.get(key).toString());
                        Toast.makeText(this, key + " : " + bundle.get(key).toString(), Toast.LENGTH_LONG).show();
                    }
                }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        btnLogin.setOnClickListener(this);
        textSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                    Netcore.login(LoginActivity.this, email);
                    SharedPreferencesManager.getInstance(getApplicationContext()).setLoginValue("login");
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, getResources().getText(R.string.text_hint_mandatory_all_field), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.text_skip:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}

