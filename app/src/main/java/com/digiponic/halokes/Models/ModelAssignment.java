package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arunstop on 14-Jun-19.
 */

public class ModelAssignment extends StructureDefault {

    @SerializedName("data")
    private List<ListAssignment> data;

    public ModelAssignment(boolean status, String message, List<ListAssignment> data) {
        super(status, message);
        this.data = data;
    }

    public List<ListAssignment> getData() {
        return data;
    }
}
