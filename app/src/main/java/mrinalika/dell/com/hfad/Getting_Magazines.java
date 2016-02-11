package mrinalika.dell.com.hfad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
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

import java.util.ArrayList;
import java.util.List;


public class Getting_Magazines extends ActionBarActivity {
    ProgressDialog pdialog3,pdialog4;
    private String TAG="getting_magazines.java";
    ArrayList<Magazine> MagazineList;
    MagazineAdapter madapter;
    static  int magazineId;
    Magazine magObChngStatusHandling;
    ListView mlistview;
    TextView noMagazinetv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting__magazines);
        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher_magzhub_transparent_logo);
        getSupportActionBar().setTitle("Magzines");
        noMagazinetv=(TextView)findViewById(R.id.noMagazinestv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MagazineList = new ArrayList<Magazine>();
        Intent intent = getIntent();
        String categoryid= intent.getStringExtra("CategoryId");
        Log.e(TAG, "CategoryId Received " + categoryid);
        new AsyncTaskforMaagazines().execute(categoryid);
        mlistview=(ListView)findViewById(R.id.list_magazines);
        madapter= new MagazineAdapter(getApplicationContext(),R.layout.row_magazine,MagazineList);
        mlistview.setAdapter(madapter);

        magObChngStatusHandling= new Magazine();


        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "ListIemClicked " + position + "Magazine Id" + MagazineList.get(position).getMagazineId());
     //           magazineId = Integer.parseInt(MagazineList.get(position).getMagazineId());

               long viewId = view.getId();
                Log.e(TAG,"view ID"+viewId);
                if (viewId == R.id.mlsubsidbtn) {
                   // Toast.makeText(getApplicationContext(), "someName item clicked", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "mlsubsbtn clicked");
                    magObChngStatusHandling.setMagazineId(MagazineList.get(position).getMagazineId());
                    magObChngStatusHandling.setSubscriptionStatus(MagazineList.get(position).getSubscriptionStatus());
                    new AsyncTaskforMagazineSubscription().execute(MagazineList.get(position).getMagazineId());
                    Button btn=(Button)findViewById(view.getId());
                    String changedstatus=SubscriptionHandling(magObChngStatusHandling);
                    btn.setText(changedstatus);
                    Log.d("Btnstatuschnged","prvs status "+magObChngStatusHandling.getSubscriptionStatus()+" now status"+changedstatus);
                }
                else {
                    Toast.makeText(getApplicationContext(), "ListView clicked: " + id, Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"no respnse: mlsubsbtn ot clicked");

                }

            }
        });

    } /*  ListView magIssuelistview=(ListView)findViewById(R.id.list_issued_magazines);
        issuedMagAdapter= new IssueSubscribedMagazineAdapter(getApplicationContext(),R.layout.row_issued_magazine,issueSubMagazineList);
        magIssuelistview.setAdapter(issuedMagAdapter);*/


    public  class AsyncTaskforMaagazines extends AsyncTask<String,Void,String>{
        String yourJsonStringUrl = "https://www.magzhub.com/services/ClassMagazine.php";
        String statusAfterFetching;
        // contacts JSONArray
        JSONArray jsonmagz;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdialog3= new ProgressDialog(Getting_Magazines.this);
            pdialog3.setMessage("Magazines");
            pdialog3.setTitle("Loading...");
            pdialog3.setCancelable(true);
            pdialog3.setIndeterminate(false);
            pdialog3.show();
        }
    @Override
        protected String doInBackground(String... arg0){
        String msubscriptionstatus, mid;
        try{
            JSONParserforHttps jParserml= new JSONParserforHttps();
            int s= Integer.parseInt(arg0[0]);
            Log.e(TAG,"ValuePassed"+s);
            List<NameValuePair> paramsml = new ArrayList<NameValuePair>();
            paramsml.add(new BasicNameValuePair("catid",Integer.toString(s)));
            jsonmagz= jParserml.makingconnectionForJSONArray(yourJsonStringUrl,paramsml,"POST");
            if(jsonmagz==null){

            }
            if(jsonmagz!=null){
            Log.e(TAG,"SIZE : "+ jsonmagz.length());
            for(int i=0; i<jsonmagz.length(); i++){
                try {
                    JSONObject c = jsonmagz.getJSONObject(i);
                    if (c.has("MagazineId")) {
                        mid = Integer.toString(c.getInt("MagazineId"));
                        String mname = c.getString("MagazineName");
                        String mthumbnail = c.getString("magazineThumbnail");
                        msubscriptionstatus = c.getString("subscriptionstatus");
                        Log.e(TAG, "id:" + mid
                                + " name:" + mname + "subscriptionstatus: " + msubscriptionstatus);
                        Magazine fetchmagazine = new Magazine();
                        fetchmagazine.setMagazineId(mid);
                        fetchmagazine.setMagazineName(mname);
                        fetchmagazine.setSubscriptionStatus(msubscriptionstatus);
                        fetchmagazine.setMagazineThumbnail(converttoBitmap(mthumbnail));
                        MagazineList.add(fetchmagazine);
                       }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
                statusAfterFetching="Success";
            return  "Success";
        }
        else {
                //noMagazinetv.setText("Oops, No magazine in this Category yet , Please go to another Category");
              //  noMagazinetv.setVisibility(View.VISIBLE);
                return "NoMagazine";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "FetchingFailed";
    }
    @Override
        protected void onPostExecute(String result){
            pdialog3.cancel();
            Log.e("OnPostxecute","result : "+result);

            madapter.notifyDataSetChanged();
        if(result.equals("FetchingFailed"))
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        else if(result.equals("NoMagazine"))
            noMagazinetv.setText("No magazines in this Category. Please go to another category");
            noMagazinetv.setVisibility(View.VISIBLE);
        }

    }
    public byte[] converttoBitmap(String thumbnail){
        byte[] imageAsByes= Base64.decode(thumbnail.getBytes(),Base64.DEFAULT);
        return imageAsByes;
     }

    public class AsyncTaskforMagazineSubscription extends AsyncTask<String,Void,Boolean>{
        JSONObject jsonObjectforMagazineSubscription;

        private String TAG="ATMagSubs";
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdialog4= new ProgressDialog(Getting_Magazines.this);
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
                jsonObjectforMagazineSubscription= jparser_magazineSubscriotion.makingConnectionForJsonObject(MagsubURL,listMagId,"POST");
                if(jsonObjectforMagazineSubscription.has("resultSubscribe")){
                    resultsubscribe= jsonObjectforMagazineSubscription.getInt("resultSubscribe");
                    Log.e(TAG, "has Result Subscribe value= "+ resultsubscribe);
                    magObChngStatusHandling.setResult_status(resultsubscribe);
                    Log.e(TAG," magObChngStatusHandling.setResult_status(resultsubscribe);"+ magObChngStatusHandling.getResult_status());
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
            if(result==false)
                Toast.makeText(getApplicationContext(),"Error in Connection",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_getting__magazines, menu);
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
            startActivity(new Intent(this,Setting.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public String SubscriptionHandling(Magazine magazine) {
        String ButtonSubscriptionStatus = magazine.getSubscriptionStatus();
        int resultSubscribeStatus = magazine.getResult_status();
        Log.e(TAG, "SubscriptionStatus = " + ButtonSubscriptionStatus + " Result recieved = " + resultSubscribeStatus);
        if (ButtonSubscriptionStatus.equals("Subscribe") && resultSubscribeStatus == 1) {
            Toast.makeText(this, "Sucessfully Subscribed", Toast.LENGTH_LONG).show();
            //Log.e(TAG, "Sucessfully Subscribed");

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
        } else if (ButtonSubscriptionStatus.equals("Unubscribe") && resultSubscribeStatus == 3) {
                Toast.makeText(this, "Can;t unsubscribe.. You can only subscribe magazine after 30 days of subscription..!!", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Can;t unsubscribe.. You can only subscribe magazine after 30 days of subscription..!!");
            ButtonSubscriptionStatus = "UnSubscribe";
        }
        magazine.setSubscriptionStatus(ButtonSubscriptionStatus);
        return ButtonSubscriptionStatus;
    }
}
