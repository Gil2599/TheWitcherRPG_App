package com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.FragmentNoMagicBinding
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.characterMagicFragments.CharMagicFragmentDirections

class NoMagicFragment : Fragment() {
    private var _binding: FragmentNoMagicBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoMagicBinding.inflate(inflater, container, false)

        binding.enableMagicalGiftsButton.setOnClickListener {
            mainCharacterViewModel.setMagicalGiftsEnabled(true)
            val action = NoMagicFragmentDirections.actionNoMagicFragmentToCharMagicFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}