package com.sha.photoviewer.listener;

/**
 * Called when image selected in viewer
 */
public interface OnPhotoSelectedListener {
    void onSelected(String url, int position);
}
