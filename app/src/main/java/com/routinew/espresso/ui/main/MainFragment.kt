package com.routinew.espresso.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.routinew.espresso.databinding.FragmentMainBinding
import com.routinew.espresso.ui.restaurant.RestaurantDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

    /**
     * @var viewModel
     * Holds the view model associated with this fragment
     */
    private val viewModel: MainViewModel by activityViewModels()
    private val selectedViewModel: RestaurantDetailViewModel by activityViewModels()
    /**
     * @var binding
     * View Binding
     */
    private lateinit var binding: FragmentMainBinding

    private lateinit var restaurantAdapter: RestaurantListAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    /**
     *  @var twoPane: Boolean
     *  Used for Master-Detail display
     */
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
        restaurantAdapter = RestaurantListAdapter(listOf()) { v ->
            selectedViewModel.selectedId = v.tag as Int
            (requireActivity() as MainActivity).show(selectedViewModel.selectedId)


//            if (twoPane) {
//                // let's stop creating new fragments willy-nilly
//                val fragment = RestaurantDetailFragment.newInstance(restaurantId)
//                requireActivity().supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.restaurant_detail_container, fragment)
//                    .commitNow()
//            } else {
//                val intent = Intent(v.context, RestaurantDetailActivity::class.java).apply {
//                    putExtra(RestaurantDetailFragment.ARG_RESTAURANT_ID, restaurantId)
//                }
//                v.context.startActivity(intent)
//            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            restaurantAdapter.setData(restaurants)
            swipeRefresh.isRefreshing = false
        }

        with(binding) {
            restaurantList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(root.context)
                adapter = restaurantAdapter
                visibility = View.VISIBLE
            }
            loading.visibility = View.GONE
        }

        swipeRefresh = binding.swiperefresh.apply {
            setOnRefreshListener {
                Timber.i("Onrefresh called")
                viewModel.getRestaurants()
            }
        }
    }
}
