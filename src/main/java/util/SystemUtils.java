package util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

/**
 * Created by private on 16/7/2.
 */
public class SystemUtils {

    /**
     * 显示或隐藏软键盘
     *
     * @param context
     * @param editText
     * @param shouldShow
     */
    public static void toggleSoftInput(Context context, EditText editText, boolean shouldShow) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (shouldShow) {
            im.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        } else {
            im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * get version name
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * get version code
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getColor(Context context, int resColorId) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, resColorId);
        } else {
            return context.getResources().getColor(resColorId);
        }
    }

    /**
     * 跳转到拨号页面
     *
     * @param context
     * @param tel
     */
    public static void makeCall(Context context, String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        if (isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        } else {
            ToastHelper.showShort("请检查您的手机是否有拨号应用");
        }
    }

    /**
     * 开启文件管理器
     *
     * @param activity
     * @param requestCode
     */
    public static void startFileManager(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (isIntentAvailable(activity, intent)) {
            activity.startActivityForResult(Intent.createChooser(intent, "请选择文件!"), requestCode);
        } else {
            ToastHelper.showShort("请安装文件管理器");
        }
    }

    /**
     * 检查是否存在相应的Intent
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;
    }

}
