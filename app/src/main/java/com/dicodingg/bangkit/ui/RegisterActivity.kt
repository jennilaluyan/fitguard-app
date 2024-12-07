package com.dicodingg.bangkit.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Initialize Views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Set initial alpha to 0 to hide views
        editTextEmail.alpha = 0f
        editTextPassword.alpha = 0f
        editTextConfirmPassword.alpha = 0f
        buttonRegister.alpha = 0f

        // Start animations
        animateViews()

        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            // Validate input
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register user in Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // If registration is successful, send verification email
                        val user = auth.currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { verificationTask ->
                                if (verificationTask.isSuccessful) {
                                    Toast.makeText(this, "Registrasi berhasil. Verifikasi email telah dikirim.", Toast.LENGTH_LONG).show()

                                    // Pass user information to EmailVerificationActivity
                                    val intent = Intent(this, EmailVerificationActivity::class.java)
                                    intent.putExtra("USER_EMAIL", user.email)
                                    intent.putExtra("USER_NAME", user.displayName)
                                    startActivity(intent)

                                    finish() // Close RegisterActivity after successful registration
                                } else {
                                    Toast.makeText(this, "Gagal mengirim email verifikasi: ${verificationTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        // If registration fails, show error message
                        Toast.makeText(this, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun animateViews() {
        // Animasi untuk email field
        val emailFadeIn = ObjectAnimator.ofFloat(editTextEmail, "alpha", 0f, 1f)

        val emailAnimation = AnimatorSet().apply {
            playTogether(emailFadeIn,)
            duration = 1000
        }

        // Animasi untuk password field
        val passwordFadeIn = ObjectAnimator.ofFloat(editTextPassword, "alpha", 0f, 1f)

        val passwordAnimation = AnimatorSet().apply {
            playTogether(passwordFadeIn,)
            duration = 1000
        }

        // Animasi untuk confirm password field
        val confirmPasswordFadeIn = ObjectAnimator.ofFloat(editTextConfirmPassword, "alpha", 0f, 1f)
        val confirmPasswordAnimation = AnimatorSet().apply {
            playTogether(confirmPasswordFadeIn, )
            duration = 1000
        }

        // Animasi untuk tombol daftar
        val buttonRegisterFadeIn = ObjectAnimator.ofFloat(buttonRegister, "alpha", 0f, 1f)
        val buttonRegisterSlideUp = ObjectAnimator.ofFloat(buttonRegister, "translationY", 100f, 0f)
        val buttonRegisterAnimation = AnimatorSet().apply {
            playTogether(buttonRegisterFadeIn, buttonRegisterSlideUp)
            duration = 1000
        }

        // Gabungkan animasi dalam urutan
        AnimatorSet().apply {
            playSequentially(emailAnimation, passwordAnimation, confirmPasswordAnimation, buttonRegisterAnimation)
            start()
        }
    }
}
