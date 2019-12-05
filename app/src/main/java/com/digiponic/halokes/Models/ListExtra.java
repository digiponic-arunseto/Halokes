package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListExtra {
    @SerializedName("nama")
    String nama;
    @SerializedName("jadwal")
    String jadwal;
    @SerializedName("ekskul_url")
    String ekskul_url;
    @SerializedName("tempat")
    String tempat;
    @SerializedName("jam")
    String jam;
    @SerializedName("data_kegiatan")
    List<ListExtraDetail> data_kegiatan;


    public ListExtra(String nama, String jadwal, String ekskul_url, String tempat, String jam, List<ListExtraDetail> data_kegiatan) {
        this.nama = nama;
        this.jadwal = jadwal;
        this.ekskul_url = ekskul_url;
        this.tempat = tempat;
        this.jam = jam;
        this.data_kegiatan = data_kegiatan;
    }

    public String getNama() {
        return nama;
    }

    public String getJadwal() {
        return jadwal;
    }

    public String getEkskul_url() {
        return ekskul_url;
    }

    public String getTempat() {
        return tempat;
    }

    public String getJam() {
        return jam;
    }

    public List<ListExtraDetail> getData_kegiatan() {
        return data_kegiatan;
    }
}
