package magzhub.com.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class ReadingMagazine extends AppCompatActivity{
    private WebView webview;
    private String bookId;
    ImageButton leftSlide,rightSlide;
    //private ProgressDialog progressDialog;
     boolean IsDownloaded=false;
    String TAG="ReadMag.java";
    //public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    public static int fileLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
      //  webview = (WebView) findViewById(R.id.webview);
       // leftSlide=(ImageButton)findViewById(R.id.leftSlideBtn);

       // rightSlide=(ImageButton)findViewById(R.id.rightSlideBtn);
        Intent intent = getIntent();
        // String receivedUrl= intent.getStringExtra("URL");
        String receivedUrl = intent.getStringExtra("DownloadingURL");
        bookId = receivedUrl.substring(receivedUrl.indexOf("id=") + 2, receivedUrl.lastIndexOf("&export"));
        Log.e(TAG, "url received" + receivedUrl);
        Log.e(TAG, "BookId" + bookId);
        mProgressDialog = new ProgressDialog(ReadingMagazine.this);
        Log.e(TAG, "IsDownloaded" + IsDownloaded);
        DownloadandRead(receivedUrl);

    }
 /*   public void LeftSlideBtnClick(View view){
        webview.loadUrl("javascript:goPrevious()");

    }
    public void RightSlideBtnClick(View view){
        webview.loadUrl("javascript:goNext()");
    }*/
    public void DownloadandRead(String urlDownloadingBook){

        String pathforReading= Environment.getExternalStorageDirectory().toString()+"/Android/data/com.magzhub.app/";
         Log.d("Files", "Path: " + pathforReading);

        File f = new File(pathforReading);
        if(f.exists()){
        File listFiles[] = f.listFiles();
        Log.d("Files", "Size: "+ listFiles.length);
        for (int i=0; i < listFiles.length; i++)
        {
            Log.e("Files", i+"FileName:" + listFiles[i].getName());
            if(listFiles[i].getName().equals(bookId+".pdf"))
            {
                IsDownloaded=true;
                break;
            }
        }}
        if(!IsDownloaded) {
            Log.e(TAG,"IsDownloaded"+IsDownloaded+"Downloading book");
            new ATDownloadingmagazine().execute(urlDownloadingBook);
        }
        else {
            Log.e(TAG,"IsDownloaded"+IsDownloaded+"Reading Book,");
            startReading();
        }
    }
    public void MagazineDownloadingwdThread(){

    }

        public class ATDownloadingmagazine extends AsyncTask<String,String,String>
    {
        JSONParserforHttps jsonMagazineDownload=new JSONParserforHttps();
        private String urlForDownload;
        InputStream receivedStream;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... arg){
            urlForDownload=arg[0];
            int count;
            receivedStream=jsonMagazineDownload.downloadingFile(urlForDownload);
            File MagzFile=new File(Environment.getExternalStorageDirectory().toString()+"/Android/data/com.magzhub.app/");
            if(!MagzFile.exists())
                MagzFile.mkdir();
           // int lengthOfIle=JSONParserforHttps.fileLength;
            Log.e(TAG, "File Length= " + fileLength);
            try {
                File BookFile = new File(Environment.getExternalStorageDirectory().toString()+"/Android/data/com.magzhub.app/"+bookId+".pdf");
                BookFile.createNewFile();
                FileOutputStream output= new FileOutputStream(BookFile);

                byte data[]= new byte[1024];
                long total=0;
                while( (count= receivedStream.read(data))!=-1){
                    total +=count;
                    publishProgress(""+(int)((total*100)/fileLength));
                    output.write(data,0,count);
                }
                output.flush();
                output.close();
                receivedStream.close();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(TAG,"Error in creating Folder");
            }
            return null;
        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
         //   mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }
        @Override
        protected void onPostExecute(String unused){
            //dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            mProgressDialog.dismiss();
            startReading();
        }
    }
    public void startReading(){
        //Reading with pdf.js
      /*  WebSettings settings=webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        // settings.setPluginState(WebSettings.PluginState.ON);
        // settings.setAllowFileAccess(true);
        //     settings.setAllowContentAccess(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        webview.setWebChromeClient(new WebChromeClient());
        try{
            String path1=Environment.getExternalStorageDirectory().toString()+"/Android/data/com.magzhub.app/"+bookId+".pdf";
            //String path
            Log.e("Reading path ", path1);
            webview.loadUrl("file:///android_asset/pdfviewer/index.html?file=file:///"+path1);
        }
        catch (Exception e){
            Log.e(TAG,"Error while reading ");
            e.printStackTrace();
        }*/

        PackageManager packageManager = getPackageManager();

        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");

        List list = packageManager.queryIntentActivities(testIntent,
                PackageManager.MATCH_DEFAULT_ONLY);

        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);

            File fileToRead = new File(Environment.getExternalStorageDirectory().toString()+"/Android/data/com.magzhub.app/"+bookId+".pdf");

            //"/data/data/com.example.filedownloader/app_books/Book.pdf");
            Uri uri = Uri.fromFile(fileToRead.getAbsoluteFile());

            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        } catch (Exception ex) {
            Log.i(getClass().toString(), ex.toString());
            Toast.makeText(ReadingMagazine.this,
                    "Cannot open your selected file, try again later",
                    Toast.LENGTH_SHORT).show();
    }}
   /* @Override
    public void onBackPressed(){
        //added on 27feb
        Log.e(TAG,"Onbackpressed is called");
        if (webview.canGoBack()) {
            webview.goBack();
            return;
        }
        super.onBackPressed();
    }*/

}
