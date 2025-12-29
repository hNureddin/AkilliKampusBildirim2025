package com.hne.akillikampusbildirim01

object ReportRepository {

    val reports = mutableListOf(
        Report(
            title = "Fire Alert at Campus Entrance",
            description = "Smoke detected near the main gate. Fire department has been notified.",
            status = "Open",
            time = "5 minutes ago",
            type = "Fire Emergency",
            ownerUsername = "admin@atauni.ogr.edu.tr",
            lat = 39.92077,
            lng = 32.85411
        ),
        Report(
            title = "Suspicious Activity in Library",
            description = "A person acting suspiciously near the computer lab area. Security has been alerted.",
            status = "Under Investigation",
            time = "12 minutes ago",
            type = "Security",
            ownerUsername = "nureddin@atauni.ogr.edu.tr",
            lat = 39.92150,
            lng = 32.85320
        ),
        Report(
            title = "Medical Emergency",
            description = "A student fainted in the cafeteria. Medical team is on the way.",
            status = "Open",
            time = "20 minutes ago",
            type = "Health",
            ownerUsername = "student1@atauni.ogr.edu.tr",
            lat = 39.92088,
            lng = 32.85390
        ),
        Report(
            title = "Lost and Found - Wallet",
            description = "A brown leather wallet was found in the sports center. Contains student ID.",
            status = "Resolved",
            time = "1 hour ago",
            type = "Lost & Found",
            ownerUsername = "nureddin@atauni.ogr.edu.tr",
            lat = 39.92150,
            lng = 32.85320
        ),
        Report(
            title = "Lighting System Malfunction",
            description = "Several lights are not working in Building B, 3rd floor corridor.",
            status = "Open",
            time = "2 hours ago",
            type = "Maintenance",
            ownerUsername = "admin@atauni.ogr.edu.tr",
            lat = 39.92150,
            lng = 32.85320
        )
    )

    fun addReport(report: Report) {
        reports.add(0, report)
    }
}