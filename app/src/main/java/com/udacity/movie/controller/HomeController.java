package com.udacity.movie.controller;


import com.udacity.movie.AppController;
import com.udacity.movie.api.ApiMovie;
import com.udacity.movie.event.NetworkErrorEvent;
import com.udacity.movie.event.home.HomeEvent;
import com.udacity.movie.model.MovieResp;
import com.udacity.movie.util.Constant;
import com.udacity.movie.util.network.APIError;
import com.udacity.movie.util.network.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeController extends BaseController {

    private void getMovies(int type) {
        Call<MovieResp> movieResponseCall = AppController.getInstance().getApiService().getMovies(Constant.MOVIE_LIST_TYPE[type], ApiMovie.API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResp>() {
            @Override
            public void onResponse(Call<MovieResp> call, Response<MovieResp> response) {
                if (response.isSuccessful()) {
                    eventBus.post(new HomeEvent(true, response.body().getResults()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    eventBus.post(new HomeEvent(false, error.message()));
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
}
