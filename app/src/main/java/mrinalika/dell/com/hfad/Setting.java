package mrinalika.dell.com.hfad;

import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
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
        toolbar.setLogo(R.mipmap.ic_logo_n_icon);
        toolbar.setTitle("Magzhub");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        logout_btn=(Button)findViewById(R.id.btn_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {     JSONParserforHttps.connection=null;
                    SessionManagement sessionManagement= new SessionManagement(getApplicationContext());
                    if(sessionManagement.isLoggedIn()){
                        userId=sessionManagement.getUserId();
                        userName=sessionManagement.getProfileDetail();
                        Log.e("TAG","UserId: "+userId+"UserName : "+userName);
                    }
                   LoginActivity.ClearCookies(Setting.this);
                    Log.e(TAG, "cookies of JSONParserforHttps" + JSONParserforHttps.firstConnection);
                    sessionManagement.logoutUser();
                    Intent i= new Intent(Setting.this, Launcher.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
