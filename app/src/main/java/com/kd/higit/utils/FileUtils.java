package com.kd.higit.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KD on 2016/6/16.
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    public static void write(Context context, String fileName, String content) {
        if (content == null) {
            content = "";
        }

        try {
            FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write(content.getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readInStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readInStream(InputStream in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isExistDataCache(Context context, String file) {
        File data = context.getFileStreamPath(file);
        if (data.exists()) {
            return true;
        }
        return false;
    }
}
