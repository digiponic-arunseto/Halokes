package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelExtra extends StructureDefault {
    @SerializedName("data")
    private List<ListExtra> data;

    public ModelExtra(boolean status, String message, List<ListExtra> data) {
        super(status, message);
        this.data = data;
    }

    public List<ListExtra> getData() {
        return data;
    }
}
