package com.rplanx.besstoo.application;

import android.app.Application;

import com.android.volley.toolbox.Volley;

/**
 * Created by soumya on 24/2/17.
 */
public class VolleyApplication extends Application {
    private static VolleyApplication sInstance;
    private com.android.volley.RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }

    public synchronized static VolleyApplication getInstance() {
        return sInstance;
    }

    public com.android.volley.RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
