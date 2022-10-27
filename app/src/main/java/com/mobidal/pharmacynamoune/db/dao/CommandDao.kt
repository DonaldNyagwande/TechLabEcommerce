package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.CommandEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommandDao {
    @Query("SELECT * FROM commands")
    fun findAll(): List<CommandEntity?>?

    @Query("SELECT * FROM commands WHERE id = :id")
    fun findById(id: Long?): CommandEntity?

    @Query("SELECT * FROM commands WHERE user_id = :user_id")
    fun findByUserId(user_id: Long?): CommandEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(command: CommandEntity?): Long
}