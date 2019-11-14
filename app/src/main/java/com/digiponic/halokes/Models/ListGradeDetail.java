package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListGradeDetail {

    @SerializedName("topik")
    String topik;
    @SerializedName("nilai")
    String nilai;

    public ListGradeDetail(String topik, String nilai) {
        this.topik = topik;
        this.nilai = nilai;
    }

    public String getTopik() {
        return topik;
    }

    public String getNilai() {
        return nilai;
    }
}
