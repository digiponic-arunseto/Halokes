package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListStudentParent {
    @SerializedName("nama_ayah")
    String nama_ayah;
    @SerializedName("nohp_ayah")
    String nohp_ayah;
    @SerializedName("nama_ibu")
    String nama_ibu;
    @SerializedName("nohp_ibu")
    String nohp_ibu;
    @SerializedName("nama_wali")
    String nama_wali;
    @SerializedName("nohp_wali")
    String nohp_wali;

    public ListStudentParent(String nama_ayah, String nohp_ayah, String nama_ibu, String nohp_ibu, String nama_wali, String nohp_wali) {
        this.nama_ayah = nama_ayah;
        this.nohp_ayah = nohp_ayah;
        this.nama_ibu = nama_ibu;
        this.nohp_ibu = nohp_ibu;
        this.nama_wali = nama_wali;
        this.nohp_wali = nohp_wali;
    }

    public String getNama_ayah() {
        return nama_ayah;
    }

    public String getNohp_ayah() {
        return nohp_ayah;
    }

    public String getNama_ibu() {
        return nama_ibu;
    }

    public String getNohp_ibu() {
        return nohp_ibu;
    }

    public String getNama_wali() {
        return nama_wali;
    }

    public String getNohp_wali() {
        return nohp_wali;
    }
}
