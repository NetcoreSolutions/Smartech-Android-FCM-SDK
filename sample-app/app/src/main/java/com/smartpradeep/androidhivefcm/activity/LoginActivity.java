package com.smartpradeep.androidhivefcm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.smartpradeep.androidhivefcm.R;

import org.json.JSONException;
import org.json.JSONObject;

import in.netcore.smartechfcm.NetcoreSDK;
import io.fabric.sdk.android.Fabric;

/**
 * Created by pradeep on 8/22/17.
 */

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView btnSkip, btnSignUp;
    EditText inputEmail, inputPassword;
    String identity = "";
    String TAG="LoginActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        NetcoreSDK.register(getApplication(), "694286863453dcae0691d37ceb322bac");

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inoutPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSkip = (TextView) findViewById(R.id.btnSkip);
        btnSignUp = (TextView) findViewById(R.id.btnSignUp);
        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null)
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key+" : "+bundle.get(key).toString());
                    Toast.makeText(LoginActivity.this,key+" : "+bundle.get(key).toString(),Toast.LENGTH_LONG).show();
                }
        } catch (Exception e) {
           e.printStackTrace();
        }
        if(!identity.equals("")){
            Intent intent = new Intent(LoginActivity.this, ActionActivity.class);
            startActivity(intent);
        }else {
            btnLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String getEmailId = inputEmail.getText().toString();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("storedData", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("identity", getEmailId);
                    editor.commit();

                    NetcoreSDK.setIdentity(getApplicationContext(), getEmailId);
                    NetcoreSDK.login(getApplicationContext());
                    Intent intent = new Intent(LoginActivity.this, ActionActivity.class);
                    startActivity(intent);
                }
            });
            btnSignUp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });
            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("storedData", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("identity", "");
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, ActionActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
