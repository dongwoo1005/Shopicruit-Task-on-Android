package com.willdson.shopifywintershipapplication.data;

import com.willdson.shopifywintershipapplication.data.model.ProductsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dwson on 9/24/16.
 */

public interface ShopifyAPI {

    @GET("products.json")
    Observable<ProductsResponse> getProductsOnPage(
            @Query("page") int page
    );
}
