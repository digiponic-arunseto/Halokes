package com.digiponic.halokes.Models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListGrade {

    @SerializedName("mapel")
    String mapel;
    @SerializedName("nilai_keseluruhan")
    int nilai_keseluruhan;
    @Nullable
    @SerializedName("data_nilai")
    List<ListGradeDetail> data_nilai;

    public ListGrade(String mapel, int nilai_keseluruhan, @Nullable List<ListGradeDetail> data_nilai) {
        this.mapel = mapel;
        this.nilai_keseluruhan = nilai_keseluruhan;
        this.data_nilai = data_nilai;
    }

    public String getMapel() {
        return mapel;
    }

    public int getNilai_keseluruhan() {
        return nilai_keseluruhan;
    }

    @Nullable
    public List<ListGradeDetail> getData_nilai() {
        return data_nilai;
    }
}
