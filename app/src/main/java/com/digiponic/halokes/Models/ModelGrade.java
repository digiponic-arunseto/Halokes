package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arunstop on 14-Jun-19.
 */

public class ModelGrade extends StructureDefault {

    @SerializedName("data")
    private List<ListGrade> data;

    public ModelGrade(boolean status, String message, List<ListGrade> data) {
        super(status, message);
        this.data = data;
    }

    public List<ListGrade> getData() {
        return data;
    }
}
