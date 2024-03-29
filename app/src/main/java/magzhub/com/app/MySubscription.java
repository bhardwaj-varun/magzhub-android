package magzhub.com.app;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class MySubscription extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    String TAG="MySubscription";
    SessionManagement sessionManagement;
    static Boolean isMSLoginStatus; //getting and stored IsLogin value from sharedprference

    private String urlForDownloadFinal=null,urlgoogledrive,urlForDownload="https://docs.google.com/uc?id=ENTER_URL_&export=download";
    Button btnChangedText;
    private Toolbar toolbar;
    private ProgressDialog pdia,pdialog4;
    private FragmentDrawer drawerFragment;
    ArrayList<Magazine> SubscribedMagazineList;
    SubscribedMagazineAdapter subscribedMagazineAdapter;
    static String MagIdforReceived, ex;
    Magazine magObjSubsHandling;
    private String storedSessionId;
    TextView noSubscribedMagazinetv;
    Magazine fetchsubmagazine,MagObjSubsHandling;// = new Magazine();
    SessionManagement ss;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_subscription);
        noSubscribedMagazinetv=(TextView)findViewById(R.id.noSubscribedMagazinestv);
        magObjSubsHandling= new Magazine();
        ToolbarHandling();

        SubscribedMagazineList= new ArrayList<Magazine>();
        ss =new SessionManagement(MySubscription.this);
        isMSLoginStatus=ss.getIsLoginStatus();
        Log.e("sp clean memory", ss.getProfileDetail() + ss.getUserId() + ss.getServerSessoinId());
        if(LoginActivity.msCookieManager.getCookieStore().getCookies().size()==0)
        {
            storedSessionId=ss.getServerSessoinId();
            ex = storedSessionId.substring((storedSessionId.indexOf("[") + 1), storedSessionId.indexOf(", P"));
            Log.e("Extractedvstr PHPSSID", ex);
            LoginActivity.msCookieManager.getCookieStore().add(null, HttpCookie.parse(ex).get(0));
            Log.e(" size cookie", " " + LoginActivity.msCookieManager.getCookieStore().getCookies().size() + "content " + LoginActivity.msCookieManager.getCookieStore().getCookies());
            JSONParserforHttps.firstConnection=false;
        }
        new MySubscriptionAsyncTask().execute();
        ListView submlistview=(ListView)findViewById(R.id.list_subscribed_magazine);
        subscribedMagazineAdapter= new SubscribedMagazineAdapter(getApplicationContext(),R.layout.row_subscribed_magazine,SubscribedMagazineList);
        submlistview.setAdapter(subscribedMagazineAdapter);
        submlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "ListIemClicked " + position + "Magazine Id" + SubscribedMagazineList.get(position).getMagazineId());
                //           magazineId = Integer.parseInt(MagazineList.get(position).getMagazineId());

                long viewId = view.getId();
                Log.e(TAG, "view ID" + viewId);
                if (viewId == R.id.subMagRead) {
                    Log.e(TAG, "Readbtn clicked");
                    new AsyncTaskReadingMag().execute(SubscribedMagazineList.get(position).getMagazineId());

                } else if (viewId == R.id.subMagIssue) {
                    Intent i = new Intent(MySubscription.this, MagazineIssue.class);
                    i.putExtra("MagIdforIssue", SubscribedMagazineList.get(position).getMagazineId());
                    startActivity(i);
                } else if (viewId == R.id.subMagSubsStatus) {
                    btnChangedText=(Button)findViewById(view.getId());
                    AlertforUnsubscription(view, SubscribedMagazineList.get(position).getMagazineId());
                    //btnChangedText.setText("hello");
                   /* if(return_answer==true){
                    new AsyncTaskforMagazineSubscriptionHandling().execute(SubscribedMagazineList.get(position).getMagazineId());
                    String ChangedStatus=SubscriptionHandling(magObjSubsHandling);
                    Log.e(TAG,"changed Status"+ChangedStatus);
                }*/
                }
            }
        });

    }
    public class AsyncTaskforMagazineSubscriptionHandling extends AsyncTask<String,Void,Boolean>{
        JSONObject jsonObjectforMagazineSubscriptionMysubscription;

        private String TAG="ATMagSubs";
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdialog4= new ProgressDialog(MySubscription.this);
            pdialog4.setMessage("Just a moment");
            //pdialog4.setTitle("Loading...");
            pdialog4.setCancelable(true);
            pdialog4.setIndeterminate(false);
            pdialog4.show();

        }
        @Override
        protected Boolean doInBackground(String... params){

            try{
                int resultsubscribe=0;
                String MagsubURL="https://magzhub.com/services/ClassSubscription.php";
                String magIdReceived=params[0];
                Log.e(TAG,"rcvdMagId "+Integer.parseInt(magIdReceived));
                JSONParserforHttps jparser_magazineSubscriotion= new JSONParserforHttps();
                List<NameValuePair> listMagId= new ArrayList<NameValuePair>();
                listMagId.add(new BasicNameValuePair("magID",magIdReceived));
                jsonObjectforMagazineSubscriptionMysubscription= jparser_magazineSubscriotion.makingConnectionForJsonObject(MagsubURL,listMagId,"POST");
                if(jsonObjectforMagazineSubscriptionMysubscription.has("resultSubscribe")){
                    resultsubscribe= jsonObjectforMagazineSubscriptionMysubscription.getInt("resultSubscribe");
                    Log.e(TAG, "has Result Subscribe value= " + resultsubscribe);
                    MagObjSubsHandling=new Magazine();
                    MagObjSubsHandling.setResult_status(resultsubscribe);
                    //fetchsubmagazine.setResult_status(resultsubscribe);
                    //Log.e(TAG," magObChngStatusHandling.setResult_status(resultsubscribe);"+ magObChngStatusHandling.getResult_status());
                }
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean result){
            pdialog4.cancel();
            String ChangedStatus=SubscriptionHandling(MagObjSubsHandling.getResult_status());
            //String ChangedStatus=SubscriptionHandling(fetchsubmagazine.getResult_status());
            Log.e("...","Changed status is "+ChangedStatus);
            btnChangedText.setText(ChangedStatus);
            if(ChangedStatus.equals("Subscribe"))
                startActivity(new Intent(MySubscription.this,MySubscription.class));
               // new MySubscriptionAsyncTask().execute();
            Log.e(TAG, "Changed Status" + ChangedStatus);
            if(result==false)
                Toast.makeText(getApplicationContext(),"Problem in network connection, try again later",Toast.LENGTH_SHORT).show();
        }
    }
public class MySubscriptionAsyncTask extends AsyncTask<String, Void,String>{
    String url="https://magzhub.com/services/displaySubscribedMagazineOfUser.php";
    JSONArray jarrayMySubscription;
    String submid,subname,subthumbnail;
    int subIdOfMag=0,gettingSubMagCalls=0;
    int TotalCount=-1;
    @Override
    protected void onPreExecute(){
        pdia= new ProgressDialog(MySubscription.this);
        pdia.setMessage("Loading...");
        pdia.setCancelable(true);
        pdia.setIndeterminate(true);
        pdia.show();
    }
    public void GetingSubscribedMagazines(){
        TAG="GetingSubscribedMagazines()";
        int count=0;
        gettingSubMagCalls++;// counts o of times GettingSubscribedMagazine is being called
        Log.e(TAG,"function called for times "+gettingSubMagCalls);

        JSONParserforHttps jParser = new JSONParserforHttps();
            List<NameValuePair> magParam= new ArrayList<NameValuePair>();
            magParam.add(new BasicNameValuePair("subId",Integer.toString(subIdOfMag)));
        try{
            jarrayMySubscription = jParser.makingconnectionForJSONArray(url,magParam,"POST");
            //String s = jParser.getStringFromUrl(yourJsonStringUrl);
           /* Log.e(TAG, "SIZE : " + jarrayMySubscription.length());
            // loop through all users*/
            Log.e(TAG,"inside try");
            Log.e(TAG," printingJarray "+jarrayMySubscription);
            if(jarrayMySubscription!=null)
            {
              //  count=jarrayMySubscription.length();
                Log.e(TAG,"SIZE"+count);
                if(TotalCount==-1)
                    TotalCount=0;
                TotalCount += count;
                for (int i = 0; i < jarrayMySubscription.length(); i++) {

                JSONObject c = jarrayMySubscription.getJSONObject(i);
                Log.e(TAG, i+" jsonObject" + c);
                try {
                    if (c.has("Magazineid")) {
                        submid = Integer.toString(c.getInt("Magazineid"));
                        String submname = c.getString("MagazineName");
                        String submthumbnail = c.getString("magazineThumbnail");
                        Log.e(TAG,"Index "+i+"  id:" + submid
                                + " name:" + submname + "thumbnail : " + submthumbnail);
                        if(submname=="null")
                        {
                            Log.e(TAG,"subname"+submname);
                            JSONObject temp=jarrayMySubscription.getJSONObject((2*(i)-1));
                            subIdOfMag=temp.getInt("Magazineid");
                            Log.e(TAG,"SubMagID"+subIdOfMag);
                            break;
                        }
                        fetchsubmagazine = new Magazine();
                        fetchsubmagazine.setMagazineId(submid);
                        fetchsubmagazine.setMagazineName(submname);
                        fetchsubmagazine.setMagazineThumbnail(converttoBitmap(submthumbnail));
                        Log.e(TAG, "Magid" + submid);
                        SubscribedMagazineList.add(fetchsubmagazine);
                        count++;
                        Log.e(TAG,"Count"+count);
                        //Count is used for counting magazines reeived from server
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error in my subs jason data");
                }
                    TotalCount +=i;
                    //adding total coun value to make function return >0 value
            }
                if(count==8)
                    GetingSubscribedMagazines();
            }
        else
             TotalCount+=0;
        }catch (Exception e){
            Log.e(TAG,"Error in json connection");
            e.printStackTrace();
        }
    }
    @Override
    protected String doInBackground(String... params){
       GetingSubscribedMagazines();
        if(TotalCount>0)
            return "Success";
        else if((gettingSubMagCalls==1)&&TotalCount==0 )
            return "NoMagazine";
        else
            return "FetchingFailed";
       /* try {
            // instantiate our json parser
            JSONParserforHttps jParser = new JSONParserforHttps();
            jarrayMySubscription = jParser.getJSONArrayHTTPS(url);
            //String s = jParser.getStringFromUrl(yourJsonStringUrl);
            Log.e(TAG, "SIZE : " + jarrayMySubscription.length());
            // loop through all users
            for (int i = 0; i < jarrayMySubscription.length(); i++) {
                JSONObject c = jarrayMySubscription.getJSONObject(i);
                Log.e(TAG,"jsonObject"+c);
                try{
                    if (c.has("Magazineid")) {
                         submid = Integer.toString(c.getInt("Magazineid"));
                        String submname = c.getString("MagazineName");
                        String submthumbnail = c.getString("thumbnail");
                        Log.e(TAG, "id:" + submid
                                + " name:" + submname +"thumbnail : "+ submthumbnail);
                       // Magazine fetchsubmagazine = new Magazine();
                        fetchsubmagazine=new Magazine();
                        fetchsubmagazine.setMagazineId(submid);
                        fetchsubmagazine.setMagazineName(submname);
                        fetchsubmagazine.setMagazineThumbnail(converttoBitmap(submthumbnail));
                        Log.e(TAG,"Magid"+submid);
                        SubscribedMagazineList.add(fetchsubmagazine);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG,"Error in my subs jason data");
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //return false;
    }
    @Override
    protected void onPostExecute(String result){

            pdia.cancel();
          subscribedMagazineAdapter.notifyDataSetChanged();
        Log.e(TAG, "result is" + result);
        if(result.equals("FetchingFailed")) {
            Toast.makeText(MySubscription.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }            //Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        else if (result.equals("NoMagazine")) {
            noSubscribedMagazinetv.setText("You have not subscribed any magazine yet. Please subscribe ");
            noSubscribedMagazinetv.setVisibility(View.VISIBLE);
        }
    }
}
    public byte[] converttoBitmap(String thumbnail){
        byte[] imageAsByes= Base64.decode(thumbnail.getBytes(), Base64.DEFAULT);
        return imageAsByes;
    }

    public class AsyncTaskReadingMag extends AsyncTask<String,Void,Boolean>{

        JSONParserforHttps jsonforReading= new JSONParserforHttps();
        String urlReading="https://magzhub.com/services/ClassMagazine.php";
        JSONArray jsonArrayReading;
        JSONObject jsonObjectReading;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(String... params){
            String magid;
            magid=params[0];
            List<NameValuePair> readparams= new ArrayList<NameValuePair>();
            readparams.add(new BasicNameValuePair("magId",magid));
            try{
            jsonObjectReading= jsonforReading.makingConnectionForJsonObject(urlReading,readparams,"POST");
        //    Log.e(TAG,"Length of JSONArray"+jsonArrayReading.length());
          //  for(int i=0; i<jsonArrayReading.length();i++) {url receivedhttps://drive.google.com/file/d/0B1peh-y5ILoUd1JoNm5xM1ZCbmc/preview?pli=1
              //  jsonObjectReading = jsonArrayReading.getJSONObject(i); https://drive.google.com/file/d/0B1peh-y5ILoUd1JoNm5xM1ZCbmc/preview?pli=1
                Log.e(TAG,"JSONObject "+jsonObjectReading);
                urlgoogledrive=jsonObjectReading.getString("url");
                Log.e(TAG,"extracted url is : "+urlgoogledrive);
                String extractingSubString= urlgoogledrive.substring(urlgoogledrive.indexOf("d/") + 2 , urlgoogledrive.lastIndexOf("/preview"));
                Log.e(TAG,"extracted substring i"+ extractingSubString);
                //https://docs.google.com/uc?id=0B1peh-y5ILoUd1JoNm5xM1ZCbmc&export=download
                urlForDownloadFinal=urlForDownload.replace("ENTER_URL_",extractingSubString);
                Log.e(TAG,"Final Download Link"+urlForDownloadFinal);
            //}
            return true;
            }catch (Exception e){
                Log.e(TAG,"Errorn in getting jsonobject for reading");
                e.printStackTrace();
            }
            //makingconnectionForJSONArray
            return false;
        }
        @Override
        protected void onPostExecute(Boolean result){
            Intent intent= new Intent(MySubscription.this,ReadingMagazine.class);
            Log.e(TAG,"urlgoogledrive"+urlgoogledrive);
          //  intent.putExtra("URL",urlgoogledrive);
            intent.putExtra("DownloadingURL",urlForDownloadFinal);
            startActivity(intent);
            if(result==false){
                Log.e(TAG,"No Internet Connection");
                Toast.makeText(MySubscription.this, "No Interent Connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(MySubscription.this, Setting.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public String SubscriptionHandling(int result_status) {
        String ButtonSubscriptionStatus = "Unsubscribe";
        Log.d("Subscription Handling ","result Status received "+result_status);
        int resultSubscribeStatus = result_status;
        Log.e(TAG, "SubscriptionStatus = " + ButtonSubscriptionStatus + " Result recieved = " + resultSubscribeStatus);
        if (ButtonSubscriptionStatus.equals("Subscribe") && resultSubscribeStatus == 1) {
            Toast.makeText(this, "Sucessfully Subscribed", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Sucessfully Subscribed");

            ButtonSubscriptionStatus = "Unsubscribe";
        } else if (ButtonSubscriptionStatus.equals("Subscribe") && resultSubscribeStatus == 0) {
            Toast.makeText(this, "Sorry..! You can only subscribe maximum 10 magazines in a month", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Sorry..! You can only subscribe maximum 10 magazines in a month");
            ButtonSubscriptionStatus = "Subscribe";
        } else if (ButtonSubscriptionStatus.equals("Subscribe") && resultSubscribeStatus == 4) {
            Toast.makeText(this, "Cannot Subscribe .. our account is not active..!!", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Cannot Subscribe .. your account is not active..!!");
            ButtonSubscriptionStatus = "Subscribe";
        } else if (ButtonSubscriptionStatus.equals("Unsubscribe") && resultSubscribeStatus == 2) {
            Toast.makeText(this, "Sucessfully Unsubscribed", Toast.LENGTH_LONG).show();
            ButtonSubscriptionStatus = "Subscribe";
            //Log.e(TAG, "Sucessfully Unsubscribed");
        } else if (ButtonSubscriptionStatus.equals("Unsubscribe") && resultSubscribeStatus == 3) {
            Toast.makeText(this, "Can;t unsubscribe.. You can only subscribe magazine after 30 days of subscription..!!", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Can;t unsubscribe.. You can only subscribe magazine after 30 days of subscription..!!");
            ButtonSubscriptionStatus = "Unsubscribe";
        }
        fetchsubmagazine.setSubscriptionStatus(ButtonSubscriptionStatus);
        return ButtonSubscriptionStatus;
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
    //    long viewidNavDrawer=view.getId();
  //      TextView UserName=(TextView)view.findViewById(R.id.txtUsername);
     //   UserName.setText(LoginActivity.username);

        displayView(position);
//        Log.e(TAG, "Username" + UserName.getText());
    }
    private void displayView(int position){
        Log.e(TAG, "position= " + position);
        switch(position){
            case 0:

                startActivity(new Intent(this,MySubscription.class));
                break;
            case 1:

                startActivity(new Intent(this,Categories.class));
                break;
            case 2:
                startActivity(new Intent(this,Setting.class));
                break;
            case 3:
                startActivity(new Intent(this,faq.class));
                break;
            default:
                break;
        }
    }
    public void ToolbarHandling(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.MySubsToolBarTitle));
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_magzhub_transparent_logo);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
    }
    public void AlertforUnsubscription(View view, String MagId){
        final String receivedMagId=MagId;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you really want to Unsubscribe ?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(MySubscription.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                new AsyncTaskforMagazineSubscriptionHandling().execute(receivedMagId);

            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(MySubscription.this,"You clicked yes button",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
