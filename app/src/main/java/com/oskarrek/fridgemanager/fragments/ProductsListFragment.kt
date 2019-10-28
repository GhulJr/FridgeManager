package com.oskarrek.fridgemanager.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.oskarrek.fridgemanager.R
import com.oskarrek.fridgemanager.adapters.ProductsListAdapter
import com.oskarrek.fridgemanager.interfaces.IMainActivityListener
import com.oskarrek.fridgemanager.models.Product
import com.oskarrek.fridgemanager.viewmodels.ProductsListViewModel
import kotlinx.android.synthetic.main.content_products_list.*
import java.lang.ClassCastException

class ProductsListFragment :
    Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ProductsListAdapter
    private lateinit var viewModel: ProductsListViewModel

    private lateinit var listener: IMainActivityListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? IMainActivityListener
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
        setupRecyclerView()
        setupViewModel()
    }

    override fun onDetach() {
        super.onDetach()
        recyclerView.clearOnScrollListeners()
    }

    private fun setupRecyclerView() {
        viewAdapter = ProductsListAdapter(listener)
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

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductsListViewModel::class.java)
        viewModel.products.observe(this,  Observer { products ->
            viewAdapter.products = products as ArrayList<Product>
            viewAdapter.notifyDataSetChanged()
        })
    }
}
