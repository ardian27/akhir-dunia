package com.suska.uin.tif.zorokunti.dificam;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Sukses Update Server....");
    }
    @Override
    protected String doInBackground(String... params) {
        String url_data = "http://192.168.43.42/dificam/server-data.php";
        String url_pengguna = "http://192.168.43.42/dificam/server-pengguna.php";
        String url_bobot  = "http://192.168.43.42/dificam/getDataJson.php";

        String JSON_STRING;

        String method = params[0];
        if (method.equals("reload_data_citra")) {

            String id=params[1];
            String id_pengguna=params[2];
            String mean=params[3];
            String variance=params[4];
            String skewness=params[5];
            String kurtosis=params[6];
            String entrophy=params[7];
            try {
                URL url = new URL(url_data);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                        URLEncoder.encode("id_pengguna", "UTF-8") + "=" + URLEncoder.encode(id_pengguna, "UTF-8") + "&" +
                        URLEncoder.encode("mean", "UTF-8") + "=" + URLEncoder.encode(mean, "UTF-8") + "&" +
                        URLEncoder.encode("variance", "UTF-8") + "=" + URLEncoder.encode(variance, "UTF-8") + "&" +
                        URLEncoder.encode("skewness", "UTF-8") + "=" + URLEncoder.encode(skewness, "UTF-8") + "&" +
                        URLEncoder.encode("kurtosis", "UTF-8") + "=" + URLEncoder.encode(kurtosis, "UTF-8") + "&" +
                        URLEncoder.encode("entrophy", "UTF-8") + "=" + URLEncoder.encode(entrophy, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                // httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "Send Data Success...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("reload_pengguna"))
        {
            String id_pengguna = params[1];
            String nama = params[2];
            String tgl_lahir = params[3];
            String jk = params[4];
            String alamat = params[5];
            try {
                URL url = new URL(url_pengguna);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id_pengguna,"UTF-8")+"&"+
                        URLEncoder.encode("nama","UTF-8")+"="+URLEncoder.encode(nama,"UTF-8")+"&"+
                        URLEncoder.encode("tgl_lahir","UTF-8")+"="+URLEncoder.encode(tgl_lahir,"UTF-8")+"&"+
                        URLEncoder.encode("jk","UTF-8")+"="+URLEncoder.encode(jk,"UTF-8")+"&"+
                        URLEncoder.encode("alamat","UTF-8")+"="+URLEncoder.encode(alamat,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (method.equals("reload_bobot")){

            String v0 = params[1];
            String v1 = params[2];
            String v2 = params[3];
            String v3 = params[4];
            String v4 = params[5];
            String w = params[6];

        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {

    }
}
