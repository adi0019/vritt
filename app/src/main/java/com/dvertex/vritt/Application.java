package com.dvertex.vritt;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class Application extends MultiDexApplication {

    @SuppressLint("StaticFieldLeak")
    private static Context context;


    @Override
    public void onCreate() {
            super.onCreate();

            context = getApplicationContext();
        MultiDex.install(this);

    }

    public static  Context getAppContext(){
        return  context;
    }
}
