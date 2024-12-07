package com.dicodingg.bangkit.ui

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
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Set padding for edge to edge view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set animations for elements
        animateElements()

        // Navigate to Login Activity
        findViewById<View>(R.id.buttonLogin).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Register Activity
        findViewById<View>(R.id.buttonRegister).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Add animations to the elements on the welcome screen
    private fun animateElements() {
        // Fade-in logo animation
        val logo = findViewById<View>(R.id.imageLogo)
        ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f).apply {
            duration = 1000
            start()
        }

        // Fade-in text animation
        val welcomeText = findViewById<View>(R.id.textWelcome)
        ObjectAnimator.ofFloat(welcomeText, "alpha", 0f, 1f).apply {
            duration = 1000
            startDelay = 500  // Delay to make it appear after the logo
            start()
        }

        val descriptionText = findViewById<View>(R.id.textDescription)
        ObjectAnimator.ofFloat(descriptionText, "alpha", 0f, 1f).apply {
            duration = 1000
            startDelay = 1000  // Delay to make it appear after the welcome text
            start()
        }

        // Slide-in buttons from the bottom
        val buttonLogin = findViewById<View>(R.id.buttonLogin)
        val buttonRegister = findViewById<View>(R.id.buttonRegister)

        ObjectAnimator.ofFloat(buttonLogin, "translationY", 500f, 0f).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 1500  // Delay to make it appear after description text
            start()
        }

        ObjectAnimator.ofFloat(buttonRegister, "translationY", 500f, 0f).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 2000  // Delay to make it appear after the login button
            start()
        }
    }
}
