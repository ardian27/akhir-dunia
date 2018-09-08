package com.suska.uin.tif.zorokunti.dificam;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PengujianActivity extends AppCompatActivity {
    DataHelper dataHelper;

    protected Cursor cursor,cursor_data;

    Button btn_uji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengujian);

    }


}
