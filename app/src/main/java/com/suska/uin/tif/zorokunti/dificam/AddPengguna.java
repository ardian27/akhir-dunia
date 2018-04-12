package com.suska.uin.tif.zorokunti.dificam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class AddPengguna extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText id, nama, tgl_lahir, jk, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pengguna);

        dbHelper = new DataHelper(this);
        nama = (EditText) findViewById(R.id.et_nama);
        tgl_lahir = (EditText) findViewById(R.id.et_tgl_lahir);
        jk = (EditText) findViewById(R.id.et_jk);
        alamat = (EditText) findViewById(R.id.et_alamat);
        ton1 = (Button) findViewById(R.id.button1);
        ton2 = (Button) findViewById(R.id.button2);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into tbl_pengguna(no, nama, tgl, jk, alamat) values(NULL,'" +
                        nama.getText().toString() + "','" +
                        tgl_lahir.getText().toString() + "','" +
                        jk.getText().toString() + "','" +
                        alamat.getText().toString() + "')");
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                PenggunaActivity.ma.RefreshList();
                finish();
            }
        });
        ton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }

}
