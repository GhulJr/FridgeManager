package com.oskarrek.fridgemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oskarrek.fridgemanager.models.Product

@Dao
interface ProductsDao {

    @Query("SELECT * FROM product")
    fun getAllProducts() : LiveData<List<Product>>

    @Insert
    fun insertProducts(vararg products : Product)

    @Update
    fun updateProducts(vararg products : Product)

    @Delete
    fun deleteProducts(vararg products: Product)
}