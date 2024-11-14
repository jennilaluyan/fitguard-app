package com.dicodingg.bangkit

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inisialisasi Views
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Email dan kata sandi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cek apakah perangkat berasal dari Transsion
            if (Build.MANUFACTURER == "Transsion") {
                // Cek apakah perangkat mendukung fitur ini
                try {
                    val multiDisplayClass = Class.forName("com.transsion.display.multidisplay.core.MultiDisplayCoreComponentImpl")
                    Log.d("LoginFragment", "MultiDisplayCoreComponentImpl berhasil dimuat: $multiDisplayClass")
                } catch (e: ClassNotFoundException) {
                    Log.e("LoginFragment", "Kelas MultiDisplayCoreComponentImpl tidak ditemukan: ${e.message}")
                    // Tidak melakukan apa-apa jika kelas tidak ditemukan, lanjutkan dengan proses login
                }
            }

            // Login ke Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login berhasil. Silakan verifikasi dengan OTP.", Toast.LENGTH_SHORT).show()
                        // Pindah ke OtpFragment
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, OtpFragment()) // Ganti dengan ID yang benar
                            .addToBackStack(null)
                            .commit()
                    } else {
                        Toast.makeText(context, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        return view
    }
}
