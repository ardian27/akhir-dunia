package com.suska.uin.tif.zorokunti.dificam;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SimpanLBPActivity extends AppCompatActivity {
    String[] daftar,no;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    SimpanLBPActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpan_lbp);

        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();

    }
    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_pengguna",null);
        daftar = new String[cursor.getCount()];
        no = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
            no[cc] = cursor.getString(0).toString();
        }
        ListView01 = (ListView)findViewById(R.id.lv_data_pengguna222);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int args2, long arg3) {
                final String selection = daftar[args2]; //.getItemAtPosition(arg2).toString();
                final String selectionNo = no[args2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Pilih Pengguna", "Kembali"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SimpanLBPActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent x = getIntent();
                                String m = x.getStringExtra("m");
                                String v = x.getStringExtra("v");
                                String s = x.getStringExtra("s");
                                String k = x.getStringExtra("k");
                                String  e= x.getStringExtra("e");
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("insert into tbl_pelatihan (id,idpengguna,mean,variance,skewness,kurtosis,entrophy)values" +
                                        "(NULL," +
                                        ""+selectionNo+
                                        ","+m+
                                        ","+v+
                                        ","+s+
                                        ","+k+
                                        ","+e+")");
                                Toast.makeText(getApplicationContext(), "Berhasil Menyimpan Data Pelatihan", Toast.LENGTH_LONG).show();
                                Intent d = new Intent(SimpanLBPActivity.this , PelatihanActivity.class);
                                startActivity(d);
                                break;
                            case 1 :
                            //    System.exit(0);
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
    }


}
