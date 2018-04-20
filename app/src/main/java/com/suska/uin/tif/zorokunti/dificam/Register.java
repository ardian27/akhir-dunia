package com.suska.uin.tif.zorokunti.dificam;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends Activity {
    EditText ET_NAME,ET_USER_NAME,ET_USER_PASS;
    String name,user_name,user_pass;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ET_NAME = (EditText)findViewById(R.id.name);
        ET_USER_NAME= (EditText)findViewById(R.id.new_user_name);
        ET_USER_PASS = (EditText)findViewById(R.id.new_user_pass);
        btn = (Button)findViewById(R.id.btn_reg);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegd();
            }
        });




    }

    private void userRegd() {
        name = ET_NAME.getText().toString();
        user_name = ET_USER_NAME.getText().toString();
        user_pass =ET_USER_PASS.getText().toString();
        String method = "register";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,name,user_name,user_pass);
        finish();
    }

    public void userReg(View view)
 {

     name = ET_NAME.getText().toString();
     user_name = ET_USER_NAME.getText().toString();
     user_pass =ET_USER_PASS.getText().toString();
     String method = "register";
     BackgroundTask backgroundTask = new BackgroundTask(this);
     backgroundTask.execute(method,name,user_name,user_pass);
     finish();

 }

}
