package com.udacity.movie.util;

public class Constant {

    public static final String[] MOVIE_LIST_TYPE = new String[]{"popular", "top_rated"};
    public static final String MOVIE_KEY = "movie";
    public static final String MOVIE_RELEASE_DATE_FORMAT_AFTER = "MMMM dd, yyyy";
    public static final String MOVIE_RELEASE_DATE_FORMAT_BEFORE = "yyyy-MM-dd";
    private static final int MOVIE_IMAGE_LIST_WIDTH = 200;
    public static final String EXTRA_MOVIE_LIST = "movie-list";
    public static final String EXTRA_PAGE = "page";
    public static final String EXTRA_TITLE = "title";
    public static final String MOVIE_FAVORITE_SUCCESS_MESSAGE = "Success get favorites movies";

    public enum MOVIE_LIST_TITLE {
        POPULAR, TOP_RATED, FAVORITE;
    }
}
