package com.example.thewitcherrpg.characterSheet.charFrags

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thewitcherrpg.characterSheet.PlaceholderFragment

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle,  private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return QuickStatsFragment()
            1 -> return ProfessionFragment()
            2 -> return PlaceholderFragment()
            3 -> return PlaceholderFragment()
            4 -> return PlaceholderFragment()
            5 -> return PlaceholderFragment()

        }
        return ProfessionFragment()
    }
}



