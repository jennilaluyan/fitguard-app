package com.dicodingg.bangkit.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dicodingg.bangkit.R
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeParseException

class InputDataActivity : AppCompatActivity() {

    private lateinit var switchPregnancies: Switch
    private lateinit var editGlucose: EditText
    private lateinit var editBloodPressure: EditText
    private lateinit var editBirthDate: EditText
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var textCalculatedBMI: TextView
    private lateinit var buttonSubmit: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_data)

        // Menghubungkan view ke variabel
        switchPregnancies = findViewById(R.id.switchPregnancies)
        editGlucose = findViewById(R.id.editGlucose)
        editBloodPressure = findViewById(R.id.editBloodPressure)
        editBirthDate = findViewById(R.id.editBirthDate)
        editWeight = findViewById(R.id.editWeight)
        editHeight = findViewById(R.id.editHeight)
        textCalculatedBMI = findViewById(R.id.textCalculatedBMI)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        // Event Listener untuk tombol submit
        buttonSubmit.setOnClickListener {
            // Mendapatkan input dari pengguna
            val pregnancies = if (switchPregnancies.isChecked) 1 else 0
            val glucose = editGlucose.text.toString()
            val bloodPressure = editBloodPressure.text.toString()
            val birthDate = editBirthDate.text.toString()
            val weight = editWeight.text.toString()
            val height = editHeight.text.toString()

            // Validasi input
            if (glucose.isEmpty() || bloodPressure.isEmpty() || birthDate.isEmpty() || weight.isEmpty() || height.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Parsing input
                val glucoseValue = glucose.toInt()
                val bloodPressureValue = bloodPressure.toInt()
                val weightValue = weight.toFloat()
                val heightValue = height.toFloat() / 100 // Konversi cm ke meter
                val age = calculateAge(birthDate)

                if (age < 0) {
                    Toast.makeText(this, "Harap masukkan tanggal lahir yang valid", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Perhitungan BMI
                val bmiValue = weightValue / (heightValue * heightValue)
                textCalculatedBMI.text = String.format("BMI: %.2f", bmiValue)

                // Berikan feedback ke pengguna
                Toast.makeText(
                    this,
                    "Data terkirim: Hamil: $pregnancies, Glukosa: $glucoseValue, BMI: %.2f".format(bmiValue),
                    Toast.LENGTH_LONG
                ).show()

                // Pindah ke LoadingDataActivity
                val intent = Intent(this, LoadingDataActivity::class.java)
                intent.putExtra("Pregnancies", pregnancies)
                intent.putExtra("Glucose", glucoseValue)
                intent.putExtra("BloodPressure", bloodPressureValue)
                intent.putExtra("BMI", bmiValue)
                intent.putExtra("Age", age)
                startActivity(intent)

            } catch (e: NumberFormatException) {
                // Menangani input non-numerik
                Toast.makeText(this, "Harap masukkan angka yang valid", Toast.LENGTH_SHORT).show()
            } catch (e: DateTimeParseException) {
                // Menangani format tanggal lahir tidak valid
                Toast.makeText(this, "Harap masukkan tanggal lahir dalam format YYYY-MM-DD", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk menghitung umur berdasarkan tanggal lahir
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(birthDate: String): Int {
        return try {
            val birthDateParsed = LocalDate.parse(birthDate)
            val currentDate = LocalDate.now()
            val age = Period.between(birthDateParsed, currentDate).years
            age
        } catch (e: DateTimeParseException) {
            -1 // Mengembalikan -1 jika tanggal tidak valid
        }
    }
}
