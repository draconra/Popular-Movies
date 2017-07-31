package com.udacity.movie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class VideoResp {
    @SerializedName("id")
    private int movieId;

    @SerializedName("results")
    private List<VideoObject> results;

    public int getMovieId() {
        return movieId;
    }

    public List<VideoObject> getResults() {
        return results;
    }
}
