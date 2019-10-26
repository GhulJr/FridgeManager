package com.oskarrek.fridgemanager

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.oskarrek.fridgemanager.fragments.IFabMorphListener
import com.oskarrek.fridgemanager.fragments.ProductsListFragment
import com.oskarrek.fridgemanager.fragments.RecipesListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IFabMorphListener {

    var currentItem = R.id.navigation_home

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                morphFab(R.id.navigation_home)
                startFragmentTransaction(ProductsListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                morphFab(R.id.navigation_dashboard)
                startFragmentTransaction(RecipesListFragment())
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

        startFragmentTransaction(ProductsListFragment())
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }

    private fun startFragmentTransaction(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

    // Changes fab drawable.
    private fun morphFab(navigationItem : Int) {

        if(currentItem == navigationItem) { return }

        if(navigationItem == R.id.navigation_home) {
            val addToWrite = AnimatedVectorDrawableCompat.create(this, R.drawable.create_to_add)

            // Hide/show block needed because of this bug: https://issuetracker.google.com/issues/117476935
            main_fab_add.hide()
            main_fab_add.setImageDrawable(addToWrite)
            main_fab_add.show()
            addToWrite?.start()

            main_fab_add.setOnClickListener {
                val intent = Intent(this, CreateEditProductActivity::class.java)
                startActivity(intent)
            }

        } else if(navigationItem == R.id.navigation_dashboard){
            val writeToAdd = AnimatedVectorDrawableCompat.create(this, R.drawable.add_to_create)
            main_fab_add.hide()
            main_fab_add.setImageDrawable(writeToAdd)
            main_fab_add.show()
            writeToAdd?.start()


            main_fab_add.setOnClickListener(null)
        }
        currentItem = navigationItem
    }

    override fun onScroll(scrollState: Int) {
        when(scrollState) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                main_fab_add.show()
            }
            else -> {
                main_fab_add.hide()
            }
        }
    }

}
