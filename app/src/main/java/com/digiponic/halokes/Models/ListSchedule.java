package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListSchedule {
    @SerializedName("hari")
    String hari;
    @SerializedName("data_jadwal")
    List<ListScheduleDetail> data_jadwal;

    public ListSchedule(String hari, List<ListScheduleDetail> data_jadwal) {
        this.hari = hari;
        this.data_jadwal = data_jadwal;
    }

    public String getHari() {
        return hari;
    }

    public List<ListScheduleDetail> getData_jadwal() {
        return data_jadwal;
    }
}
