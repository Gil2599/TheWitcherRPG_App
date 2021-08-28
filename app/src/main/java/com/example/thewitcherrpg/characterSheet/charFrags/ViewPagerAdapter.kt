package com.example.thewitcherrpg.characterSheet.charFrags

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thewitcherrpg.characterSheet.StatsFragment

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle,  private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {

    val mCharInfoFragment: CharInfoFragment = CharInfoFragment()
    val mCharStatsFragment: StatsFragment = StatsFragment()

    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return mCharInfoFragment
            1 -> return mCharStatsFragment
            2 -> return CharInfoFragment()
            3 -> return CharInfoFragment()
            4 -> return CharInfoFragment()
            5 -> return CharInfoFragment()

        }
        return StatsFragment2()
    }
}



