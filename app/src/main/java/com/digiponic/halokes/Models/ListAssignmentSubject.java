package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListAssignmentSubject {
    @SerializedName("id_mapel")
    String id_mapel;
    @SerializedName("nama_mapel")
    String nama_mapel ;
    @SerializedName("jumlah_tugas_mapel")
    String jumlah_tugas_mapel;
    @SerializedName("data_tugas")
    List<ListAssignmentTask> data_tugas;


    public ListAssignmentSubject(String id_mapel, String nama_mapel, String jumlah_tugas_mapel, List<ListAssignmentTask> data_tugas) {
        this.id_mapel = id_mapel;
        this.nama_mapel = nama_mapel;
        this.jumlah_tugas_mapel = jumlah_tugas_mapel;
        this.data_tugas = data_tugas;
    }

    public String getId_mapel() {
        return id_mapel;
    }

    public String getNama_mapel() {
        return nama_mapel;
    }

    public String getJumlah_tugas_mapel() {
        return jumlah_tugas_mapel;
    }

    public List<ListAssignmentTask> getData_tugas() {
        return data_tugas;
    }
}
