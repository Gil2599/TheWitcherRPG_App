package com.example.thewitcherrpg.characterSheet.magic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thewitcherrpg.databinding.FragmentNoMagicBinding

class NoMagicFragment : Fragment() {
    private var _binding: FragmentNoMagicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoMagicBinding.inflate(inflater, container, false)

        return binding.root
    }

}