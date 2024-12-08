package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Menginisialisasi tombol kembali
        val backButton = findViewById<android.widget.ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()  // Kembali ke activity sebelumnya
        }

        // Menginisialisasi switch untuk notifikasi
        val switchAlarm = findViewById<Switch>(R.id.switch_alarm)
        // Cek status switch ketika aplikasi dijalankan
        switchAlarm.isChecked = true // Menyeting status awal switch jika perlu

        // Anda bisa menambahkan logika untuk menangani perubahan status switch
        switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Logika untuk mengaktifkan notifikasi
                // Misalnya, menyalakan alarm atau pengaturan lain
            } else {
                // Logika untuk menonaktifkan notifikasi
                // Misalnya, mematikan alarm atau pengaturan lain
            }
        }
    }
}
