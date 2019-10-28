package com.oskarrek.fridgemanager.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.oskarrek.fridgemanager.models.Product
import com.oskarrek.fridgemanager.repositories.DatabaseRepository

class ProductsListViewModel(application: Application) : AndroidViewModel(application) {

     val products : LiveData<List<Product>> = DatabaseRepository
         .getInstance(getApplication())
         .getAllProducts()

}
