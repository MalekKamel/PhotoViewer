
# PhotoViewer
An image viewer that is simple and customizable with "swipe to dismiss" and "pinch to zoom" 
`PhotoViewer` is Compatible with all of the most popular image processing libraries such as `Glide`, `Picasso` etc.
Based on [PhotoView](https://github.com/chrisbanes/PhotoView) by [chrisbanes](https://github.com/chrisbanes).

## Install
Add this to the **project `build.gradle`** file:
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

And add to the **module `build.gradle`** file:
```gradle
dependencies {
	        implementation 'com.github.ShabanKamell:PhotoViewer:1.2.0'
	}
```

## Simple Usage
```java
  Builder builder = PhotoViewer.build(this, Data.urls(), this::loadImage)
                .startAtIndex(startIndex)
                .setOnDismissListener(
                        () -> Log.d("PhotoViewer", "dismissed")
                );

  private void loadImage(
            @Nullable String url,
            @NonNull ImageView imageView,
            int index,
            @NonNull ProgressBar progressBar
    ){
      Picasso.get().load(url).into(imageView);
    }
```

## Customization
```java
     builder.setPhotoMargin(this, R.dimen.image_margin)
                .setContainerPadding(this, R.dimen.image_margin)
                .setCanSwipeToDismiss(Option.SWIPE_TO_DISMISS.value)
                .setZoomable(Option.ZOOMING.value)
                .setOverlayView(overlay)
                .setOnPhotoSelectedListener(getImageChangeListener())
                .setBackgroundColor(getRandomColor())
                .setOnLongClickListener(v -> {
                    Log.d("PhotoViewer", "long clicked!");
                    return false;
                });
```

### See 'app' module for the full code.

# License

## Apache license 2.0
