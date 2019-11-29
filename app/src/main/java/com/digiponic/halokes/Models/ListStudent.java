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


    public void setNis(String nis) {
        this.nis = nis;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setStatus_siswa(String status_siswa) {
        this.status_siswa = status_siswa;
    }

    public void setData_diri(ListStudentIdentity data_diri) {
        this.data_diri = data_diri;
    }

    public void setData_ortu(ListStudentParent data_ortu) {
        this.data_ortu = data_ortu;
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
}
