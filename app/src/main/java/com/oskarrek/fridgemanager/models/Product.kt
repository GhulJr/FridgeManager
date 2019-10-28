package com.oskarrek.fridgemanager.models

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "product")
data class Product(
    @PrimaryKey                             val id: Int,
    @ColumnInfo(name = "name")              val name: String,
    @ColumnInfo(name = "expiration_date")   val expirationDate: Long,
    @ColumnInfo(name = "quantity")          val quantity: String,
    @ColumnInfo(name = "image_path")        val imageUri: String
) : Serializable