package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListClass {
    @SerializedName("id_kelas")
    String id_kelas;
    @SerializedName("nis")
    String nis;
    @SerializedName("nama")
    String nama;
    @SerializedName("kelas")
    String kelas;
    @SerializedName("wali_kelas")
    String wali_kelas;
    @SerializedName("data_siswa")
    List<ListStudent> data_siswa;

    public ListClass(String id_kelas, String nis, String nama, String kelas, String wali_kelas, List<ListStudent> data_siswa) {
        this.id_kelas = id_kelas;
        this.nis = nis;
        this.nama = nama;
        this.kelas = kelas;
        this.wali_kelas = wali_kelas;
        this.data_siswa = data_siswa;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public String getNis() {
        return nis;
    }

    public String getNama() {
        return nama;
    }

    public String getKelas() {
        return kelas;
    }

    public String getWali_kelas() {
        return wali_kelas;
    }

    public List<ListStudent> getData_siswa() {
        return data_siswa;
    }
}
