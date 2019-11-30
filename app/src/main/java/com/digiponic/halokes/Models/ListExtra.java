package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListExtra {
    @SerializedName("nama")
    String nama;
    @SerializedName("jadwal")
    String jadwal;
    @SerializedName("ekskulurl")
    String ekskulurl;
    @SerializedName("data_kegiatan")
    List<ListExtraDetail> data_kegiatan;


    public ListExtra(String nama, String jadwal, String ekskulurl, List<ListExtraDetail> data_kegiatan) {
        this.nama = nama;
        this.jadwal = jadwal;
        this.ekskulurl = ekskulurl;
        this.data_kegiatan = data_kegiatan;
    }

    public String getNama() {
        return nama;
    }

    public String getJadwal() {
        return jadwal;
    }

    public String getEkskulurl() {
        return ekskulurl;
    }

    public List<ListExtraDetail> getData_kegiatan() {
        return data_kegiatan;
    }
}
