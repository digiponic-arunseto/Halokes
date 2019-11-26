package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arunstop on 14-Jun-19.
 */

public class ModelScheduleDetail extends StructureDefault {

    @SerializedName("data")
    private ListSchedule data;

    public ModelScheduleDetail(boolean status, String message, ListSchedule data) {
        super(status, message);
        this.data = data;
    }

    public ListSchedule getData() {
        return data;
    }
}
