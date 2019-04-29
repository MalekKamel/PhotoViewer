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
                        .setDescription(" Macro Photography of Tree"),
                new Image()
                        .setUrl(path("2"))
                        .setDescription("Heart Red Leafed Tree on Red Field"),
                new Image()
                        .setUrl(path("3"))
                        .setDescription("Selective Focus Photography White Daisy Flower"),
                new Image()
                        .setUrl(path("4"))
                        .setDescription("Red and Black Bird on Red Flowers"),
                new Image()
                        .setUrl(path("5"))
                        .setDescription("Green Tree Plant Leaves"),
                new Image()
                        .setUrl(path("6"))
                        .setDescription("Body of Water Under Blue Sky"),
                new Image()
                        .setUrl(path("7"))
                        .setDescription("Seaside"),
                new Image()
                        .setUrl(path("8"))
                        .setDescription("Close-up of Tree Against Sky"),
                new Image()
                        .setUrl(path("9"))
                        .setDescription("Gray Bridge and Trees")
        );
    }

    public static List<String> urls(){
        return Stream.of(images())
                .map(item -> item.url)
                .toList();
    }


}
