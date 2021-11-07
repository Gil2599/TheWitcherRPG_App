package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.characterMagicFragments

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.databinding.CustomDialogCharHexBinding
import com.example.thewitcherrpg.databinding.FragmentCharHexesBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.MagicListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharHexesFragment : Fragment() {
    private var _binding: FragmentCharHexesBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharHexesBinding.inflate(inflater, container, false)
        val view = binding.root

        listAdapterInit()

        return view
    }

    private fun listAdapterInit(){

        val hexesAdapter = MagicListAdapter{
                spell -> showSpellDialog(spell)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.hexesList.collectLatest { itemList ->
                        hexesAdapter.setData(itemList)
                    }
                }
            }
        }

        binding.recyclerViewHexes.adapter = hexesAdapter
        binding.recyclerViewHexes.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewHexes.isNestedScrollingEnabled = false

    }

    private fun showSpellDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogCharHexBinding = CustomDialogCharHexBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val staCost = "<b>" + "STA Cost: " + "</b>" + if (item.staminaCost == null) "Variable" else item.staminaCost
        val effect = "<b>" + "Effect: " + "</b>" + item.description
        val danger = "<b>" + "Danger: " + "</b>" + item.danger
        val lift = "<b>" + "Requirement To Lift: " + "</b>" + item.requirementToLift
        val charSta = "<b>" + "STA: " + "</b>" + sharedViewModel.sta.value.toString()
        val vigor = sharedViewModel.vigor.value!!

        bind.spellNameText.text = item.name
        bind.staCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.effectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.liftText.text = HtmlCompat.fromHtml(lift, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.dangerText.text = HtmlCompat.fromHtml(danger, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.spellStaminaText.text = HtmlCompat.fromHtml(charSta, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.spellVigorText.text = HtmlCompat.fromHtml("<b>Vigor: </b>$vigor", HtmlCompat.FROM_HTML_MODE_LEGACY)

        bind.castSpellbutton.setOnClickListener(){
            if (!sharedViewModel.castSpell(item.staminaCost!!)) { //Stamina cost without html text
                showCastSpellDialog(item.staminaCost!!) //Show how much HP a character would lose to cast a spell if not enough vigor
            }

            dialog.dismiss()
        }
        bind.cancelButton.setOnClickListener(){
            dialog.dismiss()
        }
        bind.removebutton.setOnClickListener(){
            //Remove the spell in whichever list it is in
            //sharedViewModel.removeHex(spellName)
            Toast.makeText(context, "${item.name} removed from ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
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
                "you will lose (" + (5 * (staCost - sharedViewModel.vigor.value!!)).toString() + ") HP. Do you wish to " +
                "cast this spell?")
        builder.create().show()
    }
}