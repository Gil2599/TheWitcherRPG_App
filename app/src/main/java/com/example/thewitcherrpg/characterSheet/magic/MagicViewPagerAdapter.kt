package com.example.thewitcherrpg.characterSheet.magic

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MagicViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle)  {

    val fragmentList: MutableList<FragmentName> = mutableListOf()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (fragmentList[position]) {
            FragmentName.SPELLS -> CharSpellsFragment()
            FragmentName.INVOCATIONS -> CharInvocationsFragment()
            FragmentName.RITUALS -> NoMagicFragment()
            FragmentName.HEXES -> NoMagicFragment()
            FragmentName.SIGNS -> NoMagicFragment()
        }
    }

    override fun getItemId(position: Int): Long {
        return fragmentList[position].ordinal.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        val fragment = FragmentName.values()[itemId.toInt()]
        return fragmentList.contains(fragment)
    }

    fun add(fragment: FragmentName) {
        fragmentList.add(fragment)
        notifyDataSetChanged()
    }

    fun add(index: Int, fragment: FragmentName) {
        fragmentList.add(index, fragment)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        fragmentList.removeAt(index)
        notifyDataSetChanged()
    }

    fun remove(name: FragmentName) {
        fragmentList.remove(name)
        notifyDataSetChanged()
    }

    //Enum class to be able to add/remove tabs depending on profession
    enum class FragmentName {
        SPELLS,
        INVOCATIONS,
        RITUALS,
        HEXES,
        SIGNS
    }

}