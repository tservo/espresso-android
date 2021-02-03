package com.routinew.espresso.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.routinew.espresso.R
import com.routinew.espresso.data.LoginService
import com.routinew.espresso.databinding.ActivityLoginBinding
import com.routinew.espresso.ui.settings.SettingsActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    // called by button in resources
    fun login(_view: View) = LoginService.login(this)

    fun settings(_view: View) {
        val intent = Intent(this@LoginActivity, SettingsActivity::class.java)
        startActivity(intent)
    }


}
