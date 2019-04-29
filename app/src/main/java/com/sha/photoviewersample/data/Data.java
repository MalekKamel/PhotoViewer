package com.sha.photoviewersample.data;


import com.annimon.stream.Stream;

import java.util.Arrays;
import java.util.List;

public final class Data {
    private Data() {
        throw new AssertionError();
    }

    private static final String BASE_PATH = "https://github.com/ShabanKamell/PhotoViewer/blob/master/blob/master/raw/";

    private static String path(String name){
        return BASE_PATH + name + ".jpeg?raw=true";
    }

    public static List<Image> images(){
       return Arrays.asList(
                new Image()
                        .setUrl(path("1"))
                        .setDescription("Description"),
                new Image()
                        .setUrl(path("2"))
                        .setDescription(""),
                new Image()
                        .setUrl(path("3"))
                        .setDescription(""),
                new Image()
                        .setUrl(path("4"))
                        .setDescription(""),
                new Image()
                        .setUrl(path("5"))
                        .setDescription(""),
                new Image()
                        .setUrl(path("6"))
                        .setDescription(""),
                new Image()
                        .setUrl(path("7"))
                        .setDescription(""),
                new Image()
                        .setUrl(path("8"))
                        .setDescription(""),
                new Image()
                        .setUrl(path("9"))
                        .setDescription("")
        );
    }

    public static List<String> urls(){
        return Stream.of(images())
                .map(item -> item.url)
                .toList();
    }


}
