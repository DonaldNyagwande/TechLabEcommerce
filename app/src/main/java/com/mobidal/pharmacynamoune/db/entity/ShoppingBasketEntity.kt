package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "shopping_basket")
class ShoppingBasketEntity {
    @PrimaryKey
    var id: Long? = null

    @ColumnInfo(name = "user_id")
    var userId: Long? = null

    @ColumnInfo(name = "product_id")
    var productId: Long? = null
    var quantity: Long? = null
}