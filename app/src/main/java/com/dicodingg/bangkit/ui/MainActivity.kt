package com.dicodingg.bangkit.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicodingg.bangkit.R
import com.dicodingg.bangkit.databinding.MainActivityBinding
import com.dicodingg.bangkit.ui.dashboard.DashboardFragment
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.OvershootInterpolator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup bottom navigation
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        // Set custom icon colors
        val bottomNav = binding.bottomNavigationView
        bottomNav.itemIconTintList = null

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_dashboard -> {
                    replaceFragment(DashboardFragment())
                    animateBottomNavigationItem(bottomNav, menuItem.itemId)
                    true
                }
                R.id.navigation_reports -> {
                    replaceFragment(ReportFragment())
                    animateBottomNavigationItem(bottomNav, menuItem.itemId)
                    true
                }
                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    animateBottomNavigationItem(bottomNav, menuItem.itemId)
                    true
                }
                else -> false
            }
        }

        // Set initial fragment
        replaceFragment(DashboardFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, // Enter animation
                R.anim.exit  // Exit animation
            )
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    private fun animateBottomNavigationItem(bottomNav: BottomNavigationView, itemId: Int) {
        val itemView = bottomNav.findViewById<View>(itemId)
        itemView?.let {
            val scaleAnimation = AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(it, "scaleX", 0.8f, 1f),
                    ObjectAnimator.ofFloat(it, "scaleY", 0.8f, 1f)
                )
                duration = 200
                interpolator = OvershootInterpolator()
            }
            scaleAnimation.start()
        }
    }
}