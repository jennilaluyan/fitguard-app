package com.dicodingg.bangkit.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicodingg.bangkit.R
import com.google.android.material.animation.AnimatorSetCompat.playTogether
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Periksa apakah pengguna sudah login
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Jika pengguna sudah login, langsung ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup WelcomeActivity agar tidak kembali ke sini
            return
        }

        // Jika pengguna belum login, tampilkan halaman welcome
        setupUI()
    }

    // Fungsi untuk menyiapkan UI dan animasi halaman welcome
    private fun setupUI() {
        // Set padding untuk edge-to-edge view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set animasi elemen
        animateElements()

        // Navigasi ke LoginActivity
        findViewById<View>(R.id.buttonLogin).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Navigasi ke RegisterActivity
        findViewById<View>(R.id.buttonRegister).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Tambahkan animasi ke elemen-elemen di layar welcome
    private fun animateElements() {
        // Set initial alpha untuk semua elemen agar tidak terlihat saat memulai
        val logo = findViewById<View>(R.id.imageLogo)
        val welcomeText = findViewById<View>(R.id.textWelcome)
        val descriptionText = findViewById<View>(R.id.textDescription)
        val buttonLogin = findViewById<View>(R.id.buttonLogin)
        val buttonRegister = findViewById<View>(R.id.buttonRegister)

        logo.alpha = 0f
        welcomeText.alpha = 0f
        descriptionText.alpha = 0f
        buttonLogin.alpha = 0f
        buttonRegister.alpha = 0f

        // Animasi fade-in untuk logo
        val logoFadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f).apply {
            duration = 900
        }

        // Animasi fade-in untuk teks selamat datang
        val welcomeTextFadeIn = ObjectAnimator.ofFloat(welcomeText, "alpha", 0f, 1f).apply {
            duration = 800
            startDelay = 50 // Muncul setelah logo
        }

        // Animasi fade-in untuk teks deskripsi
        val descriptionTextFadeIn = ObjectAnimator.ofFloat(descriptionText, "alpha", 0f, 1f).apply {
            duration = 800
            startDelay = 50 // Muncul setelah teks selamat datang
        }

        // Animasi slide-in dan fade-in untuk tombol login
        val buttonLoginFadeIn = ObjectAnimator.ofFloat(buttonLogin, "alpha", 0f, 1f)
        val buttonLoginSlideUp = ObjectAnimator.ofFloat(buttonLogin, "translationY", 100f, 0f)
        val buttonLoginAnimation = AnimatorSet().apply {
            playTogether(buttonLoginFadeIn, buttonLoginSlideUp)
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 50 // Muncul setelah deskripsi teks
        }

        // Animasi slide-in dan fade-in untuk tombol register
        val buttonRegisterFadeIn = ObjectAnimator.ofFloat(buttonRegister, "alpha", 0f, 1f)
        val buttonRegisterSlideUp = ObjectAnimator.ofFloat(buttonRegister, "translationY", 100f, 0f)
        val buttonRegisterAnimation = AnimatorSet().apply {
            playTogether(buttonRegisterFadeIn, buttonRegisterSlideUp)
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 50 // Muncul setelah tombol login
        }

        // Gabungkan semua animasi secara berurutan
        AnimatorSet().apply {
            playSequentially(
                logoFadeIn,
                welcomeTextFadeIn,
                descriptionTextFadeIn,
                buttonLoginAnimation,
                buttonRegisterAnimation
            )
            start()
        }
    }}
