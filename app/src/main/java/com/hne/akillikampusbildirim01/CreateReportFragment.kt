package com.hne.akillikampusbildirim01

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment

class CreateReportFragment : Fragment(R.layout.fragment_create_report) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val spinnerType = view.findViewById<Spinner>(R.id.spinnerType)
        val btnSend = view.findViewById<Button>(R.id.btnSend)
        val tvError = view.findViewById<TextView>(R.id.tvError)

        val types = listOf("Health", "Security", "Fire", "Fault", "Other")
        spinnerType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            types
        )

        btnSend.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val desc = etDescription.text.toString().trim()
            val type = spinnerType.selectedItem.toString()

            if (title.isEmpty() || desc.isEmpty()) {
                tvError.text = "Please Enter Title and Descreption"
                return@setOnClickListener
            }

            val newReport = Report(
                title = "[$type] $title",
                description = desc,
                status = "Status: Open",
                time = "Now"
            )

            ReportRepository.addReport(newReport)

            requireActivity().onBackPressed()
        }
    }
}
