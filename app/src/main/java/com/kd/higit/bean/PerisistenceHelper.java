package com.kd.higit.bean;

import android.content.Context;

import com.kd.higit.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by KD on 2016/6/16.
 */
public class PerisistenceHelper {
    private static final String TAG = PerisistenceHelper.class.getSimpleName();

    public static boolean saveObject(Context context, Serializable obj, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public static boolean deleteObject(Context context, String file) {
        try {
            return context.deleteFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }

    public static <T extends TModel> boolean saveModel(Context context, T obj, String file) {
        return saveObject(context, obj, file);
    }

    public static Serializable loadObject(Context context, String file) {
        if (!FileUtils.isExistDataCache(context, file)) {
            return null;
        }
        FileInputStream fInput = null;
        ObjectInputStream oInput = null;
        try {
            fInput = context.openFileInput(file);
            oInput = new ObjectInputStream(fInput);
            Object obj = oInput.readObject();
            return (Serializable) obj;
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                oInput.close();
            } catch (Exception e) {
            }
            try {
                fInput.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static <T extends TModel> T loadModel(Context context, String file) {
        return (T) loadObject(context, file);
    }

    public static <T extends TModel> ArrayList<T> loadModeList(Context context, String file) {
        return (ArrayList<T>) loadObject(context, file);
    }
}
