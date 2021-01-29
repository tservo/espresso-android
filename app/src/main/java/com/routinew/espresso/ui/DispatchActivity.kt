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

        LoginService.getCredentials(this)

    }
}