package com.digiponic.halokes.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arunstop on 14-Jun-19.
 */

public class ModelAssignmentSubject extends StructureDefault {

    @SerializedName("data")
    private List<ListAssignmentSubject> data;

    public ModelAssignmentSubject(boolean status, String message, List<ListAssignmentSubject> data) {
        super(status, message);
        this.data = data;
    }

    public List<ListAssignmentSubject> getData() {
        return data;
    }
}
