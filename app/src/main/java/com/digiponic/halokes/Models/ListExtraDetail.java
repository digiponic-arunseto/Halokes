package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListExtraDetail {
    @SerializedName("judul")
    String judul;
    @SerializedName("deskripsi")
    String deskripsi;
    @SerializedName("tanggal")
    String tanggal;

    public ListExtraDetail(String judul, String deskripsi, String tanggal) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
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
}
