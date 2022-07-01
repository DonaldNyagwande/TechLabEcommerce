package com.mobidal.pharmacynamoune.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    private int id;
    private String name;
    @SerializedName("text_offer")
    private String textOffer;
    @SerializedName("picture_url")
    private String pictureUrl;
    @SerializedName("_categories")
    private List<Category> categoryList;

    public Category(int id, String name, String textOffer, String pictureUrl, List<Category> categoryList) {
        this.id = id;
        this.name = name;
        this.textOffer = textOffer;
        this.pictureUrl = pictureUrl;
        this.categoryList = categoryList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTextOffer() {
        return textOffer;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
