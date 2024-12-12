package com.dicodingg.bangkit.ui.medication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicodingg.bangkit.databinding.ActivityMedicationReminderBinding
import com.dicodingg.bangkit.databinding.DialogAddMedicationBinding
import com.dicodingg.bangkit.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class MedicationReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicationReminderBinding
    private lateinit var medicationViewModel: MedicationViewModel
    private lateinit var medicationAdapter: MedicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicationReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel setup
        medicationViewModel = ViewModelProvider(this)[MedicationViewModel::class.java]

        // Set up RecyclerView with the MedicationAdapter
        medicationAdapter = MedicationAdapter(
            onDeleteClick = { medication -> medicationViewModel.deleteMedication(medication) }
        )
        binding.recyclerViewMedications.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMedications.adapter = medicationAdapter

        // Observe the medication list from the ViewModel
        medicationViewModel.medications.observe(this) { medications ->
            medicationAdapter.submitList(medications)
            // If no medications are added, show empty state message
            binding.emptyStateText.visibility = if (medications.isEmpty()) View.VISIBLE else View.GONE
        }

        // Back button
        binding.backButton.setOnClickListener { onBackPressed() }

        // Add Medication Button
        binding.fabAddMedication.setOnClickListener {
            showAddMedicationBottomSheet()
        }
    }

    private fun showAddMedicationBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val dialogBinding = DialogAddMedicationBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding.root)

        setupSpinners(dialogBinding)
        setupTimeSlotSelection(dialogBinding)

        dialogBinding.saveButton.setOnClickListener {
            saveMedication(dialogBinding, bottomSheetDialog)
        }

        bottomSheetDialog.show()
    }

    private fun setupTimeSlotSelection(dialogBinding: DialogAddMedicationBinding) {
        val timeSelectionContainer = dialogBinding.timeSelectionContainer
        val timeSlotTemplate = dialogBinding.timeSlotTemplate
        val timeSlotsContainer = dialogBinding.timeSlotsContainer

        dialogBinding.frequencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedFrequency = parent?.getItemAtPosition(position).toString()
                updateTimeSlots(selectedFrequency, timeSelectionContainer, timeSlotTemplate, timeSlotsContainer)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateTimeSlots(
        frequency: String,
        timeSelectionContainer: LinearLayout,
        timeSlotTemplate: LinearLayout,
        timeSlotsContainer: LinearLayout
    ) {
        // Clear previous time slots
        timeSlotsContainer.removeAllViews()

        // Determine number of time slots based on frequency
        val timeSlots = when (frequency) {
            "1x sehari" -> listOf("07:00")
            "2x sehari" -> listOf("07:00", "19:00")
            "3x sehari" -> listOf("07:00", "12:00", "19:00")
            "4x sehari" -> listOf("06:00", "12:00", "18:00", "22:00")
            else -> emptyList()
        }

        // Show/hide time selection container
        timeSelectionContainer.visibility = if (timeSlots.isNotEmpty()) View.VISIBLE else View.GONE

        // Add time slots dynamically
        timeSlots.forEach { time ->
            val timeSlotView = layoutInflater.inflate(
                R.layout.time_slot_item,
                timeSlotsContainer,
                false
            ) as LinearLayout

            val timeChip = timeSlotView.findViewById<Chip>(R.id.time_chip)
            timeChip.text = time

            // Set up time picker dialog when chip is clicked
            timeChip.setOnClickListener {
                showTimePicker(timeChip)
            }

            timeSlotsContainer.addView(timeSlotView)
        }
    }

    private fun showTimePicker(timeChip: Chip) {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(timeChip.text.toString().split(":")[0].toInt())
            .setMinute(timeChip.text.toString().split(":")[1].toInt())
            .setTitleText("Pilih Waktu")
            .build()

        timePicker.show(supportFragmentManager, "TimePicker")

        timePicker.addOnPositiveButtonClickListener {
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            timeChip.text = formattedTime
        }
    }

    private fun setupSpinners(dialogBinding: DialogAddMedicationBinding) {
        // Set frequency options
        val frequencyOptions = arrayOf("1x sehari", "2x sehari", "3x sehari", "4x sehari")
        val frequencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencyOptions)
        dialogBinding.frequencySpinner.adapter = frequencyAdapter
    }

    private fun saveMedication(dialogBinding: DialogAddMedicationBinding, bottomSheetDialog: BottomSheetDialog) {
        // Collect values from the form
        val medicationName = dialogBinding.editNamaObat.text.toString()
        val dosage = dialogBinding.editDosis.text.toString()

        // Check if mandatory fields are filled
        if (medicationName.isEmpty() || dosage.isEmpty()) {
            Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        // Collect time slots
        val timeSlots = mutableListOf<String>()
        val timeSlotsContainer = dialogBinding.timeSlotsContainer
        for (i in 0 until timeSlotsContainer.childCount) {
            val timeChip = timeSlotsContainer.getChildAt(i).findViewById<Chip>(R.id.time_chip)
            timeSlots.add(timeChip.text.toString())
        }

        // Collect other data
        val dosageType = when (dialogBinding.dosageTypeGroup.checkedRadioButtonId) {
            R.id.dosage_tablet -> "Tablet"
            R.id.dosage_spoon -> "Sendok"
            R.id.dosage_drops -> "Tetes"
            else -> "Unknown"
        }

        val mealTiming = when (dialogBinding.mealTimingGroup.checkedRadioButtonId) {
            R.id.before_meal -> "Sebelum Makan"
            R.id.during_meal -> "Saat Makan"
            R.id.after_meal -> "Setelah Makan"
            else -> "Unknown"
        }

        val frequency = dialogBinding.frequencySpinner.selectedItem.toString()

        // Create Medication object
        val medication = Medication(
            nama = medicationName,
            dosis = dosage,
            tanggal = "",  // Date field not provided in your XML
            jam = timeSlots.joinToString(", "),  // Join time slots
            frekuensi = frequency,
            waktu = mealTiming,
            jenisObat = dosageType
        )

        // Save medication using ViewModel
        medicationViewModel.addMedication(medication)

        // Show success message
        Toast.makeText(this, "Obat berhasil disimpan", Toast.LENGTH_SHORT).show()

        // Dismiss the bottom sheet
        bottomSheetDialog.dismiss()
    }
}