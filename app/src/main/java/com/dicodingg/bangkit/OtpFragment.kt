package com.dicodingg.bangkit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpFragment : Fragment() {

    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextOtp: EditText
    private lateinit var buttonSendOtp: Button
    private lateinit var buttonVerifyOtp: Button
    private lateinit var auth: FirebaseAuth

    private var verificationId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inisialisasi Views
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        editTextOtp = view.findViewById(R.id.editTextOtp)
        buttonSendOtp = view.findViewById(R.id.buttonSendOtp)
        buttonVerifyOtp = view.findViewById(R.id.buttonVerifyOtp)

        buttonSendOtp.setOnClickListener {
            val phoneNumber = editTextPhoneNumber.text.toString().trim()

            if (phoneNumber.isEmpty()) {
                Toast.makeText(context, "Nomor telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cek apakah perangkat berasal dari Transsion
            if (Build.MANUFACTURER == "Transsion") {
                // Skip fitur multi-display pada perangkat Transsion
                Toast.makeText(context, "Perangkat ini tidak mendukung fitur multi-display", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sendOtp(phoneNumber)
        }

        buttonVerifyOtp.setOnClickListener {
            val otp = editTextOtp.text.toString().trim()

            if (otp.isEmpty()) {
                Toast.makeText(context, "Kode OTP tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            verifyOtp(otp)
        }

        return view
    }

    private fun sendOtp(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(context, "Pengiriman OTP gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    this@OtpFragment.verificationId = verificationId
                    Toast.makeText(context, "OTP terkirim", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyOtp(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Verifikasi berhasil!", Toast.LENGTH_SHORT).show()
                    // Pindah ke HomeActivity
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, "Kode OTP tidak valid", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
