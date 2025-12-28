package com.hne.akillikampusbildirim01

object ReportRepository {

    val reports = mutableListOf(
        Report("Yangın İhbarı", "Kampüs girişinde duman görülüyor", "Durum: Açık", "5 dakika önce", lat = 39.92077, lng = 32.85411),
        Report("Güvenlik", "Kütüphanede şüpheli kişi", "Durum: İnceleniyor", "12 dakika önce",  lat = 39.92150, lng = 32.85320),
        Report("Sağlık", "Bir öğrenci bayıldı", "Durum: Açık", "20 dakika önce",  lat = 39.92088, lng = 32.85390),
        Report("Kayıp Eşya", "Cüzdan bulundu", "Durum: Çözüldü", "1 saat önce",  lat = 39.92150, lng = 32.85320),
        Report("Arıza", "Aydınlatma çalışmıyor", "Durum: Açık", "2 saat önce",  lat = 39.92150, lng = 32.85320)
    )

    fun addReport(report: Report) {
        reports.add(0, report)
    }
}
