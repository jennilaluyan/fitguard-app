//PhysicalActivityFragment.kt
package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicodingg.bangkit.databinding.FragmentPhysicalActivityBinding

class PhysicalActivityFragment : Fragment() {

    private var _binding: FragmentPhysicalActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhysicalActivityBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up back button to return to the dashboard
        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
