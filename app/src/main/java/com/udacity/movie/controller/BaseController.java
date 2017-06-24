package com.udacity.movie.controller;

import com.udacity.movie.AppController;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseController {

    protected AppController appController = AppController.getInstance();
    protected EventBus eventBus = appController.getEventBus();

}
