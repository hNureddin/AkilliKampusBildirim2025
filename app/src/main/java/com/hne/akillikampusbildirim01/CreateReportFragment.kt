package com.hne.akillikampusbildirim01

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class CreateReportFragment : Fragment(R.layout.fragment_create_report) {

    private var pickedLat: Double? = null
    private var pickedLng: Double? = null

    private val REQ_LOCATION = 1001

    private lateinit var tvLocation: TextView
    private lateinit var tvError: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val spinnerType = view.findViewById<Spinner>(R.id.spinnerType)
        val btnUseGps = view.findViewById<Button>(R.id.btnUseGps)
        tvLocation = view.findViewById(R.id.tvLocation)
        val btnSend = view.findViewById<Button>(R.id.btnSend)
        tvError = view.findViewById(R.id.tvError)

        val types = listOf("Health", "Security", "Fire", "Accident", "Other")
        spinnerType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            types
        )

        btnUseGps.setOnClickListener {
            tvError.text = ""
            requestGpsAndFetch()
        }

        btnSend.setOnClickListener {
            tvError.text = ""

            val title = etTitle.text.toString().trim()
            val desc = etDescription.text.toString().trim()
            val type = spinnerType.selectedItem.toString()

            if (title.isEmpty() || desc.isEmpty()) {
                tvError.text = "Please Enter Title and Descreption."
                return@setOnClickListener
            }

            val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val username = prefs.getString("username", "") ?: ""

            val lat = pickedLat ?: 39.92077
            val lng = pickedLng ?: 32.85411

            val newReport = Report(
                title = title,
                description = desc,
                status = "Status: Open",
                time = "Now",
                type = type,
                ownerUsername = username,
                lat = lat,
                lng = lng
            )

            ReportRepository.addReport(newReport)
            requireActivity().onBackPressed()
        }
    }

    private fun requestGpsAndFetch() {
        val hasFine = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQ_LOCATION
            )
            return
        }

        fetchLocation()
    }

    private fun fetchLocation() {

        val hasFine = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarse = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) {
            tvLocation.text = "Location permission not granted."
            return
        }

        val fused = LocationServices.getFusedLocationProviderClient(requireActivity())
        tvLocation.text = "Location loading..."

        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { loc ->
                if (loc != null) {
                    pickedLat = loc.latitude
                    pickedLng = loc.longitude
                    tvLocation.text =
                        "Location: ${"%.5f".format(pickedLat)} , ${"%.5f".format(pickedLng)}"
                } else {
                    tvLocation.text = "Location is null. Will take default location."
                }
            }
            .addOnFailureListener {
                tvLocation.text = "Location failed. Will take default location."
            }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_LOCATION) {
            val granted = grantResults.isNotEmpty() && grantResults.any { it == PackageManager.PERMISSION_GRANTED }
            if (granted) {
                fetchLocation()
            } else {
                tvError.text = "Location permission denied. Will take default location."
            }
        }
    }
}
