package com.dicodingg.bangkit.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.dicodingg.bangkit.R

class PrescriptionFragment : Fragment(R.layout.fragment_prescription_suggestion) {

    // Deklarasikan elemen-elemen UI yang ingin diakses
    private lateinit var titleTextView: TextView
    private lateinit var metforminCardView: CardView
    private lateinit var metforminNameTextView: TextView
    private lateinit var dosageTextView: TextView
    private lateinit var purposeTextView: TextView
    private lateinit var sideEffectsTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginflasi layout XML dan mengembalikan root view
        val view = inflater.inflate(R.layout.fragment_prescription_suggestion, container, false)

        // Inisialisasi elemen-elemen dengan findViewById
        titleTextView = view.findViewById(R.id.titleTextView)
        metforminCardView = view.findViewById(R.id.metforminCardView)
        metforminNameTextView = view.findViewById(R.id.metforminNameTextView)
        dosageTextView = view.findViewById(R.id.dosageTextView)
        purposeTextView = view.findViewById(R.id.purposeTextView)
        sideEffectsTextView = view.findViewById(R.id.sideEffectsTextView)

        // Set data pada elemen-elemen UI
        titleTextView.text = "Prescription and Suggestion"
        metforminNameTextView.text = "Metformin"
        dosageTextView.text = "Dosage: 500 mg (Twice a day)"
        purposeTextView.text = "Purpose: Controls blood sugar levels."
        sideEffectsTextView.text = "Side Effects: Nausea, upset stomach."

        return view
    }
}