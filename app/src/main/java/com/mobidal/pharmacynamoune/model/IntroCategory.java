package com.mobidal.pharmacynamoune.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IntroCategory {

    private int id;
    private String name;
    @SerializedName("offer_text")
    private String offerText;
    @SerializedName("_products")
    private List<Product> productList;

    public IntroCategory(int id, String name, String offerText, List<Product> productList) {
        this.id = id;
        this.name = name;
        this.offerText = offerText;
        this.productList = productList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOfferText() {
        return offerText;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
