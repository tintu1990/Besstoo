package com.rplanx.besstoo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by soumya on 19/5/17.
 */
public class CustomViewpager extends ViewPager {
    private boolean disabled;

    public CustomViewpager(Context context) {
        super(context);
    }

    public CustomViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return  disabled && super.onInterceptTouchEvent(event) ;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return  disabled && super.onTouchEvent(event) ;

    }
}
