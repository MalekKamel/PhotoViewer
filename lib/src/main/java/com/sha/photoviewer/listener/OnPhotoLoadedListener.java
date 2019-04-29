package com.sha.photoviewer.listener;

import android.graphics.Bitmap;

/**
 * Called when image selected in viewer
 */
public interface OnPhotoLoadedListener {

    void onLoaded(String url, int index, Bitmap bitmap);

}
