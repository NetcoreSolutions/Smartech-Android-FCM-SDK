package in.netcore.smartechfcmintegration.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.netcore.smartechfcm.NetcoreSDK;
import in.netcore.smartechfcmintegration.R;


public class ActionActivity extends AppCompatActivity {
    Button btnaddtocart ,btncheckout, btnpagebrowse, btncartexpiry, btnremovefromcart, btncustom101, btncustom102, btninapp;
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

        btnpagebrowse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject newPaylaod = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "s^price", "15000 Rs." );
                    jsonObject.put( "i^prqt",  1);
                    jsonArray.put( jsonObject );
                    newPaylaod.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 1, newPaylaod.toString());
                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
        });
        btnaddtocart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject newPaylaod = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "s^price", "15000 Rs." );
                    jsonObject.put( "i^prqt",  1);
                    jsonArray.put( jsonObject );
                    newPaylaod.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 2, newPaylaod.toString());
                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
        });
        btncheckout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject newPaylaod = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "s^price", "15000 Rs." );
                    jsonObject.put( "i^prqt",  1);
                    jsonArray.put( jsonObject );
                    newPaylaod.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 3, newPaylaod.toString());
                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
        });
        btncartexpiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject newPaylaod = new JSONObject();
                try {

                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "s^price", "15000 Rs." );
                    jsonObject.put( "i^prqt",  1);
                    jsonArray.put( jsonObject );
                    newPaylaod.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 4, newPaylaod.toString());
                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
        });
        btnremovefromcart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject newPaylaod = new JSONObject();
                try {
                    jsonObject.put( "s^name", "Nexus 5" );
                    jsonObject.put( "i^prid", 2 );
                    jsonObject.put( "s^price", "15000 Rs." );
                    jsonObject.put( "i^prqt",  1);
                    jsonArray.put( jsonObject );
                    newPaylaod.put( "payload", jsonArray );
                    NetcoreSDK.track( ActionActivity.this, 5, newPaylaod.toString());
                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
        });
        btncustom101.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ActionActivity.this, ProfileActivity.class);
                startActivity( intent );
            }
        });
        btncustom102.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences pref = getApplicationContext().getSharedPreferences("storedData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("identity", "");
                editor.commit();
                NetcoreSDK.logout( ActionActivity.this);
                NetcoreSDK.clearIdentity(ActionActivity.this);
                Intent intent = new Intent(ActionActivity.this, LoginActivity.class);
                startActivity( intent );
            }
        });
        btninapp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ActionActivity.this, InAppActivity.class);
                startActivity( intent );
            }
        });
    }
}