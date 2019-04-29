package com.sha.photoviewer.adapter;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

class Photo {
    String url;
    boolean isScaled;
    PhotoView view;

    Photo(String url) {
        this.url = url;
    }

    static List<Photo> from(List<String> urls){
        List<Photo> list = new ArrayList<>();
        for (String url : urls)
            list.add(new Photo(url));
        return list;
    }
}
