package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.CharacterBackgroundFragment
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.PersonalInfoFragment
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.ProfessionFragment
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.QuickStatsFragment

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle,  private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return QuickStatsFragment()
            1 -> return ProfessionFragment()
            2 -> return PersonalInfoFragment()
            3 -> return CharacterBackgroundFragment()
        }
        return ProfessionFragment()
    }
}



