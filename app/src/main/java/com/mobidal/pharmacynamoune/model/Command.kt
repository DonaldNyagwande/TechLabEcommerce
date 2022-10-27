package com.mobidal.pharmacynamoune.model

import com.mobidal.pharmacynamoune.model.Product

class Command(
    val id: Int,
    val state: String,
    val deliveryPrice: Int,
    val productList: List<Product>
) {

    val subTotalPrice: Int
        get() {
            var subTotal = 0
            for (product in productList) {
                subTotal += product.price * product.pivot.quantity
            }
            return subTotal
        }
    val totalPrice: Int
        get() = subTotalPrice + deliveryPrice
}