package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "users")
class UserEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
        private set
    var username: String? = null
    var password: String? = null

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    var gender: String? = null

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String? = null
    fun setId(id: Long) {
        this.id = id
    }

    val fullName: String
        get() = "$firstName $lastName"
}