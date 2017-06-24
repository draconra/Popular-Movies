package com.udacity.movie.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.movie.R;
import com.udacity.movie.api.ApiMovie;
import com.udacity.movie.model.MovieObject;
import com.udacity.movie.util.Constant;
import com.udacity.movie.view.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.tv_detail_poster)
    ImageView tvDetailPoster;
    @BindView(R.id.tv_detail_overview)
    TextView tvDetailOverview;
    @BindView(R.id.tv_detail_rating)
    TextView tvDetailRating;
    @BindView(R.id.tv_detail_release)
    TextView tvDetailRelease;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MovieObject currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        if (savedInstanceState != null) {
            currentMovie = savedInstanceState.getParcelable(Constant.MOVIE_KEY);
        } else if (intent != null && intent.hasExtra(Constant.MOVIE_KEY)) {
            currentMovie = getIntent().getParcelableExtra(Constant.MOVIE_KEY);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(currentMovie.getTitle());

        initMovieViews();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constant.MOVIE_KEY, currentMovie);
        super.onSaveInstanceState(outState);
    }

    private void initMovieViews() {
        String rating = currentMovie.getVoteAverage() + "/10";

        tvDetailTitle.setText(currentMovie.getTitle());
        tvDetailRelease.setText(currentMovie.getReleaseDate());
        tvDetailRating.setText(rating);
        tvDetailOverview.setText(currentMovie.getOverview());
        Glide.with(this).load(ApiMovie.THUMBNAIL + currentMovie.getPosterPath()).into(tvDetailPoster);
        tvDetailPoster.setContentDescription("Poster for the Movie: " + currentMovie.getTitle());
    }
}
