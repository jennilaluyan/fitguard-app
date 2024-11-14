package com.dicodingg.bangkit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dicodingg.bangkit.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_register_activity)

        // Inisialisasi Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Menangani klik tombol panah kembali
        findViewById<View>(R.id.buttonBack).setOnClickListener {
            onBackPressed() // Kembali ke Activity sebelumnya
        }

        // Mengatur ViewPager dan TabLayout
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        // Menyiapkan adapter untuk ViewPager2
        val adapter = LoginRegisterPagerAdapter(this)
        viewPager.adapter = adapter

        // Menghubungkan TabLayout dengan ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Daftar" else "Masuk"
        }.attach()
    }

    // Fungsi untuk mendaftarkan pengguna
    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registrasi berhasil
                    Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    // Registrasi gagal
                    Toast.makeText(this, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Fungsi untuk login pengguna
    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login berhasil
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    // Login gagal
                    Toast.makeText(this, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
