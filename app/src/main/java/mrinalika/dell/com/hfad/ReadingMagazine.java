package mrinalika.dell.com.hfad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
public class ReadingMagazine extends AppCompatActivity {
    private WebView webview;
    private ProgressBar progressbar;
    String TAG="ReadMagwebClient";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        progressbar=(ProgressBar)findViewById(R.id.progressBar1);
        Intent intent=getIntent();
        String receivedUrl= intent.getStringExtra("URL");
        Log.e(TAG,"url received"+receivedUrl);
        webview=(WebView)findViewById(R.id.webview1);
        webview.setWebViewClient(new myWebClient());    //the lines of code added
        webview.getSettings().setJavaScriptEnabled(true);
       // webview.setWebChromeClient(new WebChromeClient()); //same as above
            webview.loadUrl(receivedUrl);
       // webview.loadUrl(url);
        //webview.getSettings().setJavaScriptEnabled(true);
        //webview.loadUrl("http://google.co.in");
        //services/ClassMagazine.php
        //magId
    }
    public class myWebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressbar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressbar.setVisibility(View.GONE);
        }

    }


}
