package com.hne.akillikampusbildirim01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        buttonSubmit.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username == "admin" && password == "admin1234") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                textViewResult.text = getString(R.string.login_error)
                textViewResult.setTextColor(Color.RED)
            }
        }
    }
}