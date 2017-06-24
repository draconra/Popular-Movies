package com.udacity.movie.api;

import com.udacity.movie.model.MovieResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMovie {

    String API_KEY          ="[YOUR_API_KEY]";

    String BASE_URL         ="https://api.themoviedb.org/3/";
    String THUMBNAIL        ="http://image.tmdb.org/t/p/w185/";

    @GET("movie/{type}")
    Call<MovieResp> getMovies(@Path("type") String type, @Query("api_key") String apiKey);
}
