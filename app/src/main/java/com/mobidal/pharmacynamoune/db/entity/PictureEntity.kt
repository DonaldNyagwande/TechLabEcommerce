package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "pictures")
class PictureEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "product_id")
    var productId: Long? = null

    @ColumnInfo(name = "picture_url")
    var pictureUrl: String? = null
}