package com.udacity.movie.event.home;

import com.udacity.movie.event.BaseEvent;
import com.udacity.movie.model.ReviewResp;

public class MovieReviewEvent extends BaseEvent {
    private ReviewResp body;

    public MovieReviewEvent(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public MovieReviewEvent(boolean isSuccess, ReviewResp body) {
        this.isSuccess = isSuccess;
        this.body = body;
    }

    public ReviewResp getBody() {
        return body;
    }
}