package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

public class ModelClass extends StructureDefault {
    @SerializedName("data")
    private ListClass data;

    public ModelClass(boolean status, String message, ListClass data) {
        super(status, message);
        this.data = data;
    }

    public ListClass getData() {
        return data;
    }
}
