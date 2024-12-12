package com.dicodingg.bangkit.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.ActivityWaterTrackerBinding
import com.dicodingg.bangkit.ui.WaterIntake
import com.dicodingg.bangkit.ui.WaterTrackerViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WaterTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaterTrackerBinding
    private lateinit var viewModel: WaterTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WaterTrackerViewModel::class.java]

        setupUI()
        setupObservers()

        // Set up back button to return to the dashboard
        binding.backButton.setOnClickListener {
            onBackPressed() // Use onBackPressed to go back
        }
    }


    private fun setupUI() {
        binding.apply {
            addSmallGlass.setOnClickListener { addWaterWithAnimation(250) }
            addMediumGlass.setOnClickListener { addWaterWithAnimation(600) }
            addCustom.setOnClickListener { showCustomAmountDialog() }
        }
    }

    private fun setupObservers() {
        viewModel.totalIntake.observe(this) { total ->
            updateProgress(total)
        }

        viewModel.waterIntakes.observe(this) { intakes ->
            updateWaterList(intakes)
        }

        viewModel.dailyGoal.observe(this) { goal ->
            // Update the progress when goal changes
            viewModel.totalIntake.value?.let { total ->
                updateProgress(total)
            }
        }
    }

    private fun showCustomAmountDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom_water_amount, null)
        val customAmountEditText = dialogView.findViewById<EditText>(R.id.custom_amount_edittext)

        MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setPositiveButton("Tambah") { _, _ ->
                val customAmount = customAmountEditText.text.toString().toIntOrNull()
                if (customAmount != null && customAmount > 0) {
                    addWaterWithAnimation(customAmount)
                } else {
                    Toast.makeText(this, "Tolong masukkan angka yang valid", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun addWaterWithAnimation(amount: Int) {
        // Splash animation
        binding.splashAnimation.apply {
            alpha = 0f
            scaleX = 0f
            scaleY = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .withEndAction {
                    animate()
                        .alpha(0f)
                        .setDuration(200)
                        .start()
                }
                .start()
        }

        viewModel.addWaterIntake(amount)
    }

    private fun updateProgress(total: Int) {
        val goal = viewModel.dailyGoal.value ?: 2000
        val progress = (total.toFloat() / goal * 100).coerceIn(0f, 100f)

        // Update CircularProgressIndicator
        binding.arcIndicator.progress = progress.toInt()

        // Update the consumption text
        binding.totalConsumption.text = "$total/$goal ml"
    }

    private fun updateWaterList(intakes: List<WaterIntake>) {
        binding.waterRecordsList.removeAllViews()

        intakes.forEach { intake ->
            val recordView = layoutInflater.inflate(
                android.R.layout.simple_list_item_2,
                binding.waterRecordsList,
                false
            )

            val text1 = recordView.findViewById<TextView>(android.R.id.text1)
            val text2 = recordView.findViewById<TextView>(android.R.id.text2)

            text1.apply {
                text = SimpleDateFormat("HH:mm", Locale.getDefault())
                    .format(Date(intake.timestamp))
                setTextColor(Color.BLACK)
            }

            text2.apply {
                text = getString(R.string.water_amount_format, intake.amount)
                setTextColor(Color.BLACK)
            }

            binding.waterRecordsList.addView(recordView)
        }
    }
}