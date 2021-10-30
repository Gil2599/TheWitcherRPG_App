package com.example.thewitcherrpg.feature_character_sheet.presentation.magic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thewitcherrpg.databinding.FragmentMagicParentBinding

class MagicParentFragment : Fragment() {
    private var _binding: FragmentMagicParentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMagicParentBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }



}