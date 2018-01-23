package com.suska.uin.tif.zorokunti.dificam;

/**
 * Created by zorokunti on 14/11/2017.
 */

public class DataPengguna {
    private int id;
    private String nama;
    private String alamat;

        public DataPengguna(){}

        public DataPengguna(String nama, String alamat) {
            super();
            this.nama = nama;
            this.alamat = alamat;
            }

        @Override
public String toString() {
return "DataPengguna [id=" + id + ", nama=" + nama + ", alamat=" + alamat
+ "]";
}
}
