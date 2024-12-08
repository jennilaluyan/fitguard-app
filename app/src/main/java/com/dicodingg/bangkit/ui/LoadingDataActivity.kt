package com.dicodingg.bangkit.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R

class LoadingDataActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var loadingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_data)

        // Inisialisasi ProgressBar dan TextView
        progressBar = findViewById(R.id.progressBar)
        loadingText = findViewById(R.id.loadingText)

        // Menampilkan halaman loading
        showLoading()

        // Simulasi proses, misalnya pengiriman data
        Handler(Looper.getMainLooper()).postDelayed({
            // Sembunyikan loading setelah proses selesai
            hideLoading()

            // Pindah ke LoginActivity setelah 3 detik
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Finish LoadingDataActivity agar tidak kembali ke halaman ini
        }, 3000) // Delay 3 detik untuk simulasi proses
    }

    // Fungsi untuk menampilkan halaman loading
    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        loadingText.visibility = View.VISIBLE
    }

    // Fungsi untuk menyembunyikan halaman loading
    private fun hideLoading() {
        progressBar.visibility = View.GONE
        loadingText.visibility = View.GONE
    }
}
