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

        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val email = prefs.getString("username", "-") ?: "-"
        val isAdmin = prefs.getBoolean("is_admin", false)

        var fullName = prefs.getString("full_name", null)
        var dept = prefs.getString("department", null)

        if (!isAdmin && (fullName == null || dept == null)) {
            fullName = prefs.getString("user_fullname_$email", "-")
            dept = prefs.getString("user_dept_$email", "-")
        }

        val roleText = if (isAdmin) "Admin" else "User"

        view.findViewById<TextView>(R.id.tvEmail).text = email
        view.findViewById<TextView>(R.id.tvRole).text = roleText
        view.findViewById<TextView>(R.id.tvFullName).text = fullName ?: "-"
        view.findViewById<TextView>(R.id.tvDepartment).text = dept ?: "-"

        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            prefs.edit()
                .remove("username")
                .remove("is_admin")
                .remove("full_name")
                .remove("department")
                .apply()

            val i = Intent(requireActivity(), LoginScreen::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}
