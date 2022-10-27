package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "commands")
class CommandEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "user_id")
    var userId = 0
    var state: String? = null

    //@ColumnInfo(name = "delivery_price")
    var deliveryPrice = 0
}