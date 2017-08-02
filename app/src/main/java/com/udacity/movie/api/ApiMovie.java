package com.udacity.movie.api;

import com.udacity.movie.model.MovieResp;
import com.udacity.movie.model.ReviewResp;
import com.udacity.movie.model.VideoResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMovie {

    String API_KEY                          ="[YOUR_API_KEY]";

    String BASE_URL                         = "https://api.themoviedb.org/3/";
    String THUMBNAIL                        = "http://image.tmdb.org/t/p/w185/";
    String BASE_URL_IMAGE_YOUTUBE           = "http://img.youtube.com/vi/";
    String BASE_URL_IMAGE_YOUTUBE_THUMBNAIL = "/0.jpg";
    String BASE_URL_VIDEO_YOUTUBE           = "https://youtube.com/watch";

    @GET("movie/{type}")
    Call<MovieResp> getMovies(@Path("type") String type, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideoResp> getTrailers(@Path("id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResp> getReviews(@Path("id") String movieId, @Query("api_key") String apiKey);
}
