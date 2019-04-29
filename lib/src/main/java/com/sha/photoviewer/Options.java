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
    int backgroundColor = Color.BLACK;
    int startAtIndex;

    OnPhotoSelectedListener onPhotoSelectedListener;
    OnDismissListener onDismissListener;
    public OnPhotoLoadedListener onPhotoLoadedListener;

    View overlayView;
    int photoMarginPixels;
    int[] containerPaddingPixels = new int[4];
    boolean showStatusBar = false;
    public boolean isZoomable = true;
    boolean canSwipeToDismiss = true;
    PhotosDialog dialog;

    @StyleRes
    int dialogStyle(){
        return showStatusBar ?
                android.R.style.Theme_Translucent_NoTitleBar :
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;
    }
}
