package com.suska.uin.tif.zorokunti.dificam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

public class UpdateDataServer extends AppCompatActivity {

    DataHelper dbcenter;

    protected Cursor cursor;
    ActionServer as = new ActionServer();
    String[] daftar,no,id_pengguna,nama,tgl_lahir,jk,alamat;
    JSONArray arrayData;
    UpdateDataServer upd;
    private ListView ListView01;
    Button btnUpdate;
    TextView teks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_server);

        upd = this;
        dbcenter = new DataHelper(this);
        UpdateDataLV();


        btnUpdate = (Button)findViewById(R.id.btn_update_server);
        teks = (TextView)findViewById(R.id.txt_updt);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FunctionServer fs = new FunctionServer();
                teks.setText(fs.tampilBiodata());

            }
        });
    }


    public void UpdateDataLV(){


        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_pengguna",null);
        daftar = new String[cursor.getCount()];
        id_pengguna= new String[cursor.getCount()];
        nama= new String[cursor.getCount()];
        tgl_lahir = new String[cursor.getCount()];
        jk= new String[cursor.getCount()];
        alamat = new String[cursor.getCount()];
        no = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);

            daftar[cc] = cursor.getString(1).toString();
            id_pengguna[cc] = cursor.getString(0).toString();
            nama[cc] = cursor.getString(1).toString();
            tgl_lahir[cc] = cursor.getString(2).toString();
            jk[cc] = cursor.getString(3).toString();
            alamat[cc] = cursor.getString(4).toString();
            no[cc] = cursor.getString(0).toString();



        }
        ListView01 = (ListView)findViewById(R.id.lv_server);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(false);

        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();


    }

    public void updateServer(){

        FunctionServer fc = new FunctionServer();
        fc.insertPengguna("1000","1000","1000","1000","1000");


    }
}
