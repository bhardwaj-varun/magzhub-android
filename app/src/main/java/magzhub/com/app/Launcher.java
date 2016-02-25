package magzhub.com.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Launcher extends ActionBarActivity {

    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        OnClickButtonListener();
    }
    public void OnClickButtonListener(){
        btn1=(Button)findViewById(R.id.btn_login);
        try{
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launcher.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }catch(Exception e){
            e.printStackTrace();
        }}



}
