package com.mobidal.pharmacynamoune.model

import com.mobidal.pharmacynamoune.model.Product

class IntroCategory(
    val id: Int,
    val name: String,
    val offerText: String,
    val productList: List<Product>
)