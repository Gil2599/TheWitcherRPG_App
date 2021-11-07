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
import com.example.thewitcherrpg.databinding.CustomDialogCharSpellBinding
import com.example.thewitcherrpg.databinding.FragmentCharSignsBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.MagicListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharSignsFragment : Fragment() {
    private var _binding: FragmentCharSignsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

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

        val basicAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }

        val alternateAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.basicSigns.collectLatest { itemList ->
                        basicAdapter.setData(itemList)
                    }
                }
                launch {
                    mainCharacterViewModel.alternateSigns.collectLatest { itemList ->
                        alternateAdapter.setData(itemList)
                    }
                }
            }
        }

        binding.recyclerViewBasic.adapter = basicAdapter
        binding.recyclerViewBasic.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewAlternate.adapter = alternateAdapter
        binding.recyclerViewAlternate.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewBasic.isNestedScrollingEnabled = false
        binding.recyclerViewAlternate.isNestedScrollingEnabled = false

    }

    //Set up dialog when a character spell is clicked
    private fun showSpellDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogCharSpellBinding = CustomDialogCharSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val staCost = "<b>" + "STA Cost: " + "</b>" + if (item.staminaCost == null) "Variable" else item.staminaCost
        val effect = "<b>" + "Effect: " + "</b>" + item.description
        val range = "<b>" + "Range: " + "</b>" + item.range
        val duration = "<b>" + "Duration: " + "</b>" + item.duration
        val defense = "<b>" + "Defense: " + "</b>" + item.defense
        val element = item.element
        val charSta = "<b>" + "STA: " + "</b>" + sharedViewModel.sta.value.toString()
        val vigor = sharedViewModel.vigor.value!!

        bind.spellNameText.text = item.name
        bind.staCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.rangeText.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.defenseText.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.effectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.durationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.elementText.text = element
        bind.staminaText.text = HtmlCompat.fromHtml(charSta, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.vigorText.text = HtmlCompat.fromHtml("<b>Vigor: </b>$vigor", HtmlCompat.FROM_HTML_MODE_LEGACY)

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
            //sharedViewModel.removeBasicSign(spellName)
            //sharedViewModel.removeAlternateSign(spellName)
            Toast.makeText(context, "${item.name} removed from ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
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