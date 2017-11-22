package in.netcore.smartechfcmintegration.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.netcore.smartechfcm.NetcoreSDK;
import in.netcore.smartechfcmintegration.R;

/**
 * Created by pradeep on 8/22/17.
 */

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView btnSkip, btnSignUp;
    EditText inputEmail, inputPassword;
    String identity = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = this.getSharedPreferences("storedData", 0); // 0 - for private mode
        identity = pref.getString("identity", "");
        NetcoreSDK.register(getApplication(), "<AppId>", identity);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inoutPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSkip = (TextView) findViewById(R.id.btnSkip);
        btnSignUp = (TextView) findViewById(R.id.btnSignUp);

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

                    NetcoreSDK.login(LoginActivity.this, getEmailId);
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
