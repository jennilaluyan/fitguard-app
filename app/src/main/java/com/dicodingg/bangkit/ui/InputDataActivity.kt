package com.dicodingg.bangkit.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R
import java.util.*

class InputDataActivity : AppCompatActivity() {

    private lateinit var editPregnancies: EditText
    private lateinit var editGlucose: EditText
    private lateinit var editBloodPressure: EditText
    private lateinit var buttonPickBirthDate: Button
    private lateinit var textBirthDate: TextView
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var textCalculatedBMI: TextView
    private lateinit var buttonSubmit: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_data)

        // Menghubungkan view ke variabel
        editPregnancies = findViewById(R.id.editPregnancies)
        editGlucose = findViewById(R.id.editGlucose)
        editBloodPressure = findViewById(R.id.editBloodPressure)
        buttonPickBirthDate = findViewById(R.id.buttonPickBirthDate)
        textBirthDate = findViewById(R.id.textBirthDate)
        editWeight = findViewById(R.id.editWeight)
        editHeight = findViewById(R.id.editHeight)
        textCalculatedBMI = findViewById(R.id.textCalculatedBMI)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        // DatePicker untuk memilih tanggal lahir
        buttonPickBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
                textBirthDate.text = formattedDate

                // Tampilkan tanggal yang dipilih dan sembunyikan tombol
                textBirthDate.visibility = View.VISIBLE
                buttonPickBirthDate.visibility = View.GONE
            }, year, month, day).show()
        }

        // Menambahkan listener untuk menghitung BMI otomatis
        addBMIWatcher()

        // Validasi input saat submit
        buttonSubmit.setOnClickListener {
            if (validateInputs()) {
                // Tampilkan pesan sukses
                Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()

                // Navigasi ke LoadingDataActivity
                val intent = Intent(this, LoadingDataActivity::class.java)
                startActivity(intent)

                // Opsional: Tutup InputDataActivity
                finish()
            }
        }
    }

    // Fungsi untuk menambahkan TextWatcher ke kolom berat badan dan tinggi badan
    private fun addBMIWatcher() {
        val bmiWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateBMI()
            }
        }
        editWeight.addTextChangedListener(bmiWatcher)
        editHeight.addTextChangedListener(bmiWatcher)
    }

    // Fungsi untuk menghitung BMI dan memperbarui tampilan
    private fun calculateBMI() {
        val weight = editWeight.text.toString().toFloatOrNull()
        val height = editHeight.text.toString().toFloatOrNull()?.div(100) // Convert cm to m

        if (weight != null && height != null) {
            val bmi = weight / (height * height)
            textCalculatedBMI.text = String.format("BMI Anda: %.2f", bmi)
            textCalculatedBMI.setTextColor(getBMITextColor(bmi)) // Warna berdasarkan kategori BMI
        } else {
            textCalculatedBMI.text = "Hasil akan muncul di sini"
            textCalculatedBMI.setTextColor(resources.getColor(R.color.gray, null))
        }
    }

    // Warna hasil BMI berdasarkan kategori
    private fun getBMITextColor(bmi: Float): Int {
        return when {
            bmi < 18.5 -> resources.getColor(R.color.blue, null) // Underweight
            bmi in 18.5..24.9 -> resources.getColor(R.color.green, null) // Normal
            bmi in 25.0..29.9 -> resources.getColor(R.color.orange, null) // Overweight
            else -> resources.getColor(R.color.red, null) // Obese
        }
    }

    // Validasi input untuk memastikan semua kolom terisi
    private fun validateInputs(): Boolean {
        val pregnancies = editPregnancies.text.toString()
        val glucose = editGlucose.text.toString()
        val bloodPressure = editBloodPressure.text.toString()
        val birthDate = textBirthDate.text.toString()
        val weight = editWeight.text.toString()
        val height = editHeight.text.toString()

        return if (pregnancies.isEmpty() || glucose.isEmpty() || bloodPressure.isEmpty() ||
            birthDate.isEmpty() || weight.isEmpty() || height.isEmpty()
        ) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}