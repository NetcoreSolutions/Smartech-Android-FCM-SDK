package com.smartech.demo;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartech.demo.Utils.Netcore;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEventName,etPayload;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        etEventName =  findViewById(R.id.activity_custom_page_et_event_name);
        etPayload =  findViewById(R.id.activity_custom_page_et_custom_payload);
        btnSubmit = findViewById(R.id.activity_custom_page_btn_submit);

        etEventName.setText("Add To Cart");
        etEventName.setSelection(etEventName.getText().toString().trim().length());
        etPayload.setText("{\"payload\":{\"name\":\"Nexus 5\",\"prid\":2,\"price\":15000,\"prqt\":1}}");

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
        switch (v.getId()){
            case R.id.activity_custom_page_btn_submit:
                SharedPreferences pref = this.getSharedPreferences("storedData", 0); // 0 - for private mode
                Netcore.track(CustomActivity.this, etEventName.getText().toString().trim(), etPayload.getText().toString().trim());
                Toast.makeText(this, "Event submitted succesfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

