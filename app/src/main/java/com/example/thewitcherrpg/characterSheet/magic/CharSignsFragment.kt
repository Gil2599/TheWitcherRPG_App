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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.magic.signsListAdapters.AlternateSignsListAdapter
import com.example.thewitcherrpg.characterSheet.magic.signsListAdapters.BasicSignsListAdapter
import com.example.thewitcherrpg.databinding.FragmentCharSignsBinding

class CharSignsFragment : Fragment() {
    private var _binding: FragmentCharSignsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharSignsBinding.inflate(inflater, container, false)
        val view = binding.root

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        val basicAdapter = BasicSignsListAdapter(requireContext()){
                spell -> showSpellDialog(spell)
        }
        sharedViewModel.basicSigns.observe(viewLifecycleOwner, { spell ->
            basicAdapter.setData(spell)
        })

        val alternateAdapter = AlternateSignsListAdapter(requireContext()){
                spell -> showSpellDialog(spell)
        }
        sharedViewModel.alternateSigns.observe(viewLifecycleOwner, { spell ->
            alternateAdapter.setData(spell)
        })

        binding.recyclerViewBasic.adapter = basicAdapter
        binding.recyclerViewBasic.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewAlternate.adapter = alternateAdapter
        binding.recyclerViewAlternate.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewBasic.isNestedScrollingEnabled = false
        binding.recyclerViewAlternate.isNestedScrollingEnabled = false

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

        /*dialog.spell_name_text.text = spellName
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
            sharedViewModel.removeBasicSign(spellName)
            sharedViewModel.removeAlternateSign(spellName)
            Toast.makeText(context, "$spellName removed from ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }*/

        dialog.show()
    }
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