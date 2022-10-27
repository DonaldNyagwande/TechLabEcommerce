package com.mobidal.pharmacynamoune.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "savedProducts")
class SavedProductEntity {
    //private LocalDateTime dateSaved;
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "user_id")
    var userId = 0

    /*public LocalDateTime getDateSaved() {
        return this.dateSaved;
    }

    public void setDateSaved(LocalDateTime dateSaved) {
        this.dateSaved = dateSaved;
    }*/
    @ColumnInfo(name = "product_id")
    var productId = 0
}