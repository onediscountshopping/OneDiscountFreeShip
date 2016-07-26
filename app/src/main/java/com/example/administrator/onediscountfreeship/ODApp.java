package com.example.administrator.onediscountfreeship;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yhy on 2016/7/26.
 */
public class ODApp extends Application {

    public static RequestQueue queue;//volley请求队列

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }
}
