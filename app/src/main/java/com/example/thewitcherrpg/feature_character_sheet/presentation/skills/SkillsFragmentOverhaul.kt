package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentSkillsBinding
import com.example.thewitcherrpg.databinding.FragmentSkillsOverhaulBinding

class SkillsFragmentOverhaul : Fragment() {
    private var _binding: FragmentSkillsOverhaulBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skills_overhaul, container, false)
        val view = binding.root


        binding.editText1.setSkillText("Awareness:")
        binding.editText2.setSkillText("Testnggggggggg:")




        return view
    }
}