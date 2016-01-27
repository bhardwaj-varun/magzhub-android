package mrinalika.dell.com.hfad;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AfterLogin extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    String TAG="AfterLogin";
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
     //   SessionManagement sessionManagement=new SessionManagement(getApplicationContext());
       //if(sessionManagement.isLoggedIn())
           ToolbarHandling();


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);
    }
    private void displayView(int position){
        Log.e(TAG,"position= "+position);
        switch(position){
            case 0:

                startActivity(new Intent(this,Setting.class));
                break;
            case 1:

                startActivity(new Intent(this,Categories.class));
                break;
            case 2:
                startActivity(new Intent(this,Home.class));
                break;
            default:
                break;

        }
    }
    public void ToolbarHandling(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.app_name));

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

}
