package com.suska.uin.tif.zorokunti.dificam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateBobot extends AppCompatActivity {


    Button btnHit;
    TextView txtJson;
    DataHelper dbHelper;
    String JSON_STRING, json_string;
    String data;

    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_server);
        dbHelper = new DataHelper(this);
        btnHit = (Button)findViewById(R.id.btnHit);
        dbHelper.getReadableDatabase();

        txtJson = (TextView) findViewById(R.id.tvJsonItem);



        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    new BackgoundTaskBobotBaru().execute();
                    new BackgoundTaskMinMax().execute();
                    new BackgoundTaskY().execute();



                }catch (Exception e){
                    Toast.makeText(UpdateBobot.this , "Update Gagal, Cek Koneksi Anda",Toast.LENGTH_LONG).show();

                }


            }
        });
    }


    class BackgoundTaskBobotBaru extends AsyncTask<Void,Void,String>{

        String json_url;
        @Override
        protected void onPreExecute() {
            //json_url = "http://192.168.43.42/dificam/getDataJSON.php";
            json_url = "http://10.0.2.1/dificam/getDataJSON.php";
        }
        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_STRING+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            txtJson = (TextView) findViewById(R.id.tvJsonItem);

            //Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //JsonFormat jsonFormat = gson.fromJson(result , JsonFormat.class);
            Gson gson = new Gson();
            JsonFormat jsonFormat = gson.fromJson(result, JsonFormat.class);

            Context context = UpdateBobot.this;

            DataHelper dataHelper = new DataHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //dataHelper.createTableBobot(sqlite);

            String droptable = "DROP TABLE bobot";

            String bobot = "create table bobot( no integer primary key,v0 double null,v1 double null,v2 double null" +
                    ",v3 double null,v4 double null, w double null);";

            Log.d("Data bobot baru", "onCreate: " + bobot);

            db.execSQL(droptable);
            db.execSQL(bobot);

            for (int a=0; a<jsonFormat.size(); a++){
                // jsonFormat.get(a);


                try {

                    db.execSQL("insert into bobot(no , v0, v1, v2, v3, v4, w) values('" +
                            a+ "','" +
                            jsonFormat.get(a).v0 + "','" +
                            jsonFormat.get(a).v1+ "','" +
                            jsonFormat.get(a).v2+ "','" +
                            jsonFormat.get(a).v3 + "','" +
                            jsonFormat.get(a).v4 + "','" +
                            jsonFormat.get(a).w + "')");


                    Log.d("insert data bobot baru ", "true");

                }catch (Exception e){
                    Log.e("gagal update bobot baru" , e.getMessage());
                }

            }
            txtJson.setText((Integer.toString(jsonFormat.size())));


        }
    }

    class BackgoundTaskMinMax extends AsyncTask<Void,Void,String>{

        String json_url;
        @Override
        protected void onPreExecute() {
            //json_url = "http://192.168.43.42/dificam/getDataMinMax.php";
            json_url = "http://10.0.2.1/dificam/getDataMinMax.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_STRING+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            txtJson = (TextView) findViewById(R.id.tvJsonItem);

            //Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //JsonFormat jsonFormat = gson.fromJson(result , JsonFormat.class);
            Gson gson = new Gson();
            JsonFormat jsonFormat = gson.fromJson(result, JsonFormat.class);

            Context context = UpdateBobot.this;

            DataHelper dataHelper = new DataHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //dataHelper.createTableBobot(sqlite);

            String droptable = "DROP TABLE min_max";

            String min_max = "create table min_max( id_minmax integer primary key,min_m double null,max_m double null,min_v double null" +
                    ",max_v double null,min_s double null, max_s double null, min_k double null, max_k double null, min_e double null,max_e double null);";

            Log.d("Data bobot baru", "onCreate: " + min_max);

            db.execSQL(droptable);
            db.execSQL(min_max);

            for (int a=0; a<jsonFormat.size(); a++){
                // jsonFormat.get(a);


                try {

                    db.execSQL("insert into min_max(id_minmax , min_m, max_m, min_v, max_v, min_s, max_s,min_k,max_k,min_e,max_e) values('" +
                            a+ "','" +
                            jsonFormat.get(a).min_m + "','" +
                            jsonFormat.get(a).max_m+ "','" +
                            jsonFormat.get(a).min_v+ "','" +
                            jsonFormat.get(a).max_v + "','" +
                            jsonFormat.get(a).min_s + "','" +
                            jsonFormat.get(a).max_s + "','" +
                            jsonFormat.get(a).min_k + "','" +
                            jsonFormat.get(a).max_k + "','" +
                            jsonFormat.get(a).min_e + "','" +
                            jsonFormat.get(a).max_e + "')");


                    Log.d(" data minmax baru ", "true");

                }catch (Exception e){
                    Log.e("gagal  minmax baru" , e.getMessage());
                }

            }
            txtJson.setText((Integer.toString(jsonFormat.size())));


        }
    }

    class BackgoundTaskY extends AsyncTask<Void,Void,String>{

        String json_url;
        @Override
        protected void onPreExecute() {
            //json_url = "http://192.168.43.42/dificam/getDataUji.php";
            json_url = "http://10.0.2.1/dificam/getDataUji.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_STRING+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            txtJson = (TextView) findViewById(R.id.tvJsonItem);

            //Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //JsonFormat jsonFormat = gson.fromJson(result , JsonFormat.class);
            Gson gson = new Gson();
            JsonFormat jsonFormat = gson.fromJson(result, JsonFormat.class);

            Context context = UpdateBobot.this;

            DataHelper dataHelper = new DataHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //dataHelper.createTableBobot(sqlite);

            String droptabley = "DROP TABLE temp_y";

            String temp_y = "create table temp_y( id_y integer primary key,y double null,t double null);";

            Log.d("Data Y baru", "onCreate: " + temp_y);

            db.execSQL(droptabley);
            db.execSQL(temp_y);

            for (int a=0; a<jsonFormat.size(); a++){
                // jsonFormat.get(a);


                try {

                    db.execSQL("insert into temp_y(id_y , y, t) values('" +
                            jsonFormat.get(a).id_y + "','" +
                            jsonFormat.get(a).y+ "','" +
                            jsonFormat.get(a).t + "')");


                    Log.d(" data temp y baru ", "true");

                }catch (Exception e){
                    Log.e("gagal temp_y baru" , e.getMessage());
                }

            }
            txtJson.setText(""+(Integer.toString(jsonFormat.size()))+" Data Pelatihan DiUpdate");


        }
    }


}