package com.dicodingg.bangkit.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.data.factory.PrefViewModelFactory
import com.dicodingg.bangkit.databinding.FragmentDashboardBinding
import com.dicodingg.bangkit.ui.HealthRecordActivity
import com.dicodingg.bangkit.ui.medication.MedicationReminderActivity
import com.dicodingg.bangkit.ui.NutritionTrackerActivity
import com.dicodingg.bangkit.ui.PhysicalActivityActivity
import com.dicodingg.bangkit.ui.WaterTrackerActivity
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

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

        // Set current date
        updateCurrentDate()

        // Get user's first name from email
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val firstName = email?.substringBefore("@")?.filter { it.isLetter() } ?: "User"

        binding.tvWelcome.text = "Halo, $firstName"

        // Setup click listeners
        setupClickListeners()

        return binding.root
    }

    private fun updateCurrentDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, dd MMM", Locale("id", "ID"))
        val currentDate = dateFormat.format(calendar.time)
        binding.tvDate.text = currentDate
    }

    private fun setupClickListeners() {
        binding.apply {
            healthRecordCard.setOnClickListener {
                startActivity(Intent(requireContext(), HealthRecordActivity::class.java))
            }

            nutritionTrackerCard.setOnClickListener {
                startActivity(Intent(requireContext(), NutritionTrackerActivity::class.java))
            }

            medicationReminderCard.setOnClickListener {
                startActivity(Intent(requireContext(), MedicationReminderActivity::class.java))
            }

            physicalActivityCard.setOnClickListener {
                startActivity(Intent(requireContext(), PhysicalActivityActivity::class.java))
            }

            waterTrackerCard.setOnClickListener {
                startActivity(Intent(requireContext(), WaterTrackerActivity::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
