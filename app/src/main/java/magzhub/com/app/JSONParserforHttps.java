package magzhub.com.app;
import android.text.TextUtils;
import android.util.Log;


import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
/**
 * Created by Dell on 17-Jan-16.
 */
public class JSONParserforHttps {
    static HttpsURLConnection connection;
    private String TAG="JSONParserforHttps";
    static final String COOKIES_HEADER = "Set-Cookie";
   // static  int fileLength;
    static boolean firstConnection=true;
    static Map<String, List<String>> headerFields;
    static List<String> cookiesHeader;


    public String convertNameValuePairToString(List<NameValuePair> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first) {
                first = false;
            } else result.append("&");
            try {
                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));

            }catch(Exception e){
                Log.e(TAG, "error in url converting name value pair to urlencoding");
                e.printStackTrace();
            }
        }
        return result.toString();
    }
     public JSONArray makingconnectionForJSONArray(String stringUrl, List<NameValuePair> params, String method) {
         //HttpsURLConnection connection;
        StringBuilder sb3= new StringBuilder();
         JSONArray jsonArray=null;
        try{

            URL url= new URL(stringUrl);
            connection= (HttpsURLConnection)url.openConnection();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "error in HttpURlCOnnectionfor JSONArray");
        }
        String parameterString= convertNameValuePairToString(params);
        try{
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(firstConnection==false) {
                Log.e(TAG, "cookie length" + LoginActivity.msCookieManager.getCookieStore().getCookies().size());
                if (LoginActivity.msCookieManager.getCookieStore().getCookies().size() > 0) {
                    //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                    connection.setRequestProperty("Cookie",
                            TextUtils.join(";", LoginActivity.msCookieManager.getCookieStore().getCookies()));
                }
            }
            connection.connect();
            //send string to server
            DataOutputStream wr = new DataOutputStream( connection.getOutputStream());
            wr.writeBytes(parameterString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //getting response from server
        try{
            int status = connection.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb3.append(line + "\n");
                    }
                    br.close();
                    sb3.toString();
                    Log.e("HTTPS connection : ","Json String "+ sb3.toString());
            }
        }catch(Exception e){
            Log.e(TAG, "inputstream error ");
            e.printStackTrace();
        }

        try {
            jsonArray=new JSONArray(sb3.toString());
                return jsonArray;

        }catch (Exception e) {
            e.printStackTrace();
            return jsonArray;
        }
     }

    public JSONObject makingConnectionForJsonObject(String stringUrl, List<NameValuePair> params,String method  ){
        JSONObject jsonObject;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(stringUrl);
            connection = (HttpsURLConnection) url.openConnection();
        }catch (Exception e) {
            Log.e(TAG, "error in URL HTTPurlconnection");
            e.printStackTrace();
        }
        String paramString=convertNameValuePairToString(params);
        try {

            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(firstConnection==false) {
                Log.e(TAG, "cookie length" + LoginActivity.msCookieManager.getCookieStore().getCookies().size());
                if (LoginActivity.msCookieManager.getCookieStore().getCookies().size() > 0) {
                    //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                    connection.setRequestProperty("Cookie",
                            TextUtils.join(";", LoginActivity.msCookieManager.getCookieStore().getCookies()));
                }
            }
           connection.connect();

            try{
                DataOutputStream wr = new DataOutputStream( connection.getOutputStream());
                wr.writeBytes(paramString);
            }catch (Exception e) {
                Log.e(TAG, "error in DataOutputStream ");
                e.printStackTrace();
            } if(firstConnection) {
                // msCookieManager.getInstance().setAcceptCookie(true);
                // cookieHandler.setDefault(msCookieManager);
                headerFields = connection.getHeaderFields();
                Log.e(TAG, connection.getHeaderFields().toString());
                try {
                    int responseCode = connection.getResponseCode();
                    Log.d(TAG, "responseCode ->" + responseCode);

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        Map<String, List<String>> headerFields = connection.getHeaderFields();
                        List<String> cookiesHeader = headerFields.get("Set-Cookie");
                        Log.e(TAG, "cookieHeader" + cookiesHeader);
                        if (cookiesHeader != null) {
                            try {
                                for (String cookie : cookiesHeader) {
                                    LoginActivity.msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, "error in  msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "error=======");
                }
                firstConnection = false;
            }
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    sb.toString();
                    Log.e("HTTPS connection : ", "Json String " + sb.toString());

        }catch (Exception e) {
            Log.e(TAG, "error in connection.connect");
            e.printStackTrace();
        }
        /*try{
            DataOutputStream wr = new DataOutputStream( connection.getOutputStream());
            wr.writeBytes(paramString);
        }catch (Exception e) {
            Log.e(TAG, "error in DataOutputStream ");
            e.printStackTrace();
        }*/

        //getting response from server
       /* try{
            int status = connection.getResponseCode();
            Log.e(TAG,"status"+status);
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    sb.toString();
                    Log.e("HTTPS connection : ","Json String "+ sb.toString());
            }
        }catch(Exception e){
            Log.e(TAG,"inputstream error ");
            e.printStackTrace();
        }
*/
        try {
             jsonObject=new JSONObject(sb.toString());
            return jsonObject;
      }catch (Exception e) {
            return null;
        }
    }
    public InputStream downloadingFile(String url) {
        InputStream input;
        try {
            URL urlMagDownload = new URL(url);
            connection = (HttpsURLConnection) urlMagDownload.openConnection();

        if (LoginActivity.msCookieManager.getCookieStore().getCookies().size() > 0) {
            connection.setRequestProperty("Cookie",
                    TextUtils.join(";", LoginActivity.msCookieManager.getCookieStore().getCookies()));
        }
        try {
            connection.connect();
           //
            Map<String, List<String>> map = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                Log.e(TAG,"Key : " + entry.getKey() +
                        " ,Value : " + entry.getValue());
            }

            //get header by 'key'
            Log.e(TAG, "Length of file connection length" + connection.getContentLength());
            Log.e(TAG,"headers"+ connection.getHeaderField("Server").toString());
            ReadingMagazine.fileLength = connection.getContentLength();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "error in connection.connect");
        }
       // fileLength = connection.getContentLength();
        //Log.e(TAG, "Length of file to be downloded" + ReadingMagazine.fileLength);
        input = new BufferedInputStream(urlMagDownload.openStream());
            return input;
    }
        catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"Error in url connection");
        }
        return null;
    }

    public JSONArray getJSONArrayHTTPSWithoutParams(String urlstring){
        StringBuilder sb2= new StringBuilder();
        try{
            URL url= new URL(urlstring);
            connection=(HttpsURLConnection)url.openConnection();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "Error in url connection in GETJSONARRAY");
        }
        try{
            if(LoginActivity.msCookieManager.getCookieStore().getCookies().size() > 0)
            {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                connection.setRequestProperty("Cookie",
                        TextUtils.join(";", LoginActivity.msCookieManager.getCookieStore().getCookies()));
            }
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "error in connection.connect0");
        }
        try{
            int status=connection.getResponseCode();
            switch (status){
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb2.append(line + "\n");
                    }
                    br.close();
                    sb2.toString();
                    Log.e("HTTPS connection : ","Json String "+ sb2.toString());
            }
        }catch(Exception e){
            Log.e(TAG,"inputstream error ");
            e.printStackTrace();
        }

        try {
            JSONArray jarray = new JSONArray(sb2.toString());
            Log.e(TAG,"jarray"+jarray);
            return  jarray;
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"Error in Parsing JSON");
        }
        return null;
    }
    public JSONArray getJSONArrayHTTPS(String urlstring){
        StringBuilder sb2= new StringBuilder();
        JSONArray jarrayofMagazines=null;
        try{
            URL url= new URL(urlstring);
            connection=(HttpsURLConnection)url.openConnection();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"Error in url connection in GETJSONARRAY");
        }
        try{
            connection.setRequestMethod("POST");
            if(firstConnection==false){
            if(LoginActivity.msCookieManager.getCookieStore().getCookies().size() > 0)
            {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                connection.setRequestProperty("Cookie",
                        TextUtils.join(";", LoginActivity.msCookieManager.getCookieStore().getCookies()));
            }}
            connection.connect();

        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "error in connection.connect");
        }
        if(connection!=null){
        try{
            int status=connection.getResponseCode();
            Log.e(TAG,"Status "+status);
            switch (status){
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb2.append(line + "\n");
                    }
                    br.close();
                    sb2.toString();
                    Log.e("HTTPS connection : ","Json String "+ sb2.toString());
            }
        }catch(Exception e){
            Log.e(TAG, "inputstream error ");
            e.printStackTrace();
        }
        try {
            jarrayofMagazines = new JSONArray(sb2.toString());
            Log.e(TAG,"jarray"+jarrayofMagazines);
            return  jarrayofMagazines;
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"Error in Parsing JSON");
        }}
        return jarrayofMagazines;
    }

}





