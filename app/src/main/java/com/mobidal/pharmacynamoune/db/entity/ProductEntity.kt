package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.mobidal.pharmacynamoune.model.Picture

@Entity(tableName = "products")
class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var mark: String? = null
    var name: String? = null
    var description: String? = null
    var price = 0

    @ColumnInfo(name = "picture_url")
    var pictureUrl: String? = null

    @ColumnInfo(name = "secondary_category_id")
    var secondaryCategoryId: Long? = null

    @Ignore
    var pictureList: List<Picture>? = null
    var viewNumber = 0
}