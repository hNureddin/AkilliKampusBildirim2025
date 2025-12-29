package com.hne.akillikampusbildirim01

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment

class ReportDetailsFragment : Fragment(R.layout.fragment_report_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = requireArguments().getInt("report_index")
        val report = ReportRepository.reports[index]

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvType = view.findViewById<TextView>(R.id.tvType)
        val tvStatus = view.findViewById<TextView>(R.id.tvStatus)
        val tvTime = view.findViewById<TextView>(R.id.tvTime)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvLocation = view.findViewById<TextView>(R.id.tvLocation)

        tvTitle.text = report.title
        tvType.text = "Type: ${report.type}"
        tvStatus.text = report.status
        tvTime.text = "Time: ${report.time}"
        tvDescription.text = report.description
        tvLocation.text = "Location: ${report.lat}, ${report.lng}"

        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isAdmin = prefs.getBoolean("is_admin", false)
        val username = prefs.getString("username", "") ?: ""
        val isOwner = (report.ownerUsername == username)

        val spinnerStatus = view.findViewById<Spinner>(R.id.spinnerStatus)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdateStatus)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)

        // اخفاء افتراضي للجميع
        spinnerStatus.visibility = View.GONE
        btnUpdate.visibility = View.GONE
        btnEdit.visibility = View.GONE

        // ✅ User owner: Edit
        if (!isAdmin && isOwner) {
            btnEdit.visibility = View.VISIBLE
            btnEdit.setOnClickListener {
                val f = EditReportFragment.newInstance(index)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, f)
                    .addToBackStack(null)
                    .commit()
            }
        }

        // ✅ Admin: Update Status
        if (isAdmin) {
            spinnerStatus.visibility = View.VISIBLE
            btnUpdate.visibility = View.VISIBLE

            val statuses = listOf("Status: Open", "Status: In Progress", "Status: Closed")
            spinnerStatus.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                statuses
            )

            val currentIndex = statuses.indexOf(report.status).coerceAtLeast(0)
            spinnerStatus.setSelection(currentIndex)

            btnUpdate.setOnClickListener {
                val newStatus = spinnerStatus.selectedItem.toString()
                report.status = newStatus
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        fun newInstance(index: Int): ReportDetailsFragment {
            return ReportDetailsFragment().apply {
                arguments = Bundle().apply { putInt("report_index", index) }
            }
        }
    }
}
