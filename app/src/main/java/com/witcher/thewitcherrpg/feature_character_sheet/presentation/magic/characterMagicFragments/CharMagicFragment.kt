package com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.characterMagicFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.Constants
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.FragmentCharMagicBinding
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.MagicViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.MainActivity

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
        when (mainCharacterViewModel.profession.value) {
            Constants.Professions.WITCHER -> {
                binding.addMagicButtonsLinearLayout.removeView(binding.addSpellButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addRitualButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addHexButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addInvocationButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addGiftButton)
            }
            Constants.Professions.PRIEST, Constants.Professions.DRUID -> {
                binding.addMagicButtonsLinearLayout.removeView(binding.addSpellButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addGiftButton)
            }
            Constants.Professions.MAGE -> {
                binding.addMagicButtonsLinearLayout.removeView(binding.addInvocationButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addGiftButton)
            }
            else -> {
                binding.addMagicButtonsLinearLayout.removeView(binding.addSpellButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addRitualButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addHexButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addInvocationButton)
                binding.addMagicButtonsLinearLayout.removeView(binding.addSignButton)
            }
        }

        //Setting up Add FAB
        binding.addButton.setOnClickListener {
            onAddButtonClicked()
        }
        binding.addGiftButton.setOnClickListener {
            val action = CharMagicFragmentDirections.actionCharMagicFragmentToMagicAddFragment(MagicViewPagerAdapter.FragmentName.MAGICAL_GIFTS)
            findNavController().navigate(action)
        }
        //If Mage, go to add spells fragment
        binding.addSpellButton.setOnClickListener {
            val action = CharMagicFragmentDirections.actionCharMagicFragmentToMagicAddFragment(MagicViewPagerAdapter.FragmentName.SPELLS)
            findNavController().navigate(action)
        }
        binding.addInvocationButton.setOnClickListener {
            if (mainCharacterViewModel.profession.value == Constants.Professions.DRUID)  {
                val action = CharMagicFragmentDirections.actionCharMagicFragmentToMagicAddFragment(MagicViewPagerAdapter.FragmentName.DRUID_INVOCATIONS)
                findNavController().navigate(action)
            } else {
                val action = CharMagicFragmentDirections.actionCharMagicFragmentToMagicAddFragment(MagicViewPagerAdapter.FragmentName.PRIEST_INVOCATIONS)
                findNavController().navigate(action)
            }
        }
        //Go to add signs fragment
        binding.addSignButton.setOnClickListener {
            val action = CharMagicFragmentDirections.actionCharMagicFragmentToMagicAddFragment(MagicViewPagerAdapter.FragmentName.SIGNS)
            findNavController().navigate(action)
        }

        //Go to add rituals fragment
        binding.addRitualButton.setOnClickListener {
            val action = CharMagicFragmentDirections.actionCharMagicFragmentToMagicAddFragment(MagicViewPagerAdapter.FragmentName.RITUALS)
            findNavController().navigate(action)
        }

        //Go to add hexes fragment
        binding.addHexButton.setOnClickListener {
            val action = CharMagicFragmentDirections.actionCharMagicFragmentToMagicAddFragment(MagicViewPagerAdapter.FragmentName.HEXES)
            findNavController().navigate(action)
        }

        //Initial animations
        binding.addGiftButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addSpellButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addInvocationButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addRitualButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addHexButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addSignButton.animate().scaleX(0.8F).scaleY(0.8F).alpha(0F)
            .translationY(50F).duration = 0
        binding.addGiftButton.visibility = View.GONE
        binding.addSpellButton.visibility = View.GONE
        binding.addInvocationButton.visibility = View.GONE
        binding.addRitualButton.visibility = View.GONE
        binding.addHexButton.visibility = View.GONE
        binding.addSignButton.visibility = View.GONE

        binding.textViewStamina.setOnClickListener {
            (requireActivity() as MainActivity).showEditStatDialog(
                label = "Current Stamina",
                onPlus = {
                    mainCharacterViewModel.onStaminaChange(it, increase = true)
                },
                onMinus = {
                    mainCharacterViewModel.onStaminaChange(-it, increase = false)
                },
                currentValue = mainCharacterViewModel.sta.value.toString()
            )
        }
        binding.textViewFocus.setOnClickListener {
            (requireActivity() as MainActivity).showEditStatDialog(
                label = "Current Focus",
                onPlus = {
                    mainCharacterViewModel.onFocusChange(it)
                },
                onMinus = {
                    mainCharacterViewModel.onFocusChange(-it)
                },
                currentValue = mainCharacterViewModel.focus.value.toString()
            )
        }
        binding.textViewVigor.setOnClickListener {
            (requireActivity() as MainActivity).showEditStatDialog(
                label = "Current Vigor",
                onPlus = {
                    mainCharacterViewModel.onVigorChange(it)
                },
                onMinus = {
                    mainCharacterViewModel.onVigorChange(-it)
                },
                currentValue = mainCharacterViewModel.vigor.value.toString()
            )
        }

        return view
    }

    //Implement animations and logic for expandable action button
    private fun onAddButtonClicked() {
        setAnimation()
        buttonClicked = !buttonClicked
    }

    private fun setAnimation() {

        if (!buttonClicked) {
            binding.addGiftButton.animate()
                .withStartAction {
                    binding.addGiftButton.visibility = View.VISIBLE
                }
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addSpellButton.animate()
                .withStartAction {
                    binding.addSpellButton.visibility = View.VISIBLE
                }
                .alpha(1F)
                .setDuration(300)
                .translationY(-10F)

            binding.addInvocationButton.animate()
                .withStartAction {
                    binding.addInvocationButton.visibility = View.VISIBLE
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
            binding.addGiftButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction {
                    binding.addGiftButton.visibility = View.GONE
                }

            binding.addSpellButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction {
                    binding.addSpellButton.visibility = View.GONE
                }

            binding.addInvocationButton.animate()
                .alpha(0F)
                .setDuration(300)
                .translationY(50F)
                .withEndAction {
                    binding.addInvocationButton.visibility = View.GONE
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
                MagicViewPagerAdapter.FragmentName.SPELLS -> resources.getString(R.string.spells)
                MagicViewPagerAdapter.FragmentName.PRIEST_INVOCATIONS-> resources.getString(R.string.priest_invocations)
                MagicViewPagerAdapter.FragmentName.DRUID_INVOCATIONS -> resources.getString(R.string.druid_invocations)
                MagicViewPagerAdapter.FragmentName.RITUALS -> resources.getString(R.string.rituals)
                MagicViewPagerAdapter.FragmentName.HEXES -> resources.getString(R.string.hexes)
                MagicViewPagerAdapter.FragmentName.SIGNS -> resources.getString(R.string.signs)
                MagicViewPagerAdapter.FragmentName.MAGICAL_GIFTS -> resources.getString(R.string.magical_gifts)
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
                tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_magic_icon)
                tabsLayout.getTabAt(1)?.setIcon(R.drawable.ic_ritual_icon)
                tabsLayout.getTabAt(2)?.setIcon(R.drawable.ic_hex_icon)
                tabsLayout.getTabAt(3)?.setIcon(R.drawable.ic_aard_sign_icon)
            }
            Constants.Professions.PRIEST -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.PRIEST_INVOCATIONS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.RITUALS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.HEXES)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
                tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_celtic_knot_icon)
                tabsLayout.getTabAt(1)?.setIcon(R.drawable.ic_ritual_icon)
                tabsLayout.getTabAt(2)?.setIcon(R.drawable.ic_hex_icon)
                tabsLayout.getTabAt(3)?.setIcon(R.drawable.ic_aard_sign_icon)
            }
            Constants.Professions.DRUID -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.DRUID_INVOCATIONS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.RITUALS)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.HEXES)
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.SIGNS)
                tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_celtic_knot_icon)
                tabsLayout.getTabAt(1)?.setIcon(R.drawable.ic_ritual_icon)
                tabsLayout.getTabAt(2)?.setIcon(R.drawable.ic_hex_icon)
                tabsLayout.getTabAt(3)?.setIcon(R.drawable.ic_aard_sign_icon)
            }
            else -> {
                tabAdapter.add(MagicViewPagerAdapter.FragmentName.MAGICAL_GIFTS)
                tabsLayout.getTabAt(0)?.setIcon(R.drawable.ic_magical_gift)
            }
        }
    }

    override fun onStart() {
        when (mainCharacterViewModel.profession.value) {
            Constants.Professions.MAGE, Constants.Professions.WITCHER, Constants.Professions.PRIEST, Constants.Professions.DRUID -> {}

            else -> {
                if (!mainCharacterViewModel.magicalGiftsEnabled) {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_charMagicFragment_to_noMagicFragment)
                }
            }
        }
        super.onStart()
    }
}