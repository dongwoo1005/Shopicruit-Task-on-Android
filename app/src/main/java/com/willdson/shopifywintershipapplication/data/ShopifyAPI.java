package com.willdson.shopifywintershipapplication.data;

import com.willdson.shopifywintershipapplication.data.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dwson on 9/24/16.
 */

public interface ShopifyAPI {

    @GET("products.json")
    Call<List<Product>> getProductsOnPage(
            @Query("page") int page
    );
}
