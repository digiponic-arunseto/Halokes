package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListExtra {
    @SerializedName("nama")
    String nama;
    @SerializedName("jadwal")
    String jadwal;

    public ListExtra(String nama, String jadwal) {
        this.nama = nama;
        this.jadwal = jadwal;
    }

    public String getNama() {
        return nama;
    }

    public String getJadwal() {
        return jadwal;
    }
}
