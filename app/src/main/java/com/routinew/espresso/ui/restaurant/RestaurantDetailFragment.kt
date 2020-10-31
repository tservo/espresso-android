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
import com.routinew.espresso.ui.main.MainFragment
import com.routinew.espresso.ui.main.MainViewModel
import com.routinew.espresso.ui.main.RestaurantListAdapter

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a [RestaurantListActivity]
 * in two-pane mode (on tablets) or a [RestaurantDetailActivity]
 * on handsets.
 */
class RestaurantDetailFragment : Fragment() {

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "restaurant_item"
        fun newInstance() = RestaurantDetailFragment()

    }

    private val viewModel: RestaurantDetailViewModel by viewModels()

    /**
     * @var binding
     * View Binding
     */
    private lateinit var binding: RestaurantDetailBinding

    /**
     * The dummy content this fragment is presenting.
     */
    private var restaurant: Restaurant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
                    item?.content
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.restaurant_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.findViewById<TextView>(R.id.restaurant_detail).text = it.details
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.restaurant.observe(viewLifecycleOwner) { restaurant ->
           // restaurantAdapter.setData(restaurant) // update the single restaurant
        }
    }






    private var restaurantAdapter = RestaurantListAdapter(listOf())


}