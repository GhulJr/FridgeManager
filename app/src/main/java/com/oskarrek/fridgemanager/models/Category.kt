package com.oskarrek.fridgemanager.models

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(@PrimaryKey val id: Int,
                    @ColumnInfo(name = "name") val name: String,
                    @Ignore val icon: Drawable
)