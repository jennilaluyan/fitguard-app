package com.dicodingg.bangkit.ui

import android.app.Application
import android.content.res.Configuration
import java.util.Locale
import com.google.firebase.FirebaseApp

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Set locale secara manual jika diperlukan
        val locale = Locale("id")  // Misalnya, menggunakan bahasa Indonesia
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)  // Update locale di context aplikasi

        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this)
    }
}
