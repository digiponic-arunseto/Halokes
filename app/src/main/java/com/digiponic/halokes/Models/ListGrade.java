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
    @SerializedName("nilai")
    List<ListGradeDetail> nilai;

    public ListGrade(String mapel, int nilai_keseluruhan, @Nullable List<ListGradeDetail> nilai) {
        this.mapel = mapel;
        this.nilai_keseluruhan = nilai_keseluruhan;
        this.nilai = nilai;
    }

    public String getMapel() {
        return mapel;
    }

    public int getNilai_keseluruhan() {
        return nilai_keseluruhan;
    }

    @Nullable
    public List<ListGradeDetail> getNilai() {
        return nilai;
    }
}
