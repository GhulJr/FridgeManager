package com.oskarrek.fridgemanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.oskarrek.fridgemanager.interfaces.IFabMorphListener
import com.oskarrek.fridgemanager.fragments.ProductsListFragment
import com.oskarrek.fridgemanager.fragments.RecipesListFragment
import com.oskarrek.fridgemanager.models.Product
import com.oskarrek.fridgemanager.repositories.DatabaseRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IFabMorphListener {

    var currentItem = R.id.navigation_home

    companion object IntentFilters {
        const val PRODUCT_CREATE = 111
        const val PRODUCT_EDIT = 211
        const val PRODUCT_SERIALIZABLE = "PRODUCT_SERIALIZABLE"
    }

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
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        startFragmentTransaction(ProductsListFragment())
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        setupOnFabClickListener(CreateEditProductActivity::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) { return }

        when(requestCode) {
            PRODUCT_CREATE -> {
                val product : Product = data?.getSerializableExtra(PRODUCT_SERIALIZABLE) as Product
                DatabaseRepository.getInstance(this).insertProducts(product)
            }
            PRODUCT_EDIT -> {
                val product : Product = data?.getSerializableExtra(PRODUCT_SERIALIZABLE) as Product
                DatabaseRepository.getInstance(this).updateProduct(product)
            }
        }

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
            animateFabIcon(R.drawable.create_to_add)
            setupOnFabClickListener(CreateEditProductActivity::class.java)

        } else if(navigationItem == R.id.navigation_dashboard){
            animateFabIcon(R.drawable.add_to_create)
            main_fab_add.setOnClickListener(null)
        }
        currentItem = navigationItem
    }

    private fun animateFabIcon(drawableId : Int) {
        val animatedDrawable = AnimatedVectorDrawableCompat.create(this, drawableId)
        // Hide/show block needed because of this bug: https://issuetracker.google.com/issues/117476935
        main_fab_add.hide()
        main_fab_add.setImageDrawable(animatedDrawable)
        main_fab_add.show()
        animatedDrawable?.start()
    }

    private fun setupOnFabClickListener(destinationClass : Class<out Activity>) {
        main_fab_add.setOnClickListener {
            val intent = Intent(this, destinationClass)
            intent.getStringExtra(PRODUCT_SERIALIZABLE)
            startActivityForResult(intent, PRODUCT_CREATE)
        }
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
