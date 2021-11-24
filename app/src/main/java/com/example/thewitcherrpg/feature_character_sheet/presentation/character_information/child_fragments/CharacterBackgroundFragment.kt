package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCharacterBackgroundBinding
import com.example.thewitcherrpg.databinding.FragmentPersonalInfoBinding

class CharacterBackgroundFragment : Fragment() {
    private var _binding: FragmentCharacterBackgroundBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBackgroundBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.customTitle.setTitle("Background Events")
        binding.customTitle.setTitleSize(20F)

        return view
    }
}
