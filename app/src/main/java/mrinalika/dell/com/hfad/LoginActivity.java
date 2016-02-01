package mrinalika.dell.com.hfad;

import android.content.Context;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    int success;
    final String TAG = "AsyncTask_JPHttps";
    Button loginbtn;
    EditText et,pass;
    ProgressDialog pdialog;
    SessionManagement sessionManagement;
    static CookieManager msCookieManager= new CookieManager();
    static CookieHandler cookieHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManagement= new SessionManagement(getApplicationContext());
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setLogo(R.mipmap.ic_magzhub_logo);
        toolbar.setTitle("Magzhub");

        //  Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar);
       // toolbar.setLogo(R.mipmap.ic_magzhub_logo);
        createVariable();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskParseJson().execute();
                cookieHandler.setDefault(msCookieManager);
            }
        });
    }
    public void createVariable(){
        loginbtn = (Button)findViewById(R.id.btn_login);
        et = (EditText)findViewById(R.id.email);
        pass= (EditText)findViewById(R.id.password);
        // passwrdshw=(Button)findViewById(R.id.showpassword); for view typed password
    }
    // you can make this class as another java file so it will be separated from your main activity.
    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
        String yourJsonStringUrl="https://magzhub.com/services/authenticateExistingUser.php";
        // contacts JSONArray
        JSONArray dataJsonArr = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(LoginActivity.this);
            pdialog.setMessage("Attempting Login");
            pdialog.setCancelable(true);
            pdialog.setIndeterminate(false);
            pdialog.setTitle("Validating User");
            pdialog.show();
            //     finish();
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                // instantiate our json parser
                JSONParserforHttps jParser = new JSONParserforHttps();

                String email=et.getText().toString();
                String password=pass.getText().toString();
                List<NameValuePair> params= new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email",email));
                params.add(new BasicNameValuePair("password",password));
                final JSONObject json= jParser.makingConnectionForJsonObject(yourJsonStringUrl,params,"POST");
                final String messagefromJson=json.getString("custommessage");
                Log.e(TAG,"customMessage"+ messagefromJson);

                // check log cat fro response
                if (json.has("messagestatus")) {
                    Log.e(TAG, "has message status =" + json.getInt("messagestatus") + messagefromJson);
                    try{
                    //success=json.getInt("messagestatus");
                   // if (success==1) {
                     //   sessionManagement = new SessionManagement(getApplicationContext());
                       // sessionManagement.createLoginSession(success);
                        //String getUserName = json.getString("custommessage");
                        //sessionManagement.setProfile(getUserName);}

                    }catch(Exception e){
                        e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (json.getInt("messagestatus") == 1) {
                                    //Toast.makeText(HomePage.this, json.getString("custommessage"), Toast.LENGTH_SHORT).show();


                                //success=json.getInt("messagestatus");

                                    //sessionManagement.createLoginSession(success);
                                    //String getUserName=json.getString("custommessage");
                                    //sessionManagement.setProfile(getUserName);
                                    sessionManagement.createLoginSession(json.getInt("userid"));
                                    sessionManagement.setProfile(messagefromJson);
                                   /* success=json.getInt("messagestatus");

                                    sessionManagement= new SessionManagement(getApplicationContext());
                                    sessionManagement.createLoginSession(success);
                                    String getUserName=json.getString("custommessage");
                                    sessionManagement.setProfile(getUserName);*/
                                    Intent intent = new Intent(LoginActivity.this, MySubscription.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, json.getString("custommessage"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    // Toast.makeText(MainActivity.this, json.getString("custommessage"), Toast.LENGTH_SHORT).show();
                }

                else{
                    Log.e(TAG,"has no message status");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String strFromDoInBg) {
            super.onPostExecute(strFromDoInBg);
            pdialog.dismiss();
        }
    }
    public void onBackPressed(){

        super.onBackPressed();
    }
    @SuppressWarnings("deprecation")
    public static void ClearCookies(Context context)
    {
        Log.e("Clearing Cookies : ","CLEARED");
          msCookieManager.getCookieStore().removeAll();
    }
}
