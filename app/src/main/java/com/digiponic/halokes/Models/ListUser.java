package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 17-Jun-19.
 */

public class ListUser {

    @SerializedName("id_user")
    private String id_user;
    @SerializedName("username")
    private String username = "";
    @SerializedName("nama_siswa")
    private String nama_siswa = "";
    @SerializedName("password")
    private String password = "";
    @SerializedName("role")
    private String role = "";

    public ListUser(String id_user, String username, String nama_siswa, String password, String role) {
        this.id_user = id_user;
        this.username = username;
        this.nama_siswa = nama_siswa;
        this.password = password;
        this.role = role;
    }

    public String getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public String getNama_siswa() {
        return nama_siswa;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
