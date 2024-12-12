package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.ActivityPhysicalActivityBinding
import com.google.android.material.snackbar.Snackbar
import android.graphics.Color

class PhysicalActivityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhysicalActivityBinding
    private lateinit var exerciseAdapter: ExerciseAdapter

    private var totalSteps = 0
    private var totalCalories = 0.0
    private val exercises = mutableListOf<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhysicalActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupStepsInput()
        setupExerciseInput()
        setupProgressBar()
        setupExerciseHistory()
        setupBackNavigation()
    }

    private fun setupStepsInput() {
        binding.addStepsButton.setOnClickListener {
            val stepsInput = binding.stepsInput.text.toString().toIntOrNull()
            if (stepsInput != null && stepsInput > 0) {
                totalSteps += stepsInput
                val caloriesBurned = stepsInput * 0.05
                totalCalories += caloriesBurned
                updateProgressBar()
                updateCaloriesText()
                Snackbar.make(binding.root, "Langkah sukses ditambahkan!", Snackbar.LENGTH_SHORT).show()
                binding.stepsInput.text?.clear()
            } else {
                Snackbar.make(binding.root, "Tolong masukkan angka yang valid.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupProgressBar() {
        binding.progressBar.max = 10000
        updateProgressBar()
        updateCaloriesText()
    }

    private fun updateProgressBar() {
        binding.progressBar.progress = totalSteps
        binding.stepsText.text = "$totalSteps/10,000 langkah"
    }

    private fun updateCaloriesText() {
        binding.caloriesText.text = "${String.format("%.0f", totalCalories)} kalori yang terbakar"
    }

    private fun setupExerciseInput() {
        binding.addExerciseButton.setOnClickListener {
            val exerciseName = binding.exerciseNameInput.text.toString()
            val duration = binding.exerciseDurationInput.text.toString().toIntOrNull() ?: 0
            val calories = binding.exerciseCaloriesInput.text.toString().toDoubleOrNull() ?: 0.0

            if (exerciseName.isNotBlank() && duration > 0 && calories > 0) {
                val exercise = Exercise(exerciseName, duration, calories)
                exercises.add(exercise)
                exerciseAdapter.notifyItemInserted(exercises.size - 1)
                totalCalories += calories
                updateCaloriesText()
                Snackbar.make(binding.root, "Exercise added!", Snackbar.LENGTH_SHORT).show()
                clearExerciseInputs()
            } else {
                Snackbar.make(binding.root, "Please enter all exercise details", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupExerciseHistory() {
        exerciseAdapter = ExerciseAdapter(exercises)
        binding.exerciseHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PhysicalActivityActivity)
            adapter = exerciseAdapter
        }
    }

    private fun clearExerciseInputs() {
        binding.exerciseNameInput.text?.clear()
        binding.exerciseDurationInput.text?.clear()
        binding.exerciseCaloriesInput.text?.clear()
    }

    private fun setupBackNavigation() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    // Exercise Adapter remains the same as in the original fragment
    class ExerciseAdapter(private val exercises: List<Exercise>) :
        RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

        class ExerciseViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.exerciseName)
            val duration: TextView = view.findViewById(R.id.exerciseDuration)
            val calories: TextView = view.findViewById(R.id.exerciseCalories)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_exercise, parent, false)
            return ExerciseViewHolder(view)
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
            val exercise = exercises[position]
            holder.name.apply {
                text = exercise.name
                setTextColor(Color.BLACK)
            }
            holder.duration.apply {
                text = "${exercise.duration} menit"
                setTextColor(Color.BLACK)
            }
            holder.calories.apply {
                text = "${exercise.calories.toInt()} cal"
                setTextColor(Color.BLACK)
            }
        }

        override fun getItemCount() = exercises.size
    }

    // Exercise data class remains the same
    data class Exercise(
        val name: String,
        val duration: Int,
        val calories: Double
    )
}