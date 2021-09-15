package com.example.thewitcherrpg.characterCreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.StatsFragment
import com.example.thewitcherrpg.databinding.FragmentCharCreationThirdBinding

class CharCreationThirdFrag : Fragment() {
    private var _binding: FragmentCharCreationThirdBinding? = null
    private val binding get() = _binding!!

    private val fragment = StatsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharCreationThirdBinding.inflate(inflater, container, false)
        val view = binding.root

        if(childFragmentManager.findFragmentByTag("frag") == null){
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment, "frag")
                .commitNow()
        }

        binding.buttonFinish.setOnClickListener(){
            (activity as CharCreationActivity?)?.onSaveFinal()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}