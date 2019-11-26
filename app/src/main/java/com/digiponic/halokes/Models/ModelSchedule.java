package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arunstop on 14-Jun-19.
 */

public class ModelSchedule extends StructureDefault {

    @SerializedName("data")
    private List<ListSchedule> data;

    public ModelSchedule(boolean status, String message, List<ListSchedule> data) {
        super(status, message);
        this.data = data;
    }

    public List<ListSchedule> getData() {
        return data;
    }
}
