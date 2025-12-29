package com.hne.akillikampusbildirim01

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reset_password_activity)

        val etEmail = findViewById<EditText>(R.id.editTextResetEmail)
        val btnSend = findViewById<Button>(R.id.buttonSendReset)
        val tvInfo = findViewById<TextView>(R.id.textViewResetInfo)

        btnSend.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isEmpty() || !email.contains("@")) {
                tvInfo.setTextColor(Color.RED)
                tvInfo.text = "Geçerli bir e-posta girin."
                return@setOnClickListener
            }

            // ✅ Simulation message
            tvInfo.setTextColor(Color.GREEN)
            tvInfo.text = "Şifre sıfırlama bağlantısı e-postaya gönderildi (simülasyon)."
        }
    }
}
