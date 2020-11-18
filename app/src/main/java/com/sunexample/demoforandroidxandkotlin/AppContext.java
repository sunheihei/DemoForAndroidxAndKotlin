package com.sunexample.demoforandroidxandkotlin;

import android.app.Application;
import android.content.Context;


public class AppContext extends Application {
    private static final String TAG = "AppContext";
    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;




      /*  if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...*/

    }

    public static Context getAppContext() {
        return mContext;
    }
}
