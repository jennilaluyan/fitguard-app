package com.dicodingg.bangkit.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.databinding.ActivityAddMealBinding
import com.dicodingg.bangkit.viewmodel.NutritionViewModel

class AddMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMealBinding
    private val nutritionViewModel: NutritionViewModel by lazy { NutritionViewModel() }

    private var selectedMealType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve meal type from Intent
        selectedMealType = intent.getStringExtra(ARG_MEAL_TYPE) ?: ""

        // Set up meal type text
        binding.mealTypeTitle.text = "Tambah $selectedMealType"

        // Set up portion/serving type spinner
        val portionTypes = when (selectedMealType) {
            "Sarapan", "Makan Siang", "Makan Malam" -> arrayOf("Porsi", "Piring", "Mangkuk")
            else -> arrayOf("Serving")
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, portionTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.portionTypeSpinner.adapter = adapter

        // Set up add button
        binding.addMealButton.setOnClickListener {
            addMeal()
        }

        // Set up back button
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun addMeal() {
        val foodName = binding.foodNameEditText.text.toString()
        val caloriesText = binding.caloriesEditText.text.toString()
        val portionText = binding.portionEditText.text.toString()
        val portionType = binding.portionTypeSpinner.selectedItem.toString()

        // Validate inputs
        if (foodName.isEmpty() || caloriesText.isEmpty() || portionText.isEmpty()) {
            Toast.makeText(this, "Tolong diisi semua kolomnya.", Toast.LENGTH_SHORT).show()
            return
        }

        val calories = caloriesText.toIntOrNull() ?: 0
        val portion = portionText.toFloatOrNull() ?: 0f

        // Add meal through ViewModel
        nutritionViewModel.addMeal(
            mealType = selectedMealType,
            foodName = foodName,
            calories = calories,
            portion = portion,
            portionType = portionType
        )

        // Navigate back to Nutrition Tracker
        onBackPressed()
    }

    companion object {
        private const val ARG_MEAL_TYPE = "arg_meal_type"

        fun newIntent(context: Context, mealType: String): Intent {
            val intent = Intent(context, AddMealActivity::class.java) // Gunakan 'context' di sini
            intent.putExtra(ARG_MEAL_TYPE, mealType)
            return intent
        }
    }
}