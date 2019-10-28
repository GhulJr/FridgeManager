package com.oskarrek.fridgemanager.models

import android.net.Uri
import androidx.room.*
import java.io.Serializable

@Entity(tableName = "product")
data class Product constructor(
    @PrimaryKey(autoGenerate = true)        val id: Int,
    @ColumnInfo(name = "name")              val name: String,
    @ColumnInfo(name = "expiration_date")   val expirationDate: Long,
    @ColumnInfo(name = "quantity")          val quantity: String,
    @ColumnInfo(name = "image_path")        val imageUri: String
) : Serializable {
    @Ignore
    constructor(
                name: String,
                expirationDate: Long,
                quantity: String,
                imageUri: String) : this(0, name, expirationDate, quantity, imageUri)
}