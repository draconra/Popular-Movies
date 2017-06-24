package com.udacity.movie.view.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.movie.AppController;

import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends AppCompatActivity {

    protected EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = AppController.getInstance().getEventBus();
    }
}
