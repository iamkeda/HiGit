package com.kd.higit.utils;

import android.util.Log;

/**
 * Created by KD on 2016/6/18.
 */
public class KLog {
    final static int DEBUG_VERBOSE = 6;
    final static int DEBUG_DEBUG = 5;
    final static int DEBUG_INFO = 4;
    final static int DEBUG_WARN = 3;
    final static int DEBUG_ERROR = 2;
    final static int DEBUG_ASSERT = 1;

    static int debugLevel = 5;
    private static final String TAG = "keda";
    public static void v(String msg){
        if (debugLevel >= DEBUG_VERBOSE){
            Log.v(TAG, msg);
        }
    }
    public static void d(String msg){
        if (debugLevel >= DEBUG_DEBUG){
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg){
        if (debugLevel >= DEBUG_INFO){
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg){
        if (debugLevel >= DEBUG_WARN){
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg){
        if (debugLevel >= DEBUG_ERROR){
            Log.i(TAG, msg);
        }
    }

    public static void a(String msg){
        if (debugLevel >= DEBUG_ASSERT){
            Log.i(TAG, msg);
        }
    }

}
