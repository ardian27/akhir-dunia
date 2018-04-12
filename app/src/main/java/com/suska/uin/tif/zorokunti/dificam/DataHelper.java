package com.suska.uin.tif.zorokunti.dificam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by zorokunti on 14/11/2017.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dificam.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String pelatihan = "create table tbl_pelatihan(id integer primary key, " +
                "idpengguna integer, mean double null, variance double null, skewness double null, " +
                "kurtosis double null, entrophy double null);";
        Log.d("Data", "onCreate: " + pelatihan);
        db.execSQL(pelatihan);


        // TODO Auto-generated method stub
        String pengguna = "create table tbl_pengguna(no integer primary key, " +
                "nama text null, tgl text null, jk text null, alamat text null);";
        Log.d("Data", "onCreate: " + pengguna);
        db.execSQL(pengguna);

    }



    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}