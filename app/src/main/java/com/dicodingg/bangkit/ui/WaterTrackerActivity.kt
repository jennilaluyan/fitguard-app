package com.dicodingg.bangkit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.databinding.ActivityWaterTrackerBinding

class WaterTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaterTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up back button to return to the dashboard
        binding.backButton.setOnClickListener {
            onBackPressed() // Use onBackPressed to go back
        }
    }
}
