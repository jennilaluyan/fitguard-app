package com.dicodingg.bangkit.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.FragmentNutritionTrackerBinding
import com.dicodingg.bangkit.viewmodel.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class NutritionTrackerActivity : AppCompatActivity() {
    private lateinit var binding: FragmentNutritionTrackerBinding
    private val viewModel = ViewModelProvider.getNutritionViewModel()

    private val mealColors = mapOf(
        "Sarapan" to Color.parseColor("#EF8ABC"),
        "Makan Siang" to Color.parseColor("#FB6469"),
        "Makan Malam" to Color.parseColor("#FFB643")
    )

    private val addMealActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            updatePieChart()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNutritionTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPieChart()
        setupClickListeners()
        observeMeals()
    }

    private fun setupPieChart() {
        binding.nutritionChart.apply {
            setUsePercentValues(false)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)
            centerText = "Target\n1000 Cal"
            setCenterTextSize(14f)
            rotationAngle = 0f
            isRotationEnabled = false
            isHighlightPerTapEnabled = false
            animateY(1400, Easing.EaseInOutQuad)
            legend.isEnabled = false
            setDrawEntryLabels(false)
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.SarapanLayout.setOnClickListener {
            navigateToAddMeal("Sarapan")
        }

        binding.MakanSiangLayout.setOnClickListener {
            navigateToAddMeal("Makan Siang")
        }

        binding.MakanMalamLayout.setOnClickListener {
            navigateToAddMeal("Makan Malam")
        }
    }

    private fun navigateToAddMeal(mealType: String) {
        val intent = AddMealActivity.newIntent(this, mealType)
        addMealActivityResult.launch(intent)
    }

    private fun updatePieChart() {
        val meals = viewModel.meals.value ?: return
        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()
        var totalConsumed = 0f

        meals.forEach { (type, meal) ->
            if (meal.calories > 0) {
                entries.add(PieEntry(meal.calories.toFloat(), type))
                mealColors[type]?.let { colors.add(it) }
                totalConsumed += meal.calories
            }
        }

        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors
            setDrawValues(false)
            sliceSpace = 3f
            selectionShift = 5f
        }

        binding.nutritionChart.apply {
            data = PieData(dataSet)
            centerText = "Target\n1000 Cal\n\nKonsumsi\n${totalConsumed.toInt()} cal"
            highlightValues(null)
            invalidate()
        }
    }

    private fun observeMeals() {
        viewModel.meals.observe(this) { meals ->
            meals["Sarapan"]?.let { meal ->
                binding.SarapanCalories.text = getString(R.string.calories_format, meal.calories)
                binding.SarapanDescription.text = meal.foodName
            }
            meals["Makan Siang"]?.let { meal ->
                binding.MakanSiangCalories.text = getString(R.string.calories_format, meal.calories)
                binding.MakanSiangDescription.text = meal.foodName
            }
            meals["Makan Malam"]?.let { meal ->
                binding.MakanMalamCalories.text = getString(R.string.calories_format, meal.calories)
                binding.MakanMalamDescription.text = meal.foodName
            }
            updatePieChart()
        }
    }
}