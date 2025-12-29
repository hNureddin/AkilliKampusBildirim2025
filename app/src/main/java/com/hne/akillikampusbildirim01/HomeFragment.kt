package com.hne.akillikampusbildirim01

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "User") ?: "User"

        view.findViewById<TextView>(R.id.tvWelcome).text =
            "Welcome, $username"

        view.findViewById<Button>(R.id.btnCreate).setOnClickListener {
            openFragment(CreateReportFragment())
        }

        view.findViewById<Button>(R.id.btnList).setOnClickListener {
            openFragment(ReportListFragment())
        }

        view.findViewById<Button>(R.id.btnMap).setOnClickListener {
            openFragment(MapFragment())
        }
    }

    private fun openFragment(f: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, f)
            .addToBackStack(null)
            .commit()
    }
}
