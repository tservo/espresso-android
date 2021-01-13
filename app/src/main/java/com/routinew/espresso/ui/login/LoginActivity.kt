package com.routinew.espresso.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.BaseCallback
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.routinew.espresso.MainActivity
import com.routinew.espresso.R
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.databinding.ActivityLoginBinding
import com.routinew.espresso.ui.settings.SettingsActivity
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS"
        const val EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN"
        const val SCHEME = "demo"
    }

    private lateinit var auth0 : Auth0
    private lateinit var credentialsManager: SecureCredentialsManager

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)


        auth0 = Auth0(this)
        auth0.apply {
            isOIDCConformant = true
            isLoggingEnabled = true
        }

        credentialsManager = SecureCredentialsManager(this,
            AuthenticationAPIClient(auth0), SharedPreferencesStorage(this))


        //Check if the activity was launched to log the user out
        if (intent.getBooleanExtra(EXTRA_CLEAR_CREDENTIALS, true)) {
            logout()
            return
        }

        // check for previous credentials
        if (credentialsManager.hasValidCredentials()) {
            // we have credentials - move on
            showNextActivity()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

     fun login(view: View) {
        WebAuthProvider.login(auth0)
            .withScheme(SCHEME)
            .withAudience(getString(R.string.com_auth0_audience))
            .withScope("openid offline_access read:restaurants")
            .start(this, object : AuthCallback {
                /**
                 * Called when the failure reason is displayed in a [android.app.Dialog].
                 *
                 * @param dialog error dialog
                 */
                override fun onFailure(dialog: Dialog) {
                    runOnUiThread { dialog.show() }
                }

                /**
                 * Called with an AuthenticationException that describes the error.
                 *
                 * @param exception cause of the error
                 */
                override fun onFailure(exception: AuthenticationException) {
                    Timber.w(exception)
                    Timber.w(exception.description)
                    runOnUiThread {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error: " + exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                /**
                 * Called when the authentication is successful using web authentication against Auth0
                 *
                 * @param credentials Auth0 credentials information (id_token, refresh_token, etc).
                 */
                override fun onSuccess(credentials: Credentials) {
                    Timber.i(credentials.toString())
                    EspressoService.buildInterface(this@LoginActivity)
                    credentialsManager.saveCredentials(credentials)

                    runOnUiThread {
                        showNextActivity()
                    }
                }

            })
    }

    fun settings(view: View) {
        val intent = Intent(this@LoginActivity, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun logout() {

        WebAuthProvider.logout(auth0)
            .withScheme(SCHEME)
            .start(this, object : VoidCallback {
                /**
                 * Method called on Auth0 API request failure
                 *
                 * @param error The reason of the failure
                 */
                override fun onFailure(error: Auth0Exception) {
                    Timber.w(error)
                    //Log out canceled, keep the user logged in
                    showNextActivity()
                }

                /**
                 * Method called on success with the payload or null.
                 *
                 * @param payload Request payload or null
                 */
                override fun onSuccess(payload: Void?) {

                    Timber.i("Logged Out")
                    credentialsManager.clearCredentials() // no longer valid
                }

            })
    }

    private fun showNextActivity() {
        credentialsManager.getCredentials(object : BaseCallback<Credentials, CredentialsManagerException> {
            /**
             * Method called on Auth0 API request failure
             *
             * @param error The reason of the failure
             */
            override fun onFailure(error: CredentialsManagerException) {
                Timber.w(error)
                runOnUiThread {
                    Toast.makeText(this@LoginActivity,
                        error.localizedMessage,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }

            /**
             * Method called on success with the payload or null.
             *
             * @param payload Request payload or null
             */
            override fun onSuccess(payload: Credentials?) {
                Timber.d(payload?.accessToken.toString())
                EspressoService.setCredentials(payload)


                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })

    }

}
