package com.smartpradeep.androidhivefcm.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smartpradeep.androidhivefcm.R;

import in.netcore.smartechfcm.NetcoreSDK;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEventName;
    EditText etPayload;
    Button btnSubmit;
    EditText txtToken;
    Button btn_changeToken;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_page);

        etEventName = (EditText) findViewById(R.id.activity_custom_page_et_event_name);
        etPayload = (EditText) findViewById(R.id.activity_custom_page_et_custom_payload);
        btnSubmit = (Button) findViewById(R.id.activity_custom_page_btn_submit);

        txtToken = (EditText) findViewById(R.id.txtToken);
        btn_changeToken = (Button) findViewById(R.id.btn_changeToken);

        etEventName.setText("Add To Cart");
        etEventName.setSelection(etEventName.getText().toString().trim().length());
        etPayload.setText("{\"payload\":{\"name\":\"Nexus 5\",\"prid\":2,\"price\":15000,\"prqt\":1}}");

        btnSubmit.setOnClickListener(this);
        btn_changeToken.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_custom_page_btn_submit:
                SharedPreferences pref = this.getSharedPreferences("storedData", 0); // 0 - for private mode

                NetcoreSDK.track(CustomActivity.this, etEventName.getText().toString().trim(), etPayload.getText().toString().trim());
                break;

            case R.id.btn_changeToken:
                String token = txtToken.getText().toString();
                NetcoreSDK.setPushToken(CustomActivity.this,token);
                break;
        }
    }
}
