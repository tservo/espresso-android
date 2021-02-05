package com.routinew.espresso.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupWindow
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.routinew.espresso.R
import com.routinew.espresso.data.LoginService
import com.routinew.espresso.databinding.MainActivityBinding
import com.routinew.espresso.ui.login.LoginActivity
import com.routinew.espresso.ui.main.MainFragment
import com.routinew.espresso.ui.main.MainViewModel
import com.routinew.espresso.ui.restaurant.RestaurantDetailActivity
import com.routinew.espresso.ui.restaurant.RestaurantDetailFragment
import com.routinew.espresso.ui.restaurant.RestaurantDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val model: MainViewModel by viewModels()
    val selectedModel: RestaurantDetailViewModel by viewModels() // this will handle the restaurant in focus


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

    fun show(restaurantId: Int) {
        if (twoPane) {
            // let's stop creating new fragments willy-nilly
            val fragment = RestaurantDetailFragment.newInstance(restaurantId)
            supportFragmentManager.commit {
                replace(R.id.restaurant_detail_container, fragment)
            }
        }
        else {
            navigateToRestaurantDetail(restaurantId)
        }
    }

    private fun navigateToRestaurantDetail(restaurantId: Int) {
        val fragment = RestaurantDetailFragment.newInstance(restaurantId)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.restaurant_list_container, fragment)
            addToBackStack(null)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun logout() {
        LoginService.logout(this)
    }
}