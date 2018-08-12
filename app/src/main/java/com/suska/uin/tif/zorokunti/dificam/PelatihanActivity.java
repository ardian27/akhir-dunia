package com.suska.uin.tif.zorokunti.dificam;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PelatihanActivity extends AppCompatActivity {

    String[] daftar,noo;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static PelatihanActivity ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button basic,simple,multi;
        TableLayout tabelPengguna;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelatihan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Pengguna pgn = new Pengguna();
        JSONArray arrayPengguna;

        basic = (Button)findViewById(R.id.btn_basicTraining);
        simple = (Button)findViewById(R.id.btn_simpleTraining);

        //init();


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

        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();


    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * from tbl_pelatihan",null);
        daftar = new String[cursor.getCount()];
        noo = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = "DATA-"+cursor.getString(1).toString()+"-00"+cursor.getString(0).toString();
            noo[cc] = cursor.getString(0).toString();
        }
        ListView01 = (ListView)findViewById(R.id.lv);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int args2, long arg3) {
                final String selection = daftar[args2]; //.getItemAtPosition(arg2).toString();
                final String selectionNo = noo[args2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PelatihanActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                 break;

                                case 1 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from tbl_pelatihan where id = '"+selectionNo+"'");

                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
    }

}
