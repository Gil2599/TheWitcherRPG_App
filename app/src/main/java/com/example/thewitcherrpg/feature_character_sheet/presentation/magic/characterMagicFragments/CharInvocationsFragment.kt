package com.example.thewitcherrpg.feature_character_sheet.presentation.magic.characterMagicFragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogCharSpellBinding
import com.example.thewitcherrpg.databinding.FragmentCharInvocationsBinding
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.MagicItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.MagicListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharInvocationsFragment : Fragment() {
    private var _binding: FragmentCharInvocationsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharInvocationsBinding.inflate(inflater, container, false)
        val view = binding.root

        druidListAdaptersInit()
        preacherListAdaptersInit()
        archPriestListAdapterInit()

        return view
    }

    private fun druidListAdaptersInit(){

        val noviceDruidAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        val journeymanDruidAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        val masterDruidAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.noviceDruidInvocations.collectLatest { itemList ->
                        noviceDruidAdapter.setData(itemList)
                    }
                }
                launch {
                    mainCharacterViewModel.journeymanDruidInvocations.collectLatest { itemList ->
                        journeymanDruidAdapter.setData(itemList)
                    }
                }
                launch {
                    mainCharacterViewModel.masterDruidInvocations.collectLatest { itemList ->
                        masterDruidAdapter.setData(itemList)
                    }
                }
            }
        }

        binding.recyclerViewNoviceDruid.adapter = noviceDruidAdapter
        binding.recyclerViewNoviceDruid.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneymanDruid.adapter = journeymanDruidAdapter
        binding.recyclerViewJourneymanDruid.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewMasterDruid.adapter = masterDruidAdapter
        binding.recyclerViewMasterDruid.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewNoviceDruid.isNestedScrollingEnabled = false
        binding.recyclerViewJourneymanDruid.isNestedScrollingEnabled = false
        binding.recyclerViewMasterDruid.isNestedScrollingEnabled = false

    }

    private fun preacherListAdaptersInit(){

        val novicePreacherAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        val journeymanPreacherAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        val masterPreacherAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.novicePreacherInvocations.collectLatest { itemList ->
                        novicePreacherAdapter.setData(itemList)
                    }
                }
                launch {
                    mainCharacterViewModel.journeymanPreacherInvocations.collectLatest { itemList ->
                        journeymanPreacherAdapter.setData(itemList)
                    }
                }
                launch {
                    mainCharacterViewModel.masterPreacherInvocations.collectLatest { itemList ->
                        masterPreacherAdapter.setData(itemList)
                    }
                }
            }
        }

        binding.recyclerViewNovicePreacher.adapter = novicePreacherAdapter
        binding.recyclerViewNovicePreacher.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewJourneymanPreacher.adapter = journeymanPreacherAdapter
        binding.recyclerViewJourneymanPreacher.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewMasterPreacher.adapter = masterPreacherAdapter
        binding.recyclerViewMasterPreacher.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewNovicePreacher.isNestedScrollingEnabled = false
        binding.recyclerViewJourneymanPreacher.isNestedScrollingEnabled = false
        binding.recyclerViewMasterPreacher.isNestedScrollingEnabled = false
    }

    private fun archPriestListAdapterInit(){
        val archPriestAdapter = MagicListAdapter {
                spell -> showSpellDialog(spell)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.archPriestInvocations.collectLatest { itemList ->
                        archPriestAdapter.setData(itemList)
                    }
                }
            }
        }

        binding.recyclerViewArchPriest.adapter = archPriestAdapter
        binding.recyclerViewArchPriest.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewArchPriest.isNestedScrollingEnabled = false
    }

    //Set up dialog when a character spell is clicked
    private fun showSpellDialog(item: MagicItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val staCost = "<b>" + "STA Cost: " + "</b>" + if (item.staminaCost == null) "Variable" else item.staminaCost
        val effect = "<b>" + "Effect: " + "</b>" + item.description
        val range = "<b>" + "Range: " + "</b>" + item.range
        val duration = "<b>" + "Duration: " + "</b>" + item.duration
        val defense = "<b>" + "Defense: " + "</b>" + item.defense
        val charSta = "<b>" + "STA: " + "</b>" + sharedViewModel.sta.value.toString()
        val vigor = sharedViewModel.vigor.value!!

        val bind : CustomDialogCharSpellBinding = CustomDialogCharSpellBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.spellNameText.text = item.name
        bind.staCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.rangeText.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.defenseText.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.effectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.durationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
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
            //sharedViewModel.removeNoviceDruidInvo(spellName)
            //sharedViewModel.removeJourneymanDruidInvo(spellName)
            //sharedViewModel.removeMasterDruidInvo(spellName)
            //sharedViewModel.removeNovicePreacherInvo(spellName)
            //sharedViewModel.removeJourneymanPreacherInvo(spellName)
            //sharedViewModel.removeMasterPreacherInvo(spellName)
            //sharedViewModel.removeArchPriestInvo(spellName)
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