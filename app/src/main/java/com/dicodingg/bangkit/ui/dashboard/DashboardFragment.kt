package com.dicodingg.bangkit.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.data.factory.PrefViewModelFactory
import com.dicodingg.bangkit.databinding.FragmentDashboardBinding
import com.dicodingg.bangkit.ui.HealthRecordActivity
import com.dicodingg.bangkit.ui.MedicationReminderActivity
import com.dicodingg.bangkit.ui.NutritionTrackerActivity
import com.dicodingg.bangkit.ui.PhysicalActivityActivity
import com.dicodingg.bangkit.ui.WaterTrackerActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels {
        PrefViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // Mendapatkan nama pertama dari email pengguna dan memfilter hanya huruf
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val firstName = email?.substringBefore("@")?.filter { it.isLetter() } ?: "User"

        // Menampilkan pesan selamat datang dengan nama pertama di TextView dengan ID tv_welcome
        binding.tvWelcome.text = "Welcome, $firstName"

        // Setting click listeners using ViewBinding untuk berpindah ke halaman yang sesuai
        binding.healthRecordCard.setOnClickListener {
            val intent = Intent(requireContext(), HealthRecordActivity::class.java)
            startActivity(intent)
        }


        binding.nutritionTrackerCard.setOnClickListener {
            val intent = Intent(requireContext(), NutritionTrackerActivity::class.java)
            startActivity(intent)
        }


        binding.medicationReminderCard.setOnClickListener {
            val intent = Intent(requireContext(), MedicationReminderActivity::class.java)
            startActivity(intent)
        }

        binding.physicalActivityCard.setOnClickListener {
            val intent = Intent(requireContext(), PhysicalActivityActivity::class.java)
            startActivity(intent)
        }

        binding.waterTrackerCard.setOnClickListener {
            val intent = Intent(requireContext(), WaterTrackerActivity::class.java)
            startActivity(intent)
        }




        // Menangani klik untuk rekomendasi lainnya
        binding.foodRecommendationCard.setOnClickListener {
            Toast.makeText(requireContext(), "Food Recommendation selected", Toast.LENGTH_SHORT).show()
            // Menangani navigasi atau aksi untuk rekomendasi makanan
        }

        binding.physicalActivitiesRecommendationCard.setOnClickListener {
            Toast.makeText(requireContext(), "Physical Activities Recommendation selected", Toast.LENGTH_SHORT).show()
            // Menangani navigasi atau aksi untuk rekomendasi aktivitas fisik
        }

        binding.regularBedtimeRecommendationCard.setOnClickListener {
            Toast.makeText(requireContext(), "Regular Bedtime Recommendation selected", Toast.LENGTH_SHORT).show()
            // Menangani navigasi atau aksi untuk rekomendasi waktu tidur teratur
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
