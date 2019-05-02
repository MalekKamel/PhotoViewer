package com.sha.photoviewersample.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.RestrictTo;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by sha on 03/01/17.
 */

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PicassoUtil {

    public interface Callable{
        void call(Bitmap bitmap);
    }

    public static void bitmap(String url, ImageView view, Callable callback) {
        if (url == null) return;

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                callback.call(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        view.setTag(target);
        Picasso.get().load(url).into(target);
    }


}
