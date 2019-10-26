package com.oskarrek.fridgemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oskarrek.fridgemanager.models.Category
import com.oskarrek.fridgemanager.models.Product

@Database(entities = [Product::class, Category::class], version = 1)
abstract class FridgeDatabase : RoomDatabase() {
    abstract val productsDao: ProductsDao
    abstract val categoriesDao: CategoriesDao
}