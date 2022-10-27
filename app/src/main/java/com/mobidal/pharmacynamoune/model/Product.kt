package com.mobidal.pharmacynamoune.model

import com.google.gson.annotations.SerializedName

class Product(
    val id: Int,
    val mark: String,
    val name: String,
    val description: String,
    val price: Int,
    val pictureUrl: String,
    val pictureList: List<Picture>?,
    val viewNumber: Int,
    val pivot: Pivot?
) {

    class Pivot(@field:SerializedName("is_saved") var isSaved: Boolean, var quantity: Int) {

        fun decrementQuantity() {
            quantity--
        }

        fun incrementQuantity() {
            quantity++
        }
    }
}