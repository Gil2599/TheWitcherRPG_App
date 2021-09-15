package com.example.thewitcherrpg.characterSheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCharCreationFirstBinding
import com.example.thewitcherrpg.databinding.FragmentPlaceholderBinding

class PlaceholderFragment : Fragment() {
    private var _binding: FragmentPlaceholderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceholderBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}