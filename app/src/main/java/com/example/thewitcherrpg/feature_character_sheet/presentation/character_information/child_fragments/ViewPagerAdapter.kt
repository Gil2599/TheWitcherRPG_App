package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.NoMagicFragment

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle,  private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return QuickStatsFragment()
            1 -> return ProfessionFragment()
            2 -> return NoMagicFragment()
            3 -> return NoMagicFragment()
            4 -> return NoMagicFragment()
            5 -> return NoMagicFragment()

        }
        return ProfessionFragment()
    }
}



