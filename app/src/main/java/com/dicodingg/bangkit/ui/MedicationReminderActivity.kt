package com.dicodingg.bangkit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.databinding.ActivityMedicationReminderBinding

class MedicationReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicationReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicationReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up back button functionality
        binding.backButton.setOnClickListener {
            onBackPressed() // Use onBackPressed to go back
        }
    }
}
