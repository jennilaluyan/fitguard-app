package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.ActivityHealthRecordBinding

class HealthRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle back button click
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Handle add data button click
        binding.addDataButton.setOnClickListener {
            val dialog = AddDataDialogFragment { dataType, dataValue, date ->
                when (dataType) {
                    "Glucose Levels" -> {
                        binding.glucoseCard.findViewById<TextView>(R.id.glucoseValue).text = dataValue
                        binding.glucoseCard.findViewById<TextView>(R.id.glucoseDate).text = date
                    }
                    "Blood Pressure" -> {
                        binding.bloodPressureCard.findViewById<TextView>(R.id.bloodPressureValue).text = dataValue
                        binding.bloodPressureCard.findViewById<TextView>(R.id.bloodPressureDate).text = date
                    }
                }
            }
            dialog.show(supportFragmentManager, "AddDataDialogFragment")
        }
    }
}
