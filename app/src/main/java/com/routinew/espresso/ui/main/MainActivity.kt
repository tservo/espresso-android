package com.routinew.espresso.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.routinew.espresso.R
import com.routinew.espresso.data.LoginService
import com.routinew.espresso.databinding.MainActivityBinding
import com.routinew.espresso.ui.compose.CreateRestaurantFormFragment
import com.routinew.espresso.ui.restaurant.RestaurantDetailFragment
import com.routinew.espresso.ui.restaurant.RestaurantDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val model: MainViewModel by viewModels()
    val selectedModel: RestaurantDetailViewModel by viewModels() // this will handle the restaurant in focus

    var toolbarTitle: CharSequence
            get() {
                return binding.toolbar.title
            }
            set(value: CharSequence) {
                binding.toolbar.title = value
            }
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    lateinit var binding: MainActivityBinding
    lateinit var fab: FloatingActionButton // set from MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        toolbar.title = title
        setSupportActionBar(toolbar)

        if (binding.restaurantDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.restaurant_list_container, MainFragment.newInstance(twoPane))
                commitNow()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                supportFragmentManager.popBackStack()
                if (supportFragmentManager.backStackEntryCount <= 1) {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDetail(restaurantId: Int) {
        if (twoPane) {
            // let's stop creating new fragments willy-nilly
            val fragment = RestaurantDetailFragment.newInstance(twoPane)
            supportFragmentManager.commit {
                replace(R.id.restaurant_detail_container, fragment)
            }
        }
        else {
            navigateToRestaurantDetail()
        }
    }

    fun showCreate() {
        val fragment = CreateRestaurantFormFragment.newInstance(twoPane)
        if (twoPane) {
            // let's stop creating new fragments willy-nilly
            supportFragmentManager.commit {
                replace(R.id.restaurant_detail_container, fragment)
            }
        }
        else {
            navigate(R.id.restaurant_list_container, fragment)
        }
    }



    private fun navigate(container: Int, fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(container, fragment)
            addToBackStack(null)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun navigateToRestaurantDetail() {
        navigate(
            R.id.restaurant_list_container,
            RestaurantDetailFragment.newInstance(twoPane)
        )
    }


    private fun logout() {
        LoginService.logout(this)
    }
}