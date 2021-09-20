package com.example.thewitcherrpg.characterSheet.magic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.databinding.FragmentCharMagicBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CharMagicFragment : Fragment() {
    private var _binding: FragmentCharMagicBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabAdapter: MagicViewPagerAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var buttonClicked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharMagicBinding.inflate(inflater, container, false)
        val view = binding.root

        setupViewPager()

        //Remove or change magic categories depending on profession
        if(sharedViewModel.profession.value == "Witcher"){
            (binding.addSpellButton.parent as ViewGroup).removeView(binding.addSpellButton)
            (binding.addRitualButton.parent as ViewGroup).removeView(binding.addRitualButton)
            (binding.addHexButton.parent as ViewGroup).removeView(binding.addHexButton)
        }
        else if (sharedViewModel.profession.value == "Priest"){
            binding.addSpellButton.setImageResource(R.drawable.ic_celtic_knot_icon)
        }

        //Setting up Add FAB
        binding.addButton.setOnClickListener(){
            onAddButtonClicked()
        }
        binding.addSpellButton.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_charMagicFragment_to_spellAddFragment)
        }

        binding.addSpellButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0
        binding.addRitualButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0
        binding.addHexButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0
        binding.addSignButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F).translationY(50F).duration = 0


        return view
    }

    //Implement animations and logic for expandable action button
    private fun onAddButtonClicked() {
        setAnimation()
        buttonClicked = !buttonClicked
    }

    private fun setAnimation(){

        if (!buttonClicked){
            binding.addSpellButton.animate()
                .withStartAction(Runnable {
                    binding.addSpellButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addRitualButton.animate()
                .withStartAction(Runnable {
                    binding.addRitualButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addHexButton.animate()
                .withStartAction(Runnable {
                    binding.addRitualButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addSignButton.animate()
                .withStartAction(Runnable {
                    binding.addRitualButton.visibility = View.VISIBLE })
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addButton.animate()
                .rotation(135F) }

        else{
            binding.addSpellButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            binding.addRitualButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            binding.addHexButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            binding.addSignButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction(Runnable {
                    binding.addSpellButton.visibility = View.GONE })

            //binding.addButton.startAnimation(rotateClose)
            binding.addButton.animate()
                .rotation(0F).duration = 300
        }
    }

    private fun setupViewPager(){
        //Setting up viewPager
        tabAdapter = MagicViewPagerAdapter(childFragmentManager, lifecycle)

        val viewPager = binding.viewPager2
        viewPager.apply{adapter = tabAdapter}

        val tabsLayout: TabLayout = binding.MagicTabs
        TabLayoutMediator(tabsLayout, viewPager,true) {tab, position ->
            tab.text = when (tabAdapter.fragmentList[position]) {
                MagicViewPagerAdapter.FragmentName.SPELLS -> "Spells"
                MagicViewPagerAdapter.FragmentName.INVOCATIONS -> "Invocations"
                MagicViewPagerAdapter.FragmentName.RITUALS -> "Rituals"
                MagicViewPagerAdapter.FragmentName.HEXES -> "Hexes"
                MagicViewPagerAdapter.FragmentName.SIGNS -> "Signs"
            }
        }.attach()

        //Check the profession and set the tabLayout and Adapter accordingly
        when (sharedViewModel.profession.value){
            "Witcher" -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
                tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_aard_sign_icon)
            }

            "Mage" -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SPELLS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.RITUALS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.HEXES)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
            }
            "Priest" -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.INVOCATIONS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.RITUALS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.HEXES)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
            }

        }
        if (sharedViewModel.profession.value == "Mage") tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_magic_icon)
        else if (sharedViewModel.profession.value == "Priest") tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_celtic_knot_icon)

        tabsLayout.getTabAt(1)?.setIcon(R.drawable.ic_ritual_icon)
        tabsLayout.getTabAt(2)?.setIcon(R.drawable.ic_hex_icon)
        tabsLayout.getTabAt(3)?.setIcon(R.drawable.ic_aard_sign_icon)
    }

    override fun onStart() {
        when (sharedViewModel.profession.value){
            "Mage", "Witcher", "Priest" -> super.onStart()

            else -> Navigation.findNavController(requireView()).navigate(R.id.action_charMagicFragment_to_noMagicFragment)
        }
        super.onStart()
    }
}