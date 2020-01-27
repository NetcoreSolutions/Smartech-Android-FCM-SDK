package com.smartech.nativedemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.smartech.nativedemo.Utils.Netcore;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEventName, etPayload;
    private Button btnSubmit;
    private String payload = "{\n" +
            "    \"payload\": {\n" +
            "        \"company_name\": \"Apple\",\n" +
            "        \"prid\": 12,\n" +
            "        \"product_name\": \"iPhone\",\n" +
            "        \"product_price\": 999.99,\n" +
            "        \"product_quantity\": 2,\n" +
            "        \"prqt\": 2,\n" +
            "        \"user_id\": 10,\n" +
            "        \"country\": [\"india\", \"aus\", \"us\"],\n" +
            "        \"countrycode\": [91, 92, 140],\n" +
            "        \"countrygdp\": [91, 92.5, 14.278],\n" +
            "        \"items\": [{\n" +
            "            \"company_name\": \"Apple\",\n" +
            "            \"prid\": 12,\n" +
            "            \"product_name\": \"iPhone\",\n" +
            "            \"product_price\": 50001,\n" +
            "            \"product_quantity\": 2,\n" +
            "            \"prqt\": 2,\n" +
            "            \"user_id\": 10\n" +
            "        }, {\n" +
            "            \"company_name\": \"Apple\",\n" +
            "            \"prid\": 123,\n" +
            "            \"product_name\": \"iPhone X\",\n" +
            "            \"product_price\": 49999,\n" +
            "            \"product_quantity\": 1,\n" +
            "            \"prqt\": 1,\n" +
            "            \"user_id\": 10\n" +
            "        }],\n" +
            "        \"product\": {\n" +
            "            \"product_name\": \"apple\",\n" +
            "            \"prid\": 100\n" +
            "        }\n" +
            "    }\n" +
            "}\n}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        etEventName = findViewById(R.id.activity_custom_page_et_event_name);
        etPayload = findViewById(R.id.activity_custom_page_et_custom_payload);
        btnSubmit = findViewById(R.id.activity_custom_page_btn_submit);

        etEventName.setText("event1");
        etEventName.setSelection(etEventName.getText().toString().trim().length());
        etPayload.setText(payload);

        btnSubmit.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Custom event");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_custom_page_btn_submit:
                SharedPreferences pref = this.getSharedPreferences("storedData", 0); // 0 - for private mode
                Netcore.track(CustomActivity.this, etEventName.getText().toString().trim(), etPayload.getText().toString().trim());
                Toast.makeText(this, "Event submitted succesfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

