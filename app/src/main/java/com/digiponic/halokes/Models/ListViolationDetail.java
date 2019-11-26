package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListViolationDetail {
    @SerializedName("pelanggaran")
    String pelanggaran;
    @SerializedName("kategori")
    String kategori;
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("poin")
    String poin;

    public ListViolationDetail(String pelanggaran, String kategori, String tanggal, String poin) {
        this.pelanggaran = pelanggaran;
        this.kategori = kategori;
        this.tanggal = tanggal;
        this.poin = poin;
    }

    public String getPelanggaran() {
        return pelanggaran;
    }

    public String getKategori() {
        return kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getPoin() {
        return poin;
    }
}
