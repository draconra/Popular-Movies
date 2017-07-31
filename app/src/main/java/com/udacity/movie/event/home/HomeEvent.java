package com.udacity.movie.event.home;

import com.udacity.movie.event.BaseEvent;
import com.udacity.movie.model.MovieObject;
import com.udacity.movie.util.Constant;

import java.util.List;

public class HomeEvent extends BaseEvent {

    private List<MovieObject> movieObjects;
    private Constant.MOVIE_LIST_TITLE movieListTitle;


    public HomeEvent(boolean isSuccess,  List<MovieObject> results, Constant.MOVIE_LIST_TITLE movieListTitle) {
        this.movieListTitle = movieListTitle;
        this.isSuccess = isSuccess;
        this.movieObjects = results;
    }


    public HomeEvent(boolean isSuccess, List<MovieObject> movieData) {
        this.isSuccess = isSuccess;
        this.movieObjects = movieData;
    }

    public HomeEvent(boolean isSuccess, String message, Constant.MOVIE_LIST_TITLE movieListTitle) {
        this.movieListTitle = movieListTitle;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public List<MovieObject> getMovieObjects() {
        return movieObjects;
    }

    public Constant.MOVIE_LIST_TITLE getMovieListTitle() {
        return movieListTitle;
    }
}
