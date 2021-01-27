package com.routinew.espresso.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.BaseCallback
import com.auth0.android.result.Credentials
import com.routinew.espresso.data.EspressoService
import com.routinew.espresso.data.LoginService
import com.routinew.espresso.ui.login.LoginActivity
import timber.log.Timber

class DispatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val credentialsManager = LoginService.credentialsManager
        if (credentialsManager.hasValidCredentials()) {
             initializeCredentials()
        }
        else {
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun initializeCredentials() {
        LoginService.credentialsManager.getCredentials(object :
            BaseCallback<Credentials, CredentialsManagerException> {
            /**
             * Method called on Auth0 API request failure
             *
             * @param error The reason of the failure
             */
            override fun onFailure(error: CredentialsManagerException) {
                Timber.w(error)
                // we have credentials but we failed to read them?
                runOnUiThread {
                    Toast.makeText(this@DispatchActivity,git 
                        error.localizedMessage,
                        Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this@DispatchActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            /**
             * Method called on success with the payload or null.
             *
             * @param payload Request payload or null
             */
            override fun onSuccess(payload: Credentials?) {
                Timber.d(payload?.accessToken.toString())
                EspressoService.credentials = payload // place this in a user class

                val intent = Intent(this@DispatchActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }
}