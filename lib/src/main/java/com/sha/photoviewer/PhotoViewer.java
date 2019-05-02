
package com.sha.photoviewer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sha.photoviewer.listener.ImageLoader;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sha on 1/06/19.
 */
public class PhotoViewer {

    private Options options;

    PhotoViewer(Options options) {
        this.options = options;
    }

    /**
     * Build viewer
     * @param context Context
     * @param image single image
     * @return {@link Builder}
     */
    public static Builder build(
            Context context,
            String image,
            @NonNull ImageLoader imageLoader
    ){
        return build(context, Arrays.asList(image), imageLoader);
    }

    /**
     * Build viewer
     * @param context Context
     * @param images array
     * @return {@link Builder}
     */
    public static Builder build(
            Context context,
            String[] images,
            @NonNull ImageLoader imageLoader
    ){
        return build(context, Arrays.asList(images), imageLoader);
    }

    /**
     * Build viewer
     * @param context Context
     * @param images List
     * @return {@link Builder}
     */
    public static Builder build(
            @NonNull Context context,
            @Nullable List<String> images,
            @NonNull ImageLoader imageLoader
    ){
        return new Builder(context, images, imageLoader);
    }

    /**
     * Displays images
     */
    void show() {
        if (options.urls == null || options.urls.isEmpty()){
            Log.w(PhotoViewer.class.getSimpleName(), "urls can't be empty or null! Viewer ignored.");
            return;
        }

        PhotosDialog dialog = new PhotosDialog(options);
        dialog.show();
    }

}
