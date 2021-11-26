package com.example.thewitcherrpg.feature_character_sheet.presentation.campaign_notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCampaignNotesBinding
import com.example.thewitcherrpg.databinding.FragmentInventoryBinding

class CampaignNotesFragment : Fragment() {
    private var _binding: FragmentCampaignNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCampaignNotesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.customTitle.setTitle("Campaign Notes")
        binding.customTitle.setTitleSize(20F)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

}