package com.mobidal.pharmacynamoune.db.dao

import androidx.room.*
import com.mobidal.pharmacynamoune.db.entity.SavedProductEntity

@Dao
interface SavedProductDao {
    @Query("SELECT * FROM savedProducts")
    fun findAll(): List<SavedProductEntity?>?

    @Query("SELECT * FROM savedProducts WHERE id = :id")
    fun findById(id: Long?): SavedProductEntity?

    @Query("SELECT * FROM savedProducts WHERE user_id = :user_id")
    fun findByUserId(user_id: Long?): SavedProductEntity?

    @Query("SELECT * FROM savedProducts WHERE user_id = :user_id AND product_id = :productId")
    fun findByUserIdAndProductId(user_id: Long?, productId: Long?): SavedProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(savedProduct: SavedProductEntity?): Long?

    @Delete
    fun delete(savedProduct: SavedProductEntity?)
}