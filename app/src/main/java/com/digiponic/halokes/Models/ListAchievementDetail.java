package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListAchievementDetail {
    @SerializedName("nama_lomba")
    String nama_lomba;
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("tingkat")
    String tingkat;
    @SerializedName("prestasi_keterangan")
    String prestasi_keterangan;

    public ListAchievementDetail(String nama_lomba, String tanggal, String tingkat, String prestasi_keterangan) {
        this.nama_lomba = nama_lomba;
        this.tanggal = tanggal;
        this.tingkat = tingkat;
        this.prestasi_keterangan = prestasi_keterangan;
    }

    public String getNama_lomba() {
        return nama_lomba;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTingkat() {
        return tingkat;
    }

    public String getPrestasi_keterangan() {
        return prestasi_keterangan;
    }
}
