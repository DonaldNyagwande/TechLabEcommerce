package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "secondary_category")
class SecondaryCategoryEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var name: String? = null

    @ColumnInfo(name = "offer_text")
    var offerText: String? = null

    @ColumnInfo(name = "picture_url")
    var pictureUrl: String? = null

    @ColumnInfo(name = "primary_category_id")
    var primaryCategoryId: Long? = null
}