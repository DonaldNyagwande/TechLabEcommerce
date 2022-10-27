package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "recentlySearched")
class RecentlySearchedEntity {
    //private LocalDateTime dateSearched;
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "user_id")
    var userId = 0

    @ColumnInfo(name = "product_id")
    var productId = 0

    /*public LocalDateTime getDateSearched() {
        return this.dateSearched;
    }
    public void setDateSearched(LocalDateTime dateSaved) {
        this.dateSearched = dateSearched;
    }*/
    @ColumnInfo(name = "text")
    var text: String? = null
}