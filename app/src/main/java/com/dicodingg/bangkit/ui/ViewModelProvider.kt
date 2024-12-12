package com.dicodingg.bangkit.viewmodel

object ViewModelProvider {
    private var nutritionViewModel: NutritionViewModel? = null

    fun getNutritionViewModel(): NutritionViewModel {
        if (nutritionViewModel == null) {
            nutritionViewModel = NutritionViewModel()
        }
        return nutritionViewModel!!
    }
}