package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 17-Jun-19.
 */

public class ListUser {

    @SerializedName("id_user")
    private String id_user;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("nis")
    private String nis;
    @SerializedName("nama_siswa")
    private String nama_siswa;
    @SerializedName("kelas")
    private String kelas;
    @SerializedName("role")
    private String role;
    @SerializedName("foto")
    private String foto;

    public ListUser(String id_user, String username, String password, String nis, String nama_siswa, String kelas, String role, String foto) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.nis = nis;
        this.nama_siswa = nama_siswa;
        this.kelas = kelas;
        this.role = role;
        this.foto = foto;
    }

    public String getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNis() {
        return nis;
    }

    public String getNama_siswa() {
        return nama_siswa;
    }

    public String getKelas() {
        return kelas;
    }

    public String getRole() {
        return role;
    }

    public String getFoto() {
        return foto;
    }
}
