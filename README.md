
# PhotoViewer
Simple and customizable image viewer with "swipe to dismiss" and "pinch to zoom" .
Compatible with any image processing library such as `Glide`, `Picasso` etc.
Based on [PhotoView](https://github.com/chrisbanes/PhotoView) by [chrisbanes](https://github.com/chrisbanes).

[![](https://jitpack.io/v/ShabanKamell/PhotoViewer.svg)](https://jitpack.io/#ShabanKamell/PhotoViewer)


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

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

### Credit
 [FrescoImageViewer](https://github.com/stfalcon-studio/FrescoImageViewer).
