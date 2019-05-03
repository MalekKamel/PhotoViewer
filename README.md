
# PhotoViewer
An image viewer that is simple and customizable with "swipe to dismiss" and "pinch to zoom" 
`PhotoViewer` is Compatible with all of the popular image processing libraries such as `Glide`, `Picasso` etc.
Based on [PhotoView](https://github.com/chrisbanes/PhotoView) by [chrisbanes](https://github.com/chrisbanes).

![alt tag](https://github.com/ShabanKamell/PhotoViewer/blob/master/blob/master/raw/demo.gif?raw=true)

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
  PhotoViewer.build(this, Data.urls(), this::loadImage).show();

  private void loadImage(
            @Nullable String url,
            @NonNull ImageView imageView,
            int index,
            @NonNull ProgressBar progressBar
    ){
      Picasso.get().load(url).into(imageView);
      // Or Glide...etc
      // GlideApp.with(context)
      //   .load(url != null ? url : "")
      //   .into(this)
    }
```

## Customization
```java
     builder.setPhotoMargin(this, R.dimen.image_margin)
                .startAtIndex(startIndex)
                .setContainerPadding(this, R.dimen.image_margin)
                .setCanSwipeToDismiss(Option.SWIPE_TO_DISMISS.value)
                .setZoomable(Option.ZOOMING.value)
                .setOverlayView(overlay)
                .setOnPhotoSelectedListener(getImageChangeListener())
                .setBackgroundColor(getRandomColor())
                .setOnLongClickListener(v -> {
                    Log.d("PhotoViewer", "long clicked!");
                    return false;
                })
		.setOnDismissListener(
                        () -> Log.d("PhotoViewer", "dismissed")
                );
```

### See 'app' module for the full code.

### License

#### Apache license 2.0

### Credit
 [FrescoImageViewer](https://github.com/stfalcon-studio/FrescoImageViewer).
