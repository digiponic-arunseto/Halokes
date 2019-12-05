package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListCounseling {
    @SerializedName("nama_siswa")
    String nama_siswa;
    @SerializedName("kelas")
    String kelas;
    @SerializedName("status_siswa")
    String status_siswa;
    @SerializedName("data_prestasi")
    ListAchievement data_prestasi;
    @SerializedName("data_pelanggaran")
    ListViolation data_pelanggaran;

    public ListCounseling(String nama_siswa, String kelas, String status_siswa, ListAchievement data_prestasi, ListViolation data_pelanggaran) {
        this.nama_siswa = nama_siswa;
        this.kelas = kelas;
        this.status_siswa = status_siswa;
        this.data_prestasi = data_prestasi;
        this.data_pelanggaran = data_pelanggaran;
    }

    public String getNama_siswa() {
        return nama_siswa;
    }

    public String getKelas() {
        return kelas;
    }

    public String getStatus_siswa() {
        return status_siswa;
    }

    public ListAchievement getData_prestasi() {
        return data_prestasi;
    }

    public ListViolation getData_pelanggaran() {
        return data_pelanggaran;
    }
}
