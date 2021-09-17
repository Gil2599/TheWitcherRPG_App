package com.example.thewitcherrpg.characterSheet.magic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.CharSpellsListAdapter
import com.example.thewitcherrpg.databinding.FragmentSpellsBinding

class SpellsFragment : Fragment() {
    private var _binding: FragmentSpellsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentSpellsBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = CharSpellsListAdapter(requireContext())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sharedViewModel.spellList.observe(viewLifecycleOwner, {spell ->
            adapter.setData(spell)
        })

        binding.addButton.setOnClickListener(){
            sharedViewModel.addSpell("Blinding Dust " + (0..10).random())
            Navigation.findNavController(view).navigate(R.id.action_spellsFragment_to_spellAddFragment)

        }


        return view
    }

}

