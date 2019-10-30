package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arunstop on 24-May-19.
 */

public class StructureDefault {

    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message = "";


    public StructureDefault(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
