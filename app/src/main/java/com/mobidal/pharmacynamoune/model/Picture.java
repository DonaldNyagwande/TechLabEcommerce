package com.mobidal.pharmacynamoune.model;

import com.google.gson.annotations.SerializedName;

public class Picture {

    private int id;
    @SerializedName("picture_url")
    private String mPictureUrl;

    public int getId() {
        return id;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }
}
