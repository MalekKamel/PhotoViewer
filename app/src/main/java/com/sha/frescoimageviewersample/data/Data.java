package com.sha.frescoimageviewersample.data;


import com.annimon.stream.Stream;

import java.util.Arrays;
import java.util.List;

public final class Data {
    private Data() {
        throw new AssertionError();
    }

    private static final String BASE_PATH = "";

    public static List<Image> images(){
       return Arrays.asList(
                new Image()
                        .setUrl("https://images.pexels.com/photos/255441/pexels-photo-255441.jpeg?cs=srgb&dl=art-background-blur-255441.jpg&fm=jpg")
                        .setDescription(""),
                new Image()
                        .setUrl("https://images.pexels.com/photos/35598/peacock-bird-colorful-blue.jpg?cs=srgb&dl=animal-blue-colorful-35598.jpg&fm=jpg")
                        .setDescription(""),
                new Image()
                        .setUrl("https://images.pexels.com/photos/39629/tiger-tiger-baby-tigerfamile-young-39629.jpeg?cs=srgb&dl=animal-photography-animals-cold-39629.jpg&fm=jpg")
                        .setDescription(""),
                new Image()
                        .setUrl("https://images.pexels.com/photos/459301/pexels-photo-459301.jpeg?cs=srgb&dl=art-artistic-background-459301.jpg&fm=jpg")
                        .setDescription(""),
                new Image()
                        .setUrl("https://images.pexels.com/photos/1173777/pexels-photo-1173777.jpeg?cs=srgb&dl=aerial-aerial-photography-aerial-shot-1173777.jpg&fm=jpg")
                        .setDescription(""),
                new Image()
                        .setUrl("https://images.pexels.com/photos/258109/pexels-photo-258109.jpeg?cs=srgb&dl=backlit-beach-color-258109.jpg&fm=jpg")
                        .setDescription(""),
                new Image()
                        .setUrl("https://images.pexels.com/photos/1591447/pexels-photo-1591447.jpeg?cs=srgb&dl=color-depth-of-field-environment-1591447.jpg&fm=jpg")
                        .setDescription(""),
                new Image()
                        .setUrl("https://images.pexels.com/photos/572861/pexels-photo-572861.jpeg?cs=srgb&dl=animal-animal-photography-blur-572861.jpg&fm=jpg")
                        .setDescription("")
        );
    }

    public static List<String> urls(){
        return Stream.of(images())
                .map(item -> item.url)
                .toList();
    }


}
