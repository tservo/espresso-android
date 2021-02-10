package com.routinew.espresso.ui.restaurant

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.routinew.espresso.databinding.FragmentRestaurantDetailBinding
import com.routinew.espresso.data.model.Restaurant
import com.routinew.espresso.ui.main.MainActivity

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or as a fragment navigation on phones
 */
class RestaurantDetailFragment : Fragment() {

    companion object {
        /**
         * use to find in the fragment manager
         */
        const val TAG = "RESTAURANT.DETAIL"

        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_RESTAURANT_ID = "restaurant_id"
        const val ARG_TWOPANE = "twopane"
        fun newInstance(twoPane: Boolean): RestaurantDetailFragment {
            return RestaurantDetailFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_TWOPANE, twoPane)
                }
            }
        }

    }

    private var twoPane = false

    private val selectedViewModel: RestaurantDetailViewModel by activityViewModels()

    /**
     * @var binding
     * View Binding
     */
    private lateinit var binding: FragmentRestaurantDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.run {
            if (containsKey(ARG_TWOPANE)) {
                twoPane = getBoolean(ARG_TWOPANE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
     //   setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedViewModel.restaurant.observe(viewLifecycleOwner) { restaurant ->
            displayView(restaurant) // update the single restaurant
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun displayView(restaurant: Restaurant?) {
        // update the parent activity layout
        if (twoPane) {
            (requireActivity() as MainActivity).toolbarTitle = restaurant?.name ?: "Name Unknown"
        }
    }
}