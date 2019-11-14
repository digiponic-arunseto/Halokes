package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListAssignmentTask {
    @SerializedName("id_tugas")
    String id_tugas;
    @SerializedName("judul_tugas")
    String judul_tugas;
    @SerializedName("deskripsi")
    String deskripsi;
    @SerializedName("tgl_buat")
    String tgl_buat;
    @SerializedName("deadline")
    String deadline;
    @SerializedName("sisa_waktu")
    int sisa_waktu;

    public ListAssignmentTask(String id_tugas, String judul_tugas, String deskripsi, String tgl_buat, String deadline, int sisa_waktu) {
        this.id_tugas = id_tugas;
        this.judul_tugas = judul_tugas;
        this.deskripsi = deskripsi;
        this.tgl_buat = tgl_buat;
        this.deadline = deadline;
        this.sisa_waktu = sisa_waktu;
    }

    public String getId_tugas() {
        return id_tugas;
    }

    public String getJudul_tugas() {
        return judul_tugas;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTgl_buat() {
        return tgl_buat;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getSisa_waktu() {
        return sisa_waktu;
    }
}
