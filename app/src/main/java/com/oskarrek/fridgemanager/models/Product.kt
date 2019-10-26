package com.oskarrek.fridgemanager.models

import androidx.room.*

@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "expiration_date")val expirationDate: Long,
    @ColumnInfo(name = "quantity") val quantity: Number,
    @ColumnInfo(name = "unit") val unit: String,
    @ColumnInfo(name = "image_path") val imageUri: String,
    @ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"])
    val categoryId: Int
)