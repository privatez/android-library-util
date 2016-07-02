package util;

import android.util.Log;

/**
 * Created by private on 16/7/2.
 */
public class LogHelper {

    public static final String TAG = "lhy";

    public static boolean isDebug = true;

    public static void log(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void log(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }
}
