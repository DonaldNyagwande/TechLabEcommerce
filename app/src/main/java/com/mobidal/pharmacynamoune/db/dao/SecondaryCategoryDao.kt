package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.SecondaryCategoryEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SecondaryCategoryDao {
    @Query("SELECT * FROM secondary_category LIMIT 6")
    fun findTopCategories(): List<SecondaryCategoryEntity?>?

    @Query("SELECT * FROM secondary_category WHERE primary_category_id = :primaryCategoryId ORDER BY id DESC")
    fun findAllByPrimaryCategory(primaryCategoryId: Int): List<SecondaryCategoryEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(secondaryCategoryEntity: SecondaryCategoryEntity?): Long?
}