package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListGradeDetail {

    @SerializedName("topik")
    String topik;
    @SerializedName("nilai")
    int[] nilai;

    public ListGradeDetail(String topik, int[] nilai) {
        this.topik = topik;
        this.nilai = nilai;
    }

    public String getTopik() {
        return topik;
    }

    public int[] getNilai() {
        return nilai;
    }
}
