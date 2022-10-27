package com.mobidal.pharmacynamoune.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    private int id;
    private String name;
    @SerializedName("offer_text")
    private String offerText;
    @SerializedName("picture_url")
    private String pictureUrl;
    @SerializedName("_categories")
    private List<Category> categoryList;

    public Category(int id, String name, String offerText, String pictureUrl, List<Category> categoryList) {
        this.id = id;
        this.name = name;
        this.offerText = offerText;
        this.pictureUrl = pictureUrl;
        this.categoryList = categoryList;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
