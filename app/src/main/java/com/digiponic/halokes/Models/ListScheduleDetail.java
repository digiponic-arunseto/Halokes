package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListScheduleDetail {
    @SerializedName("id_jadwal")
    String id_jadwal;
    @SerializedName("hari")
    String hari;
    @SerializedName("mapel")
    String mapel;
    @SerializedName("guru")
    String guru;
    @SerializedName("jam")
    String jam;

    public ListScheduleDetail(String id_jadwal, String hari, String mapel, String guru, String jam) {
        this.id_jadwal = id_jadwal;
        this.hari = hari;
        this.mapel = mapel;
        this.guru = guru;
        this.jam = jam;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public String getHari() {
        return hari;
    }

    public String getMapel() {
        return mapel;
    }

    public String getGuru() {
        return guru;
    }

    public String getJam() {
        return jam;
    }
}
