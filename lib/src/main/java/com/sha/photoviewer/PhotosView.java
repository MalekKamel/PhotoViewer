package com.sha.photoviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager.widget.ViewPager;

import com.sha.photoviewer.adapter.PhotosAdapter;
import com.sha.photoviewer.listener.SwipeDirectionDetector;
import com.sha.photoviewer.listener.SwipeToDismissListener;
import com.sha.photoviewer.pager.MultiTouchViewPager;
import com.sha.photoviewer.util.AnimationUtil;

class PhotosView extends RelativeLayout{

    private View backgroundView;
    private MultiTouchViewPager pager;
    private PhotosAdapter adapter;
    private SwipeDirectionDetector directionDetector;
    private ScaleGestureDetector scaleDetector;
    private GestureDetectorCompat gestureDetector;

    private ViewGroup dismissContainer;
    private SwipeToDismissListener swipeDismissListener;

    private SwipeDirectionDetector.Direction direction;

    private boolean wasScaled;
    private boolean isOverlayWasClicked;
    private Options options;

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

        backgroundView = findViewById(R.id.backgroundView);

        backgroundView.setBackgroundColor(options.backgroundColor);

        setupSwipe();
        setupScaleDetector();
        setupGestureDetector();
        setupOverlay();
        setupPager();
        setupImageSelectedListener();
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
        dismissContainer.addView(options.overlayView);
    }

    private void setupSwipe() {
        swipeDismissListener = new SwipeToDismissListener(
                findViewById(R.id.dismissView),
                () -> options.dialog.dismiss(),
                (translationY, translationLimit) -> {
                    float alpha = 1.0f - (1.0f / translationLimit / 4) * Math.abs(translationY);

                    backgroundView.setAlpha(alpha);

                    if (options.overlayView != null)
                        options.overlayView.setAlpha(alpha);
                });

        dismissContainer.setOnTouchListener(swipeDismissListener);

        directionDetector = new SwipeDirectionDetector(getContext()) {
            @Override
            public void onDirectionDetected(Direction direction) {
                PhotosView.this.direction = direction;
            }
        };
    }

    private void setupGestureDetector() {
        gestureDetector = new GestureDetectorCompat(
                getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (pager.isScrolled()) {
                            onClick(e, isOverlayWasClicked);
                        }
                        return false;
                    }
                });
    }

    private void setupScaleDetector() {
        scaleDetector = new ScaleGestureDetector(getContext(),
                new ScaleGestureDetector.SimpleOnScaleGestureListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        onUpDownEvent(event);

        if (direction == null) {
            if (scaleDetector.isInProgress() || event.getPointerCount() > 1) {
                wasScaled = true;
                return pager.dispatchTouchEvent(event);
            }
        }

        if (!adapter.isScaled(pager.getCurrentItem())) {
            directionDetector.onTouchEvent(event);
            if (direction != null) {
                switch (direction) {
                    case UP:
                    case DOWN:
                        if (options.canSwipeToDismiss && !wasScaled && pager.isScrolled()) {
                            return swipeDismissListener.onTouch(dismissContainer, event);
                        } else break;
                    case LEFT:
                    case RIGHT:
                        return pager.dispatchTouchEvent(event);
                }
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
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

    private void onUpDownEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onActionUp(event);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onActionDown(event);
        }

        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
    }

    private void onActionDown(MotionEvent event) {
        direction = null;
        wasScaled = false;
        pager.dispatchTouchEvent(event);
        swipeDismissListener.onTouch(dismissContainer, event);
        isOverlayWasClicked = dispatchOverlayTouch(event);
    }

    private void onActionUp(MotionEvent event) {
        swipeDismissListener.onTouch(dismissContainer, event);
        pager.dispatchTouchEvent(event);
        isOverlayWasClicked = dispatchOverlayTouch(event);
    }

    private void onClick(MotionEvent event, boolean isOverlayWasClicked) {
        if (options.overlayView == null || isOverlayWasClicked)  return;
        AnimationUtil.visibility(options.overlayView);
        super.dispatchTouchEvent(event);
    }

    private boolean dispatchOverlayTouch(MotionEvent event) {
        return options.overlayView != null
                && options.overlayView.getVisibility() == VISIBLE
                && options.overlayView.dispatchTouchEvent(event);
    }

}
