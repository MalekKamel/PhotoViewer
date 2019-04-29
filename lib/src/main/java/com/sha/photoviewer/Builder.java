package com.sha.photoviewer;

import android.content.Context;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;

import com.sha.photoviewer.listener.OnDismissListener;
import com.sha.photoviewer.listener.OnPhotoLoadedListener;
import com.sha.photoviewer.listener.OnPhotoSelectedListener;
import com.sha.photoviewer.util.DimenUtil;

import java.util.List;

public class Builder {

    private Options options = new Options();

    /**
     *
     * @param context Context
     * @param photos  List of photos to view
     */
    public Builder(Context context, List<String> photos) {
        options.context = context;
        options.urls = photos;
    }

    /**
     * Set background color res
     *
     * @return this to chain easily
     */
    public Builder setBackgroundColorRes(@ColorRes int color) {
        return this.setBackgroundColor(options.context.getResources().getColor(color));
    }

    /**
     * Set background color
     *
     * @return this to chain easily
     */
    public Builder setBackgroundColor(@ColorInt int color) {
        options.backgroundColor = color;
        return this;
    }

    /**
     * start with photo at index
     * @param index of url
     * @return this
     */
    public Builder startAtIndex(int index) {
        options.startAtIndex = index;
        return this;
    }

    /**
     * get notified when photo is viewed
     *
     * @return this to chain easily
     */
    public Builder setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener) {
        options.onPhotoSelectedListener = onPhotoSelectedListener;
        return this;
    }

    /**
     * get notified when photo is loaded from network
     *
     * @return this to chain easily
     */
    public Builder setOnPhotoLoadedListener(OnPhotoLoadedListener onPhotoLoadedListener) {
        options.onPhotoLoadedListener = onPhotoLoadedListener;
        return this;
    }

    /**
     * Set overlay view
     *
     * @return this to chain easily
     */
    public Builder setOverlayView(View view) {
        options.overlayView = view;
        return this;
    }

    /**
     * Set space between the photos in px.
     *
     * @return this to chain easily
     */
    public Builder setPhotoMarginPx(int marginPixels) {
        options.photoMarginPixels = marginPixels;
        return this;
    }

    /**
     * Set space between the photos using dimension.
     *
     * @return this to chain easily
     */
    public Builder setPhotoMargin(Context context, @DimenRes int dimen) {
        options.photoMarginPixels = Math.round(context.getResources().getDimension(dimen));
        return this;
    }

    /**
     * photo container padding
     *
     * @return this to chain easily
     */
    public Builder setContainerPaddingPx(int start, int top, int end, int bottom) {
        options.containerPaddingPixels = new int[]{start, top, end, bottom};
        return this;
    }

    /**
     * photo container padding
     *
     * @return this to chain easily
     */
    public Builder showPagingIndicator(boolean show) {
        options.showPagingIndicator = show;
        return this;
    }

    /**
     * photo container padding
     *
     * @return this to chain easily
     */
    public Builder setContainerPadding(
            Context context,
            @DimenRes int start,
            @DimenRes int top,
            @DimenRes int end,
            @DimenRes int bottom) {
        setContainerPaddingPx(
                DimenUtil.from(start, context),
                DimenUtil.from(top, context),
                DimenUtil.from(end, context),
                DimenUtil.from(bottom, context)
        );
        return this;
    }

    /**
     * photo container padding
     *
     * @return this to chain easily
     */
    public Builder setContainerPaddingPx(int padding) {
        setContainerPaddingPx(padding, padding, padding, padding);
        return this;
    }

    /**
     * photo container padding
     *
     * @return this to chain easily
     */
    public Builder setContainerPadding(Context context, @DimenRes int padding) {
        int paddingPx = DimenUtil.from(padding, context);
        setContainerPaddingPx(paddingPx, paddingPx, paddingPx, paddingPx);
        return this;
    }

    /**
     * Status bar visibility. By default false.
     *
     * @return this to chain easily
     */
    public Builder showStatusBar(boolean shouldHide) {
        options.showStatusBar = shouldHide;
        return this;
    }

    /**
     *  Zooming. By default is true.
     *
     * @return this to chain easily
     */
    public Builder setZoomable(boolean value) {
        options.isZoomable = value;
        return this;
    }

    /**
     * Swipe to dismiss gesture. By default is true.
     *
     * @return this to chain easily
     */
    public Builder setCanSwipeToDismiss(boolean value) {
        options.canSwipeToDismiss = value;
        return this;
    }

    /**
     * Get notified when dismissed
     *
     * @return this to chain easily
     */
    public Builder setOnDismissListener(OnDismissListener onDismissListener) {
        options.onDismissListener = onDismissListener;
        return this;
    }

    /**
     * Show photos
     */
    public PhotoViewer show() {
        PhotoViewer dialog = new PhotoViewer(options);
        dialog.show();
        return dialog;
    }


}
