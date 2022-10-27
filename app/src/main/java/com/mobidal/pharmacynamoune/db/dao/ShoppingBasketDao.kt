package com.mobidal.pharmacynamoune.db.dao

import androidx.room.*
import com.mobidal.pharmacynamoune.db.entity.ShoppingBasketEntity

@Dao
interface ShoppingBasketDao {
    @Query("SELECT * FROM shopping_basket WHERE user_id = :userId")
    fun findAllByUser(userId: Long?): List<ShoppingBasketEntity?>?

    @Query("SELECT * FROM shopping_basket WHERE user_id = :userId AND product_id = :productId")
    fun find(userId: Long?, productId: Long?): ShoppingBasketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingBasketEntity: ShoppingBasketEntity?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(shoppingBasketEntity: ShoppingBasketEntity?)

    @Delete
    fun delete(shoppingBasketEntity: ShoppingBasketEntity?)

    @Query("DELETE FROM shopping_basket")
    fun delete()
}