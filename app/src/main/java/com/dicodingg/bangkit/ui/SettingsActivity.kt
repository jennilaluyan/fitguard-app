package com.dicodingg.bangkit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up back button functionality (for now, it doesn't perform any action)
        binding.backButton.setOnClickListener {
            onBackPressed() // Use onBackPressed to go back
        }
    }
}
