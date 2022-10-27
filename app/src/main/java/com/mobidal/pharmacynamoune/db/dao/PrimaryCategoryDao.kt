package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.PrimaryCategoryEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrimaryCategoryDao {
    @Query("SELECT * FROM primary_category")
    fun findAll(): List<PrimaryCategoryEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(primaryCategoryEntity: PrimaryCategoryEntity?): Long?
}