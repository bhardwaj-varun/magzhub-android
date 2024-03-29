package magzhub.com.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class StartActivity extends Activity {
    SessionManagement sessionManagement;
    Intent i;
    String TAG="StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_start);
        sessionManagement= new SessionManagement(getApplicationContext());
        if(!sessionManagement.isLoggedIn()){
            Log.e(TAG,"Starting Launcher Activity ");
            i= new Intent(StartActivity.this,Launcher.class);
        }
        else {
            Log.e(TAG,"Starting MySubscription Activity ");
            i = new Intent(StartActivity.this, MySubscription.class);
        }
            startActivity(i);
        finish();

    }

}
