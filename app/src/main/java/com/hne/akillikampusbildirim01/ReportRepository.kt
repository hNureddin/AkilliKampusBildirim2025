package com.hne.akillikampusbildirim01

object ReportRepository {

    val reports = mutableListOf(
        Report("Yangın İhbarı", "Kampüs girişinde duman görülüyor", "Durum: Açık", "5 dakika önce"),
        Report("Güvenlik", "Kütüphanede şüpheli kişi", "Durum: İnceleniyor", "12 dakika önce"),
        Report("Sağlık", "Bir öğrenci bayıldı", "Durum: Açık", "20 dakika önce"),
        Report("Kayıp Eşya", "Cüzdan bulundu", "Durum: Çözüldü", "1 saat önce"),
        Report("Arıza", "Aydınlatma çalışmıyor", "Durum: Açık", "2 saat önce")
    )

    fun addReport(report: Report) {
        reports.add(0, report)
    }
}
