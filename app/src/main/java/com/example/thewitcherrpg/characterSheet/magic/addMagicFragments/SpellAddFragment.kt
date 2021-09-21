package com.example.thewitcherrpg.characterSheet.magic.addMagicFragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.magic.spellListAdapters.JourneymanSpellListAdapter
import com.example.thewitcherrpg.characterSheet.magic.spellListAdapters.MasterSpellListAdapter
import com.example.thewitcherrpg.characterSheet.magic.spellListAdapters.NoviceSpellListAdapter
import com.example.thewitcherrpg.databinding.FragmentSpellAddBinding
import kotlinx.android.synthetic.main.custom_dialog_add_spell.*

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
            Navigation.findNavController(view).navigate(R.id.action_spellAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        //Receive information from recyclerView adapter
        val noviceAdapter = NoviceSpellListAdapter(requireContext()){
            spell -> showSpellDialog(spell, "novice") //Determines which list this spell goes under
        }
        noviceAdapter.setData(resources.getStringArray(R.array.novice_spells_list_data).toList())
        noviceAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val journeymanAdapter = JourneymanSpellListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "journeyman")
        }
        journeymanAdapter.setData(resources.getStringArray(R.array.journeyman_spells_list_data).toList())
        journeymanAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val masterAdapter = MasterSpellListAdapter(requireContext()){
                spell -> showSpellDialog(spell, "master")
        }
        masterAdapter.setData(resources.getStringArray(R.array.master_spells_list_data).toList())
        masterAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        binding.recyclerViewNovice.adapter = noviceAdapter
        binding.recyclerViewNovice.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneyman.adapter = journeymanAdapter
        binding.recyclerViewJourneyman.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewMaster.adapter = masterAdapter
        binding.recyclerViewMaster.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewNovice.isNestedScrollingEnabled = false
        binding.recyclerViewJourneyman.isNestedScrollingEnabled = false
        binding.recyclerViewMaster.isNestedScrollingEnabled = false

    }

    private fun showSpellDialog(spell: String?, level: String?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_add_spell)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val pair = spell!!.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "<b>" + "STA Cost: " + "</b>" + pair[1]
        val effect = "<b>" + "Effect: " + "</b>" + pair[2]
        val range = "<b>" + "Range: " + "</b>" + pair[3]
        val duration = "<b>" + "Duration: " + "</b>" + pair[4]
        val defense = "<b>" + "Defense: " + "</b>" + pair[5]
        val element = pair[6]

        dialog.spell_name_text.text = spellName
        dialog.sta_cost_text.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.range_text.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.defense_text.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.effect_text.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.duration_text.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.add_spell_element_text.text = element
        //textview.setText(Html.fromHtml(resources.getString(R.string.text)));

        //Check spell level to add it to correct character spell list
        dialog.addSpellbutton.setOnClickListener(){
            when (level){
                "novice" -> {
                    if (sharedViewModel.addNoviceSpell(spellName))
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()

                    Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                }
                "journeyman" -> {
                    if (sharedViewModel.addJourneymanSpell(spellName))
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()

                    Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                }
                "master" -> {
                    if (sharedViewModel.addMasterSpell(spellName))
                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()

                    Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.dismiss()
        }

        dialog.add_spell_cancel_button.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }

}