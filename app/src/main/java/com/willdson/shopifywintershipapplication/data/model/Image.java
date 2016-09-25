package com.willdson.shopifywintershipapplication.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dwson on 9/24/16.
 */

public class Image {

    public Long id;
    public String createdAt;
    public int position;
    public String updatedAt;
    public long productId;
    public List<Long> variantIds = new ArrayList<Long>();
    public String src;

}