package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "commandProducts")
class CommandProductsEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "product_id")
    var productId = 0

    @ColumnInfo(name = "command_id")
    var commandId = 0
}