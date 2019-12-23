package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListStudent {
    @SerializedName("nis")
    String nis;
    @SerializedName(value = "name", alternate = {"nama", "nama_siswa"})
    String nama;
    @SerializedName("status_siswa")
    String status_siswa;
    @SerializedName("data_diri")
    ListStudentIdentity data_diri;
    @SerializedName("data_ortu")
    ListStudentParent data_ortu;
    @SerializedName("total_rr")
    int total_rr;
    @SerializedName("total_kehadiran")
    double total_kehadiran;
    @SerializedName("total_prestasi")
    int total_prestasi;
    @SerializedName("total_pelanggaran")
    int total_pelanggaran;
    @SerializedName("total_tugas")
    int total_tugas;

    public ListStudent(String nis, String nama, String status_siswa, ListStudentIdentity data_diri, ListStudentParent data_ortu, int total_rr, double total_kehadiran, int total_prestasi, int total_pelanggaran, int total_tugas) {
        this.nis = nis;
        this.nama = nama;
        this.status_siswa = status_siswa;
        this.data_diri = data_diri;
        this.data_ortu = data_ortu;
        this.total_rr = total_rr;
        this.total_kehadiran = total_kehadiran;
        this.total_prestasi = total_prestasi;
        this.total_pelanggaran = total_pelanggaran;
        this.total_tugas = total_tugas;
    }

    public String getNis() {
        return nis;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus_siswa() {
        return status_siswa;
    }

    public ListStudentIdentity getData_diri() {
        return data_diri;
    }

    public ListStudentParent getData_ortu() {
        return data_ortu;
    }

    public int getTotal_rr() {
        return total_rr;
    }

    public double getTotal_kehadiran() {
        return total_kehadiran;
    }

    public int getTotal_prestasi() {
        return total_prestasi;
    }

    public int getTotal_pelanggaran() {
        return total_pelanggaran;
    }

    public int getTotal_tugas() {
        return total_tugas;
    }
}
