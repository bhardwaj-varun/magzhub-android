package magzhub.com.app;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;

/**
 * Created by Dell on 18-Jan-16.
 */
public class SessionManagement {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE=0;

    //sharedPrefrence File name
    private static final String PREF_NAME="SharedPrefrence";
    private static final String IS_LOGIN="IsLoggedIn";
    public static final String KEY_USERID = "keyUserId";
    public static final String KEY_FULLNAME = "keyFullName";
    public static final String KEY_SERVER_SESSION_ID = "keySessionId";

    public SessionManagement(Context context){
        this.context= context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createLoginSession(int userId,String cookies){
        // Storing login value as TRUE
        //Setting cookies or session id in shared preference
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putInt(KEY_USERID, userId);
        editor.putString(KEY_SERVER_SESSION_ID,cookies);
//managing session if first connenction

        // commit changes
        editor.commit();
    }

    public void setProfile(String fullName){
        // Storing login value as TRUE
        editor.putString(KEY_FULLNAME, fullName);

        // Storing name in pref

        // commit changes
        editor.commit();
    }


    public boolean isLoggedIn(){

        return preferences.getBoolean(IS_LOGIN, false);
    }
    public void checkLogin(Context _context){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Launcher.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Add new Flag to start new Activity

            // Staring Login Activity
            _context.startActivity(i);
            }
        else{
            Intent i= new Intent(_context,MySubscription.class);
            _context.startActivity(i);
        }

    }


    public int getUserId()
    {
        return preferences.getInt(KEY_USERID,0);
    }
    public String getServerSessoinId(){
        return preferences.getString(KEY_SERVER_SESSION_ID, "");}
    public Boolean getIsLoginStatus(){
        return  preferences.getBoolean(IS_LOGIN,false);
    }
    public String getProfileDetail()
    {
        String profileDetail;

        profileDetail=preferences.getString(KEY_FULLNAME,"");
        //profileDetail[1]=preferences.getString(KEY_EmailId,"");
        return  profileDetail;
    }


    /*public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }*/
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

}

