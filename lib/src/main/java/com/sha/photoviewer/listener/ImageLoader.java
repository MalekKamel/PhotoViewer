package com.sha.photoviewer.listener;

import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ImageLoader {
    void load(
            @Nullable String url,
            @NonNull ImageView imageView,
            int index,
            @NonNull ProgressBar progressBar
    );
}
