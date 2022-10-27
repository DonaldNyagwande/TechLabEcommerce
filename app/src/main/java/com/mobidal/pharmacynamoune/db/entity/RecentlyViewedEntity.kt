package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "recentlyViewed")
class RecentlyViewedEntity {
    //private LocalDateTime dateViewed;
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "user_id")
    var userId = 0

    /*public LocalDateTime getDateViewed() {
        return this.dateViewed;
    }
    public void setDateViewed(LocalDateTime dateSaved) {
        this.dateViewed = dateViewed;
    }*/
    @ColumnInfo(name = "product_id")
    var productId = 0
}