package com.udacity.movie.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.movie.R;
import com.udacity.movie.api.ApiMovie;
import com.udacity.movie.model.MovieObject;
import com.udacity.movie.util.helper.ImageHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesViewHolder> {

    private Context context;
    private ArrayList<MovieObject> movies;
    private static MovieAdapterOnClickHandler mListener;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }


    public List<MovieObject> getData() {
        return this.movies;
    }


    public void setData(List<MovieObject> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_movie, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {
        ImageHelper.setImageResource(context, ApiMovie.THUMBNAIL + movies.get(position).getPosterPath(), holder.ivMoviePoster);
    }

    public void setOnItemClickListener(MovieAdapterOnClickHandler listener) {
        mListener = listener;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieObject selectedMovie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.iv_poster)
        ImageView ivMoviePoster;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieObject selectedMovie = movies.get(adapterPosition);
            mListener.onClick(selectedMovie);
        }
    }
}