package com.routinew.espresso.data

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.routinew.espresso.R
import com.routinew.espresso.ui.DispatchActivity
import com.routinew.espresso.ui.MainActivity
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

object LoginService {
    const val EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS"
    const val EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN"
    const val SCHEME = "demo"

    lateinit var auth0: Auth0
    lateinit var credentialsManager: SecureCredentialsManager
    private lateinit var context: Context

    // webauthprovider builder.
    private fun LoginBuilder() = WebAuthProvider.login(auth0).run {
            withScheme(SCHEME)
            withAudience(context.getString(R.string.com_auth0_audience))
            withScope("openid offline_access read:restaurants")
    }

    private fun LogoutBuilder() =  WebAuthProvider.logout(auth0).run {
        withScheme(SCHEME)
    }

    fun initialize(context: Context) {
        this.context = context.applicationContext
        auth0 = Auth0(this.context).apply {
            isOIDCConformant = true
            isLoggingEnabled = true
        }

        credentialsManager = SecureCredentialsManager(this.context,
            AuthenticationAPIClient(auth0),
            SharedPreferencesStorage(this.context)
        )
    }

    /**
     *  Tries to log in.  Uses the activity specified (Usually LoginActivity)
     */
    fun login(activity: Activity) = LoginBuilder().start(activity,object : AuthCallback {
            /**
             * Called when the failure reason is displayed in a [android.app.Dialog].
             *
             * @param dialog error dialog
             */
            override fun onFailure(dialog: Dialog) {
                activity.runOnUiThread { dialog.show() }
            }

            /**
             * Called with an AuthenticationException that describes the error.
             *
             * @param exception cause of the error
             */
            override fun onFailure(exception: AuthenticationException) {
                Timber.w(exception)
                Timber.w(exception.description)
                activity.run {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "Error: " + exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            /**
             * Called when the authentication is successful using web authentication against Auth0
             *
             * @param credentials Auth0 credentials information (id_token, refresh_token, etc).
             */
            override fun onSuccess(credentials: Credentials) {
                Timber.i(credentials.toString())
                credentialsManager.saveCredentials(credentials)

                activity.run {
                    runOnUiThread {
                        // this will see that we have new credentials and go to the correct location
                        val intent = Intent(this, DispatchActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })

    fun logout(activity: Activity) = LogoutBuilder().start(activity, object : VoidCallback {
        /**
         * Method called on Auth0 API request failure
         *
         * @param error The reason of the failure
         */
        override fun onFailure(error: Auth0Exception) {
            Timber.w(error)
            //Log out canceled, keep the user logged in
        }

        /**
         * Method called on success with the payload or null.
         *
         * @param payload Request payload or null
         */
        override fun onSuccess(payload: Void?) {

            Timber.i("Logged Out")
            credentialsManager.clearCredentials() // no longer valid

            activity.run {
                runOnUiThread {
                    // this will see that we have new credentials and go to the correct location
                    val intent = Intent(this, DispatchActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    })
}


