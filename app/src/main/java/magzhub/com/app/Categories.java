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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Categories extends ActionBarActivity {
    ArrayList<Category> CategoriesList;
    CategoryAdapter adapter;
    ProgressDialog pdialog2;
    private String TAG="Categories";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setLogo(R.mipmap.ic_launcher_magzhub_transparent_logo);
        getSupportActionBar().setTitle("Categories");

        CategoriesList= new ArrayList<Category>();
        new AsyncTaskfroCategories().execute();

        ListView listview=(ListView)findViewById(R.id.list);
        adapter=new CategoryAdapter(getApplicationContext(),R.layout.category_row,CategoriesList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(Categories.this,Getting_Magazines.class);
                intent.putExtra("CategoryId",CategoriesList.get(position).getId());
                startActivity(intent);
                Log.e(TAG, "Categry Id " + CategoriesList.get(position).getId());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);
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
            startActivity(new Intent(Categories.this,Setting.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class AsyncTaskfroCategories extends AsyncTask<String, Void, Boolean> {
        String yourJsonStringUrl = "https://magzhub.com/services/ClassCategory.php";
        JSONArray dataJsonArr = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog2 = new ProgressDialog(Categories.this);
            pdialog2.setMessage("Categories");
            pdialog2.setTitle("Loading...");
            pdialog2.setCancelable(true);
            pdialog2.setIndeterminate(false);
            pdialog2.show();
        }
        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                // instantiate our json parser
                JSONParserforHttps jParser = new JSONParserforHttps();
                dataJsonArr = jParser.getJSONArrayHTTPS(yourJsonStringUrl);
                //String s = jParser.getStringFromUrl(yourJsonStringUrl);
                Log.e(TAG, "SIZE : " + dataJsonArr.length());
                // loop through all users
                for (int i = 0; i < dataJsonArr.length(); i++) {
                    JSONObject c = dataJsonArr.getJSONObject(i);
                    if (c.has("id")) {
                        // Storing each json item in variable
                        String id = Integer.toString(c.getInt("id"));
                        String name = c.getString("name");
                        String thumbnail= c.getString("thumbnail");
                        // show the values in our logcat
                        Log.e(TAG, "id:" + id
                                + " name:" + name + " thumbnail: " + thumbnail);
                        Category fetchcategory= new Category();
                        fetchcategory.setId(id);
                        fetchcategory.setName(c.getString("name"));
                        fetchcategory.setThumbnail(converttoBitmap(thumbnail));
                        CategoriesList.add(fetchcategory);
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            pdialog2.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    public byte[] converttoBitmap(String thumbnail) {
        byte[] imageAsBytes = Base64.decode(thumbnail.getBytes(), Base64.DEFAULT);
        return imageAsBytes;
    }
}
