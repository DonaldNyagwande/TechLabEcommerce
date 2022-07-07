package com.mobidal.pharmacynamoune.model;

import com.google.gson.annotations.SerializedName;

public class Picture {

    private int id;
    @SerializedName("picture_url")
    private String mPictureUrl;

    public Picture(int id, String mPictureUrl) {
        this.id = id;
        this.mPictureUrl = mPictureUrl;
    }

    public int getId() {
        return id;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }
}
