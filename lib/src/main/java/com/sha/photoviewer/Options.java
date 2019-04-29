package com.sha.photoviewer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;

import com.sha.photoviewer.listener.OnDismissListener;
import com.sha.photoviewer.listener.OnPhotoLoadedListener;
import com.sha.photoviewer.listener.OnPhotoSelectedListener;

import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class Options {

    public Context context;
    public List<String> urls;
    @ColorInt
    public int backgroundColor = Color.BLACK;
    public int startAtIndex;
    public boolean showImagesIndicator = true;

    public OnPhotoSelectedListener onPhotoSelectedListener;
    public OnDismissListener onDismissListener;
    public OnPhotoLoadedListener onPhotoLoadedListener;

    public View overlayView;
    public int photoMarginPixels;
    public int[] containerPaddingPixels = new int[4];
    public boolean showStatusBar = false;
    public boolean isZoomable = true;
    public boolean canSwipeToDismiss = true;
    public PhotosDialog dialog;

    @StyleRes
    int dialogStyle(){
        return showStatusBar ?
                android.R.style.Theme_Translucent_NoTitleBar :
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;
    }
}
