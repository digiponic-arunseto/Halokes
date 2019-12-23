package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ListAnnouncement {
    @SerializedName("id_berita_url")
    String id_berita_url;
    @SerializedName("judul_berita")
    String judul_berita;
    @SerializedName("isi_berita")
    String isi_berita;
    @SerializedName("gambar_berita")
    String gambar_berita ;
    @SerializedName("penulis")
    String penulis;
    @SerializedName("tanggal")
    String tanggal;

    public ListAnnouncement(String id_berita_url, String judul_berita, String isi_berita, String gambar_berita, String penulis, String tanggal) {
        this.id_berita_url = id_berita_url;
        this.judul_berita = judul_berita;
        this.isi_berita = isi_berita;
        this.gambar_berita = gambar_berita;
        this.penulis = penulis;
        this.tanggal = tanggal;
    }

    public String getId_berita_url() {
        return id_berita_url;
    }

    public String getJudul_berita() {
        return judul_berita;
    }

    public String getIsi_berita() {
        return isi_berita;
    }

    public String getGambar_berita() {
        return gambar_berita;
    }

    public String getPenulis() {
        return penulis;
    }

    public String getTanggal() {
        return tanggal;
    }
}
