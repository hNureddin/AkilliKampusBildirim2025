package com.hne.akillikampusbildirim01

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginScreen : AppCompatActivity() {
    private val accounts = listOf(
        Account(username = "admin", password = "admin1234", isAdmin = true),
        Account(username = "user", password = "1234", isAdmin = false),
        Account(username = "nureddin", password = "nureddin1234", isAdmin = false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        buttonSubmit.setOnClickListener {

            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // ابحث عن حساب مطابق
            val matchedAccount = accounts.firstOrNull {
                it.username == username && it.password == password
            }

            if (matchedAccount != null) {
                val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                prefs.edit()
                    .putBoolean("is_admin", matchedAccount.isAdmin)
                    .putString("username", matchedAccount.username)
                    .apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                textViewResult.text = getString(R.string.login_error)
                textViewResult.setTextColor(Color.RED)

                val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                prefs.edit()
                    .putBoolean("is_admin", false)
                    .putString("username", "")
                    .apply()
            }
        }
    }
}
data class Account(
    val username: String,
    val password: String,
    val isAdmin: Boolean
)
