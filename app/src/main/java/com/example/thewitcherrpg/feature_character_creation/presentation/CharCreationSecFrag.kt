package com.example.thewitcherrpg.feature_character_creation.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCharCreationSecBinding
import com.example.thewitcherrpg.feature_character_sheet.presentation.skills.SkillsFragment

class CharCreationSecFrag : Fragment() {
    private var _binding: FragmentCharCreationSecBinding? = null
    private val binding get() = _binding!!

    private val fragment = SkillsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharCreationSecBinding.inflate(inflater, container, false)
        val view = binding.root

        //Check if fragment exists in case user tries to return to this fragment after pressing back button and saves state
        if(childFragmentManager.findFragmentByTag("frag") == null){
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment, "frag")
                .commitNow()
        }

        binding.buttonToThirdFrag.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_charCreation_secFrag_to_charCreation_thirdFrag2)
        }
        binding.buttonToFirstFrag.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_charCreation_secFrag_to_charCreation_firstFrag)
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}