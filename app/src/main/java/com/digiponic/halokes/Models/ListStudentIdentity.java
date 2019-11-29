package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListStudentIdentity {
    @SerializedName("nama_lengkap")
    String nama_lengkap;
    @SerializedName("nisn")
    String nisn;
    @SerializedName("tempat_lahir")
    String tempat_lahir;
    @SerializedName("tgl_lahir")
    String tgl_lahir;
    @SerializedName("jkel")
    String jkel;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("no_hp")
    String no_hp;
    @SerializedName("email")
    String email;
    @SerializedName("agama")
    String agama;
    @SerializedName("asal_sekolah")
    String asal_sekolah;

    public ListStudentIdentity(String nama_lengkap, String nisn, String tempat_lahir, String tgl_lahir, String jkel, String alamat, String no_hp, String email, String agama, String asal_sekolah) {
        this.nama_lengkap = nama_lengkap;
        this.nisn = nisn;
        this.tempat_lahir = tempat_lahir;
        this.tgl_lahir = tgl_lahir;
        this.jkel = jkel;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.email = email;
        this.agama = agama;
        this.asal_sekolah = asal_sekolah;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getNisn() {
        return nisn;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public String getJkel() {
        return jkel;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getEmail() {
        return email;
    }

    public String getAgama() {
        return agama;
    }

    public String getAsal_sekolah() {
        return asal_sekolah;
    }
}
