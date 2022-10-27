package com.mobidal.pharmacynamoune.model

import com.mobidal.pharmacynamoune.model.Product
import java.time.LocalDateTime

class RecentlySearched(
    var id: Int,
    var userId: Int,
    val productId: Int,
    private var dateSearched: LocalDateTime
) {
    private val productList: List<Product>? = null
    fun getDateSearched(): LocalDateTime {
        return dateSearched
    }

    fun setDateSearched(dateSaved: LocalDateTime?) {
        dateSearched = dateSearched
    }
}