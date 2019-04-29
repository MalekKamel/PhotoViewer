package com.sha.photoviewersample;

public enum Option {
    SHOW_STATUS_BAR("Show status bar", false),
    PHOTO_MARGIN("images margin", true),
    CONTAINER_PADDING("container padding", false),
    PHOTO_ROUNDING("images rounding", false),
    SWIPE_TO_DISMISS("swipe-to-dismiss", true),
    ZOOMING("zooming", true),
    SHOW_OVERLAY("show overlay", false),
    RANDOM_BACKGROUND("Random background", false),
    SHOW_IMAGES_INDICATOR("Show images indicator", true);

    public boolean value;
    public String title;

    Option(String title, boolean value) {
        this.value = value;
        this.title = title;
    }

    public static String[] titles(){
        String[] array = new String[values().length];
        int i = 0;
        for (Option v : values()) {
            array[i] = v.title;
            i++;
        }
        return array;
    }

    public static boolean[] getValues(){
        boolean[] array = new boolean[values().length];
        int i = 0;
        for (Option v : values()) {
            array[i] = v.value;
            i++;
        }
        return array;
    }
}
