package com.willdson.shopifywintershipapplication.injection.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.willdson.shopifywintershipapplication.data.ShopifyAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dwson on 9/24/16.
 */

@Module
public class NetModule {

    String mBaseUrl;

    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache providesOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson providesGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Cache cache) {

        OkHttpClient client = new OkHttpClient.Builder()
//                .cache(cache)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        return client;
    }

    @Provides
    @Singleton
    RxJavaCallAdapterFactory providesRxAdapter() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        return rxAdapter;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient, RxJavaCallAdapterFactory rxAdapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ShopifyAPI providesShopifyAPI(Retrofit retrofit) {
        return retrofit.create(ShopifyAPI.class);
    }
}
