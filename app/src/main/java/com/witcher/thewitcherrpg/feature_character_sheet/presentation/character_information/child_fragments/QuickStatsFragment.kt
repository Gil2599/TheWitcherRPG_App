package com.witcher.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.FragmentCharQuickStatsBinding
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.MainActivity


class QuickStatsFragment : Fragment() {
    private var _binding: FragmentCharQuickStatsBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_char_quick_stats, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = mainCharacterViewModel

        binding.buttonHP.setOnClickListener {
            (requireActivity() as MainActivity).showEditStatDialog(
                label = "Current HP",
                onPlus = {
                    mainCharacterViewModel.onHpChange(it, increase = true)
                },
                onMinus = {
                    mainCharacterViewModel.onHpChange(it, increase = false)
                },
                currentValue = mainCharacterViewModel.hp.value.toString()
            )
        }
        binding.buttonSTA.setOnClickListener {
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
        binding.buttonCROWNS.setOnClickListener {
            (requireActivity() as MainActivity).showEditStatDialog(
                label = "Current Stamina",
                onPlus = {
                    mainCharacterViewModel.onCrownsChange(it)
                },
                onMinus = {
                    mainCharacterViewModel.onCrownsChange(-it)
                },
                currentValue = mainCharacterViewModel.crowns.value.toString()
            )
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
