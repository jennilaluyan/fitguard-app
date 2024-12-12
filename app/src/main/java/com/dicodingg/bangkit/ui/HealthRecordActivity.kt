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
// Handle add data button click
        binding.addDataButton.setOnClickListener {
            val dialog = AddDataDialogFragment { dataType, dataValue, date ->
                when (dataType) {
                    "Tingkat Glukosa" -> {
                        binding.glucoseCard.findViewById<TextView>(R.id.glucoseValue).text = dataValue
                        binding.glucoseCard.findViewById<TextView>(R.id.glucoseDate).text = date
                    }
                    "Tekanan Darah" -> {
                        binding.bloodPressureCard.findViewById<TextView>(R.id.bloodPressureValue).text = dataValue
                        binding.bloodPressureCard.findViewById<TextView>(R.id.bloodPressureDate).text = date
                    }
                    "Dosis Insulin" -> { // Note the exact match with strings.xml
                        binding.insulinDoseCard.findViewById<TextView>(R.id.insulinDoseValue).text = dataValue
                        binding.insulinDoseCard.findViewById<TextView>(R.id.insulinDoseDate).text = date
                    }
                }
            }
            dialog.show(supportFragmentManager, "AddDataDialogFragment")
        }
    }
}
