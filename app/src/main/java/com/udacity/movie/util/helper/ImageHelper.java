package com.udacity.movie.util.helper;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.movie.R;

/**
 * Created by draconra on 7/31/17.
 */

public class ImageHelper {

    private static final int MOVIE_IMAGE_LIST_WIDTH = 200;

    public static int getColumnsCount(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = activity.getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        return Math.round(dpWidth / MOVIE_IMAGE_LIST_WIDTH);
    }

    public static void setImageResource(Context context, String urlImage, ImageView imageView) {
        Glide.with(context)
                .load(urlImage)
                .dontAnimate()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .into(imageView);
    }
}
