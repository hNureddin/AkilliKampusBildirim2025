package com.hne.akillikampusbildirim01
data class Report(
    var title: String,
    var description: String,
    var status: String,
    val time: String,
    val type: String = "Other",
    val ownerUsername: String = "",
    val lat: Double = 39.92077,
    val lng: Double = 32.85411
)
