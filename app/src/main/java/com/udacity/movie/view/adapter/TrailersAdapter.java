package com.udacity.movie.view.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.movie.R;
import com.udacity.movie.model.VideoObject;
import com.udacity.movie.util.helper.ImageHelper;
import com.udacity.movie.util.helper.VideoHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {
    private List<VideoObject> mVideos;

    public TrailersAdapter() {
        mVideos = new ArrayList<>();
    }

    public void setData(List<VideoObject> videos) {
        this.mVideos.clear();
        this.mVideos.addAll(videos);
        notifyDataSetChanged();
    }

    public List<VideoObject> getData() {
        return this.mVideos;
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trailers, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        ImageHelper.setImageResource(holder.itemView.getContext(), VideoHelper.getYoutubeThumbnailUrl(mVideos.get(holder.getAdapterPosition()).getVideoKey()), holder.mTrailersImage);
        holder.mTrailersName.setText(mVideos.get(holder.getAdapterPosition()).getName());
        String videoDescription = mVideos.get(holder.getAdapterPosition()).getType() + " "
                + mVideos.get(holder.getAdapterPosition()).getLanguageCode() + "-"
                + mVideos.get(holder.getAdapterPosition()).getNationalCode() + " "
                + mVideos.get(holder.getAdapterPosition()).getSite();
        holder.mTrailersDescription.setText(videoDescription);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_trailers_layout)
        CardView mTrailersLayout;

        @BindView(R.id.adapter_trailers_image)
        ImageView mTrailersImage;

        @BindView(R.id.adapter_trailers_name)
        TextView mTrailersName;

        @BindView(R.id.adapter_trailers_description)
        TextView mTrailersDescription;

        TrailersViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mTrailersLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideos.get(getAdapterPosition()).getSite().equals(v.getContext().getString(R.string.youtube_video))) {
                        VideoHelper.showingVideos(v.getContext(), VideoHelper.getYoutubeUrl(mVideos.get(getAdapterPosition()).getVideoKey()));
                    } else {
                        Toast.makeText(itemView.getContext(), v.getContext().getString(R.string.video_play_error), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}