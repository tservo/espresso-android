package com.routinew.espresso.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.routinew.espresso.R
import com.routinew.espresso.databinding.MainFragmentBinding
import com.routinew.espresso.databinding.RestaurantBinding
import com.routinew.espresso.objects.Restaurant

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel



    private var _binding: MainFragmentBinding? = null
    // only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.restaurantList.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(view.context)

            adapter = RestaurantListAdapter(arrayOf(
                Restaurant("joe"),
                Restaurant("brad")
            ))
        }

        binding.message.visibility = View.GONE
        binding.restaurantList.visibility = View.VISIBLE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}