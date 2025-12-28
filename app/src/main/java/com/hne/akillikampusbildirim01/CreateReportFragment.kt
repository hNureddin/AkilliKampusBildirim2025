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

class CreateReportFragment : Fragment(R.layout.fragment_create_report) {

    private var pickedLat: Double? = null
    private var pickedLng: Double? = null

    private val REQ_LOCATION = 1001

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val spinnerType = view.findViewById<Spinner>(R.id.spinnerType)
        val btnUseGps = view.findViewById<Button>(R.id.btnUseGps)
        val tvLocation = view.findViewById<TextView>(R.id.tvLocation)
        val btnSend = view.findViewById<Button>(R.id.btnSend)
        val tvError = view.findViewById<TextView>(R.id.tvError)

        // Types
        val types = listOf("Sağlık", "Güvenlik", "Yangın", "Arıza", "Diğer")
        spinnerType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            types
        )

        btnUseGps.setOnClickListener {
            tvError.text = ""
            requestGpsAndFetch(tvLocation)
        }

        btnSend.setOnClickListener {
            tvError.text = ""

            val title = etTitle.text.toString().trim()
            val desc = etDescription.text.toString().trim()
            val type = spinnerType.selectedItem.toString()

            if (title.isEmpty() || desc.isEmpty()) {
                tvError.text = "Lütfen başlık ve açıklama girin."
                return@setOnClickListener
            }

            // owner + role
            val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val username = prefs.getString("username", "") ?: ""

            // fallback location if GPS not taken
            val lat = pickedLat ?: 39.92077
            val lng = pickedLng ?: 32.85411

            val newReport = Report(
                title = title,
                description = desc,
                status = "Durum: Açık",
                time = "Şimdi",
                type = type,
                ownerUsername = username,
                lat = lat,
                lng = lng
            )

            ReportRepository.addReport(newReport)
            requireActivity().onBackPressed()
        }
    }

    private fun requestGpsAndFetch(tvLocation: TextView) {
        val hasFine = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQ_LOCATION
            )
            return
        }

        fetchLocation(tvLocation)
    }

    private fun fetchLocation(tvLocation: TextView) {
        val fused = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Last known location (سريع، وقد يكون null)
        fused.lastLocation
            .addOnSuccessListener { loc ->
                if (loc != null) {
                    pickedLat = loc.latitude
                    pickedLng = loc.longitude
                    tvLocation.text = "Konum: ${"%.5f".format(pickedLat)} , ${"%.5f".format(pickedLng)}"
                } else {
                    tvLocation.text = "Konum alınamadı (GPS kapalı olabilir). Varsayılan konum kullanılacak."
                }
            }
            .addOnFailureListener {
                tvLocation.text = "Konum alınamadı. Varsayılan konum kullanılacak."
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_LOCATION) {
            // بعد الموافقة، سيتم جلب الموقع عند الضغط مرة ثانية (أبسط)
            // إذا تريد تلقائيًا: يمكننا إعادة النداء مع tvLocation لكن نحتاج مرجع.
        }
    }
}
