package com.dicodingg.bangkit.ui.medication

data class Medication(
    val id: Long = System.currentTimeMillis(), // Unique ID for each medication
    val nama: String,
    val dosis: String,
    val tanggal: String,
    val jam: String, // Store time as a string (e.g., "07:00")
    val frekuensi: String,
    val waktu: String,
    val jenisObat: String
)

