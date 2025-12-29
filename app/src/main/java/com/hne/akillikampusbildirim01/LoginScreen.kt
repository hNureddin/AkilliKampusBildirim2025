package com.hne.akillikampusbildirim01

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginScreen : AppCompatActivity() {

    private val adminEmail = "admin@atauni.ogr.edu.tr"
    private val adminPassword = "admin123"
    private val adminFullName = "Admin"

    // Default regular user
    private val defaultUserEmail = "nureddin@atauni.ogr.edu.tr"
    private val defaultUserPassword = "nur1234"
    private val defaultUserFullName = "Nureddin"
    private val defaultUserDepartment = "Computer Engineering"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)
        val btnCreate = findViewById<Button>(R.id.buttonCreateAccount)
        val tvResult = findViewById<TextView>(R.id.textViewResult)
        val btnForgot = findViewById<TextView>(R.id.textViewForgotPassword)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        // Initialize default user if not exists
        initializeDefaultUser(prefs)

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
                    .putString("full_name", adminFullName)
                    .apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // Default user (hardcoded)
            if (email == defaultUserEmail && pass == defaultUserPassword) {
                prefs.edit()
                    .putString("username", email)
                    .putBoolean("is_admin", false)
                    .putString("full_name", defaultUserFullName)
                    .putString("department", defaultUserDepartment)
                    .apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // User (local from SharedPreferences)
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

    private fun initializeDefaultUser(prefs: android.content.SharedPreferences) {
        // Check if default user already exists in SharedPreferences
        val userExists = prefs.contains("user_$defaultUserEmail")

        if (!userExists) {
            // Add default user to SharedPreferences for consistency
            prefs.edit()
                .putString("user_$defaultUserEmail", defaultUserPassword)
                .putString("user_fullname_$defaultUserEmail", defaultUserFullName)
                .putString("user_dept_$defaultUserEmail", defaultUserDepartment)
                .apply()
        }
    }
}