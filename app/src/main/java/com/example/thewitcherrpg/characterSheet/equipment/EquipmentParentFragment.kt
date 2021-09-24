package com.example.thewitcherrpg.characterSheet.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.databinding.FragmentCharHexesBinding
import com.example.thewitcherrpg.databinding.FragmentEquipmentParentBinding

class EquipmentParentFragment : Fragment() {
    private var _binding: FragmentEquipmentParentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEquipmentParentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}