package com.willdson.shopifywintershipapplication.injection;

import com.willdson.shopifywintershipapplication.MainHandler;
import com.willdson.shopifywintershipapplication.injection.module.AppModule;
import com.willdson.shopifywintershipapplication.injection.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dwson on 9/24/16.
 */

@Singleton
@Component(modules = { AppModule.class, NetModule.class })
public interface NetComponent {
    void inject(MainHandler handler);
}
