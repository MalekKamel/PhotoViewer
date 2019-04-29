package com.sha.photoviewer.util;

import android.content.Context;

public class DimenUtil {

    public static int from(int i, Context context){
        return Math.round(context.getResources().getDimension(i));
    }

}
