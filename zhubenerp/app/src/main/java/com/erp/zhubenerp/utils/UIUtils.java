package com.erp.zhubenerp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.erp.zhubenerp.app.ZhubenerpApp;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by gubin on 2018/1/3.
 */

public class UIUtils {

    private static final String TAG = "UIUtils";

    public static Context getContext() {
        return ZhubenerpApp.getAppContext();
    }

    public static Thread getMainThread() {
        return ZhubenerpApp.getMainThread();
    }

    public static long getMainThreadId() {
        return ZhubenerpApp.getMainThreadId();
    }

    public static Handler getHandler() {
        Looper mainLooper = ZhubenerpApp.getMainThreadLooper();
        Handler handler = new Handler(mainLooper);
        return handler;
    }

    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    public static void hideInputMethodManager(Activity context) {
        if (context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String getUrl(Map<String,String> params) {
        int index = 0;
        StringBuffer sb = new StringBuffer();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = params.get(key);
            if (index == 0) {
                sb.append("?").append(key).append("=").append(value);
            } else {
                sb.append("&").append(key).append("=").append(value);
            }
            index++;
        }
        return sb.toString();
    }
}
