package com.routinew.espresso.ui.compose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.routinew.espresso.R
import com.routinew.espresso.databinding.FragmentCreateRestaurantFormBinding
import com.routinew.espresso.ui.main.MainActivity

//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
private const val ARG_TWOPANE = "twopane"


/**
 * A simple [Fragment] subclass.
 * Use the [CreateRestaurantFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateRestaurantFormFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    private var twoPane = false

    private lateinit var binding: FragmentCreateRestaurantFormBinding

    private val viewModel: ComposeRestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            twoPane = it.getBoolean(ARG_TWOPANE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateRestaurantFormBinding.inflate(inflater,container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            cancel.setOnClickListener {
                (requireActivity() as MainActivity).run {
                    Snackbar.make(this.binding.root,"Cancelled",Snackbar.LENGTH_SHORT).show()
                    supportFragmentManager.popBackStack()
                }
            }

            create.setOnClickListener {
                (requireActivity() as MainActivity).run {
                    viewModel.createRestaurant()
                    Snackbar.make(this.binding.root,"Created",Snackbar.LENGTH_SHORT).show()
                    supportFragmentManager.popBackStack()
                }
            }

            viewModel.isRestaurantValid.observe(viewLifecycleOwner) { valid ->
                create.isEnabled = valid
            }
        }


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CreateRestaurantFormFragment.
         */
        @JvmStatic
        fun newInstance(twoPane: Boolean = false) =
            CreateRestaurantFormFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_TWOPANE,twoPane)
                }
            }
    }


}