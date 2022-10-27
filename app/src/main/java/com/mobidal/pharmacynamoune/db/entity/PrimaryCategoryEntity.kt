package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "primary_category")
class PrimaryCategoryEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var name: String? = null

    @ColumnInfo(name = "picture_url")
    var pictureUrl: String? = null
}