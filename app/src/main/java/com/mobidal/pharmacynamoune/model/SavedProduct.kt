package com.mobidal.pharmacynamoune.model

import com.mobidal.pharmacynamoune.model.Product
import java.time.LocalDateTime

class SavedProduct(var id: Int, var userId: Int, val productId: Int, var dateSaved: LocalDateTime) {
    private val productList: List<Product>? = null
}