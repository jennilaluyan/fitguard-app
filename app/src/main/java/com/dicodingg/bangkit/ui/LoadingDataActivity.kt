package com.dicodingg.bangkit.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.dicodingg.bangkit.R

class LoadingDataActivity : AppCompatActivity() {

    private lateinit var lottieAnimation: LottieAnimationView
    private lateinit var loadingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_data)

        // Inisialisasi Lottie Animation dan TextView
        lottieAnimation = findViewById(R.id.lottieAnimation)
        loadingText = findViewById(R.id.loadingText)

        // Menampilkan animasi dan teks loading
        showLoading()

        // Simulasi proses loading selama 3 detik
        Handler(Looper.getMainLooper()).postDelayed({
            // Sembunyikan loading setelah selesai
            hideLoading()

            // Pindah ke LoginActivity setelah proses selesai
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Tutup LoadingDataActivity
        }, 3000) // Durasi 3 detik
    }

    // Fungsi untuk menampilkan loading
    private fun showLoading() {
        lottieAnimation.playAnimation()
        loadingText.text = "Mengambil data...\nSebentar ya!"
    }

    // Fungsi untuk menyembunyikan loading
    private fun hideLoading() {
        lottieAnimation.cancelAnimation()
        loadingText.text = "Proses selesai!"
    }
}
