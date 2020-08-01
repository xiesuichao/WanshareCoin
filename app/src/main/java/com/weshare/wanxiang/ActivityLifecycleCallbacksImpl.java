package com.weshare.wanxiang;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.weshare.wanxiang.sophix.SophixRestart;

/**
 * Created by Jason on 2018/8/14.
 * 所有activity生命周期方法的回调
 */

public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d("SophixStubApplication", "onActivityPaused");
        SophixRestart.appRestart(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
