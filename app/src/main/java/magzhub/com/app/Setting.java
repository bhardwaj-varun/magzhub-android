package magzhub.com.app;

import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class Setting extends ActionBarActivity {
    Button logout_btn;
    private String TAG="SettingClass";
    int userId;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher_magzhub_transparent_logo);
        getSupportActionBar().setTitle("Magzhub");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        logout_btn=(Button)findViewById(R.id.btn_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    JSONParserforHttps.connection = null;
                    SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
                    if (sessionManagement.isLoggedIn()) {
                        userId = sessionManagement.getUserId();
                        userName = sessionManagement.getProfileDetail();
                        Log.e(TAG, "UserId: " + userId + "UserName : " + userName);
                    }
                    LoginActivity.ClearCookies(Setting.this);
                    Log.e(TAG, "cookies of JSONParserforHttps" + JSONParserforHttps.firstConnection);
                    JSONParserforHttps.firstConnection=true;
                    sessionManagement.logoutUser();
                    Intent i = new Intent(Setting.this, Launcher.class);
                    //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    Log.e(TAG, "IsLoggedIn" + sessionManagement.isLoggedIn());
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }
}
