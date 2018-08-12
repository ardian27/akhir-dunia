package com.suska.uin.tif.zorokunti.dificam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

public class UpdateDataServer extends AppCompatActivity {

    DataHelper dbcenter;

    protected Cursor cursor,cursor_data;
    //ActionServer as = new ActionServer();
    String[] daftar,no,id_pengguna,nama,tgl_lahir,jk,alamat;
    String[] id,idp,mean,variance,skewness,kurtosis,entrophy;
    //JSONArray arrayData;
    UpdateDataServer upd;
    private ListView ListView01;
    Button btnUpdate;
    TextView teks;
    EditText namaa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_server);

        upd = this;
        dbcenter = new DataHelper(this);



        btnUpdate = (Button)findViewById(R.id.btn_update_server);
        teks = (TextView)findViewById(R.id.txt_updt);
        namaa = (EditText)findViewById(R.id.et_namaKirim);

        UpdateDataLV();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updateServerPengguna();
                updateServerDataCitraStatistika();
                Intent v = new Intent(UpdateDataServer.this, MainActivity.class);
                startActivity(v);
                Toast.makeText(getApplicationContext(), "Server Telah Terupdate", Toast.LENGTH_LONG).show();

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


        SQLiteDatabase dbc = dbcenter.getReadableDatabase();
        cursor_data = dbc.rawQuery("SELECT * FROM tbl_pelatihan",null);
        id = new String[cursor_data.getCount()];
        idp= new String[cursor_data.getCount()];
        mean= new String[cursor_data.getCount()];
        variance = new String[cursor_data.getCount()];
        skewness= new String[cursor_data.getCount()];
        kurtosis = new String[cursor_data.getCount()];
        entrophy = new String[cursor_data.getCount()];

        cursor_data.moveToFirst();
        for (int cc=0; cc < cursor_data.getCount(); cc++) {
            cursor_data.moveToPosition(cc);

            id[cc] = cursor_data.getString(0).toString();
            idp[cc] = cursor_data.getString(1).toString();
            mean[cc] = cursor_data.getString(2).toString();
            variance[cc] = cursor_data.getString(3).toString();
            skewness[cc] = cursor_data.getString(4).toString();
            kurtosis[cc] = cursor_data.getString(5).toString();
            entrophy[cc] = cursor_data.getString(6).toString();

        }
        //String method = "reload_pengguna";
        //BackgroundTask backgroundTask = new BackgroundTask(this);
        //backgroundTask.execute(method,nama[1],nama[1],nama[1],nama[1],nama[1]);

        ListView01 = (ListView)findViewById(R.id.lv_server);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(false);
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();

    }

    public void updateServerPengguna(){
        for (int d=0; d<no.length; d++){
            String method = "reload_pengguna";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method,id_pengguna[d],nama[d],tgl_lahir[d],jk[d],alamat[d]);
        }
    }

    public void updateServerDataCitraStatistika(){
        for (int d=0; d<id.length; d++){
            String method = "reload_data_citra";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method,id[d],idp[d],mean[d],variance[d],skewness[d],kurtosis[d],entrophy[d]);
        }
    }
}

