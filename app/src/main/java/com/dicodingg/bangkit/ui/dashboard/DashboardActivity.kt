package com.dicodingg.bangkit.ui.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.data.factory.PrefViewModelFactory
import com.dicodingg.bangkit.databinding.ActivityDashboardBinding
import com.dicodingg.bangkit.ui.HealthRecordFragment
import com.dicodingg.bangkit.ui.MedicationReminderFragment
import com.dicodingg.bangkit.ui.NutritionTrackerFragment
import com.dicodingg.bangkit.ui.PhysicalActivityFragment
import com.dicodingg.bangkit.ui.WaterTrackerFragment
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels {
        PrefViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan nama pertama dari email pengguna dan memfilter hanya huruf
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val firstName = email?.substringBefore("@")?.filter { it.isLetter() } ?: "User"

        // Menampilkan pesan selamat datang dengan nama pertama di TextView dengan ID tv_welcome
        binding.tvWelcome.text = "Welcome, $firstName"

        // Setting click listeners using ViewBinding
        binding.healthRecordCard.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HealthRecordFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.nutritionTrackerCard.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, NutritionTrackerFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.medicationReminderCard.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, MedicationReminderFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.physicalActivityCard.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, PhysicalActivityFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.waterTrackerCard.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, WaterTrackerFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.foodRecommendationCard.setOnClickListener {
            Toast.makeText(this, "Food Recommendation selected", Toast.LENGTH_SHORT).show()
            // Handle navigation or actions for Food Recommendation
        }

        binding.physicalActivitiesRecommendationCard.setOnClickListener {
            Toast.makeText(this, "Physical Activities Recommendation selected", Toast.LENGTH_SHORT).show()
            // Handle navigation or actions for Physical Activities Recommendation
        }

        binding.regularBedtimeRecommendationCard.setOnClickListener {
            Toast.makeText(this, "Regular Bedtime Recommendation selected", Toast.LENGTH_SHORT).show()
            // Handle navigation or actions for Regular Bedtime Recommendation
        }
    }
}