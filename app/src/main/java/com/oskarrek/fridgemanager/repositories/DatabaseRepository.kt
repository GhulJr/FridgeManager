package com.oskarrek.fridgemanager.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oskarrek.fridgemanager.database.FridgeDatabase
import com.oskarrek.fridgemanager.models.Product
import com.oskarrek.fridgemanager.utills.SingletonHolder
import java.util.concurrent.Executors

class DatabaseRepository private constructor(context: Context) {

    private val executor = Executors.newSingleThreadExecutor()
    private val database : FridgeDatabase = Room.databaseBuilder(context,
        FridgeDatabase::class.java,
        "FridgeDatabase").build()

    companion object : SingletonHolder<DatabaseRepository, Context>(::DatabaseRepository)

    fun getAllProducts() : LiveData<List<Product>> = database.productsDao.getAllProducts()

    fun insertProducts(vararg products : Product) {
        executor.execute {
            database.productsDao.insertProducts(*products)
        }
    }

    fun updateProduct(vararg products: Product){
        executor.execute {
            database.productsDao.updateProducts(*products)
        }
    }

    fun deleteProduct(vararg products: Product) {
        executor.execute {
            database.productsDao.deleteProducts(*products)
        }
    }
}