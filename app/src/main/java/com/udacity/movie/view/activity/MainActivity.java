package com.udacity.movie.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.movie.AppController;
import com.udacity.movie.R;
import com.udacity.movie.controller.ControllerFactory;
import com.udacity.movie.controller.HomeController;
import com.udacity.movie.event.NetworkErrorEvent;
import com.udacity.movie.event.home.HomeEvent;
import com.udacity.movie.model.MovieObject;
import com.udacity.movie.util.Constant;
import com.udacity.movie.util.helper.ImageHelper;
import com.udacity.movie.view.adapter.MovieAdapter;
import com.udacity.movie.view.core.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.pr)
    ProgressBar pr;
    @BindView(R.id.tv_status_error)
    TextView tvStatusError;

    private HomeController controller;
    private MovieAdapter mAdapter;
    private Constant.MOVIE_LIST_TITLE movieListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        controller = ControllerFactory.homeController();

        initView();

        if (savedInstanceState != null) {
            List<MovieObject> movieList = Arrays.asList(AppController.getInstance().gson().fromJson(savedInstanceState.getString(Constant.EXTRA_MOVIE_LIST), MovieObject[].class));
            mAdapter.setData(movieList);

            getSupportActionBar().setTitle(savedInstanceState.getString(Constant.EXTRA_TITLE));
            return;
        }

        setPopularMovies();
    }

    private void initView() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, ImageHelper.getColumnsCount(this));
        rvMovie.setLayoutManager(layoutManager);
        rvMovie.setHasFixedSize(true);

        mAdapter = new MovieAdapter(this);
        rvMovie.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MovieAdapter.MovieAdapterOnClickHandler() {
            @Override
            public void onClick(MovieObject selectedMovie) {
                Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                intent.putExtra(Constant.MOVIE_KEY, selectedMovie);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                setPopularMovies();
                break;
            case R.id.action_top_rated:
                setTopRatedMovies();
                break;
            case R.id.action_favorite:
                setFavoriteMovies();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTopRatedMovies() {
        getSupportActionBar().setTitle(getString(R.string.top_rated_movies));
        movieListTitle = Constant.MOVIE_LIST_TITLE.TOP_RATED;
        controller.getTopRatedMovies();
        pr.setVisibility(View.VISIBLE);
    }

    private void setPopularMovies() {
        getSupportActionBar().setTitle(getString(R.string.popular_movies));
        movieListTitle = Constant.MOVIE_LIST_TITLE.POPULAR;
        controller.getPopularMovies();
        pr.setVisibility(View.VISIBLE);
    }

    private void setFavoriteMovies() {
        getSupportActionBar().setTitle(getString(R.string.favorite_movies));
        movieListTitle = Constant.MOVIE_LIST_TITLE.FAVORITE;
        controller.getFavoriteMovies(this);
        pr.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieList(HomeEvent event) {
        pr.setVisibility(View.GONE);
        if (event.isSuccess()) {
            mAdapter.setData(event.getMovieObjects());
            rvMovie.scrollToPosition(0);
        } else {
            tvStatusError.setVisibility(View.VISIBLE);
            tvStatusError.setText(event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieListError(NetworkErrorEvent event) {
        pr.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        List<MovieObject> movieList = mAdapter.getData();
        String movieListJson = AppController.getInstance().gson().toJson(movieList);
        outState.putString(Constant.EXTRA_MOVIE_LIST, movieListJson);
        outState.putString(Constant.EXTRA_TITLE, getSupportActionBar().getTitle().toString());
    }
}