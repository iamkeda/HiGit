package com.kd.higit.utils;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.kd.higit.ui.AuthorizeActivity;
import com.kd.higit.ui.BaseSwipeActivity;

/**
 * Created by KD on 2016/6/18.
 */
public class MessageUtils {
    public static void showErrorMessage(Context context, String errorString) {
        if (errorString == "Requires authentication") {
            context.startActivity(new Intent(context, AuthorizeActivity.class));
        } else {
            if (context instanceof BaseSwipeActivity) {
                Snackbar.make(((AuthorizeActivity)context).getCoordinatorLayout(), errorString, Snackbar.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
