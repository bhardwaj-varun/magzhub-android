package mrinalika.dell.com.hfad;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Getting_Magazines extends ActionBarActivity {
    ProgressDialog pdialog3;
    private String TAG="getting_magazines.java";
    ArrayList<Magazine> MagazineList;
    MagazineAdapter madapter;
    static  int magazineId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting__magazines);
        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_logo_n_icon);
        toolbar.setTitle("Magzhub");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MagazineList = new ArrayList<Magazine>();
        Intent intent = getIntent();
        String categoryid= intent.getStringExtra("CategoryId");
        Log.e(TAG, "CategoryId Received " + categoryid);
        new AsyncTaskforMaagazines().execute(categoryid);
        ListView mlistview=(ListView)findViewById(R.id.list_magazines);
        madapter= new MagazineAdapter(getApplicationContext(),R.layout.row_magazine,MagazineList);
        mlistview.setAdapter(madapter);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Getting_Magazines.this, "ListIemClicked " + position + "Magazine Id" + MagazineList.get(position).getMagazineId(), Toast.LENGTH_SHORT).show();
                magazineId = Integer.parseInt(MagazineList.get(position).getMagazineId());
            }
        });

    }

    public void handleButtonClick(View view){
        Button button=(Button)findViewById(view.getId());
        Log.e(TAG,"Mag id"+magazineId+"Button text"+button.getText().toString());

    }
    public  class AsyncTaskforMaagazines extends AsyncTask<String,Void,Boolean>{
        String yourJsonStringUrl = "https://www.magzhub.com/services/ClassMagazine.php";
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
        protected Boolean doInBackground(String... arg0){
        String msubscriptionstatus, mid;
        try{
            JSONParserforHttps jParserml= new JSONParserforHttps();
            int s= Integer.parseInt(arg0[0]);
            Log.e(TAG,"ValuePassed"+s);
            List<NameValuePair> paramsml = new ArrayList<NameValuePair>();
            paramsml.add(new BasicNameValuePair("catid",Integer.toString(s)));
            jsonmagz= jParserml.makingconnectionForJSONArray(yourJsonStringUrl,paramsml,"POST");
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
            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
        protected void onPostExecute(Boolean result){
            pdialog3.cancel();
            madapter.notifyDataSetChanged();
        if(result==null)
            Toast.makeText(getApplicationContext(),"Unable to fetch data from Server",Toast.LENGTH_SHORT).show();
        }
    }
    public byte[] converttoBitmap(String thumbnail){
        byte[] imageAsByes= Base64.decode(thumbnail.getBytes(),Base64.DEFAULT);
        return imageAsByes;
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
