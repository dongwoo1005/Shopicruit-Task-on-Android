package com.willdson.shopifywintershipapplication.data;

import com.willdson.shopifywintershipapplication.data.model.Product;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dwson on 9/24/16.
 */

public interface ShopifyAPI {

    @GET("products.json")
    Observable<List<Product>> getProductsOnPage(
            @Query("page") int page
    );
}
