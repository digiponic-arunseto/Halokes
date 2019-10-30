package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 17-Jun-19.
 */

public class ListAttendance {

    @SerializedName("hadir")
    String hadir;
    @SerializedName("sakit")
    String sakit;
    @SerializedName("izin")
    String izin;
    @SerializedName("alpha")
    String alpha;
    @SerializedName("persen_hadir")
    String persen_hadir;

    public ListAttendance(String hadir, String sakit, String izin, String alpha, String persen_hadir) {
        this.hadir = hadir;
        this.sakit = sakit;
        this.izin = izin;
        this.alpha = alpha;
        this.persen_hadir = persen_hadir;
    }

    public String getHadir() {
        return hadir;
    }

    public String getSakit() {
        return sakit;
    }

    public String getIzin() {
        return izin;
    }

    public String getAlpha() {
        return alpha;
    }

    public String getPersen_hadir() {
        return persen_hadir;
    }
}
