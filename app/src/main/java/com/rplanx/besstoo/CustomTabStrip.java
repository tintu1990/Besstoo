package com.rplanx.besstoo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by soumya on 19/5/17.
 */
public class CustomTabStrip extends PagerSlidingTabStrip {
    private boolean disabled;

    public CustomTabStrip(Context context) {
        super(context);
    }

    public CustomTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return disabled ;
        /*|| super.onInterceptTouchEvent(ev)*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return disabled;
    }
}
