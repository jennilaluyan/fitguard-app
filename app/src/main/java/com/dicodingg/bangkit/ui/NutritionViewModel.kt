package com.dicodingg.bangkit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Meal(
    val type: String,
    val foodName: String,
    val calories: Int,
    val portion: Float,
    val portionType: String
)

class NutritionViewModel : ViewModel() {
    private val _meals = MutableLiveData<MutableMap<String, Meal>>(mutableMapOf())
    val meals: LiveData<MutableMap<String, Meal>> = _meals

    // Add period tracking
    private val _selectedPeriod = MutableLiveData("Today")
    val selectedPeriod: LiveData<String> = _selectedPeriod

    // Store historical data
    private val weeklyMeals = mutableMapOf<String, MutableMap<String, Meal>>()
    private val monthlyMeals = mutableMapOf<String, MutableMap<String, Meal>>()

    fun addMeal(mealType: String, foodName: String, calories: Int, portion: Float, portionType: String) {
        val newMeal = Meal(mealType, foodName, calories, portion, portionType)
        _meals.value?.let { currentMeals ->
            currentMeals[mealType] = newMeal
            _meals.value = currentMeals
        }
    }

    fun getMeal(mealType: String): Meal? {
        return _meals.value?.get(mealType)
    }

    fun updatePeriodData(periodData: Map<String, Meal>) {
        _meals.value = periodData.toMutableMap()
    }

    fun setTimePeriod(period: String) {
        _selectedPeriod.value = period
        // Trigger data refresh based on period
        refreshData()
    }

    private fun refreshData() {
        when (_selectedPeriod.value) {
            "Today" -> {
                // Keep current day data
            }
            "Week" -> {
                // Simulate weekly data
                generateSampleWeekData()
            }
            "Month" -> {
                // Simulate monthly data
                generateSampleMonthData()
            }
        }
    }

    private fun generateSampleWeekData() {
        val sampleWeekData = mutableMapOf<String, Meal>()
        var totalBreakfast = 0
        var totalLunch = 0
        var totalDinner = 0

        // Simulate accumulated data for the week
        for (i in 1..7) {
            totalBreakfast += (200..400).random()
            totalLunch += (300..500).random()
            totalDinner += (300..600).random()
        }

        // Calculate averages
        sampleWeekData["Breakfast"] = Meal("Breakfast", "Weekly Average", totalBreakfast / 7, 1f, "portions")
        sampleWeekData["Lunch"] = Meal("Lunch", "Weekly Average", totalLunch / 7, 1f, "portions")
        sampleWeekData["Dinner"] = Meal("Dinner", "Weekly Average", totalDinner / 7, 1f, "portions")

        _meals.value = sampleWeekData
    }

    private fun generateSampleMonthData() {
        val sampleMonthData = mutableMapOf<String, Meal>()
        var totalBreakfast = 0
        var totalLunch = 0
        var totalDinner = 0

        // Simulate accumulated data for the month
        for (i in 1..30) {
            totalBreakfast += (200..400).random()
            totalLunch += (300..500).random()
            totalDinner += (300..600).random()
        }

        // Calculate averages
        sampleMonthData["Breakfast"] = Meal("Breakfast", "Monthly Average", totalBreakfast / 30, 1f, "portions")
        sampleMonthData["Lunch"] = Meal("Lunch", "Monthly Average", totalLunch / 30, 1f, "portions")
        sampleMonthData["Dinner"] = Meal("Dinner", "Monthly Average", totalDinner / 30, 1f, "portions")

        _meals.value = sampleMonthData
    }
}