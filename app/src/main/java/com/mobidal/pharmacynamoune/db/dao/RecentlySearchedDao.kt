package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.RecentlySearchedEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentlySearchedDao {
    @Query("SELECT * FROM recentlySearched")
    fun findAll(): List<RecentlySearchedEntity?>?

    @Query("SELECT * FROM recentlySearched WHERE id = :id")
    fun findById(id: Long?): RecentlySearchedEntity?

    @Query("SELECT * FROM recentlySearched WHERE user_id = :user_id")
    fun findByUserId(user_id: Long?): List<RecentlySearchedEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentlySearched: RecentlySearchedEntity?)
}