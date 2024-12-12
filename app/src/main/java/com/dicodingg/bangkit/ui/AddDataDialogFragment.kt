package com.dicodingg.bangkit.ui

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.dicodingg.bangkit.databinding.DialogAddDataBinding
import java.util.Calendar
import com.dicodingg.bangkit.R

class AddDataDialogFragment(
    private val onDataSubmitted: (dataType: String, dataValue: String, date: String) -> Unit
) : DialogFragment() {

    private var _binding: DialogAddDataBinding? = null
    private val binding get() = _binding!!
    private var selectedDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the dropdown (Spinner) with options
        val dataTypeOptions = resources.getStringArray(R.array.data_type_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dataTypeOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dataTypeSpinner.adapter = adapter
        binding.dataTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (view as? TextView)?.setTextColor(Color.BLACK)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // Handle "Select Date" button click
        binding.datePickerButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.datePickerButton.text = selectedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Handle Add Button
        binding.addButton.setOnClickListener {
            val selectedType = binding.dataTypeSpinner.selectedItem.toString()
            val dataValue = binding.dataValueInput.text.toString().trim()

            if (dataValue.isNotEmpty() && selectedDate.isNotEmpty()) {
                onDataSubmitted(selectedType, dataValue, selectedDate)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Cancel Button
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        // Make dialog box full width and height wrap content
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}