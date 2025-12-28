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
        val spinnerLocation = view.findViewById<Spinner>(R.id.spinnerLocation)
        val btnSend = view.findViewById<Button>(R.id.btnSend)
        val tvError = view.findViewById<TextView>(R.id.tvError)
        val locNames = ReportRepository.locations.map { it.name }

        val types = listOf("Health", "Security", "Fire", "Fault", "Other")
        spinnerLocation.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            types
        )
        spinnerLocation.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            locNames
        )

        btnSend.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val desc = etDescription.text.toString().trim()
            if (title.isEmpty() || desc.isEmpty()) return@setOnClickListener

            val selectedIndex = spinnerLocation.selectedItemPosition
            val loc = ReportRepository.locations[selectedIndex]

            val newReport = Report(
                title = title,
                description = desc,
                status = "Durum: Açık",
                time = "Şimdi",
                locationName = loc.name,
                lat = loc.lat,
                lng = loc.lng
            )

            ReportRepository.addReport(newReport)
            requireActivity().onBackPressed()
        }
    }
}
