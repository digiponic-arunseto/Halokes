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
    @SerializedName("password")
    private String password = "";
    @SerializedName("role")
    private String role = "";

    public String getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public ListUser(String id_user, String username, String password, String role) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
