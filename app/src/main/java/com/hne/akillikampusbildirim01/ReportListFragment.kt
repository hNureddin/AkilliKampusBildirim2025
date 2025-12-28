package com.hne.akillikampusbildirim01

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReportListFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnNewReport: View

    private var isAdmin = false
    private var username: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_report_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        isAdmin = prefs.getBoolean("is_admin", false)
        username = prefs.getString("username", "") ?: ""

        recycler = view.findViewById(R.id.recyclerViewReports)
        btnNewReport = view.findViewById(R.id.btnNewReport)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        bindList()

        btnNewReport.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CreateReportFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun bindList() {
        val listToShow = if (isAdmin) {
            ReportRepository.reports
        } else {
            ReportRepository.reports.filter { it.ownerUsername == username }
        }

        recycler.adapter = ReportAdapter(listToShow, isAdmin)
    }

    override fun onResume() {
        super.onResume()
        if (::recycler.isInitialized) {
            // إعادة ربط القائمة حتى تتحدث الفلترة + التغييرات
            bindList()
        }
    }
}
