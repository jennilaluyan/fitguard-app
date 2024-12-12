package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.DialogAddMedicationBinding
import com.dicodingg.bangkit.ui.medication.Medication
import com.dicodingg.bangkit.ui.medication.MedicationViewModel
import com.dicodingg.bangkit.data.notification.NotificationUtils
import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.AdapterView

class AddMedicationActivity : AppCompatActivity() {

    private lateinit var binding: DialogAddMedicationBinding
    private lateinit var medicationViewModel: MedicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicationViewModel = ViewModelProvider(this).get(MedicationViewModel::class.java)

        val frequencyOptions = arrayOf("Harian", "Mingguan", "Bulanan")
        val frequencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencyOptions)
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.frequencySpinner.adapter = frequencyAdapter

        // Set spinner text color
        binding.frequencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (view as? TextView)?.setTextColor(Color.BLACK)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.saveButton.setOnClickListener {
            saveMedication()
        }
    }

    private fun saveMedication() {
        val medicineName = binding.editNamaObat.text.toString().trim()
        val dosage = binding.editDosis.text.toString().trim()
        val frequency = binding.frequencySpinner.selectedItem.toString()

        val times = getTimesFromUI() // A method to get times from the UI, e.g., ["07:00"]

        // Create a Medication object
        val medication = Medication(
            nama = medicineName,
            dosis = dosage,
            tanggal = "",
            jam = times.joinToString(", "), // e.g., "07:00, 08:00"
            frekuensi = frequency,
            waktu = "Sebelum Makan", // example, use the actual selected value
            jenisObat = "Tablet" // example, use the actual selected value
        )

        // Save the medication to the ViewModel
        medicationViewModel.addMedication(medication)

        // Schedule notifications based on the medication times
        scheduleMedicationNotification(medication)

        // Show success message and close activity
        Toast.makeText(this, "Obat berhasil disimpan", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun scheduleMedicationNotification(medication: Medication) {
        val times = medication.jam.split(", ") // Split times by commas
        times.forEach { time ->
            val timeParts = time.split(":")
            val hour = timeParts[0].toInt() // Hour part
            val minute = timeParts[1].toInt() // Minute part

            // Pass only the medication name (medication.nama) to NotificationUtils
            NotificationUtils.scheduleMedicationNotification(applicationContext, hour, minute, medication.nama)
        }
    }

    private fun getTimesFromUI(): List<String> {
        // You need to extract time from UI components (e.g., TimePicker or EditText) here
        return listOf("07:00", "08:00") // Example, this should be dynamic
    }
}
