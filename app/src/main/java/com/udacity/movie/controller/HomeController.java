package com.udacity.movie.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.movie.AppController;
import com.udacity.movie.api.ApiMovie;
import com.udacity.movie.event.NetworkErrorEvent;
import com.udacity.movie.event.home.HomeEvent;
import com.udacity.movie.event.home.MovieReviewEvent;
import com.udacity.movie.event.home.MovieTrailerEvent;
import com.udacity.movie.model.MovieObject;
import com.udacity.movie.model.MovieResp;
import com.udacity.movie.model.ReviewResp;
import com.udacity.movie.model.VideoResp;
import com.udacity.movie.util.Constant;
import com.udacity.movie.util.db.MovieContract;
import com.udacity.movie.util.network.APIError;
import com.udacity.movie.util.network.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeController extends BaseController {

    private Constant.MOVIE_LIST_TITLE movieListTitle;

    private void getMovies(int type) {
        Call<MovieResp> movieResponseCall = AppController.getInstance().getApiService().getMovies(Constant.MOVIE_LIST_TYPE[type], ApiMovie.API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResp>() {
            @Override
            public void onResponse(Call<MovieResp> call, Response<MovieResp> response) {
                if (response.isSuccessful()) {
                    eventBus.post(new HomeEvent(true, response.body().getResults()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    eventBus.post(new HomeEvent(false, error.message(), movieListTitle));
                }
            }

            @Override
            public void onFailure(Call<MovieResp> call, Throwable t) {
                eventBus.post(new NetworkErrorEvent(""));
            }
        });
    }

    public void getPopularMovies() {
        getMovies(0);
    }

    public void getTopRatedMovies() {
        getMovies(1);
    }

    public void getMovieTrailers(String movieId) {
        Call<VideoResp> videoResponseCall = AppController.getInstance().getApiService().getTrailers(movieId, ApiMovie.API_KEY, null);
        videoResponseCall.enqueue(new Callback<VideoResp>() {
            @Override
            public void onResponse(Call<VideoResp> call, Response<VideoResp> response) {
                if (response.isSuccessful()) {
                    eventBus.post(new MovieTrailerEvent(true, response.body()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    eventBus.post(new MovieTrailerEvent(false, error.message()));
                }
            }

            @Override
            public void onFailure(Call<VideoResp> call, Throwable t) {
                eventBus.post(new NetworkErrorEvent(""));
            }
        });
    }

    public void getMovieReviews(String movieId, int page) {
        Call<ReviewResp> reviewResponseCall = AppController.getInstance().getApiService().getReviews(movieId, ApiMovie.API_KEY, null, page);
        reviewResponseCall.enqueue(new Callback<ReviewResp>() {
            @Override
            public void onResponse(Call<ReviewResp> call, Response<ReviewResp> response) {
                if (response.isSuccessful()) {
                    eventBus.post(new MovieReviewEvent(true, response.body()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    eventBus.post(new MovieReviewEvent(false, error.message()));
                }
            }

            @Override
            public void onFailure(Call<ReviewResp> call, Throwable t) {
                eventBus.post(new NetworkErrorEvent(""));
            }
        });
    }

    public void getFavoriteMovies(Context context) {
        movieListTitle = Constant.MOVIE_LIST_TITLE.FAVORITE;
        Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);

        List<MovieObject> mMovieFavorites = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MovieObject movie = new MovieObject();
                movie.setId(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
                mMovieFavorites.add(movie);
            }
            cursor.close();
        }

        eventBus.post(new HomeEvent(true, mMovieFavorites,movieListTitle));
    }

    public boolean addFavoriteMovie(Context context, MovieObject movie) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieEntry.COLUMN_ID, movie.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());

        Uri uri = context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

        getFavoriteMovies(context);

        return (uri != null);
    }

    public boolean removeFavoriteMovie(Context context, MovieObject movie) {
        Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)).equals(movie.getId())) {
                    String id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
                    Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(id).build();
                    int itemDeleted = context.getContentResolver().delete(uri, null, null);

                    getFavoriteMovies(context);
                    return (itemDeleted != 0);
                }
            }
            cursor.close();
        }
        return false;
    }

    public boolean isFavoriteMovie(Context context, MovieObject movie) {
        Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)).equals(movie.getId())) {
                    return true;
                }
            }
            cursor.close();
        }
        return false;
    }
}
