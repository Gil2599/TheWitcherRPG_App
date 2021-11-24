package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.characterMagicFragments

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.core.Resource
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogCharHexBinding
import com.example.thewitcherrpg.databinding.FragmentCharHexesBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.MagicListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharHexesFragment : Fragment() {
    private var _binding: FragmentCharHexesBinding? = null
    private val binding get() = _binding!!

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
        val charSta = "<b>" + "STA: " + "</b>" + mainCharacterViewModel.sta.value.toString()
        val vigor = mainCharacterViewModel.vigor.value

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(18F)
        bind.staCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.effectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.effectText.typeface = Typeface.DEFAULT
        bind.liftText.text = HtmlCompat.fromHtml(lift, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.dangerText.text = HtmlCompat.fromHtml(danger, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.spellStaminaText.text = HtmlCompat.fromHtml(charSta, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.spellVigorText.text = HtmlCompat.fromHtml("<b>Vigor: </b>$vigor", HtmlCompat.FROM_HTML_MODE_LEGACY)

        bind.castSpellbutton.setOnClickListener{
            if (item.staminaCost != null) {
                if (item.staminaCost!! > mainCharacterViewModel.sta.value){
                    Snackbar.make(binding.root, "Not enough stamina to cast ${item.name}.",
                        Snackbar.LENGTH_SHORT).show()
                }
                else {
                    when (val result = mainCharacterViewModel.onCastMagic(item)){
                        is Resource.Error -> showCastSpellDialog(item, result.data!!)
                        is Resource.Success -> {
                            Snackbar.make(binding.root, "${item.name} has been casted.",
                                Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else{
                Snackbar.make(binding.root, "Variable stamina cost: Adjust stamina accordingly.",
                    Snackbar.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        bind.cancelButton.setOnClickListener(){
            dialog.dismiss()
        }
        bind.removebutton.setOnClickListener(){
            mainCharacterViewModel.removeMagicItem(item)
            listAdapterInit()
            Snackbar.make(binding.root, "${item.name} removed from ${mainCharacterViewModel.name.value}",
                Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    //Dialog that asks the user if they would like to use a portion of their HP to cast a spell that they
    //do not have enough stamina for
    private fun showCastSpellDialog(item: MagicItem, hpCost: Int){

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _,
                                           _ -> mainCharacterViewModel.onCastMagic(item, true)}
        builder.setNegativeButton("Cancel") { _, _ -> }

        builder.setTitle("NOT ENOUGH VIGOR")
        builder.setMessage("You do not have enough Vigor to cast this spell. If you decide to cast this spell, " +
                "you will lose (" + hpCost.toString() + ") HP. Do you wish to " +
                "cast this spell?")
        builder.create().show()
    }
}