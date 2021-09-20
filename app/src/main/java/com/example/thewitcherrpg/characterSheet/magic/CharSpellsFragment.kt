package com.example.thewitcherrpg.characterSheet.magic

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.JourneymanSpellListAdapter
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.MasterSpellListAdapter
import com.example.thewitcherrpg.characterSheet.magic.SpellListAdapters.NoviceSpellListAdapter
import com.example.thewitcherrpg.databinding.FragmentCharSpellsBinding
import kotlinx.android.synthetic.main.custom_dialog_add_spell.*
import kotlinx.android.synthetic.main.custom_dialog_add_spell.defense_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.duration_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.effect_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.range_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.spell_name_text
import kotlinx.android.synthetic.main.custom_dialog_add_spell.sta_cost_text
import kotlinx.android.synthetic.main.custom_dialog_char_spell.*

class CharSpellsFragment : Fragment() {
    private var _binding: FragmentCharSpellsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentCharSpellsBinding.inflate(inflater, container, false)
        val view = binding.root

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        val noviceAdapter = NoviceSpellListAdapter(requireContext()){
                spell -> showSpellDialog(spell)
        }
        sharedViewModel.noviceSpellList.observe(viewLifecycleOwner, { spell ->
            noviceAdapter.setData(spell)
        })

        val journeymanAdapter = JourneymanSpellListAdapter(requireContext()){
                spell -> showSpellDialog(spell)
        }
        sharedViewModel.journeymanSpellList.observe(viewLifecycleOwner, { spell ->
            journeymanAdapter.setData(spell)
        })

        val masterAdapter = MasterSpellListAdapter(requireContext()){
                spell -> showSpellDialog(spell)
        }
        sharedViewModel.masterSpellList.observe(viewLifecycleOwner, { spell ->
            masterAdapter.setData(spell)
        })

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

    //Set up dialog when a character spell is clicked
    private fun showSpellDialog(spell: String?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_char_spell)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val pair = spell!!.split(":").toTypedArray()
        val spellName = pair[0]
        val staCost = "<b>" + "STA Cost: " + "</b>" + pair[1]
        val effect = "<b>" + "Effect: " + "</b>" + pair[2]
        val range = "<b>" + "Range: " + "</b>" + pair[3]
        val duration = "<b>" + "Duration: " + "</b>" + pair[4]
        val defense = "<b>" + "Defense: " + "</b>" + pair[5]
        val element = pair[6]
        val charSta = "<b>" + "STA: " + "</b>" + sharedViewModel.sta.value.toString()
        val vigor = sharedViewModel.vigor.value!!

        dialog.spell_name_text.text = spellName
        dialog.sta_cost_text.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.range_text.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.defense_text.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.effect_text.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.duration_text.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.element_text.text = element
        dialog.stamina_text.text = HtmlCompat.fromHtml(charSta, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.vigor_text.text = HtmlCompat.fromHtml("<b>Vigor: </b>$vigor", HtmlCompat.FROM_HTML_MODE_LEGACY)

        dialog.castSpellbutton.setOnClickListener(){
            if (!sharedViewModel.castSpell(pair[1].toInt())) { //Stamina cost without html text
                showCastSpellDialog(pair[1].toInt()) //Show how much HP a character would lose to cast a spell if not enough vigor
            }

            dialog.dismiss()
        }
        dialog.cancel_button.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.removebutton.setOnClickListener(){
            //Remove the spell in whichever list it is in
            sharedViewModel.removeNoviceSpell(spellName)
            sharedViewModel.removeJourneymanSpell(spellName)
            sharedViewModel.removeMasterSpell(spellName)
            Toast.makeText(context, "$spellName removed from ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    //Dialog that asks the user if they would like to use a portion of their HP to cast a spell that they
    //do not have enough stamina for
    private fun showCastSpellDialog(staCost: Int){

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ -> }
        builder.setNegativeButton("Cancel") { _, _ -> }

        builder.setTitle("NOT ENOUGH VIGOR")
        builder.setMessage("You do not have enough Vigor to cast this spell. If you decide to cast this spell, " +
                "you will lose (" + (staCost - sharedViewModel.vigor.value!!).toString() + ") HP. Do you wish to " +
                "cast this spell?")
        builder.create().show()
    }


}

