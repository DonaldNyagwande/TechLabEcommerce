package com.mobidal.pharmacynamoune.model

import com.mobidal.pharmacynamoune.model.Product
import java.time.LocalDateTime

class RecentlyViewed(
    var id: Int,
    var userId: Int,
    val productId: Int,
    private var dateViewed: LocalDateTime
) {
    private val productList: List<Product>? = null
    fun getDateViewed(): LocalDateTime {
        return dateViewed
    }

    fun setDateViewed(dateSaved: LocalDateTime?) {
        dateViewed = dateViewed
    }
}