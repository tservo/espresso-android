package com.routinew.espresso.ui.login

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.BaseCallback
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.routinew.espresso.ui.MainActivity
import com.routinew.espresso.R
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.LoginService
import com.routinew.espresso.databinding.ActivityLoginBinding
import com.routinew.espresso.ui.settings.SettingsActivity
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth0: Auth0
    private lateinit var credentialsManager: SecureCredentialsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        auth0 = LoginService.auth0
        credentialsManager = LoginService.credentialsManager


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    // called by button in resources
    fun login(_view: View) = LoginService.login(this)


    fun settings(_view: View) {
        val intent = Intent(this@LoginActivity, SettingsActivity::class.java)
        startActivity(intent)
    }


}
