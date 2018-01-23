package com.suska.uin.tif.zorokunti.dificam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePengguna extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText id,nama, tgl_lahir, jk, alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pengguna);

        dbHelper = new DataHelper(this);
        id = (EditText) findViewById(R.id.et_updateID);
        nama = (EditText) findViewById(R.id.et_updateNama);
        tgl_lahir = (EditText) findViewById(R.id.et_updateTglLahir);
        jk = (EditText) findViewById(R.id.et_updateJK);
        alamat = (EditText) findViewById(R.id.et_updateAlamat);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_pengguna WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            id.setText(cursor.getString(0).toString());
            nama.setText(cursor.getString(0).toString());
            tgl_lahir.setText(cursor.getString(1).toString());
            jk.setText(cursor.getString(2).toString());
            alamat.setText(cursor.getString(3).toString());
        }
        ton1 = (Button) findViewById(R.id.btn_updatePengguna);
        // daftarkan even onClick pada btnSimpan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update tbl_pengguna set nama='"+
                        nama.getText().toString() +"', tgl='" +
                        tgl_lahir.getText().toString()+"', jk='"+
                        jk.getText().toString() +"', alamat='" +
                        alamat.getText().toString() + "' where no='" +
                        id.getText().toString()+"'");
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                PenggunaActivity.ma.RefreshList();
                finish();
            }
        });

    }

}
