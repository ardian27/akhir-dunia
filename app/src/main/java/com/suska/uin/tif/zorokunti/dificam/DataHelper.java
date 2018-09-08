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


        String bobot = "create table bobot(v0 double primary key,v1 double null, " +
                "v3 double null, v4 double null, w double null);";

        Log.d("Data", "onCreate: " + bobot);
        db.execSQL(bobot);

        String temp_y = "create table temp_y(y double primary key,t integer null);";
        Log.d("Data", "onCreate: " + temp_y);
        db.execSQL(temp_y);



    }

    public  void createTableBobot(SQLiteDatabase db){
        try {

            String bobot = "create table bobot(v0 double primary key,v1 double null, " +
                    "v3 double null, v4 double null, w double null);";

            Log.d("Data", "onCreate: " + bobot);
            db.execSQL(bobot);

        }catch (Exception e){
            Log.e("Error Membuat DB" , e.getMessage());
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}