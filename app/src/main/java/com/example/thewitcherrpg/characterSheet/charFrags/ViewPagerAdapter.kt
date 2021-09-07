package com.example.thewitcherrpg.characterSheet.charFrags

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle,  private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return QuickStatsFragment()
            1 -> return ProfessionFragment()
            2 -> return QuickStatsFragment()
            3 -> return QuickStatsFragment()
            4 -> return QuickStatsFragment()
            5 -> return QuickStatsFragment()

        }
        return ProfessionFragment()
    }
}



