package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListViolation {
    @SerializedName("poin_pelanggaran")
    int poin_pelanggaran;
    @SerializedName("data_pelanggaran_detail")
    List<ListViolationDetail> data_pelanggaran_detail;

    public ListViolation(int poin_pelanggaran, List<ListViolationDetail> data_pelanggaran_detail) {
        this.poin_pelanggaran = poin_pelanggaran;
        this.data_pelanggaran_detail = data_pelanggaran_detail;
    }

    public int getPoin_pelanggaran() {
        return poin_pelanggaran;
    }

    public List<ListViolationDetail> getData_pelanggaran_detail() {
        return data_pelanggaran_detail;
    }
}
