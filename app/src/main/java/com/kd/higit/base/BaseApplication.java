package com.kd.higit.base;

import android.app.Application;
import android.content.Context;

import com.kd.higit.api.GitHub;
import com.kd.higit.api.MyX509TrustManager;

/**
 * Created by KD on 2016/6/16.
 */
public class BaseApplication extends Application{
    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        MyX509TrustManager.allowAllSSL();
        //Glide来加载图片
        GitHub.init(mBaseApplication);

    }

    public static Context getBaseApplication() {
        return mBaseApplication;
    }
}
