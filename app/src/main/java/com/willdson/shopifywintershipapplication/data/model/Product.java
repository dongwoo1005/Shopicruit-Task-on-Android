package com.willdson.shopifywintershipapplication.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dwson on 9/24/16.
 */

public class Product {

    public long id;
    public String title;
    public String handle;
    public String bodyHtml;
    public String publishedAt;
    public String createdAt;
    public String updatedAt;
    public String vendor;
    public String productType;
    public List<String> tags = new ArrayList<String>();
    public List<Variant> variants = new ArrayList<Variant>();
    public List<Image> images = new ArrayList<Image>();
    public List<Option> options = new ArrayList<Option>();
}