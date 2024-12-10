//WaterIntake.kt
package com.dicodingg.bangkit.ui

data class WaterIntake(
    val id: Long = 0,
    val amount: Int,
    val timestamp: Long = System.currentTimeMillis()
)