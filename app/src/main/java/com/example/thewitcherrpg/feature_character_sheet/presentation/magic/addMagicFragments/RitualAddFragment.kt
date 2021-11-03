package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.addMagicFragments

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
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.ritualsListAdapters.JourneymanRitualListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.ritualsListAdapters.MasterRitualListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.ritualsListAdapters.NoviceRitualListAdapter
import com.example.thewitcherrpg.databinding.CustomDialogAddSpellBinding
import com.example.thewitcherrpg.databinding.FragmentRitualAddBinding

class RitualAddFragment : Fragment() {
    private var _binding: FragmentRitualAddBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRitualAddBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view).navigate(R.id.action_ritualAddFragment_to_charMagicFragment)
        }
        callback.isEnabled = true

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        //Receive information from recyclerView adapter
        val noviceAdapter = NoviceRitualListAdapter(requireContext()){
                spell -> showSpellDialog(spell, SpellLevel.NOVICE) //Determines which list this spell goes under
        }
        noviceAdapter.setData(resources.getStringArray(R.array.novice_rituals_list_data).toList())
        noviceAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val journeymanAdapter = JourneymanRitualListAdapter(requireContext()){
                spell -> showSpellDialog(spell, SpellLevel.JOURNEYMAN)
        }
        journeymanAdapter.setData(resources.getStringArray(R.array.journeyman_rituals_list_data).toList())
        journeymanAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        val masterAdapter = MasterRitualListAdapter(requireContext()){
                spell -> showSpellDialog(spell, SpellLevel.MASTER)
        }
        masterAdapter.setData(resources.getStringArray(R.array.master_rituals_list_data).toList())
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

    private fun showSpellDialog(spell: String?, level: SpellLevel) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogAddSpellBinding = CustomDialogAddSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val pair = spell!!.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "<b>" + "STA Cost: " + "</b>" + pair[1]
        val effect = "<b>" + "Effect: " + "</b>" + pair[2]
        val preparation = "<b>" + "Preparation Time: " + "</b>" + pair[3]
        val difficulty = "<b>" + "Difficulty: " + "</b>" + pair[4]
        val duration = "<b>" + "Duration: " + "</b>" + pair[5]
        val components = "<b>" + "Components: " + "</b>" + pair[6]

        bind.addSpellNameText.text = spellName
        bind.addStaCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addRangeText.text = HtmlCompat.fromHtml(preparation, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDefenseText.text = HtmlCompat.fromHtml(components, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addEffectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addDurationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.addSpellElementText.text = HtmlCompat.fromHtml(difficulty, HtmlCompat.FROM_HTML_MODE_LEGACY)

        //Check spell level to add it to correct character spell list
        bind.addSpellbutton.setOnClickListener(){
            when (level){
                /*SpellLevel.NOVICE -> {
                    if (sharedViewModel.addNoviceRitual(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()

                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()

                }
                SpellLevel.JOURNEYMAN -> {
                    if (sharedViewModel.addJourneymanRitual(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()

                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }
                SpellLevel.MASTER -> {
                    if (sharedViewModel.addMasterRitual(spellName))
                        Toast.makeText(context, "$spellName added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()

                    else Toast.makeText(context, "${sharedViewModel.name.value} already knows $spellName", Toast.LENGTH_SHORT).show()
                }*/
            }

            dialog.dismiss()
        }

        bind.addSpellCancelButton.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }

    //Enum class to determine spell level
    enum class SpellLevel {
        NOVICE,
        JOURNEYMAN,
        MASTER
    }

}