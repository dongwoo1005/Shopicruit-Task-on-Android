package com.willdson.shopifywintershipapplication;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.willdson.shopifywintershipapplication.injection.DaggerNetComponent;
import com.willdson.shopifywintershipapplication.injection.NetComponent;
import com.willdson.shopifywintershipapplication.injection.module.AppModule;
import com.willdson.shopifywintershipapplication.injection.module.NetModule;

/**
 * Created by dwson on 9/24/16.
 */

public class MyApp extends Application {
    private String SHOPICRUIT_BASE_URL = "https://shopicruit.myshopify.com/";
    private NetComponent mNetComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mContext = this;
        Stetho.initializeWithDefaults(this);
    }

    public NetComponent getNetComponent() {
        if (mNetComponent == null) {
            mNetComponent = DaggerNetComponent.builder()
                    .appModule(new AppModule(this))
                    .netModule(new NetModule(SHOPICRUIT_BASE_URL))
                    .build();
        }
        return mNetComponent;
    }

    public static Context getContext() {
        return mContext;
    }
}
