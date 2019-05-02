
package com.sha.photoviewer.pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.viewpager.widget.ViewPager;


@RestrictTo(RestrictTo.Scope.LIBRARY)
public class MultiTouchViewPager extends ViewPager {

    private boolean isDisallowIntercept;
    private boolean isScrolled = true;

    public MultiTouchViewPager(Context context) {
        super(context);
        setScrollStateListener();
    }

    public MultiTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrollStateListener();
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        isDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (ev.getPointerCount() <= 1 || !isDisallowIntercept)
            return super.dispatchTouchEvent(ev);

        requestDisallowInterceptTouchEvent(false);
        boolean handled = super.dispatchTouchEvent(ev);
        requestDisallowInterceptTouchEvent(true);
        return handled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() > 1) return false;

        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean isScrolled() {
        return isScrolled;
    }

    private void setScrollStateListener() {
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                isScrolled = state == SCROLL_STATE_IDLE;
            }
        });
    }
}