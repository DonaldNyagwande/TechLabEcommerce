package com.mobidal.pharmacynamoune.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobidal.pharmacynamoune.db.entity.CommandProductsEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommandProductsDao {
    @Query("SELECT * FROM commandProducts")
    fun findAll(): List<CommandProductsEntity?>?

    @Query("SELECT * FROM commandProducts WHERE id = :id")
    fun findById(id: Long?): CommandProductsEntity?

    @Query("SELECT * FROM commandProducts WHERE command_id = :command_id")
    fun findByCommandId(command_id: Long?): List<CommandProductsEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commandProduct: CommandProductsEntity?)
}