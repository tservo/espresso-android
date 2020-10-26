package com.routinew.espresso.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.routinew.espresso.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    /**
     * @var binding
     * View Binding
     */
    private lateinit var binding: MainFragmentBinding

    private var restaurantAdapter = RestaurantListAdapter(listOf())

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.restaurantList.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(view.context)

            adapter = restaurantAdapter
        }

        binding.loading.visibility = View.GONE 
        binding.restaurantList.visibility = View.VISIBLE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            restaurantAdapter.setData(restaurants)
        }
    }
}