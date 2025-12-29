package com.hne.akillikampusbildirim01

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment

class EditReportFragment : Fragment(R.layout.fragment_edit_report) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = requireArguments().getInt("report_index")
        val report = ReportRepository.reports[index]

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val spinnerType = view.findViewById<Spinner>(R.id.spinnerType)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        val types = listOf("Health", "Security", "Fire", "Accident", "Other")
        spinnerType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)

        // Fill current values
        etTitle.setText(report.title)
        etDescription.setText(report.description)
        val typeIndex = types.indexOf(report.type).coerceAtLeast(0)
        spinnerType.setSelection(typeIndex)

        btnSave.setOnClickListener {
            val newTitle = etTitle.text.toString().trim()
            val newDesc = etDescription.text.toString().trim()
            val newType = spinnerType.selectedItem.toString()

            if (newTitle.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill title & description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            report.title = newTitle
            report.description = newDesc
            // لو بدك type قابل للتعديل لازم type يكون var أيضًا
            // report.type = newType

            requireActivity().onBackPressed()
        }
    }

    companion object {
        fun newInstance(index: Int) = EditReportFragment().apply {
            arguments = Bundle().apply { putInt("report_index", index) }
        }
    }
}
