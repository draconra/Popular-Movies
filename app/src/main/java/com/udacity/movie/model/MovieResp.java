package com.udacity.movie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieResp {

    @SerializedName("results")
    private List<MovieObject> results;

    public List<MovieObject> getResults() {
        return results;
    }
}

