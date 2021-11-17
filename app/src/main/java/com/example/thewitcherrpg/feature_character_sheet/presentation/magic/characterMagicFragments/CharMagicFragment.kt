package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.characterMagicFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.FragmentCharMagicBinding
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.MagicViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CharMagicFragment : Fragment() {
    private var _binding: FragmentCharMagicBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabAdapter: MagicViewPagerAdapter
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()
    private var buttonClicked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharMagicBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.mainViewModel = mainCharacterViewModel
        binding.lifecycleOwner = this

        setupViewPager()

        //Remove or change magic categories depending on profession
        if (mainCharacterViewModel.profession.value == Constants.Professions.WITCHER) {
            (binding.addSpellButton.parent as ViewGroup).removeView(binding.addSpellButton)
            (binding.addRitualButton.parent as ViewGroup).removeView(binding.addRitualButton)
            (binding.addHexButton.parent as ViewGroup).removeView(binding.addHexButton)
        } else if (mainCharacterViewModel.profession.value == Constants.Professions.PRIEST) {
            binding.addSpellButton.setImageResource(R.drawable.ic_celtic_knot_icon)
        }

        //Setting up Add FAB
        binding.addButton.setOnClickListener {
            onAddButtonClicked()
        }
        //If Mage, go to add spells fragment, otherwise (Priest) go to add invocations fragment
        binding.addSpellButton.setOnClickListener {
            if (mainCharacterViewModel.profession.value == Constants.Professions.MAGE)
                Navigation.findNavController(view)
                    .navigate(R.id.action_charMagicFragment_to_spellAddFragment)
            else Navigation.findNavController(view)
                .navigate(R.id.action_charMagicFragment_to_invocationAddFragment)
        }
        //Go to add signs fragment
        binding.addSignButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_charMagicFragment_to_signsAddFragment)
        }

        //Go to add rituals fragment
        binding.addRitualButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_charMagicFragment_to_ritualAddFragment)
        }

        //Go to add hexes fragment
        binding.addHexButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_charMagicFragment_to_hexesAddFragment)
        }

        //Initial animations
        binding.addSpellButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addRitualButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addHexButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addSignButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addSpellButton.visibility = View.GONE
        binding.addRitualButton.visibility = View.GONE
        binding.addHexButton.visibility = View.GONE
        binding.addSignButton.visibility = View.GONE

        return view
    }

    //Implement animations and logic for expandable action button
    private fun onAddButtonClicked() {
        setAnimation()
        buttonClicked = !buttonClicked
    }

    private fun setAnimation() {

        if (!buttonClicked) {
            binding.addSpellButton.animate()
                .withStartAction {
                    binding.addSpellButton.visibility = View.VISIBLE
                }
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addRitualButton.animate()
                .withStartAction {
                    binding.addRitualButton.visibility = View.VISIBLE
                }
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addHexButton.animate()
                .withStartAction {
                    binding.addHexButton.visibility = View.VISIBLE
                }
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addSignButton.animate()
                .withStartAction {
                    binding.addSignButton.visibility = View.VISIBLE
                }
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addButton.animate()
                .rotation(135F)
        } else {
            binding.addSpellButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction {
                    binding.addSpellButton.visibility = View.GONE
                }

            binding.addRitualButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction {
                    binding.addRitualButton.visibility = View.GONE
                }

            binding.addHexButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction {
                    binding.addHexButton.visibility = View.GONE
                }

            binding.addSignButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction {
                    binding.addSignButton.visibility = View.GONE
                }

            //binding.addButton.startAnimation(rotateClose)
            binding.addButton.animate()
                .rotation(0F).duration = 300
        }
    }

    private fun setupViewPager() {
        //Setting up viewPager
        tabAdapter = MagicViewPagerAdapter(childFragmentManager, lifecycle)

        val viewPager = binding.viewPager2
        viewPager.apply { adapter = tabAdapter }

        val tabsLayout: TabLayout = binding.MagicTabs
        TabLayoutMediator(tabsLayout, viewPager, true) { tab, position ->
            tab.text = when (tabAdapter.fragmentList[position]) {
                MagicViewPagerAdapter.FragmentName.SPELLS -> "Spells"
                MagicViewPagerAdapter.FragmentName.INVOCATIONS -> "Invocations"
                MagicViewPagerAdapter.FragmentName.RITUALS -> "Rituals"
                MagicViewPagerAdapter.FragmentName.HEXES -> "Hexes"
                MagicViewPagerAdapter.FragmentName.SIGNS -> "Signs"
            }
        }.attach()

        //Check the profession and set the tabLayout and Adapter accordingly
        when (mainCharacterViewModel.profession.value) {
            Constants.Professions.WITCHER -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
                tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_aard_sign_icon)
            }

            Constants.Professions.MAGE -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SPELLS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.RITUALS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.HEXES)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
            }
            Constants.Professions.PRIEST -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.INVOCATIONS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.RITUALS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.HEXES)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
            }

        }
        if (mainCharacterViewModel.profession.value == Constants.Professions.MAGE) tabsLayout.getTabAt(
            0
        )?.setIcon(R.drawable.ic_magic_icon)
        else if (mainCharacterViewModel.profession.value == Constants.Professions.PRIEST) tabsLayout.getTabAt(
            0
        )?.setIcon(R.drawable.ic_celtic_knot_icon)

        tabsLayout.getTabAt(1)?.setIcon(R.drawable.ic_ritual_icon)
        tabsLayout.getTabAt(2)?.setIcon(R.drawable.ic_hex_icon)
        tabsLayout.getTabAt(3)?.setIcon(R.drawable.ic_aard_sign_icon)
    }

    override fun onStart() {
        when (mainCharacterViewModel.profession.value) {
            Constants.Professions.MAGE, Constants.Professions.WITCHER, Constants.Professions.PRIEST -> super.onStart()

            else -> Navigation.findNavController(requireView())
                .navigate(R.id.action_charMagicFragment_to_noMagicFragment)
        }
        super.onStart()
    }
}