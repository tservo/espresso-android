package com.routinew.espresso.ui.restaurant

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.routinew.espresso.R
import com.routinew.espresso.databinding.FragmentRestaurantDetailBinding
import com.routinew.espresso.objects.Restaurant
import com.routinew.espresso.ui.main.MainActivity

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [RestaurantDetailActivity]
 * on handsets.
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
        fun newInstance(restaurantId: Int): RestaurantDetailFragment {
            return RestaurantDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_RESTAURANT_ID, restaurantId)
                }
            }
        }

    }

    private val selectedViewModel: RestaurantDetailViewModel by activityViewModels()

    /**
     * @var binding
     * View Binding
     */
    private lateinit var binding: FragmentRestaurantDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.run {
            if (containsKey(ARG_RESTAURANT_ID)) {
                // this isn't necessary - we have activity scoped viewmodel
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader (or ViewModel!)
                // to load content from a content provider.
                selectedViewModel.selectedId = (getInt(ARG_RESTAURANT_ID))
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
        val activity = requireActivity()
        // this is wrong
        val toolbar = when (activity) {
            is MainActivity -> {
                activity.binding.toolbar
            }
            is RestaurantDetailActivity -> {
                activity.binding.detailToolbar
            }
            else -> throw ActivityNotFoundException()
        }

        toolbar.title =
            restaurant?.name ?: getString(R.string.RESTAURANT_NAME_UNKNOWN)

    }
}