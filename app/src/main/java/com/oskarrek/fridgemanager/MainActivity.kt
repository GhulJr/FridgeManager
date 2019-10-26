package com.oskarrek.fridgemanager

import android.os.Bundle
import android.view.ViewManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oskarrek.fridgemanager.adapters.ProductsListAdapter
import com.oskarrek.fridgemanager.models.Product

class MainActivity() : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        setupRecyclerView()

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = ProductsListAdapter(getDummyProducts())

        recyclerView = findViewById<RecyclerView>(R.id.product_list).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    private fun getDummyProducts() : ArrayList<Product> {
        val products = ArrayList<Product>()

        for(i in 0..20) {
            products.add(Product(
                i,
                "Kurczak z pomidorami",
                242987192,
                2,
                "kawałki",
                "",
                i))
        }

        return products
    }
}
