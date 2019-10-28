package com.oskarrek.fridgemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oskarrek.fridgemanager.models.Category
import com.oskarrek.fridgemanager.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class FridgeDatabase : RoomDatabase() {
    abstract val productsDao: ProductsDao
}