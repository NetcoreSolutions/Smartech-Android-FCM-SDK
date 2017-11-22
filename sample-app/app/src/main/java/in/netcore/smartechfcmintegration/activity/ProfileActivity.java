package in.netcore.smartechfcmintegration.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import in.netcore.smartechfcm.NetcoreSDK;
import in.netcore.smartechfcmintegration.R;

/**
 * Created by pradeep on 8/22/17.
 */

public class ProfileActivity extends AppCompatActivity

    {
        Button btnSave;
        TextView btnSkip;
        EditText inputEmail, inputName, inputMobile, inputSalary, inputDOB, inputAge, inputWebsite, inputKey;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            inputKey = (EditText) findViewById(R.id.inputKey);
            inputEmail = (EditText) findViewById(R.id.inputEmail);
            inputAge = (EditText) findViewById(R.id.inoutAge);
            inputDOB = (EditText) findViewById(R.id.inoutDOB);
            inputMobile = (EditText) findViewById(R.id.inputMobile);
            inputName = (EditText) findViewById(R.id.inputName);
            inputSalary = (EditText) findViewById(R.id.inputSalary);
            inputWebsite = (EditText) findViewById(R.id.inputWebsite);
            btnSave = (Button) findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//Log.d("TESTING"," name "+inputName.getText()+" name "+inputEmail.getText()+" name "+inputAge.getText()+" name "+inputWebsite.getText()+" name "+inputMobile.getText()+" name "+inputSalary.getText()+" name "+inputDOB.getText()+" name "+inputKey.getText());
                    JSONObject profileDetails = new JSONObject();
                    try {
                        profileDetails.put( "NAME", inputName.getText().toString());
                        profileDetails.put( "AGE", inputAge.getText() );
                        profileDetails.put( "MOBILENO", inputMobile.getText().toString() );
                        profileDetails.put( "DOB", inputDOB.getText().toString() );
                        profileDetails.put( "SALARY", inputSalary.getText() );
                        profileDetails.put( "WEBSITE", inputWebsite.getText().toString() );
                        profileDetails.put ( "EMAILID", inputEmail.getText().toString() );

                        NetcoreSDK.profile(getApplicationContext(), inputKey.getText().toString(), profileDetails);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("storedData", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("identity", inputKey.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(ProfileActivity.this, ActionActivity.class);
                        startActivity( intent );
                    }
                    catch ( JSONException e ) {
                        e.printStackTrace();
                    }

                }
            });


        }
}
