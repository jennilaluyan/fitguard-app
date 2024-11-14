package com.dicodingg.bangkit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicodingg.bangkit.RegisterFragment
import com.dicodingg.bangkit.LoginFragment

class LoginRegisterPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RegisterFragment()  // Fragment for Registration
            1 -> LoginFragment()     // Fragment for Login
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}
