package com.willdson.shopifywintershipapplication;

import android.app.Application;

import com.willdson.shopifywintershipapplication.injection.module.AppModule;
import com.willdson.shopifywintershipapplication.injection.NetComponent;
import com.willdson.shopifywintershipapplication.injection.module.NetModule;

/**
 * Created by dwson on 9/24/16.
 */

public class ApplicationController extends Application {

    private String SHOPICRUIT_BASE_URL = "https://shopicruit.myshopify.com/";
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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
}
