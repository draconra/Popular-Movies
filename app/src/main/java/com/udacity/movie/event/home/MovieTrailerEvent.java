package com.udacity.movie.event.home;

import com.udacity.movie.event.BaseEvent;
import com.udacity.movie.model.VideoResp;

public class MovieTrailerEvent extends BaseEvent {
    private VideoResp body;

    public MovieTrailerEvent(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public MovieTrailerEvent(boolean isSuccess, VideoResp body) {
        this.isSuccess = isSuccess;
        this.body = body;
    }

    public VideoResp getBody() {
        return body;
    }
}