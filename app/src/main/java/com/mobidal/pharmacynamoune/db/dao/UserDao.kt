package com.mobidal.pharmacynamoune.db.dao

import androidx.room.*
import com.mobidal.pharmacynamoune.db.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun findAll(): List<UserEntity?>?

    @Query("SELECT * FROM users WHERE id = :id")
    fun find(id: Long?): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun isExist(username: String?, password: String?): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity?): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: UserEntity?)
}