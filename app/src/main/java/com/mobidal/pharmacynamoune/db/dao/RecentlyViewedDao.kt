package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.RecentlyViewedEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentlyViewedDao {
    @Query("SELECT * FROM recentlyViewed")
    fun findAll(): List<RecentlyViewedEntity?>?

    @Query("SELECT * FROM recentlyViewed WHERE id = :id")
    fun findById(id: Long?): RecentlyViewedEntity?

    @Query("SELECT * FROM recentlyViewed WHERE user_id = :user_id")
    fun findByUserId(user_id: Long?): RecentlyViewedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentlyViewed: RecentlyViewedEntity?)
}