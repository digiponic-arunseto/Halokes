package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListStudent {
    @SerializedName("nis")
    String nis;
    @SerializedName("nama")
    String nama;

    public ListStudent(String nis, String nama) {
        this.nis = nis;
        this.nama = nama;
    }

    public String getNis() {
        return nis;
    }

    public String getNama() {
        return nama;
    }
}
