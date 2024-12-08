package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.MainActivityBinding
import com.dicodingg.bangkit.ui.dashboard.DashboardFragment
import com.dicodingg.bangkit.ui.ProfileFragment
import com.dicodingg.bangkit.ui.ReportFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(DashboardFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.navigation_dashboard -> replaceFragment(DashboardFragment())
                R.id.navigation_profile -> replaceFragment(ProfileFragment())
                R.id.navigation_reports -> replaceFragment(ReportFragment())
                else -> {
                    // Optional: Handle other cases if needed
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

        // Log to check if the fragment was loaded successfully
        Log.d("MainActivity", "Fragment ${fragment::class.java.simpleName} successfully loaded.")
    }
}
