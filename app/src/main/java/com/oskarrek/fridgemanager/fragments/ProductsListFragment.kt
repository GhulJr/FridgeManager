package com.oskarrek.fridgemanager.fragments

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oskarrek.fridgemanager.CreateEditProductActivity

import com.oskarrek.fridgemanager.R
import com.oskarrek.fridgemanager.adapters.ProductsListAdapter
import com.oskarrek.fridgemanager.models.Product
import com.oskarrek.fridgemanager.viewmodels.ProductsListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_products_list.*
import java.lang.ClassCastException

class ProductsListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductsListFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewModel: ProductsListViewModel

    private lateinit var listener: IFabMorphListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? IFabMorphListener
            ?: throw ClassCastException("$context must implement OnScrollListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductsListViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onDetach() {
        super.onDetach()
        recyclerView.clearOnScrollListeners()
    }

    private fun setupRecyclerView() {
        viewAdapter = ProductsListAdapter(getDummyProducts())

        recyclerView = products_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    listener.onScroll(newState)
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }

    }

    private fun getDummyProducts() : ArrayList<Product> {
        val products = ArrayList<Product>()

        for(i in 0..20) {
            products.add(
                Product(
                    i,
                    "Kurczak z pomidorami",
                    242987192,
                    2,
                    "kawałki",
                    "",
                    i)
            )
        }

        return products
    }

}
