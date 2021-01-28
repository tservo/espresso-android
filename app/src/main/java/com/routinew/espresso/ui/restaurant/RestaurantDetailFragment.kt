package com.routinew.espresso.ui.restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.routinew.espresso.R
import com.routinew.espresso.databinding.MainFragmentBinding
import com.routinew.espresso.databinding.RestaurantDetailBinding
import com.routinew.espresso.dummy.DummyContent
import com.routinew.espresso.objects.Restaurant
import com.routinew.espresso.ui.main.MainFragment
import com.routinew.espresso.ui.main.MainViewModel
import com.routinew.espresso.ui.main.RestaurantListAdapter

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [RestaurantDetailActivity]
 * on handsets.
 */
class RestaurantDetailFragment : Fragment() {

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_RESTAURANT_ID = "restaurant_id"
        fun newInstance(restaurantId: Int) : RestaurantDetailFragment {
            return RestaurantDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_RESTAURANT_ID, restaurantId)
                }
            }
        }

    }

    private val viewModel: RestaurantDetailViewModel by viewModels()

    /**
     * @var binding
     * View Binding
     */
    private lateinit var binding: RestaurantDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.run {
            if (containsKey(ARG_RESTAURANT_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader (or ViewModel!)
                // to load content from a content provider.
                viewModel.selectedId = getInt(ARG_RESTAURANT_ID)
                // viewModel.getRestaurant(getInt(ARG_RESTAURANT_ID))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RestaurantDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Show the dummy content as text in a TextView. -- this might be a loading screen.

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.restaurant.observe(viewLifecycleOwner) { restaurant ->
           displayView(restaurant) // update the single restaurant
        }
    }

    fun displayView(restaurant: Restaurant?) {
        // update the parent activity layout

        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
            restaurant?.name

    }
}