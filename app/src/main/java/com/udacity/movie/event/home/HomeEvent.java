package com.udacity.movie.event.home;

import com.udacity.movie.event.BaseEvent;
import com.udacity.movie.model.MovieObject;

import java.util.List;

public class HomeEvent extends BaseEvent {

    private List<MovieObject> movieObjects;

    public HomeEvent(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }


    public HomeEvent(boolean isSuccess, List<MovieObject> movieData) {
        this.isSuccess = isSuccess;
        this.movieObjects = movieData;
    }

    public List<MovieObject> getMovieObjects() {
        return movieObjects;
    }
}
