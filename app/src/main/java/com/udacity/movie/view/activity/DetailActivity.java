package com.udacity.movie.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.udacity.movie.R;
import com.udacity.movie.api.ApiMovie;
import com.udacity.movie.controller.ControllerFactory;
import com.udacity.movie.controller.HomeController;
import com.udacity.movie.event.home.MovieReviewEvent;
import com.udacity.movie.event.home.MovieTrailerEvent;
import com.udacity.movie.model.MovieObject;
import com.udacity.movie.util.Constant;
import com.udacity.movie.util.helper.ImageHelper;
import com.udacity.movie.util.helper.VideoHelper;
import com.udacity.movie.view.adapter.ReviewsAdapter;
import com.udacity.movie.view.adapter.TrailersAdapter;
import com.udacity.movie.view.core.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.detail_review_title)
    TextView detailReviewTitle;
    @BindView(R.id.detail_review_list)
    RecyclerView detailReviewList;
    @BindView(R.id.detail_trailer_title)
    TextView detailTrailerTitle;
    @BindView(R.id.detail_trailer_list)
    RecyclerView detailTrailerList;

    private MovieObject currentMovie;
    private HomeController controller;
    private int mReviewPage = 1;
    private ReviewsAdapter mReviewsAdapter;
    private TrailersAdapter mTrailersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        controller = ControllerFactory.homeController();

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
        ImageHelper.setImageResource(this, ApiMovie.THUMBNAIL + currentMovie.getPosterPath(),tvDetailPoster);
        tvDetailPoster.setContentDescription("Poster for the Movie: " + currentMovie.getTitle());

        RecyclerView.LayoutManager reviewsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detailReviewList.setLayoutManager(reviewsLayoutManager);
        detailReviewList.setHasFixedSize(true);

        mReviewsAdapter = new ReviewsAdapter();
        detailReviewList.setAdapter(mReviewsAdapter);

        ViewCompat.setNestedScrollingEnabled(detailReviewList, false);

        RecyclerView.LayoutManager trailersLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detailTrailerList.setLayoutManager(trailersLayoutManager);
        detailTrailerList.setHasFixedSize(true);

        mTrailersAdapter = new TrailersAdapter();
        detailTrailerList.setAdapter(mTrailersAdapter);

        ViewCompat.setNestedScrollingEnabled(detailTrailerList, false);

        controller.getMovieReviews(currentMovie.getId(), mReviewPage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieReviewList(MovieReviewEvent event) {
        mReviewsAdapter.setData(event.getBody().getResults());
        controller.getMovieTrailers(currentMovie.getId());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieTrailerList(MovieTrailerEvent event) {
        mTrailersAdapter.setData(event.getBody().getResults());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem item = menu.findItem(R.id.action_favorite_star);

        boolean isFavorite = controller.isFavoriteMovie(this, currentMovie);
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_off));
        if (isFavorite) {
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_on));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_favorite_star) {
            if (item.getIcon().getConstantState() == (ContextCompat.getDrawable(this, R.drawable.ic_favorite_off).getConstantState())) {
                boolean success = controller.addFavoriteMovie(this, currentMovie);

                if (success) {
                    Toast.makeText(getBaseContext(), getString(R.string.toast_favorite_success), Toast.LENGTH_LONG).show();
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_on));
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.toast_favorite_failed), Toast.LENGTH_LONG).show();
                }
            } else {
                boolean success = controller.removeFavoriteMovie(this, currentMovie);
                if (success) {
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_off));
                    Toast.makeText(getBaseContext(), getString(R.string.toast_unfavorite_success), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.toast_unfavorite_failed), Toast.LENGTH_LONG).show();
                }
            }
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getShareContent());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getShareContent() {
        String content = "Popular Movies" + "\n";
        content += currentMovie.getTitle() + " ";
        content += VideoHelper.getYoutubeUrl(mTrailersAdapter.getData().get(0).getVideoKey()) + "\n";
        content += "Watch it now.";
        return content;
    }
}
