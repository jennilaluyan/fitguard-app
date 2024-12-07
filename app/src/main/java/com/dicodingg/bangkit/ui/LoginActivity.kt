package com.dicodingg.bangkit.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.ui.dashboard.DashboardActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)  // Pastikan layoutnya adalah activity_login.xml

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()



        animateViews()

        // Inisialisasi Views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        // Tombol Login
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Validasi input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan kata sandi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Login ke Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Jika login berhasil
                        Toast.makeText(this, "Login berhasil.", Toast.LENGTH_SHORT).show()

                        // Pindah ke halaman utama setelah login berhasil
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish() // Tutup LoginActivity agar tidak bisa kembali ke halaman login
                    } else {
                        // Jika login gagal, tampilkan pesan error
                        Toast.makeText(this, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun animateViews() {
        // Inisialisasi Views
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val registerTextView = findViewById<TextView>(R.id.textViewRegister)

        // Set initial alpha to 0 for password and email fields so they are hidden when activity starts
        emailEditText.alpha = 0f
        passwordEditText.alpha = 0f
        loginButton.alpha = 0f
        registerTextView.alpha = 0f


        // Animasi untuk email field
        val emailFadeIn = ObjectAnimator.ofFloat(emailEditText, "alpha", 0f, 1f)
        val emailAnimation = AnimatorSet().apply {
            playTogether(emailFadeIn,)
            duration = 1000
        }

        // Animasi untuk password field
        val passwordFadeIn = ObjectAnimator.ofFloat(passwordEditText, "alpha", 0f, 1f)
        val passwordAnimation = AnimatorSet().apply {
            playTogether(passwordFadeIn,)
            duration = 1000
        }


        // Animasi untuk tombol login
        val loginButtonFadeIn = ObjectAnimator.ofFloat(loginButton, "alpha", 0f, 1f)
        val loginButtonSlideUp = ObjectAnimator.ofFloat(loginButton, "translationY", 100f, 0f)
        val loginButtonAnimation = AnimatorSet().apply {
            playTogether(loginButtonFadeIn, loginButtonSlideUp)
            duration = 1000
        }

        // Animasi untuk teks register
        val registerFadeIn = ObjectAnimator.ofFloat(registerTextView, "alpha", 0f, 1f)
        val registerSlideUp = ObjectAnimator.ofFloat(registerTextView, "translationY", 100f, 0f)
        val registerAnimation = AnimatorSet().apply {
            playTogether(registerFadeIn, registerSlideUp)
            duration = 1000
        }

        // Gabungkan semua animasi secara berurutan
        AnimatorSet().apply {
            playSequentially(emailAnimation, passwordAnimation, loginButtonAnimation, registerAnimation)
            start()
        }
    }


}
