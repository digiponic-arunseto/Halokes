package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ModelAttendance extends StructureDefault{
    @SerializedName("data")
    private ListAttendance data;

    public ModelAttendance(boolean error, String msg, ListAttendance data) {
        super(error, msg);
        this.data = data;
    }

    public ListAttendance getData() {
        return data;
    }
}
