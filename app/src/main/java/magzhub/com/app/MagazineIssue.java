package magzhub.com.app;

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
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class MagazineIssue extends ActionBarActivity {
    ProgressDialog progressDialogIssue;
    String urlMagazineIssue="https://magzhub.com/services/IssuesOfMagazine.php";
    private String urlForDownloadFinal=null,urlgoogledrive,urlForDownload="https://docs.google.com/uc?id=ENTER_URL_&export=download";
    String urlIssueMagRead;
    JSONArray jsonArrayMagazineIssue;
    JSONObject jsonObjectMagazineIssue;
    String receivedMagId;
    Intent intent;
    Magazine issuedMagazine;
    IssueSubscribedMagazineAdapter  issuedMagAdapter;
    ArrayList<Magazine> issueSubMagazineList;
    String TAG="Magazine Issue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_issue);
        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_logo_n_icon);
        toolbar.setTitle("Magzhub");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        issueSubMagazineList= new ArrayList<Magazine>();
        //issuedMagazine= new Magazine();
        intent= getIntent();
        receivedMagId=intent.getStringExtra("MagIdforIssue");
        Log.e(TAG,"received Mag Id"+receivedMagId);
        new IssueMagAsyncTask().execute(receivedMagId);
        ListView magIssuelistview=(ListView)findViewById(R.id.list_issued_magazines);
        issuedMagAdapter= new IssueSubscribedMagazineAdapter(getApplicationContext(),R.layout.row_issued_magazine,issueSubMagazineList);
        magIssuelistview.setAdapter(issuedMagAdapter);
        magIssuelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG,"Item clicked with position "+position+"Issue Id"+ issueSubMagazineList.get(position).getIssueId() );
                long viewIdissue=view.getId();
                if(viewIdissue==R.id.issuesubMagRead){
                    new AsyncTaskReadingMagIssues().execute(issueSubMagazineList.get(position).getIssueId());
                }
            }
        });
    }
public class IssueMagAsyncTask extends AsyncTask<String,Void,Boolean>{
    JSONParserforHttps jsonIssueMagazine;
    int issueIdOfMag=0;
    int getIssueCalls=0;
    int TotalCount=-1;
//    ProgressDialog progressDialogIssue;
    private String TAG="IssueMagAsyncTask";
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialogIssue= new ProgressDialog(MagazineIssue.this);
        progressDialogIssue.setMessage("Loading..");
        progressDialogIssue.setIndeterminate(true);
        progressDialogIssue.setCancelable(true);
        progressDialogIssue.show();
    }
    public void getIssues(String magid){
        getIssueCalls++;
        jsonIssueMagazine= new JSONParserforHttps();
        int count=0;
        //int magid=params[0];
        Log.e(TAG,"receivedMagId prameter"+magid);
        List<NameValuePair> listIssue= new ArrayList<NameValuePair>();
        listIssue.add(new BasicNameValuePair("MagazineId", magid));
        listIssue.add(new BasicNameValuePair("issueIdOfMag",Integer.toString(issueIdOfMag)));
        try{
            jsonArrayMagazineIssue=jsonIssueMagazine.makingconnectionForJSONArray(urlMagazineIssue,listIssue,"POST");
            count=jsonArrayMagazineIssue.length();
            Log.e(TAG," Length of jsonArrayMagazineIssue"+count);
            if(jsonArrayMagazineIssue!=null){
                if(TotalCount==-1)
                    TotalCount=0;
                TotalCount += count;
            for(int i=0; i<jsonArrayMagazineIssue.length();i++){
                jsonObjectMagazineIssue=jsonArrayMagazineIssue.getJSONObject(i);
                Log.e(TAG,"jsonObjectMagazineIssue : "+jsonObjectMagazineIssue);
                    /*"issueid":13,"issueName":"June (2015)","issueThumbnail":*/
                //if(jsonObjectMagazineIssue.has("issueid")){
                String issueMagName=jsonObjectMagazineIssue.getString("issueName");
                String issueMagThumbnail=jsonObjectMagazineIssue.getString("issueThumbnail");
                String issueMagId=Integer.toString(jsonObjectMagazineIssue.getInt("issueid"));
                Log.e("DATA","issueMagName"+issueMagName+"issueMagId"+issueMagId);
                issuedMagazine= new Magazine();//creating different objects for each issues;
                issuedMagazine.setIssueId(issueMagId);
                issuedMagazine.setMagazineIssueThumbnail(converttoBitmap(issueMagThumbnail));
                issuedMagazine.setMagazineIssueName(issueMagName);
                issueSubMagazineList.add(issuedMagazine);
                issueIdOfMag=Integer.parseInt(issueMagId);
            }
            if(count==1)
                getIssues(Integer.toString(issueIdOfMag));
            }
            else
                TotalCount+=0;

        }catch (Exception e){
            e.printStackTrace();
        }

        }
    @Override
    protected Boolean doInBackground(String... params){
        //jsonIssueMagazine= new JSONParserforHttps();
        String recievedMagIdforIssue=(params[0]);
        getIssues(recievedMagIdforIssue);
        if(TotalCount>=0)
            return true;
        /*Log.e(TAG,"receivedMagId prameter"+recievedMagIdforIssue);
        List<NameValuePair> listIssue= new ArrayList<NameValuePair>();
        listIssue.add(new BasicNameValuePair("MagazineId", recievedMagIdforIssue));
        try{
            jsonArrayMagazineIssue=jsonIssueMagazine.makingconnectionForJSONArray(urlMagazineIssue,listIssue,"POST");
            Log.e(TAG," Length of jsonArrayMagazineIssue"+jsonArrayMagazineIssue.length());
            for(int i=0; i<jsonArrayMagazineIssue.length();i++){
                jsonObjectMagazineIssue=jsonArrayMagazineIssue.getJSONObject(i);
                Log.e(TAG,"jsonObjectMagazineIssue : "+jsonObjectMagazineIssue);
                    /*"issueid":13,"issueName":"June (2015)","issueThumbnail":
                //if(jsonObjectMagazineIssue.has("issueid")){
                    String issueMagName=jsonObjectMagazineIssue.getString("issueName");
                    String issueMagThumbnail=jsonObjectMagazineIssue.getString("issueThumbnail");
                    String issueMagId=Integer.toString(jsonObjectMagazineIssue.getInt("issueid"));
                    Log.e("DATA","issueMagName"+issueMagName+"issueMagId"+issueMagId);
                    issuedMagazine= new Magazine();//creating different objects for each issues;
                    issuedMagazine.setIssueId(issueMagId);
                    issuedMagazine.setMagazineIssueThumbnail(converttoBitmap(issueMagThumbnail));
                    issuedMagazine.setMagazineIssueName(issueMagName);
                    issueSubMagazineList.add(issuedMagazine);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }*/
        else
            return false;
    }
    @Override
    protected void onPostExecute(Boolean result){
        issuedMagAdapter.notifyDataSetChanged();
        progressDialogIssue.dismiss();
        if(result==false)
            Toast.makeText(MagazineIssue.this,"No internet connection",Toast.LENGTH_SHORT).show();
    }
}
    public byte[] converttoBitmap(String thumbnail){
        byte[] imageAsByes= Base64.decode(thumbnail.getBytes(), Base64.DEFAULT);
        return imageAsByes;
    }

    public class AsyncTaskReadingMagIssues extends AsyncTask<String,Void,Boolean>{

        JSONParserforHttps jsonforIssueReading= new JSONParserforHttps();
        //String urlReading="https://magzhub.com/services/ClassMagazine.php";
        JSONArray jsonArrayReading;
        JSONObject jsonObjectReading;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(String... params){
            String issueId;
            issueId=params[0];
            Log.e(TAG,"received issue id"+issueId);
            List<NameValuePair> issuereadparams= new ArrayList<NameValuePair>();
            issuereadparams.add(new BasicNameValuePair("issueId",issueId));
            try{
                jsonObjectReading= jsonforIssueReading.makingConnectionForJsonObject(urlMagazineIssue,issuereadparams,"POST");
                //    Log.e(TAG,"Length of JSONArray"+jsonArrayReading.length()); urlReading.
                //  for(int i=0; i<jsonArrayReading.length();i++) {
                //  jsonObjectReading = jsonArrayReading.getJSONObject(i);
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
            Intent intent= new Intent(MagazineIssue.this,ReadingMagazine.class);
            Log.e(TAG,"urlgoogledrive"+urlgoogledrive);
            //  intent.putExtra("URL",urlgoogledrive);
            intent.putExtra("DownloadingURL",urlForDownloadFinal);
            startActivity(intent);
            if(result==false){
                Log.e(TAG,"No Internet Connection");
            }
        }
              /* // urlIssueMagRead=jsonObjectReading.getString("url");
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
            Intent intent= new Intent(MagazineIssue.this,ReadingMagazine.class);
            Log.e(TAG,"urlIssueMagRead"+urlIssueMagRead);
            intent.putExtra("URL",urlIssueMagRead);
            startActivity(intent);
            if(result==false){
                Log.e(TAG,"Unable to fetch data");
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_magazine_issue, menu);
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
