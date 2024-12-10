//WaterTrackerViewModel.kt
package com.dicodingg.bangkit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Calendar
import kotlin.collections.orEmpty
import kotlin.collections.toMutableList

class WaterTrackerViewModel : androidx.lifecycle.ViewModel() {
    private val _waterIntakes = MutableLiveData<List<com.dicodingg.bangkit.ui.WaterIntake>>()
    val waterIntakes: LiveData<List<com.dicodingg.bangkit.ui.WaterIntake>> = _waterIntakes

    private val _totalIntake = MutableLiveData<Int>()
    val totalIntake: LiveData<Int> = _totalIntake

    private val _dailyGoal = MutableLiveData(2000) // 2000ml default goal
    val dailyGoal: LiveData<Int> = _dailyGoal

    fun addWaterIntake(amount: Int) {
        val currentList = _waterIntakes.value.orEmpty().toMutableList()
        currentList.add(0, WaterIntake(amount = amount))
        _waterIntakes.value = currentList
        updateTotalIntake()
    }

    private fun updateTotalIntake() {
        val total = _waterIntakes.value.orEmpty()
            .filter { isToday(it.timestamp) }
            .sumOf { it.amount }
        _totalIntake.value = total
    }

    private fun isToday(timestamp: Long): Boolean {
        val today = Calendar.getInstance()
        val date = Calendar.getInstance().apply { timeInMillis = timestamp }
        return today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
    }
}