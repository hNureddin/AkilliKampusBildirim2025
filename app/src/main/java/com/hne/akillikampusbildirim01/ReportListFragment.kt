package com.hne.akillikampusbildirim01

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReportListFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnNewReport: View
    private var isAdmin: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_report_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1️⃣ اقرأ هل المستخدم Admin
        val prefs = requireActivity()
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        isAdmin = prefs.getBoolean("is_admin", false)

        // 2️⃣ ربط الواجهات
        recycler = view.findViewById(R.id.recyclerViewReports)
        btnNewReport = view.findViewById(R.id.btnNewReport)

        // 3️⃣ إعداد RecyclerView
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = ReportAdapter(
            ReportRepository.reports,
            isAdmin
        )

        btnNewReport.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CreateReportFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::recycler.isInitialized) {
            recycler.adapter?.notifyDataSetChanged()
        }
    }
}
