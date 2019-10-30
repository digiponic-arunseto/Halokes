package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 14-Jun-19.
 */

public class ModelUser extends StructureDefault {

    @SerializedName("data")
    private ListUser data;

    public ModelUser(boolean error, String msg, ListUser data) {
        super(error, msg);
        this.data = data;
    }

    public ListUser getData() {
        return data;
    }
}
