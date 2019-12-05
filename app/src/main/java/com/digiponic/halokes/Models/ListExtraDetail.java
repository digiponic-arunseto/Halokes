package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListExtraDetail {
    @SerializedName("judul")
    String judul;
    @SerializedName("deskripsi")
    String deskripsi;
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("tempat")
    String tempat;

    public ListExtraDetail(String judul, String deskripsi, String tanggal, String tempat) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.tempat = tempat;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTempat() {
        return tempat;
    }
}
