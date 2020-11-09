package com.routinew.espresso.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.routinew.espresso.databinding.MainFragmentBinding
import com.routinew.espresso.ui.restaurant.RestaurantDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        const val ARG_TWOPANE = "twopane"

        fun newInstance(twoPane: Boolean) : MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_TWOPANE, twoPane)
                }
            }
        }
    }

    private val viewModel: MainViewModel by viewModels()
    /**
     * @var binding
     * View Binding
     */
    private lateinit var binding: MainFragmentBinding

    private lateinit var restaurantAdapter: RestaurantListAdapter

    private var twoPane = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_TWOPANE)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader (or ViewModel!)
                // to load content from a content provider.
                twoPane = it.getBoolean(ARG_TWOPANE,false)
            }
        }
        restaurantAdapter = RestaurantListAdapter(requireActivity(),listOf(), twoPane)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val restaurantList = binding.restaurantList
        restaurantList.apply {
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
        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            restaurantAdapter.setData(restaurants)
        }
    }
}