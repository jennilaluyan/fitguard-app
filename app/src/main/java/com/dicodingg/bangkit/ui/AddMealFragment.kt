//AddMealFragment.kt
package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dicodingg.bangkit.databinding.FragmentAddMealBinding
import com.dicodingg.bangkit.viewmodel.NutritionViewModel

class AddMealFragment : Fragment() {
    private var _binding: FragmentAddMealBinding? = null
    private val binding get() = _binding!!

    private val nutritionViewModel: NutritionViewModel by activityViewModels()

    private var selectedMealType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve meal type from arguments
        selectedMealType = arguments?.getString(ARG_MEAL_TYPE) ?: ""

        // Set up meal type text
        binding.mealTypeTitle.text = "Add $selectedMealType"

        // Set up portion/serving type spinner
        val portionTypes = when (selectedMealType) {
            "Breakfast", "Lunch", "Dinner" -> arrayOf("Portion", "Plate", "Bowl")
            "Water" -> arrayOf("Glass", "Ml")
            else -> arrayOf("Serving")
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, portionTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.portionTypeSpinner.adapter = adapter

        // Set up add button
        binding.addMealButton.setOnClickListener {
            addMeal()
        }

        // Set up back button
        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun addMeal() {
        val foodName = binding.foodNameEditText.text.toString()
        val caloriesText = binding.caloriesEditText.text.toString()
        val portionText = binding.portionEditText.text.toString()
        val portionType = binding.portionTypeSpinner.selectedItem.toString()

        // Validate inputs
        if (foodName.isEmpty() || caloriesText.isEmpty() || portionText.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val calories = caloriesText.toIntOrNull() ?: 0
        val portion = portionText.toFloatOrNull() ?: 0f

        // Add meal through ViewModel
        nutritionViewModel.addMeal(
            mealType = selectedMealType,
            foodName = foodName,
            calories = calories,
            portion = portion,
            portionType = portionType
        )

        // Navigate back to Nutrition Tracker
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_MEAL_TYPE = "arg_meal_type"

        fun newInstance(mealType: String): AddMealFragment {
            val fragment = AddMealFragment()
            val args = Bundle()
            args.putString(ARG_MEAL_TYPE, mealType)
            fragment.arguments = args
            return fragment
        }
    }
}