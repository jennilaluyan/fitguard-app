package com.dicodingg.bangkit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var welcomeTextView: TextView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inisialisasi Views
        welcomeTextView = findViewById(R.id.welcomeTextView)
        logoutButton = findViewById(R.id.logoutButton)

        // Mendapatkan nama pertama dari email pengguna dan memfilter hanya huruf
        val user = auth.currentUser
        val email = user?.email
        val firstName = email?.substringBefore("@")?.filter { it.isLetter() } ?: "User"

        // Menampilkan pesan selamat datang dengan nama pertama
        welcomeTextView.text = "Welcome, $firstName"

        // Fungsi tombol logout
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java) // Ganti dengan login activity
            startActivity(intent)
            finish()  // Menutup HomeActivity agar tidak bisa kembali ke sini
            Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show()
        }
    }
}
