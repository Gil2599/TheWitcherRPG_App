package com.example.thewitcherrpg.characterSheet.magic

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.JourneymanSpellListAdapter
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.NoviceSpellListAdapter
import com.example.thewitcherrpg.databinding.FragmentSpellAddBinding
import kotlinx.android.synthetic.main.custom_dialog_spell.*

class SpellAddFragment : Fragment() {
    private var _binding: FragmentSpellAddBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpellAddBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view).navigate(R.id.action_spellAddFragment_to_spellsFragment)
        }
        callback.isEnabled = true

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        val noviceAdapter = NoviceSpellListAdapter(requireContext()){
            spell -> showSpellDialog(spell)
        }
        noviceAdapter.setData(resources.getStringArray(R.array.novice_spells_list_data).toList())

        val journeymanAdapter = JourneymanSpellListAdapter(requireContext())
        journeymanAdapter.setData(resources.getStringArray(R.array.journeyman_spells_list_data).toList())

        binding.recyclerViewNovice.adapter = noviceAdapter
        binding.recyclerViewNovice.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneyman.adapter = journeymanAdapter
        binding.recyclerViewJourneyman.layoutManager = LinearLayoutManager(requireContext())



        binding.recyclerViewNovice.isNestedScrollingEnabled = false
        binding.recyclerViewJourneyman.isNestedScrollingEnabled = false

    }

    private fun showSpellDialog(spell: String?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_spell)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.spell_name_text.text = spell
        //textview.setText(Html.fromHtml(resources.getString(R.string.text)));

        dialog.show()
    }



}