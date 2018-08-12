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
        setContentView(R.layout.activity_tes_json);
        dbHelper = new DataHelper(this);
        btnHit = (Button)findViewById(R.id.btnHit);
        dbHelper.getReadableDatabase();

        txtJson = (TextView) findViewById(R.id.tvJsonItem);



        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgoundTask().execute();


            }
        });




    }


    class BackgoundTask extends AsyncTask<Void,Void,String>{

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://192.168.43.42/dificam/getDataJSON.php";
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

            String temp_y = "create table temp_y( no integer primary key,y double ,t double null);";

            Log.d("Data temp_y baru", "onCreate: " + temp_y);

            db.execSQL(droptable);
            db.execSQL(temp_y);

            for (int a=0; a<jsonFormat.size(); a++){
               // jsonFormat.get(a);


                try {

                    db.execSQL("insert into temp_y(no , y, t) values('" +
                            a+ "','" +
                            jsonFormat.get(a).y + "','" +
                            jsonFormat.get(a).t+ "')");

                    Log.d("insert data temp y ", "true");

                }catch (Exception e){
                    Log.e("gagal update y_temp" , e.getMessage());
                }

            }
            txtJson.setText((Integer.toString(jsonFormat.size())));


        }
    }


}