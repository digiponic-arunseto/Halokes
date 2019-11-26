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
    @SerializedName("tgl_sakit")
    String[] tgl_sakit;
    @SerializedName("izin")
    String izin;
    @SerializedName("tgl_izin")
    String[] tgl_izin;
    @SerializedName("alpha")
    String alpha;
    @SerializedName("tgl_alpha")
    String[] tgl_alpha;
    @SerializedName("persen_hadir")
    String persen_hadir;

    public ListAttendance(String hadir, String sakit, String[] tgl_sakit, String izin, String[] tgl_izin, String alpha, String[] tgl_alpha, String persen_hadir) {
        this.hadir = hadir;
        this.sakit = sakit;
        this.tgl_sakit = tgl_sakit;
        this.izin = izin;
        this.tgl_izin = tgl_izin;
        this.alpha = alpha;
        this.tgl_alpha = tgl_alpha;
        this.persen_hadir = persen_hadir;
    }

    public String getHadir() {
        return hadir;
    }

    public String getSakit() {
        return sakit;
    }

    public String[] getTgl_sakit() {
        return tgl_sakit;
    }

    public String getIzin() {
        return izin;
    }

    public String[] getTgl_izin() {
        return tgl_izin;
    }

    public String getAlpha() {
        return alpha;
    }

    public String[] getTgl_alpha() {
        return tgl_alpha;
    }

    public String getPersen_hadir() {
        return persen_hadir;
    }
}
