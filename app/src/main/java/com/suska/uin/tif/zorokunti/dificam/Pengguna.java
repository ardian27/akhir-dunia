package com.suska.uin.tif.zorokunti.dificam;

/**
 * Created by zorokunti on 11/03/2018.
 */

public class Pengguna extends Koneksi {
    String URL = "http://192.168.43.6/dificam/server.php";
    String url = "";
    String response = "";

    public String tampilPengguna() {
        try {
            url = URL + "?operasi=view";
            //System.out.println("URL Tampil Biodata: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String insertPengguna(String nama, String alamat) {
        try {
            url = URL + "?operasi=insert&nama=" + nama + "&alamat=" + alamat;
            //System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getPenggunaById(int id) {
        try {
            url = URL + "?operasi=get_pengguna_by_id&id=" + id;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updateBiodata(String id, String nama, String alamat) {
        try {
            url = URL + "?operasi=update&id=" + id + "&nama=" + nama + "&alamat=" + alamat;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String deleteBiodata(int id) {
        try {
            url = URL + "?operasi=delete&id=" + id;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

}
