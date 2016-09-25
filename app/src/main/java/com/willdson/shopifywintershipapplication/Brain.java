package com.willdson.shopifywintershipapplication;

import android.app.Activity;
import android.app.Application;

import com.annimon.stream.Stream;
import com.willdson.shopifywintershipapplication.data.ShopifyAPI;
import com.willdson.shopifywintershipapplication.data.model.Product;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dwson on 9/24/16.
 */

public class Brain {

    @Inject
    ShopifyAPI shopifyAPI;

    List<Product> products = new ArrayList<Product>();
    MainViewModel viewModel;
    Subscription subscription;

    public Brain(Activity activity, MainViewModel viewModel) {

        Application app = (Application) MyApp.getContext();
        ((MyApp) app).getNetComponent()
                .inject(this);

        this.viewModel = viewModel;
    }

    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void getAllProducts() {

        int page = 1;
        while (true) {
            int numProducts = products.size();
            Observable<List<Product>> call = shopifyAPI.getProductsOnPage(page);
            viewModel.setCurrentState("getting products on page " + page);
            subscription = call
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Product>>() {
                        @Override
                        public void onCompleted() {
                            viewModel.setCurrentState("completed handling response successfully");
                        }

                        @Override
                        public void onError(Throwable e) {
                            viewModel.setCurrentState("an error occurred");
                        }

                        @Override
                        public void onNext(List<Product> currentProducts) {
                            products.addAll(currentProducts);
                            viewModel.setCurrentState("mapping response to object");
                        }
                    });
            if (numProducts == products.size()) break;
            page += 1;
        }
    }

    public void computeTotalCostOfClockAndWatch() {

        int totalCostOfClockAndWatch = Stream.of(products)
                .filter(product -> product.productType == "Clock" || product.productType == "Watch")
                .map(product -> product.variants)
                .flatMap(variants -> Stream.of(variants))
                .mapToInt(variant -> Integer.parseInt(variant.price))
                .sum();
        viewModel.setCurrentState("Total cost of clock and watch is " + totalCostOfClockAndWatch);
    }
}
