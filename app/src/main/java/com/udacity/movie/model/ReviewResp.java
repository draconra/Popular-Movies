package com.udacity.movie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResp {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<ReviewObject> results;

    public int getId() {
        return id;
    }

    public List<ReviewObject> getResults() {
        return results;
    }
}
