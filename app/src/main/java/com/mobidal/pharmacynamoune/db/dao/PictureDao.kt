package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.PictureEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureDao {
    @Query("SELECT * FROM pictures WHERE product_id = :productId")
    fun findAllByProductId(productId: Long?): List<PictureEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureEntity: PictureEntity?): Long
}