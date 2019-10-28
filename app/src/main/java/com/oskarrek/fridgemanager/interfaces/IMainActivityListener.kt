package com.oskarrek.fridgemanager.interfaces

import com.oskarrek.fridgemanager.models.Product

interface IMainActivityListener {
    fun onScroll(scrollState : Int)
    fun onClick(product : Product)
    fun onLongClick(product: Product)
}