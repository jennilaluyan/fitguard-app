package com.dicodingg.bangkit.ui

import android.widget.Button
import android.widget.TextView
import com.dicodingg.bangkit.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class ProfileFragment : Fragment(R.layout.fragment_profil) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan findViewById untuk mengakses elemen
        val textUserName = view.findViewById<TextView>(R.id.textUserName)
        val textUserInfo = view.findViewById<TextView>(R.id.textUserInfo)
        val textGlucoseLevel = view.findViewById<TextView>(R.id.textGlucoseLevel)
        val textWeight = view.findViewById<TextView>(R.id.textWeight)
        val buttonEditProfile = view.findViewById<Button>(R.id.buttonEditProfile)

        // Set data
        textUserName.text = "Nama Pengguna"
        textUserInfo.text = "25 Tahun | Jakarta"
        textGlucoseLevel.text = "Normal"
        textWeight.text = "70 kg"

        // Set click listener untuk tombol edit profil
        buttonEditProfile.setOnClickListener {
            // Aksi untuk tombol edit, seperti membuka activity lain atau fragment lain
        }
    }
}
