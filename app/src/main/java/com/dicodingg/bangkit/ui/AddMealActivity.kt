package com.dicodingg.bangkit.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.databinding.ActivityAddMealBinding
import com.dicodingg.bangkit.viewmodel.ViewModelProvider
import android.graphics.Color
import android.widget.TextView
import android.widget.AdapterView

class AddMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMealBinding
    private val viewModel = ViewModelProvider.getNutritionViewModel()
    private var mealType: String = ""

    companion object {
        private const val EXTRA_MEAL_TYPE = "MEAL_TYPE"

        fun newIntent(context: Context, mealType: String): Intent {
            return Intent(context, AddMealActivity::class.java).apply {
                putExtra(EXTRA_MEAL_TYPE, mealType)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealType = intent.getStringExtra(EXTRA_MEAL_TYPE) ?: ""

        setupViews()
        setupSpinner()
        setupListeners()
    }

    private fun setupViews() {
        binding.mealTypeTitle.text = "Tambah $mealType"
        binding.calorieInfoBox.visibility = View.GONE

        // Pre-fill existing data if any
        viewModel.getMeal(mealType)?.let { meal ->
            binding.foodNameEditText.setText(meal.foodName)
            binding.caloriesEditText.setText(meal.calories.toString())
            binding.portionEditText.setText(meal.portion.toString())
        }
    }

    private fun setupSpinner() {
        val portionTypes = arrayOf("gram", "porsi", "butir", "piring")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, portionTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.portionTypeSpinner.adapter = adapter

        binding.portionTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (view as? TextView)?.setTextColor(Color.BLACK)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.infoLogo.setOnClickListener {
            toggleCalorieInfo()
        }

        binding.addMealButton.setOnClickListener {
            addMeal()
        }
    }

    private fun toggleCalorieInfo() {
        binding.calorieInfoBox.visibility = if (binding.calorieInfoBox.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun addMeal() {
        val foodName = binding.foodNameEditText.text.toString()
        val calories = binding.caloriesEditText.text.toString().toIntOrNull()
        val portion = binding.portionEditText.text.toString().toFloatOrNull()
        val portionType = binding.portionTypeSpinner.selectedItem.toString()

        if (foodName.isBlank()) {
            Toast.makeText(this, "Mohon isi nama makanan", Toast.LENGTH_SHORT).show()
            return
        }

        if (calories == null || calories <= 0) {
            Toast.makeText(this, "Mohon isi jumlah kalori dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        if (portion == null || portion <= 0f) {
            Toast.makeText(this, "Mohon isi ukuran porsi dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.addMeal(mealType, foodName, calories, portion, portionType)
        setResult(RESULT_OK)
        finish()
    }
}