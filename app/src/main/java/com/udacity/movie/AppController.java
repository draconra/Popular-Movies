package com.udacity.movie;

import android.app.Application;

import com.google.gson.Gson;
import com.udacity.movie.api.ApiMovie;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AppController extends Application {

    private Retrofit retrofit;
    private EventBus eventBus;
    private static Gson sGson;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        eventBus = new EventBus();
        sGson = new Gson();

        createRetrofitClient();
    }

    public Gson gson() {
        if (sGson == null) {
            sGson = new Gson();
        }
        return sGson;
    }


    private void createRetrofitClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiMovie.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public EventBus getEventBus() {
        if (null == eventBus) {
            eventBus = new EventBus();
        }
        return eventBus;
    }

    public Retrofit getRetrofitClient() {
        return retrofit;
    }

    public ApiMovie getApiService() {
        return getRetrofitClient().create(ApiMovie.class);
    }
}
