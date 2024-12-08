package com.dicodingg.bangkit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.databinding.ActivityPhysicalActivityBinding

class PhysicalActivityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhysicalActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhysicalActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up back button to return to the dashboard
        binding.backButton.setOnClickListener {
            onBackPressed() // Use onBackPressed to go back
        }
    }
}
