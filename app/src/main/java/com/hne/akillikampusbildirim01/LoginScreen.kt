package com.hne.akillikampusbildirim01

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginScreen : AppCompatActivity() {

    private val adminEmail = "admin@university.edu"
    private val adminPassword = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)
        val btnCreate = findViewById<Button>(R.id.buttonCreateAccount)
        val tvResult = findViewById<TextView>(R.id.textViewResult)
        val btnForgot = findViewById<Button>(R.id.buttonForgotPassword)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        btnLogin.setOnClickListener {
            tvResult.setTextColor(Color.RED)

            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                tvResult.text = "Please fill all fields."
                return@setOnClickListener
            }

            // Admin
            if (email == adminEmail && pass == adminPassword) {
                prefs.edit()
                    .putString("username", email)
                    .putBoolean("is_admin", true)
                    .apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // User (local)
            val stored = prefs.getString("user_$email", null)
            if (stored == pass) {
                prefs.edit()
                    .putString("username", email)
                    .putBoolean("is_admin", false)
                    .putString("full_name", prefs.getString("user_fullname_$email","") ?: "")
                    .putString("department", prefs.getString("user_dept_$email","") ?: "")
                    .apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                tvResult.text = "Invalid email or password."
            }
        }
        btnForgot.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
        btnCreate.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }
}
