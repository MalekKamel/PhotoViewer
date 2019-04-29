package com.sha.photoviewer.touch;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.RestrictTo;
import androidx.core.view.GestureDetectorCompat;

import com.sha.photoviewer.Options;
import com.sha.photoviewer.adapter.PhotosAdapter;
import com.sha.photoviewer.listener.SwipeDirectionDetector;
import com.sha.photoviewer.listener.SwipeToDismissListener;
import com.sha.photoviewer.pager.MultiTouchViewPager;
import com.sha.photoviewer.util.AnimationUtil;
import com.sha.photoviewer.util.Procedure;

import static android.view.View.VISIBLE;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class TouchHandler {

    private SwipeDirectionDetector directionDetector;
    private ScaleGestureDetector scaleDetector;
    private GestureDetectorCompat gestureDetector;
    private SwipeDirectionDetector.Direction direction = SwipeDirectionDetector.Direction.NOT_DETECTED;
    private boolean wasScaled;
    private boolean isOverlayWasClicked;
    private SwipeToDismissListener swipeDismissListener;
    private Args args;
    private enum TouchAction {
        SCALE_ON_TAP,
        SWIPE_TO_DISMISS
    }

    public static class Args {
        Options options;
        Context context;
        MultiTouchViewPager pager;
        View dismissView;
        View dismissContainer;
        View backgroundView;
        PhotosAdapter adapter;

        public Args setOptions(Options options) {
            this.options = options;
            return this;
        }

        public Args setContext(Context context) {
            this.context = context;
            return this;
        }

        public Args setPager(MultiTouchViewPager pager) {
            this.pager = pager;
            return this;
        }

        public Args setDismissView(View dismissView) {
            this.dismissView = dismissView;
            return this;
        }

        public Args setBackgroundView(View backgroundView) {
            this.backgroundView = backgroundView;
            return this;
        }

        public Args setDismissContainer(View dismissContainer) {
            this.dismissContainer = dismissContainer;
            return this;
        }

        public Args setAdapter(PhotosAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public TouchHandler(Args args){
        this.args = args;
        setupSwipe();
        setupScaleDetector();
        setupGestureDetector();
    }

    private void setupSwipe() {
        swipeDismissListener = new SwipeToDismissListener(
                args.dismissView,
                () -> args.options.dialog.dismiss(),
                (translationY, translationLimit) -> {
                    float alpha = 1.0f - (1.0f / translationLimit / 4) * Math.abs(translationY);

                    args.backgroundView.setAlpha(alpha);

                    if (args.options.overlayView != null)
                        args.options.overlayView.setAlpha(alpha);
                });

        args.dismissContainer.setOnTouchListener(swipeDismissListener);

        directionDetector = new SwipeDirectionDetector(args.context) {
            @Override
            public void onDirectionDetected(Direction direction) {
                TouchHandler.this.direction = direction;
            }
        };
    }

    private void setupGestureDetector() {
        gestureDetector = new GestureDetectorCompat(
                args.context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (!args.pager.isScrolled()
                                || args.options.overlayView == null
                                || isOverlayWasClicked)
                            return false;

                        AnimationUtil.visibility(args.options.overlayView);

                        return false;
                    }
                });
    }

    private void setupScaleDetector() {
        scaleDetector = new ScaleGestureDetector(args.context,
                new ScaleGestureDetector.SimpleOnScaleGestureListener());
    }

    public void handle(MotionEvent event, Procedure superDelegate) {

        onUpDownEvent(event);
        directionDetector.onTouchEvent(event);

        switch (direction){
            case NOT_DETECTED:
                 handleTouchAction(TouchAction.SCALE_ON_TAP, event, superDelegate);
                 break;

            case UP:
            case DOWN:
                if (args.options.canSwipeToDismiss && !wasScaled && args.pager.isScrolled()){
                    handleTouchAction(TouchAction.SWIPE_TO_DISMISS, event, superDelegate);
                    return;
                }
                superDelegate.run();
                break;

            case LEFT:
            case RIGHT:
                // nothing, just delegate to super
                break;

        }

        superDelegate.run();
    }

    private void handleTouchAction(TouchAction action, MotionEvent event, Procedure superDelegate) {
        switch (action) {

            case SCALE_ON_TAP:

                if (scaleDetector.isInProgress() || event.getPointerCount() > 1){
                    wasScaled = true;
                    // delegate st pager to handle scale
                    args.pager.dispatchTouchEvent(event);
                    return;
                }

                superDelegate.run();
                break;

            case SWIPE_TO_DISMISS:
                // if is scaled, can't dismiss
                if (args.adapter.isScaled(args.pager.getCurrentItem())){
                    superDelegate.run();
                    return;
                }

                // delegate to dismiss listener
                swipeDismissListener.onTouch(args.dismissContainer, event);
                break;
        }
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
        direction = SwipeDirectionDetector.Direction.NOT_DETECTED;
        wasScaled = false;
        args.pager.dispatchTouchEvent(event);
        swipeDismissListener.onTouch(args.dismissContainer, event);
        isOverlayWasClicked = dispatchOverlayTouch(event);
    }

    private void onActionUp(MotionEvent event) {
        swipeDismissListener.onTouch(args.dismissContainer, event);
        args.pager.dispatchTouchEvent(event);
        isOverlayWasClicked = dispatchOverlayTouch(event);
    }

    private boolean dispatchOverlayTouch(MotionEvent event) {
        return args.options.overlayView != null
                && args.options.overlayView.getVisibility() == VISIBLE
                && args.options.overlayView.dispatchTouchEvent(event);
    }


}
