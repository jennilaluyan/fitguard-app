package com.dicodingg.bangkit.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R

class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var textViewMessage: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonResendEmail: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        // Inisialisasi komponen UI
        textViewMessage = findViewById(R.id.textViewMessage)
        progressBar = findViewById(R.id.progressBarVerifying)
        buttonResendEmail = findViewById(R.id.buttonResendEmail)
        auth = FirebaseAuth.getInstance()

        // Tampilkan progress bar
        progressBar.visibility = android.view.View.VISIBLE

        // Menunggu verifikasi email
        textViewMessage.text = "Tautan verifikasi telah dikirim ke email Anda. Silakan periksa inbox Anda untuk melanjutkan proses pendaftaran."

        // Tombol kirim ulang email verifikasi
        buttonResendEmail.setOnClickListener {
            sendVerificationEmail()
        }

        // Cek apakah email sudah diverifikasi
        checkEmailVerification()
    }

    override fun onStart() {
        super.onStart()

        // Cek apakah pengguna sudah login dan verifikasi
        val user = auth.currentUser
        if (user != null) {
            checkEmailVerification()  // Periksa status verifikasi saat activity dimulai
        } else {
            // Jika belum login, arahkan ke halaman login
            redirectToLogin()
        }
    }

    private fun checkEmailVerification() {
        val user = auth.currentUser
        user?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Jika email sudah diverifikasi, redirect ke halaman login
                if (user.isEmailVerified) {
                    redirectToLogin()
                } else {
                    // Jika email belum diverifikasi, tampilkan pesan
                    textViewMessage.text = "Silakan verifikasi email Anda terlebih dahulu."

                }
            } else {
                // Tampilkan error jika reload gagal
                textViewMessage.text = "Gagal memuat status verifikasi."
                progressBar.visibility = android.view.View.INVISIBLE
            }
        }
    }

    private fun sendVerificationEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                textViewMessage.text = "Tautan verifikasi email telah dikirim ulang."
            } else {
                textViewMessage.text = "Gagal mengirim email verifikasi: ${task.exception?.message}"
            }
        }
    }

    private fun redirectToLogin() {
        val intent = Intent(this, InputDataActivity::class.java)
        startActivity(intent)
        finish() // Tutup EmailVerificationActivity
    }
}
