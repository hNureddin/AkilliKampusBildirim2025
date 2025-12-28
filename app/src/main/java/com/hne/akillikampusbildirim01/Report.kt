package com.hne.akillikampusbildirim01

data class Report(
    val title: String,
    val description: String,
    var status: String,
    val time: String,

    val locationName: String = "Kampus",
    val lat: Double = 39.92077,
    val lng: Double = 32.85411
)