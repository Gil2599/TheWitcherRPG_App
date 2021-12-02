package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogEditStatsBinding
import com.example.thewitcherrpg.databinding.FragmentSkillsOverhaulBinding
import kotlin.properties.Delegates.observable

class SkillsFragmentOverhaul : Fragment() {
    private var _binding: FragmentSkillsOverhaulBinding? = null
    private val binding get() = _binding!!

    private var focusedView: CustomSkillRow? = null
    private val allEditTexts = arrayListOf<CustomSkillRow>()

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skills_overhaul, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        onSkillsInit()

        binding.editIP.setOnClickListener {
            showDialogIP()
        }

        binding.buttonPlus.setOnClickListener() {
            increaseButton()
        }

        binding.buttonMinus.setOnClickListener() {
            decreaseButton()
        }

        return view
    }

    private fun onSkillsInit(){

        val inCharCreation = mainCharacterViewModel.inCharacterCreation.value

        //Initialize all EditTexts
        if (inCharCreation) allEditTexts.add(binding.editText1)
        binding.editText1.setSkillText("Awareness:")
        binding.editText1.setSkillValue(mainCharacterViewModel.awareness.value)

        if (inCharCreation) allEditTexts.add(binding.editText2)
        binding.editText2.setSkillText("Business:")
        binding.editText2.setSkillValue(mainCharacterViewModel.business.value)

        if (inCharCreation) allEditTexts.add(binding.editText3)
        binding.editText3.setSkillText("Deduction:")
        binding.editText3.setSkillValue(mainCharacterViewModel.deduction.value)

        if (inCharCreation) allEditTexts.add(binding.editText4)
        binding.editText4.setSkillText("Education:")
        binding.editText4.setSkillValue(mainCharacterViewModel.education.value)

        if (inCharCreation) allEditTexts.add(binding.editText5)
        binding.editText5.setSkillText("Common Speech:")
        binding.editText5.setSkillValue(mainCharacterViewModel.commonSpeech.value)

        if (inCharCreation) allEditTexts.add(binding.editText6)
        binding.editText6.setSkillText("Elder Speech:")
        binding.editText6.setSkillValue(mainCharacterViewModel.elderSpeech.value)

        if (inCharCreation) allEditTexts.add(binding.editText7)
        binding.editText7.setSkillText("Dwarven:")
        binding.editText7.setSkillValue(mainCharacterViewModel.dwarven.value)

        if (inCharCreation) allEditTexts.add(binding.editText8)
        binding.editText8.setSkillText("Monster Lore:")
        binding.editText8.setSkillValue(mainCharacterViewModel.monsterLore.value)

        if (inCharCreation) allEditTexts.add(binding.editText9)
        binding.editText9.setSkillText("Social Etiquette:")
        binding.editText9.setSkillValue(mainCharacterViewModel.socialEtiquette.value)

        if (inCharCreation) allEditTexts.add(binding.editText10)
        binding.editText10.setSkillText("Streetwise:")
        binding.editText10.setSkillValue(mainCharacterViewModel.streetwise.value)

        if (inCharCreation) allEditTexts.add(binding.editText11)
        binding.editText11.setSkillText("Tactics:")
        binding.editText11.setSkillValue(mainCharacterViewModel.tactics.value)

        if (inCharCreation) allEditTexts.add(binding.editText12)
        binding.editText12.setSkillText("Teaching:")
        binding.editText12.setSkillValue(mainCharacterViewModel.teaching.value)

        if (inCharCreation) allEditTexts.add(binding.editText13)
        binding.editText13.setSkillText("Wilderness Survival:")
        binding.editText13.setSkillValue(mainCharacterViewModel.wildernessSurvival.value)

        if (inCharCreation) allEditTexts.add(binding.editText14)
        binding.editText14.setSkillText("Brawling:")
        binding.editText14.setSkillValue(mainCharacterViewModel.brawling.value)

        if (inCharCreation) allEditTexts.add(binding.editText15)
        binding.editText15.setSkillText("Dodge/Escape:")
        binding.editText15.setSkillValue(mainCharacterViewModel.dodgeEscape.value)

        if (inCharCreation) allEditTexts.add(binding.editText16)
        binding.editText16.setSkillText("Melee:")
        binding.editText16.setSkillValue(mainCharacterViewModel.melee.value)

        if (inCharCreation) allEditTexts.add(binding.editText17)
        binding.editText17.setSkillText("Riding:")
        binding.editText17.setSkillValue(mainCharacterViewModel.riding.value)

        if (inCharCreation) allEditTexts.add(binding.editText18)
        binding.editText18.setSkillText("Sailing:")
        binding.editText18.setSkillValue(mainCharacterViewModel.sailing.value)

        if (inCharCreation) allEditTexts.add(binding.editText19)
        binding.editText19.setSkillText("Small Blades:")
        binding.editText19.setSkillValue(mainCharacterViewModel.smallBlades.value)

        if (inCharCreation) allEditTexts.add(binding.editText20)
        binding.editText20.setSkillText("Staff/Spear:")
        binding.editText20.setSkillValue(mainCharacterViewModel.staffSpear.value)

        if (inCharCreation) allEditTexts.add(binding.editText21)
        binding.editText21.setSkillText("Swordsmanship:")
        binding.editText21.setSkillValue(mainCharacterViewModel.swordsmanship.value)

        if (inCharCreation) allEditTexts.add(binding.editText22)
        binding.editText22.setSkillText("Archery:")
        binding.editText22.setSkillValue(mainCharacterViewModel.archery.value)

        if (inCharCreation) allEditTexts.add(binding.editText23)
        binding.editText23.setSkillText("Athletics:")
        binding.editText23.setSkillValue(mainCharacterViewModel.athletics.value)

        if (inCharCreation) allEditTexts.add(binding.editText24)
        binding.editText24.setSkillText("Crossbow:")
        binding.editText24.setSkillValue(mainCharacterViewModel.crossbow.value)

        if (inCharCreation) allEditTexts.add(binding.editText25)
        binding.editText25.setSkillText("Sleight of Hand:")
        binding.editText25.setSkillValue(mainCharacterViewModel.sleightOfHand.value)

        if (inCharCreation) allEditTexts.add(binding.editText26)
        binding.editText26.setSkillText("Stealth:")
        binding.editText26.setSkillValue(mainCharacterViewModel.stealth.value)

        if (inCharCreation) allEditTexts.add(binding.editText27)
        binding.editText27.setSkillText("Physique:")
        binding.editText27.setSkillValue(mainCharacterViewModel.physique.value)

        if (inCharCreation) allEditTexts.add(binding.editText28)
        binding.editText28.setSkillText("Endurance:")
        binding.editText28.setSkillValue(mainCharacterViewModel.endurance.value)

        if (inCharCreation) allEditTexts.add(binding.editText29)
        binding.editText29.setSkillText("Charisma:")
        binding.editText29.setSkillValue(mainCharacterViewModel.charisma.value)

        if (inCharCreation) allEditTexts.add(binding.editText30)
        binding.editText30.setSkillText("Deceit:")
        binding.editText30.setSkillValue(mainCharacterViewModel.deceit.value)

        if (inCharCreation) allEditTexts.add(binding.editText31)
        binding.editText31.setSkillText("Fine Arts:")
        binding.editText31.setSkillValue(mainCharacterViewModel.fineArts.value)

        if (inCharCreation) allEditTexts.add(binding.editText32)
        binding.editText32.setSkillText("Gambling:")
        binding.editText32.setSkillValue(mainCharacterViewModel.gambling.value)

        if (inCharCreation) allEditTexts.add(binding.editText33)
        binding.editText33.setSkillText("Grooming and Style:")
        binding.editText33.setSkillValue(mainCharacterViewModel.groomingAndStyle.value)

        if (inCharCreation) allEditTexts.add(binding.editText34)
        binding.editText34.setSkillText("Human Perception:")
        binding.editText34.setSkillValue(mainCharacterViewModel.humanPerception.value)

        if (inCharCreation) allEditTexts.add(binding.editText35)
        binding.editText35.setSkillText("Leadership:")
        binding.editText35.setSkillValue(mainCharacterViewModel.leadership.value)

        if (inCharCreation) allEditTexts.add(binding.editText36)
        binding.editText36.setSkillText("Persuasion:")
        binding.editText36.setSkillValue(mainCharacterViewModel.persuasion.value)

        if (inCharCreation) allEditTexts.add(binding.editText37)
        binding.editText37.setSkillText("Performance:")
        binding.editText37.setSkillValue(mainCharacterViewModel.performance.value)

        if (inCharCreation) allEditTexts.add(binding.editText38)
        binding.editText38.setSkillText("Seduction:")
        binding.editText38.setSkillValue(mainCharacterViewModel.seduction.value)

        if (inCharCreation) allEditTexts.add(binding.editText39)
        binding.editText39.setSkillText("Alchemy:")
        binding.editText39.setSkillValue(mainCharacterViewModel.alchemy.value)

        if (inCharCreation) allEditTexts.add(binding.editText40)
        binding.editText40.setSkillText("Crafting:")
        binding.editText40.setSkillValue(mainCharacterViewModel.crafting.value)

        if (inCharCreation) allEditTexts.add(binding.editText41)
        binding.editText41.setSkillText("Disguise:")
        binding.editText41.setSkillValue(mainCharacterViewModel.disguise.value)

        if (inCharCreation) allEditTexts.add(binding.editText42)
        binding.editText42.setSkillText("First Aid:")
        binding.editText42.setSkillValue(mainCharacterViewModel.firstAid.value)

        if (inCharCreation) allEditTexts.add(binding.editText43)
        binding.editText43.setSkillText("Forgery:")
        binding.editText43.setSkillValue(mainCharacterViewModel.forgery.value)

        if (inCharCreation) allEditTexts.add(binding.editText44)
        binding.editText44.setSkillText("Pick Lock:")
        binding.editText44.setSkillValue(mainCharacterViewModel.pickLock.value)

        if (inCharCreation) allEditTexts.add(binding.editText45)
        binding.editText45.setSkillText("Trap Crafting:")
        binding.editText45.setSkillValue(mainCharacterViewModel.trapCrafting.value)

        if (inCharCreation) allEditTexts.add(binding.editText46)
        binding.editText46.setSkillText("Courage:")
        binding.editText46.setSkillValue(mainCharacterViewModel.courage.value)

        if (inCharCreation) allEditTexts.add(binding.editText47)
        binding.editText47.setSkillText("Hex Weaving:")
        binding.editText47.setSkillValue(mainCharacterViewModel.hexWeaving.value)

        if (inCharCreation) allEditTexts.add(binding.editText48)
        binding.editText48.setSkillText("Intimidation:")
        binding.editText48.setSkillValue(mainCharacterViewModel.intimidation.value)

        if (inCharCreation) allEditTexts.add(binding.editText49)
        binding.editText49.setSkillText("Spell Casting:")
        binding.editText49.setSkillValue(mainCharacterViewModel.spellCasting.value)

        if (inCharCreation) allEditTexts.add(binding.editText50)
        binding.editText50.setSkillText("Resist Magic:")
        binding.editText50.setSkillValue(mainCharacterViewModel.resistMagic.value)

        if (inCharCreation) allEditTexts.add(binding.editText51)
        binding.editText51.setSkillText("Resist Coercion:")
        binding.editText51.setSkillValue(mainCharacterViewModel.resistCoercion.value)

        if (inCharCreation) allEditTexts.add(binding.editText52)
        binding.editText52.setSkillText("Ritual Crafting:")
        binding.editText52.setSkillValue(mainCharacterViewModel.ritualCrafting.value)

    }

    private fun increaseButton() {
        if (activity?.currentFocus is EditText){
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null) {
                val row = getCustomRow(skill.toString())
                if (row.editModifier) mainCharacterViewModel.onSkillModifierChange(skill.toString(),  true)
                else (activity?.currentFocus as EditText).setText(mainCharacterViewModel.onSkillChange(skill.toString(),  true).toString())
            }
        }
    }

    private fun decreaseButton() {
        if (activity?.currentFocus is EditText){
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null) {
                val row = getCustomRow(skill.toString())
                if (row.editModifier) mainCharacterViewModel.onSkillModifierChange(skill.toString(),  false)
                else (activity?.currentFocus as EditText).setText(mainCharacterViewModel.onSkillChange(skill.toString(),  false).toString())
            }
        }
    }

    private fun getCustomRow(tag: String): CustomSkillRow{
        when (tag){
            "Awareness" -> return binding.editText1
            "Business" -> return binding.editText2
            "Deduction" -> return binding.editText3
            "Education" -> return binding.editText4
            "Common Speech" -> return binding.editText5
            "Elder Speech" -> return binding.editText6
            "Dwarven" -> return binding.editText7
            "Monster Lore" -> return binding.editText8
            "Social Etiquette" -> return binding.editText9
            "Streetwise" -> return binding.editText10
            "Tactics" -> return binding.editText11
            "Teaching" -> return binding.editText12
            "Wilderness Survival" -> return binding.editText13
            "Brawling" -> return binding.editText14
            "Dodge/Escape" -> return binding.editText15
            "Melee" -> return binding.editText16
            "Riding" -> return binding.editText17
            "Sailing" -> return binding.editText18
            "Small Blades" -> return binding.editText19
            "Staff/Spear" -> return binding.editText20
            "Swordsmanship" -> return binding.editText21
            "Archery" -> return binding.editText22
            "Athletics" -> return binding.editText23
            "Crossbow" -> return binding.editText24
            "Sleight of Hand" -> return binding.editText25
            "Stealth" -> return binding.editText26
            "Physique" -> return binding.editText27
            "Endurance" -> return binding.editText28
            "Charisma" -> return binding.editText29
            "Deceit" -> return binding.editText30
            "Fine Arts" -> return binding.editText31
            "Gambling" -> return binding.editText32
            "Grooming and Style" -> return binding.editText33
            "Human Perception" -> return binding.editText34
            "Leadership" -> return binding.editText35
            "Persuasion" -> return binding.editText36
            "Performance" -> return binding.editText37
            "Seduction" -> return binding.editText38
            "Alchemy" -> return binding.editText39
            "Crafting" -> return binding.editText40
            "Disguise" -> return binding.editText41
            "First Aid" -> return binding.editText42
            "Forgery" -> return binding.editText43
            "Pick Lock" -> return binding.editText44
            "Trap Crafting" -> return binding.editText45
            "Courage" -> return binding.editText46
            "Hex Weaving" -> return binding.editText47
            "Intimidation" -> return binding.editText48
            "Spell Casting" -> return binding.editText49
            "Resist Magic" -> return binding.editText50
            "Resist Coercion" -> return binding.editText51
            "Ritual Crafting" -> return binding.editText52
        }
        return binding.editText1
    }

    private fun showDialogIP() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogEditStatsBinding =
            CustomDialogEditStatsBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewCurrent.text = "CURRENT IP"

        bind.textView.text = mainCharacterViewModel.ip.value.toString()

        bind.editText.requestFocus()

        bind.buttonPlus.setOnClickListener {
            val value =
                if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onIpChange(value)
            dialog.dismiss()
        }

        bind.buttonMinus.setOnClickListener {
            val value =
                if (bind.editText.text.isEmpty()) 0 else bind.editText.text.toString().toInt()
            mainCharacterViewModel.onIpChange(-value)
            dialog.dismiss()
        }
        dialog.show()
    }
}