package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListAchievement {
    @SerializedName("poin_prestasi")
    int poin_prestasi;
    @SerializedName("data_prestasi_detail")
    List<ListAchievementDetail> data_prestasi_detail;

    public ListAchievement(int poin_prestasi, List<ListAchievementDetail> data_prestasi_detail) {
        this.poin_prestasi = poin_prestasi;
        this.data_prestasi_detail = data_prestasi_detail;
    }

    public int getPoin_prestasi() {
        return poin_prestasi;
    }

    public List<ListAchievementDetail> getData_prestasi_detail() {
        return data_prestasi_detail;
    }
}
