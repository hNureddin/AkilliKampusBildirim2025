package com.hne.akillikampusbildirim01

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_account_activity)

        val etFullName = findViewById<EditText>(R.id.editTextFullName)
        val etDept = findViewById<EditText>(R.id.editTextDepartment)
        val etEmail = findViewById<EditText>(R.id.editTextNewEmail)
        val etPass = findViewById<EditText>(R.id.editTextNewPassword)
        val etConfirm = findViewById<EditText>(R.id.editTextConfirmPassword)

        val btnCreate = findViewById<Button>(R.id.buttonCreate)
        val btnBack = findViewById<Button>(R.id.buttonBackToLogin)
        val tvResult = findViewById<TextView>(R.id.textViewCreateResult)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        btnBack.setOnClickListener { finish() }

        btnCreate.setOnClickListener {
            tvResult.setTextColor(Color.RED)

            val fullName = etFullName.text.toString().trim()
            val dept = etDept.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()
            val confirm = etConfirm.text.toString().trim()

            if (fullName.isEmpty() || dept.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                tvResult.text = "Lütfen tüm alanları doldurun."
                return@setOnClickListener
            }
            if (!email.contains("@")) {
                tvResult.text = "Geçerli bir e-posta girin."
                return@setOnClickListener
            }
            if (pass.length < 4) {
                tvResult.text = "Şifre en az 4 karakter olmalı."
                return@setOnClickListener
            }
            if (pass != confirm) {
                tvResult.text = "Şifreler eşleşmiyor."
                return@setOnClickListener
            }

            val keyPass = "user_$email"
            if (prefs.getString(keyPass, null) != null) {
                tvResult.text = "Bu e-posta ile hesap zaten var."
                return@setOnClickListener
            }

            // ✅ Save user info (default role User)
            prefs.edit()
                .putString(keyPass, pass)
                .putString("user_fullname_$email", fullName)
                .putString("user_dept_$email", dept)
                .putString("user_role_$email", "User")
                .apply()

            tvResult.setTextColor(Color.GREEN)
            tvResult.text = "Kayıt başarılı! Giriş yapabilirsiniz."

            finish()
        }
    }
}
