package com.suska.uin.tif.zorokunti.dificam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PelatihanActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button basic,simple,multi;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelatihan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        basic = (Button)findViewById(R.id.btn_basicTraining);
        simple = (Button)findViewById(R.id.btn_simpleTraining);
        multi = (Button)findViewById(R.id.btn_multiTraining);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_mulaiPelatihan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(PelatihanActivity.this , GetPictActivity.class);
                startActivity(a);
            }
        });

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PelatihanActivity.this , GetPictActivity.class);
                startActivity(a);
            }
        });

        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(PelatihanActivity.this , SimpleGetPictActivity.class);
                startActivity(b);
            }
        });
    }

}
