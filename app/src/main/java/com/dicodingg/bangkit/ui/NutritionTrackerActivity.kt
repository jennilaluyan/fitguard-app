package com.dicodingg.bangkit.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.FragmentNutritionTrackerBinding
import com.dicodingg.bangkit.viewmodel.Meal
import com.dicodingg.bangkit.viewmodel.NutritionViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NutritionTrackerActivity : AppCompatActivity() {

    private lateinit var binding: FragmentNutritionTrackerBinding
    private val nutritionViewModel: NutritionViewModel by viewModels()
    private var currentWeekOffset = 0
    private var currentMonthOffset = 0

    private val mealColors = mapOf(
        "Breakfast" to Color.parseColor("#EF8ABC"),
        "Lunch" to Color.parseColor("#FB6469"),
        "Water" to Color.parseColor("#4D90F5"),
        "Dinner" to Color.parseColor("#FFB643")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNutritionTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPieChart()

        // Set up the back button
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Set up meal click listeners
        setupMealClickListeners()

        // Observe meal changes
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

    private fun updatePieChart() {
        val meals = nutritionViewModel.meals.value ?: return
        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()
        var totalConsumed = 0f

        meals.forEach { (type, meal) ->
            if (type != "Water" && meal.calories > 0) {
                entries.add(PieEntry(meal.calories.toFloat(), ""))
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

        val pieData = PieData(dataSet)

        binding.nutritionChart.apply {
            data = pieData
            centerText = "Target\n1000 Cal\n\nKonsumsi\n${totalConsumed.toInt()} cal"
            highlightValues(null)
            invalidate()
        }
    }

    private fun enableMealBoxes(enabled: Boolean) {
        binding.breakfastLayout.isClickable = enabled
        binding.lunchLayout.isClickable = enabled
        binding.dinnerLayout.isClickable = enabled
    }

    private fun updateWeeklyData() {
        val dummyData = mapOf(
            "Breakfast" to Meal("Breakfast", "Weekly Average", (250..350).random(), 1f, "portions"),
            "Lunch" to Meal("Lunch", "Weekly Average", (400..600).random(), 1f, "portions"),
            "Dinner" to Meal("Dinner", "Weekly Average", (350..550).random(), 1f, "portions"),
            "Water" to Meal("Water", "Daily Average", (1500..2500).random(), 1f, "ml")
        )
        nutritionViewModel.updatePeriodData(dummyData)
    }

    private fun updateMonthlyData() {
        val dummyData = mapOf(
            "Breakfast" to Meal("Breakfast", "Monthly Average", (250..350).random(), 1f, "portions"),
            "Lunch" to Meal("Lunch", "Monthly Average", (400..600).random(), 1f, "portions"),
            "Dinner" to Meal("Dinner", "Monthly Average", (350..550).random(), 1f, "portions"),
            "Water" to Meal("Water", "Daily Average", (1800..2200).random(), 1f, "ml")
        )
        nutritionViewModel.updatePeriodData(dummyData)
    }

    private fun setupMealClickListeners() {
        binding.breakfastLayout.setOnClickListener {
            navigateToAddMealActivity("Breakfast")
        }

        binding.lunchLayout.setOnClickListener {
            navigateToAddMealActivity("Lunch")
        }

        binding.dinnerLayout.setOnClickListener {
            navigateToAddMealActivity("Dinner")
        }
    }

    private fun navigateToAddMealActivity(mealType: String) {
        val intent = Intent(this, AddMealActivity::class.java).apply {
            putExtra("MEAL_TYPE", mealType)
        }
        startActivity(intent)
    }

    private fun observeMeals() {
        nutritionViewModel.meals.observe(this) { meals ->
            meals["Breakfast"]?.let { meal ->
                binding.breakfastCalories.text = getString(R.string.calories_format, meal.calories)
                binding.breakfastDescription.text =  meal.foodName
            }
            meals["Lunch"]?.let { meal ->
                binding.lunchCalories.text = getString(R.string.calories_format, meal.calories)
                binding.lunchDescription.text =  meal.foodName
            }
            meals["Dinner"]?.let { meal ->
                binding.dinnerCalories.text = getString(R.string.calories_format, meal.calories)
                binding.dinnerDescription.text =  meal.foodName
            }
            updatePieChart()
        }
    }
}
