package com.udacity.movie.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.movie.R;
import com.udacity.movie.model.ReviewObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private List<ReviewObject> mReviews;

    public ReviewsAdapter() {
        mReviews = new ArrayList<>();
    }

    public void setData(List<ReviewObject> movies) {
        this.mReviews.clear();
        this.mReviews.addAll(movies);
        notifyDataSetChanged();
    }

    public List<ReviewObject> getData() {
        return this.mReviews;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reviews, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.mReviewAuthor.setText(mReviews.get(holder.getAdapterPosition()).getAuthor());
        holder.mReviewContent.setText(mReviews.get(holder.getAdapterPosition()).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_reviews_author)
        TextView mReviewAuthor;

        @BindView(R.id.adapter_reviews_content)
        TextView mReviewContent;

        ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}