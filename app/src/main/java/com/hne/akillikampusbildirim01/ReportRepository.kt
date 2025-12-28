package com.hne.akillikampusbildirim01

object ReportRepository {

    val reports = mutableListOf(
        Report("Yangın İhbarı", "Kampüs girişinde duman görülüyor", "Durum: Açık", "5 dakika önce",locationName = "Kampüs Girişi", lat = 39.92077, lng = 32.85411),
        Report("Güvenlik", "Kütüphanede şüpheli kişi", "Durum: İnceleniyor", "12 dakika önce",locationName = "Kütüphane",  lat = 39.92150, lng = 32.85320),
        Report("Sağlık", "Bir öğrenci bayıldı", "Durum: Açık", "20 dakika önce",locationName = "Yemekhane",  lat = 39.92088, lng = 32.85390),
        Report("Kayıp Eşya", "Cüzdan bulundu", "Durum: Çözüldü", "1 saat önce",locationName = "Mühendislik Fakültesi",  lat = 39.92150, lng = 32.85320),
        Report("Arıza", "Aydınlatma çalışmıyor", "Durum: Açık", "2 saat önce",locationName = "Edebiyat Fakültesi",  lat = 39.92150, lng = 32.85320)
    )
    val locations = listOf(
        LocationItem("Kampüs Girişi", 39.92077, 32.85411),
        LocationItem("Kütüphane", 39.92150, 32.85320),
        LocationItem("Yurt", 39.91980, 32.85540),
        LocationItem("Spor Salonu", 39.92210, 32.85260)
    )
    fun addReport(report: Report) {
        reports.add(0, report)
    }
    data class LocationItem(val name: String, val lat: Double, val lng: Double)

}
