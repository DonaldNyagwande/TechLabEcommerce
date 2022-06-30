package com.mobidal.pharmacynamoune.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    private int id;
    private String mark;
    private String name;
    private String description;
    private int price;
    @SerializedName("picture_url")
    private String pictureUrl;
    @SerializedName("_pictures")
    private List<Picture> pictureList;
    @SerializedName("views_number")
    private int viewNumber;
    private Pivot pivot;

    public int getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public class Pivot {

        @SerializedName("is_saved")
        private boolean isSaved;
        private int quantity;

        public boolean isSaved() {
            return isSaved;
        }

        public void setSaved(boolean isSaved) {
            this.isSaved = isSaved;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void decrementQuantity() {
            quantity--;
        }

        public void incrementQuantity() {
            quantity++;
        }

    }

}
