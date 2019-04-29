package com.sha.frescoimageviewersample;

public enum Option {
    SHOW_STATUS_BAR(false),
    PHOTO_MARGIN(true),
    CONTAINER_PADDING(false),
    PHOTO_ROUNDING(false),
    SWIPE_TO_DISMISS(true),
    ZOOMING(true),
    SHOW_OVERLAY(false),
    RANDOM_BACKGROUND(false),
    POST_PROCESSING(false);

    public boolean value;

    Option(boolean value) {
        this.value = value;
    }

    public static boolean[] toArray(){
        boolean[] array = new boolean[values().length];
        int i = 0;
        for (Option v : values()) {
            array[i] = v.value;
            i++;
        }
        return array;
    }
}
