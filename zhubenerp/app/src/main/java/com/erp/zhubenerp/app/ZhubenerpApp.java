package com.erp.zhubenerp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.erp.zhubenerp.global.Constant;
import com.erp.zhubenerp.model.UserWrapper;
import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.erp.zhubenerp.global.Constant;
import com.erp.zhubenerp.http.ApiServer;
import com.erp.zhubenerp.http.CommonServer;
import com.erp.zhubenerp.model.UserWrapper;
import com.erp.zhubenerp.utils.ACache;
import com.orhanobut.logger.Logger;

import java.util.Stack;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import com.joanzapata.iconify.Iconify;
/**
 * Created by gubin on 2017/12/29.
 */

public class ZhubenerpApp extends Application {
    private static ZhubenerpApp app;
    private static Stack<Activity> actStack;

    private static ApiServer apiServer;
    private static CommonServer commonServer;

    public static boolean networkAvailable = true;

    public static NetStatus isWifi = NetStatus.WIFI;

    private static int mMainThreadID = -1;

    private static Thread mMainThread;

    private static Handler mMainThreadHandler;

    private static Looper mMainLooper;

    public enum NetStatus {
        WIFI,
        WIFI_NO_INTERNET,
        MOBILE_INTERNET
    }

    private static UserWrapper.User mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mMainThreadID = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();

        Logger.init("ZhubenerpApp").methodCount(3);

        
    }

    public static ZhubenerpApp getAppContext() {
        return app;
    }

    public static ApiServer getApiServer() {
        return apiServer;
    }

    public static CommonServer getCommonServer() {
        return commonServer;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public void add(Activity instance) {
        if(actStack == null)
            actStack = new Stack<Activity>();
        final int index = actStack.lastIndexOf(instance);
        if (index >= 0) {
            actStack.remove(index);
        }
        actStack.push(instance);
    }

    public void remove(Activity instance) {
        if (actStack == null)
            return;
        actStack.remove(instance);
    }

    public static int getMainThreadId() {
        return mMainThreadID;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static UserWrapper.User getUser() {
        if (mUser == null) {
            mUser = (UserWrapper.User) ACache.get(app).getAsObject(Constant.KEY_ACache_UserWrapper);
        }

        return mUser;
    }

    public static void setUser(UserWrapper.User mUserWrapper) {
        ZhubenerpApp.mUser = mUserWrapper;
    }
}
