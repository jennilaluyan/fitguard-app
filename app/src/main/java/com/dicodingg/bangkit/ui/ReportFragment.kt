package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.dicodingg.bangkit.R

class ReportFragment : Fragment(R.layout.fragment_reports) {

    // Deklarasi elemen-elemen UI yang ada di XML
    private lateinit var header: TextView
    private lateinit var weeklyProgressTitle: TextView
    private lateinit var weeklyProgressMessage: TextView
    private lateinit var dailyReportTitle: TextView
    private lateinit var waterIntakeText: TextView
    private lateinit var waterIntakeValue: TextView
    private lateinit var glucoseLevelText: TextView
    private lateinit var glucoseLevelValue: TextView
    private lateinit var nutritionText: TextView
    private lateinit var nutritionValue: TextView
    private lateinit var stepsText: TextView
    private lateinit var stepsValue: TextView
    private lateinit var overallStatsTitle: TextView
    private lateinit var medicationTitle: TextView
    private lateinit var medicationMessage: TextView

    // Inisialisasi atau setup yang diperlukan di sini
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menghubungkan elemen UI dengan findViewById
        header = view.findViewById(R.id.header)
        weeklyProgressTitle = view.findViewById(R.id.weeklyProgressTitle)
        weeklyProgressMessage = view.findViewById(R.id.weeklyProgressMessage)
        dailyReportTitle = view.findViewById(R.id.dailyReportTitle)
        waterIntakeText = view.findViewById(R.id.waterIntakeText)
        waterIntakeValue = view.findViewById(R.id.waterIntakeValue)
        glucoseLevelText = view.findViewById(R.id.glucoseLevelText)
        glucoseLevelValue = view.findViewById(R.id.glucoseLevelValue)
        nutritionText = view.findViewById(R.id.nutritionText)
        nutritionValue = view.findViewById(R.id.nutritionValue)
        stepsText = view.findViewById(R.id.stepsText)
        stepsValue = view.findViewById(R.id.stepsValue)
        overallStatsTitle = view.findViewById(R.id.overallStatsTitle)
        medicationTitle = view.findViewById(R.id.medicationTitle)
        medicationMessage = view.findViewById(R.id.medicationMessage)

        // Menetapkan teks ke elemen UI sesuai data statis atau dinamis
        header.text = "Reports"
        weeklyProgressTitle.text = "Weekly Blood Sugar Progress"
        weeklyProgressMessage.text = "Your blood sugar levels are stable, keep following your healthy routine."
        dailyReportTitle.text = "Daily Report"
        waterIntakeText.text = "Water Intake"
        waterIntakeValue.text = "2.5 L"
        glucoseLevelText.text = "Glucose Level"
        glucoseLevelValue.text = "120 mg/dL"
        nutritionText.text = "Nutrition"
        nutritionValue.text = "High in protein, low in carbs"
        stepsText.text = "Steps"
        stepsValue.text = "7500 steps"
        overallStatsTitle.text = "Overall Blood Sugar Statistics"
        medicationTitle.text = "Medication Taken"
        medicationMessage.text = "Insulin - 10 units (Morning and Evening)"

        // Anda dapat menambahkan logika lainnya di sini
    }
}
