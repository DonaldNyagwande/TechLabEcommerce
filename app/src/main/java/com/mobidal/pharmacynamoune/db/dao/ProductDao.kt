package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.ProductEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun findAll(): List<ProductEntity?>?

    @Query("SELECT * FROM products WHERE secondary_category_id = :secondaryCategoryId")
    fun findAllBySecondaryCategoryId(secondaryCategoryId: Long?): List<ProductEntity?>?

    @Query("SELECT * FROM products WHERE secondary_category_id = :secondaryCategoryId LIMIT 4")
    fun findFourBySecondaryCategoryId(secondaryCategoryId: Long?): List<ProductEntity?>?

    @Query("SELECT * FROM products WHERE id = :id")
    fun find(id: Long?): ProductEntity?

    @Query("SELECT * FROM products WHERE LOWER(mark) LIKE '%'||LOWER(:query)||'%' OR LOWER(name) LIKE '%'||LOWER(:query)||'%' OR LOWER(description) LIKE '%'||LOWER(:query)||'%'")
    fun query(query: String?): List<ProductEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productEntity: ProductEntity?): Long?
}