package com.willdson.shopifywintershipapplication;

import android.app.Application;
import android.util.Log;
import android.view.View;

import com.willdson.shopifywintershipapplication.data.ShopifyAPI;
import com.willdson.shopifywintershipapplication.data.model.Product;
import com.willdson.shopifywintershipapplication.data.model.ProductsResponse;
import com.willdson.shopifywintershipapplication.data.model.Variant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.ContentValues.TAG;

/**
 * Created by dwson on 9/24/16.
 */

public class Brain {

    @Inject
    ShopifyAPI shopifyAPI;

    private MainViewModel mViewModel;
    private List<Product> mProducts = new ArrayList<Product>();
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public Brain(MainViewModel viewModel) {

        Application app = (Application) MyApp.getContext();
        ((MyApp) app).getNetComponent()
                .inject(this);

        this.mViewModel = viewModel;
    }

    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
    }

    public void getProductsFromPage(int page) {

        final List<Product> productsOnPage = new ArrayList<Product>();

        Observable<ProductsResponse> apiCall = shopifyAPI.getProductsOnPage(page);
        Subscriber<ProductsResponse> apiSubscriber = new Subscriber<ProductsResponse>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: " + productsOnPage.size());
                mViewModel.addState("Completed handling page " + page);
                if (!productsOnPage.isEmpty()) getProductsFromPage(page + 1);
                else computeTotalCostOfClockAndWatch();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
                if (e instanceof HttpException) {
                    HttpException response = (HttpException) e;
                    int code = response.code();
                    Log.d(TAG, "onError: code " + code);
                }
                e.printStackTrace();
                mViewModel.addState("an error occurred");
            }

            @Override
            public void onNext(ProductsResponse productsResponse) {
                Log.d(TAG, "onNext: ");
                productsOnPage.addAll(productsResponse.products);
                mProducts.addAll(productsResponse.products);
            }
        };

        Subscription subscription = apiCall
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiSubscriber);

        mCompositeSubscription.add(subscription);
    }

    public void computeTotalCostOfClockAndWatch() {

        Log.d(TAG, "computeTotalCostOfClockAndWatch: products size " + mProducts.size());
//        double totalCost = Stream.of(mProducts)
//                .filter(product -> product.productType == "Clock" || product.productType == "Watch")
//                .map(product -> product.variants)
//                .flatMap(variants -> Stream.of(variants))
//                .map(variant -> Double.parseDouble(variant.price))
//                .reduce(0.0, Double::sum);


        double total = 0;
        for (Product product : mProducts) {
            if (product.productType.equalsIgnoreCase("Clock") || product.productType.equalsIgnoreCase("Watch")) {
                for (Variant variant : product.variants) {
                    total += Double.parseDouble(variant.price);
                }
            }
        }

        DecimalFormat df = new DecimalFormat("0.00");
        Log.d(TAG, "computeTotalCostOfClockAndWatch: " + df.format(total));
        mViewModel.addState("Total cost of clock and watch is $" + df.format(total));
    }

    public void sendRequest(View view) {
        Log.d(TAG, "sendRequest: ");
        mProducts = new ArrayList<Product>();
        getProductsFromPage(1);
    }
}
