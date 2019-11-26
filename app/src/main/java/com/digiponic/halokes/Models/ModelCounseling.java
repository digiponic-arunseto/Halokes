package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ModelCounseling extends StructureDefault{
    @SerializedName("data")
    private ListCounseling data;

    public ModelCounseling(boolean status, String message, ListCounseling data) {
        super(status, message);
        this.data = data;
    }

    public ListCounseling getData() {
        return data;
    }
}
