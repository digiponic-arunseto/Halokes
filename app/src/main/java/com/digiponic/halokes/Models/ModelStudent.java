package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arunstop on 14-Jun-19.
 */

public class ModelStudent extends StructureDefault {

    @SerializedName("data")
    private ListStudent data;

    public ModelStudent(boolean status, String message, ListStudent data) {
        super(status, message);
        this.data = data;
    }

    public ListStudent getData() {
        return data;
    }
}
