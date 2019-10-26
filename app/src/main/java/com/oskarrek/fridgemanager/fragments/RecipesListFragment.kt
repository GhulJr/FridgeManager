package com.oskarrek.fridgemanager.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.oskarrek.fridgemanager.R
import com.oskarrek.fridgemanager.viewmodels.RecipesListViewModel

class RecipesListFragment : Fragment() {

    companion object {
        fun newInstance() = RecipesListFragment()
    }

    private lateinit var viewModel: RecipesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
