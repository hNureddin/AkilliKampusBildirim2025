package com.hne.akillikampusbildirim01

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcome)
        val tvRole = view.findViewById<TextView>(R.id.tvRole)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "User") ?: "User"
        val isAdmin = prefs.getBoolean("is_admin", false)

        tvWelcome.text = "Welcome, $username"
        tvRole.text = if (isAdmin) "Role: Admin" else "Role: User"

        btnLogout.setOnClickListener {
            prefs.edit()
                .putBoolean("is_admin", false)
                .putString("username", "")
                .apply()

            val intent = Intent(requireContext(), LoginScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
