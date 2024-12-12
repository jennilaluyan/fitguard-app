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

    fun addMeal(mealType: String, foodName: String, calories: Int, portion: Float, portionType: String) {
        val currentMeals = _meals.value ?: mutableMapOf()
        currentMeals[mealType] = Meal(mealType, foodName, calories, portion, portionType)
        _meals.postValue(currentMeals)
    }

    fun getMeal(mealType: String): Meal? {
        return _meals.value?.get(mealType)
    }
}