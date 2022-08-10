package com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.characterMagicFragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.witcher.thewitcherrpg.core.Resource
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogCharSpellBinding
import com.witcher.thewitcherrpg.databinding.FragmentCharMagicNewBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicHeader
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.MagicItem
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.MagicViewPagerAdapter
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.magic.spellListAdapter.CharacterMagicListAdapter

class CharacterMagicFragment(private val magicType: MagicViewPagerAdapter.FragmentName) : Fragment() {
    private var _binding: FragmentCharMagicNewBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentCharMagicNewBinding.inflate(inflater, container, false)
        val view = binding.root

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        val magicListAdapter = CharacterMagicListAdapter{
                spell -> showSpellDialog(spell)
        }
        magicListAdapter.setData(getItemList())

        binding.magicRv.adapter = magicListAdapter
        binding.magicRv.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun getItemList() : ArrayList<Any>{
        val itemList = arrayListOf<Any>()
        when (magicType){
            MagicViewPagerAdapter.FragmentName.SPELLS -> {
                if (mainCharacterViewModel.noviceSpellList.value.size > 0){
                    itemList.add(MagicHeader("Novice Spells"))
                    itemList.addAll(mainCharacterViewModel.noviceSpellList.value)
                }
                if (mainCharacterViewModel.journeymanSpellList.value.size > 0){
                    itemList.add(MagicHeader("Journeyman Spells"))
                    itemList.addAll(mainCharacterViewModel.journeymanSpellList.value)
                }
                if (mainCharacterViewModel.masterSpellList.value.size > 0){
                    itemList.add(MagicHeader("Master Spells"))
                    itemList.addAll(mainCharacterViewModel.masterSpellList.value)
                }
                if (itemList.isEmpty()) {
                    itemList.add(MagicHeader("No Spells Added"))
                }
            }
            MagicViewPagerAdapter.FragmentName.INVOCATIONS -> {
                if (mainCharacterViewModel.noviceDruidInvocations.value.size > 0){
                    itemList.add(MagicHeader("Novice Druid Invocations"))
                    itemList.addAll(mainCharacterViewModel.noviceDruidInvocations.value)
                }
                if (mainCharacterViewModel.journeymanDruidInvocations.value.size > 0){
                    itemList.add(MagicHeader("Journeyman Druid Invocations"))
                    itemList.addAll(mainCharacterViewModel.journeymanDruidInvocations.value)
                }
                if (mainCharacterViewModel.masterDruidInvocations.value.size > 0){
                    itemList.add(MagicHeader("Master Druid Invocations"))
                    itemList.addAll(mainCharacterViewModel.masterDruidInvocations.value)
                }
                if (mainCharacterViewModel.novicePreacherInvocations.value.size > 0){
                    itemList.add(MagicHeader("Novice Preacher Invocations"))
                    itemList.addAll(mainCharacterViewModel.noviceDruidInvocations.value)
                }
                if (mainCharacterViewModel.journeymanPreacherInvocations.value.size > 0){
                    itemList.add(MagicHeader("Journeyman Preacher Invocations"))
                    itemList.addAll(mainCharacterViewModel.journeymanPreacherInvocations.value)
                }
                if (mainCharacterViewModel.masterPreacherInvocations.value.size > 0){
                    itemList.add(MagicHeader("Master Preacher Invocations"))
                    itemList.addAll(mainCharacterViewModel.masterPreacherInvocations.value)
                }
                if (mainCharacterViewModel.archPriestInvocations.value.size > 0){
                    itemList.add(MagicHeader("Arch Priest Invocations"))
                    itemList.addAll(mainCharacterViewModel.archPriestInvocations.value)
                }
                if (itemList.isEmpty()) {
                    itemList.add(MagicHeader("No Invocations Added"))
                }
            }
            MagicViewPagerAdapter.FragmentName.RITUALS -> {
                if (mainCharacterViewModel.noviceRitualList.value.size > 0){
                    itemList.add(MagicHeader("Novice Rituals"))
                    itemList.addAll(mainCharacterViewModel.noviceRitualList.value)
                }
                if (mainCharacterViewModel.journeymanRitualList.value.size > 0){
                    itemList.add(MagicHeader("Journeyman Rituals"))
                    itemList.addAll(mainCharacterViewModel.journeymanRitualList.value)
                }
                if (mainCharacterViewModel.masterRitualList.value.size > 0){
                    itemList.add(MagicHeader("Master Rituals"))
                    itemList.addAll(mainCharacterViewModel.masterRitualList.value)
                }
                if (itemList.isEmpty()) {
                    itemList.add(MagicHeader("No Rituals Added"))
                }
            }
            MagicViewPagerAdapter.FragmentName.HEXES -> {
                if (mainCharacterViewModel.hexesList.value.size > 0){
                    itemList.add(MagicHeader("Hexes"))
                    itemList.addAll(mainCharacterViewModel.hexesList.value)
                }
                if (itemList.isEmpty()) {
                    itemList.add(MagicHeader("No Hexes Added"))
                }
            }
            MagicViewPagerAdapter.FragmentName.SIGNS -> {
                if (mainCharacterViewModel.basicSigns.value.size > 0){
                    itemList.add(MagicHeader("Basic Signs"))
                    itemList.addAll(mainCharacterViewModel.basicSigns.value)
                }
                if (mainCharacterViewModel.alternateSigns.value.size > 0){
                    itemList.add(MagicHeader("Alternate Signs"))
                    itemList.addAll(mainCharacterViewModel.alternateSigns.value)
                }
                if (itemList.isEmpty()) {
                    itemList.add(MagicHeader("No Signs Added"))
                }
            }
        }
        return itemList
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
        val charSta = "<b>" + "STA: " + "</b>" + mainCharacterViewModel.sta.value.toString()
        val vigor = mainCharacterViewModel.vigor.value

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(18F)
        bind.staCostText.text = HtmlCompat.fromHtml(staCost, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.rangeText.text = HtmlCompat.fromHtml(range, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.defenseText.text = HtmlCompat.fromHtml(defense, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.effectText.text = HtmlCompat.fromHtml(effect, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.effectText.typeface = Typeface.DEFAULT
        bind.durationText.text = HtmlCompat.fromHtml(duration, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.elementText.text = element
        bind.staminaText.text = HtmlCompat.fromHtml(charSta, HtmlCompat.FROM_HTML_MODE_LEGACY)
        bind.vigorText.text = HtmlCompat.fromHtml("<b>Vigor: </b>$vigor", HtmlCompat.FROM_HTML_MODE_LEGACY)

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
                        else -> {}
                    }
                }
            }
            else{
                Snackbar.make(binding.root, "Variable stamina cost: Adjust stamina accordingly.",
                    Snackbar.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        bind.cancelButton.setOnClickListener{
            dialog.dismiss()
        }
        bind.removebutton.setOnClickListener{
            mainCharacterViewModel.removeMagicItem(item)
            listAdaptersInit()
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