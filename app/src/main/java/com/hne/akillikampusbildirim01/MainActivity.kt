package com.hne.akillikampusbildirim01

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val reportListBtn = findViewById<Button>(R.id.reportListBtn)
        val profileBtn = findViewById<Button>(R.id.profileBtn)
        val mapBtn = findViewById<Button>(R.id.mapBtn)

        if (savedInstanceState == null) {
            loadFragment(FirstFragment())
        }

        reportListBtn.setOnClickListener {
            loadFragment(ReportListFragment())
        }

        profileBtn.setOnClickListener {
            loadFragment(ProfileFragment())
        }

        mapBtn.setOnClickListener {
            loadFragment(MapFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}