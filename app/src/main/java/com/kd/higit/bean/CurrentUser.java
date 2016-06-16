package com.kd.higit.bean;

import android.content.Context;

/**
 * Created by KD on 2016/6/16.
 */
public class CurrentUser {
    private static String FILE_NAME = "current_name";
    public static void save(Context context, User user) {
        PerisistenceHelper.saveModel(context, user, FILE_NAME);
    }

    public static User get(Context context) {
        return PerisistenceHelper.loadModel(context, FILE_NAME);
    }

    public static boolean delete(Context context) {
        return PerisistenceHelper.deleteObject(context, FILE_NAME);
    }
}
