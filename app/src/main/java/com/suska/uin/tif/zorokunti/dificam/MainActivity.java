package com.suska.uin.tif.zorokunti.dificam;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DataHelper dbcenter;
    MainActivity ma;
    Button  menuscanner, menupengguna, menutraining, menutesting, menulog, menubpnn, menustatistika, menulbp, menureset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(MainActivity.this,PenggunaActivity.class);
                startActivity(c);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menureset = (Button)findViewById(R.id.btn_menu_reset);
        menuscanner = (Button)findViewById(R.id.btn_menu_scanner);
        menupengguna = (Button)findViewById(R.id.btn_menu_pengguna);
        menutraining = (Button)findViewById(R.id.btn_menu_training);
        menutesting = (Button)findViewById(R.id.btn_menu_testing);
        menulog = (Button)findViewById(R.id.btn_menu_log);
        menubpnn = (Button)findViewById(R.id.btn_menu_bpnn);
        menustatistika = (Button)findViewById(R.id.btn_menu_statistika);
        menulbp = (Button)findViewById(R.id.btn_menu_lbp);


        menuscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sc = new Intent(MainActivity.this , GetPictActivity.class);
                startActivity(sc);

            }
        });
        menupengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pg = new Intent(MainActivity.this , PenggunaActivity.class);
                startActivity(pg);

            }
        });

        menutraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pl = new Intent(MainActivity.this , PelatihanActivity.class);
                startActivity(pl);
            }
        });
        menutesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        menulog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        menubpnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        menustatistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        menulbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        menureset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            System.exit(1);
            }
        });
    }


    public void resetPengguna(){
        ma = this;

        dbcenter = new DataHelper(this);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("delete  from tbl_pengguna");
        Toast.makeText(getApplication(), "Data Pengguna Telah Dihapus",Toast.LENGTH_SHORT);
    }

    public void resetPelatihan(){
        ma = this;

        dbcenter = new DataHelper(this);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("delete  from tbl_pelatihan");
        Toast.makeText(getApplication(), "Data Pelatihan Telah Dihapus",Toast.LENGTH_SHORT);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_dev) {
            Intent about = new Intent(MainActivity.this , AboutActivity.class);
            startActivity(about);
        }

        return super.onOptionsItemSelected(item);
    }

    public void exportDatabase(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pengguna) {
            Intent pengguna= new Intent(MainActivity.this , PenggunaActivity.class);
            startActivity(pengguna);
        } else if (id == R.id.nav_update_server) {
            Intent updateserver= new Intent(MainActivity.this , UpdateDataServer.class);
            startActivity(updateserver);

        } else if (id == R.id.nav_r_pelatihan) {
            resetPelatihan();

        } else if (id == R.id.nav_r_pengguna) {
            resetPengguna();
        } else if (id == R.id.nav_exsport_db) {
            exportDatabase("dificam.db");

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
