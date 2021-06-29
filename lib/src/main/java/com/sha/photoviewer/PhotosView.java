package com.sha.photoviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.RestrictTo;
import androidx.viewpager.widget.ViewPager;

import com.sha.photoviewer.adapter.PhotosAdapter;
import com.sha.photoviewer.pager.MultiTouchViewPager;
import com.sha.photoviewer.touch.TouchHandler;

import me.relex.circleindicator.CircleIndicator;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PhotosView extends RelativeLayout {

    private MultiTouchViewPager pager;
    private PhotosAdapter adapter;
    private ViewGroup dismissContainer;
    private Options options;
    private TouchHandler touchHandler;


    public PhotosView(Options options) {
        super(options.context);
        this.options = options;
        setup();
    }

    public PhotosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public PhotosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.photos_view, this);

        dismissContainer = findViewById(R.id.container);

        View backgroundView = findViewById(R.id.backgroundView);
        backgroundView.setBackgroundColor(options.backgroundColor);

        setupOverlay();
        setupPager();
        setupImageSelectedListener();

        touchHandler = new TouchHandler(
                new TouchHandler.Args()
                        .setOptions(options)
                        .setContext(getContext())
                        .setPager(pager)
                        .setDismissView(findViewById(R.id.dismissView))
                        .setDismissContainer(dismissContainer)
                        .setBackgroundView(backgroundView)
                        .setAdapter(adapter)
        );
    }

    public void setupPager() {
        pager = findViewById(R.id.pager);

        pager.setPageMargin(options.photoMarginPixels);

        pager.setPadding(
                options.containerPaddingPixels[0],
                options.containerPaddingPixels[1],
                options.containerPaddingPixels[2],
                options.containerPaddingPixels[3]
        );

        adapter = new PhotosAdapter(options);
        pager.setAdapter(adapter);
        pager.setCurrentItem(options.startAtIndex);

        if (options.showPagingIndicator) {
            CircleIndicator indicator = findViewById(R.id.indicator);
            indicator.setViewPager(pager);
            pager.getAdapter().registerDataSetObserver(indicator.getDataSetObserver());
        }
    }


    public void setupImageSelectedListener() {
        if (options.onPhotoSelectedListener == null) return;

        ViewPager.SimpleOnPageChangeListener listener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                options.onPhotoSelectedListener.onSelected(getUrl(), position);
            }
        };

        pager.clearOnPageChangeListeners();
        pager.addOnPageChangeListener(listener);
        listener.onPageSelected(pager.getCurrentItem());
    }

    private void setupOverlay() {
        if (options.overlayView == null) return;
        ViewGroup root = findViewById(R.id.overlayView);
        root.addView(options.overlayView);
        root.bringChildToFront(options.overlayView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        touchHandler.handle(event, () -> super.dispatchTouchEvent(event));
        return true;
    }

    public void resetScale() {
        adapter.resetScale(pager.getCurrentItem());
    }

    public boolean isScaled() {
        return adapter.isScaled(pager.getCurrentItem());
    }

    public String getUrl() {
        return adapter.getUrl(pager.getCurrentItem());
    }


}
