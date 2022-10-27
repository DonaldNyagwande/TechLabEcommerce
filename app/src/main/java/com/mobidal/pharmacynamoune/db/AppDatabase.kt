package com.mobidal.pharmacynamoune.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import com.mobidal.pharmacynamoune.db.entity.UserEntity
import com.mobidal.pharmacynamoune.db.entity.SecondaryCategoryEntity
import com.mobidal.pharmacynamoune.db.entity.PrimaryCategoryEntity
import com.mobidal.pharmacynamoune.db.entity.ProductEntity
import com.mobidal.pharmacynamoune.db.entity.ShoppingBasketEntity
import com.mobidal.pharmacynamoune.db.entity.PictureEntity
import com.mobidal.pharmacynamoune.db.entity.CommandEntity
import com.mobidal.pharmacynamoune.db.entity.CommandProductsEntity
import com.mobidal.pharmacynamoune.db.entity.RecentlySearchedEntity
import com.mobidal.pharmacynamoune.db.entity.RecentlyViewedEntity
import com.mobidal.pharmacynamoune.db.entity.SavedProductEntity
import androidx.room.RoomDatabase
import com.mobidal.pharmacynamoune.db.dao.UserDao
import com.mobidal.pharmacynamoune.db.dao.PrimaryCategoryDao
import com.mobidal.pharmacynamoune.db.dao.SecondaryCategoryDao
import com.mobidal.pharmacynamoune.db.dao.ProductDao
import com.mobidal.pharmacynamoune.db.dao.ShoppingBasketDao
import com.mobidal.pharmacynamoune.db.dao.PictureDao
import com.mobidal.pharmacynamoune.db.dao.CommandDao
import com.mobidal.pharmacynamoune.db.dao.CommandProductsDao
import com.mobidal.pharmacynamoune.db.dao.RecentlySearchedDao
import com.mobidal.pharmacynamoune.db.dao.RecentlyViewedDao
import com.mobidal.pharmacynamoune.db.dao.SavedProductDao
import com.mobidal.pharmacynamoune.db.AppDatabase
import androidx.room.Room

@Database(entities = [UserEntity::class, SecondaryCategoryEntity::class, PrimaryCategoryEntity::class, ProductEntity::class, ShoppingBasketEntity::class, PictureEntity::class, CommandEntity::class, CommandProductsEntity::class, RecentlySearchedEntity::class, RecentlyViewedEntity::class, SavedProductEntity::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?
    abstract fun primaryCategoryDao(): PrimaryCategoryDao?
    abstract fun secondaryCategoryDao(): SecondaryCategoryDao?
    abstract fun productDao(): ProductDao?
    abstract fun shoppingBasketDao(): ShoppingBasketDao?
    abstract fun pictureDao(): PictureDao?
    abstract fun commandDao(): CommandDao?
    abstract fun commandProductsDao(): CommandProductsDao?
    abstract fun recentlySearchedDao(): RecentlySearchedDao?
    abstract fun recentlyViewedDao(): RecentlyViewedDao?
    abstract fun savedProductDao(): SavedProductDao?

    companion object {
        private val LOG_TAG = AppDatabase::class.java.simpleName
        private val LOCK = Any()
        private const val DATABASE_NAME = "walmart"
        private var sInstance: AppDatabase? = null
        @JvmStatic
        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return sInstance
        }
    }
}