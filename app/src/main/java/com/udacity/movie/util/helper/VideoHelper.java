package com.udacity.movie.util.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.udacity.movie.api.ApiMovie;

/**
 * Created by draconra on 7/31/17.
 */

public class VideoHelper {

    public static void showingVideos(Context context, String videoUrl) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));

        // Verify that the intent will resolve to an activity
        if (i.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(i);
        }
    }

    public static String getYoutubeUrl(String key) {
        return ApiMovie.BASE_URL_VIDEO_YOUTUBE + "?v=" + key;
    }

    public static String getYoutubeThumbnailUrl(String key) {
        return ApiMovie.BASE_URL_IMAGE_YOUTUBE + key + ApiMovie.BASE_URL_IMAGE_YOUTUBE_THUMBNAIL;
    }
}
