package com.suska.uin.tif.zorokunti.dificam;

/**
 * Created by angsakumisan on 07/04/18.
 */

public class ActionServer extends KoneksiServer{

    String URL = "http://10.0.2.2/dificam/server.php";
    String url = "";
    String response = "";


    public String insertBiodata(String nama, String alamat) {
        try {
            url = URL + "?operasi=insert&nama=" + nama + "&alamat=" + alamat;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

}
