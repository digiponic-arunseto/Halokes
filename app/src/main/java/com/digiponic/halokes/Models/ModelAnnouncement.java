package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAnnouncement extends StructureDefault {
    @SerializedName("data")
    private List<ListAnnouncement> data;

    public ModelAnnouncement(boolean status, String message, List<ListAnnouncement> data) {
        super(status, message);
        this.data = data;
    }

    public List<ListAnnouncement> getData() {
        return data;
    }
}
