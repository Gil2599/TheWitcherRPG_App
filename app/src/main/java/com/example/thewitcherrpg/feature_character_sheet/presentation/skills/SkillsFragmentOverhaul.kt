package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogEditStatsBinding
import com.example.thewitcherrpg.databinding.FragmentSkillsBinding
import com.example.thewitcherrpg.databinding.FragmentSkillsOverhaulBinding

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
        binding.editText1.setModifier(mainCharacterViewModel.awarenessModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText2)
        binding.editText2.setSkillText("Business:")
        binding.editText2.setSkillValue(mainCharacterViewModel.business.value)
        binding.editText2.setModifier(mainCharacterViewModel.businessModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText3)
        binding.editText3.setSkillText("Deduction:")
        binding.editText3.setSkillValue(mainCharacterViewModel.deduction.value)
        binding.editText3.setModifier(mainCharacterViewModel.deductionModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText4)
        binding.editText4.setSkillText("Education:")
        binding.editText4.setSkillValue(mainCharacterViewModel.education.value)
        binding.editText4.setModifier(mainCharacterViewModel.educationModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText5)
        binding.editText5.setSkillText("Common Speech:")
        binding.editText5.setSkillValue(mainCharacterViewModel.commonSpeech.value)
        binding.editText5.setModifier(mainCharacterViewModel.commonSpeechModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText6)
        binding.editText6.setSkillText("Elder Speech:")
        binding.editText6.setSkillValue(mainCharacterViewModel.elderSpeech.value)
        binding.editText6.setModifier(mainCharacterViewModel.elderSpeechModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText7)
        binding.editText7.setSkillText("Dwarven:")
        binding.editText7.setSkillValue(mainCharacterViewModel.dwarven.value)
        binding.editText7.setModifier(mainCharacterViewModel.dwarvenModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText8)
        binding.editText8.setSkillText("Monster Lore:")
        binding.editText8.setSkillValue(mainCharacterViewModel.monsterLore.value)
        binding.editText8.setModifier(mainCharacterViewModel.monsterLoreModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText9)
        binding.editText9.setSkillText("Social Etiquette:")
        binding.editText9.setSkillValue(mainCharacterViewModel.socialEtiquette.value)
        binding.editText9.setModifier(mainCharacterViewModel.socialEtiquetteModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText10)
        binding.editText10.setSkillText("Streetwise:")
        binding.editText10.setSkillValue(mainCharacterViewModel.streetwise.value)
        binding.editText10.setModifier(mainCharacterViewModel.streetwiseModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText11)
        binding.editText11.setSkillText("Tactics:")
        binding.editText11.setSkillValue(mainCharacterViewModel.tactics.value)
        binding.editText11.setModifier(mainCharacterViewModel.tacticsModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText12)
        binding.editText12.setSkillText("Teaching:")
        binding.editText12.setSkillValue(mainCharacterViewModel.teaching.value)
        binding.editText12.setModifier(mainCharacterViewModel.teachingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText13)
        binding.editText13.setSkillText("Wilderness Survival:")
        binding.editText13.setSkillValue(mainCharacterViewModel.wildernessSurvival.value)
        binding.editText13.setModifier(mainCharacterViewModel.wildernessSurvivalModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText14)
        binding.editText14.setSkillText("Brawling:")
        binding.editText14.setSkillValue(mainCharacterViewModel.brawling.value)
        binding.editText14.setModifier(mainCharacterViewModel.brawlingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText15)
        binding.editText15.setSkillText("Dodge/Escape:")
        binding.editText15.setSkillValue(mainCharacterViewModel.dodgeEscape.value)
        binding.editText15.setModifier(mainCharacterViewModel.dodgeEscapeModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText16)
        binding.editText16.setSkillText("Melee:")
        binding.editText16.setSkillValue(mainCharacterViewModel.melee.value)
        binding.editText16.setModifier(mainCharacterViewModel.meleeModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText17)
        binding.editText17.setSkillText("Riding:")
        binding.editText17.setSkillValue(mainCharacterViewModel.riding.value)
        binding.editText17.setModifier(mainCharacterViewModel.ridingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText18)
        binding.editText18.setSkillText("Sailing:")
        binding.editText18.setSkillValue(mainCharacterViewModel.sailing.value)
        binding.editText18.setModifier(mainCharacterViewModel.sailingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText19)
        binding.editText19.setSkillText("Small Blades:")
        binding.editText19.setSkillValue(mainCharacterViewModel.smallBlades.value)
        binding.editText19.setModifier(mainCharacterViewModel.smallBladesModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText20)
        binding.editText20.setSkillText("Staff/Spear:")
        binding.editText20.setSkillValue(mainCharacterViewModel.staffSpear.value)
        binding.editText20.setModifier(mainCharacterViewModel.staffSpearModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText21)
        binding.editText21.setSkillText("Swordsmanship:")
        binding.editText21.setSkillValue(mainCharacterViewModel.swordsmanship.value)
        binding.editText21.setModifier(mainCharacterViewModel.swordsmanshipModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText22)
        binding.editText22.setSkillText("Archery:")
        binding.editText22.setSkillValue(mainCharacterViewModel.archery.value)
        binding.editText22.setModifier(mainCharacterViewModel.archeryModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText23)
        binding.editText23.setSkillText("Athletics:")
        binding.editText23.setSkillValue(mainCharacterViewModel.athletics.value)
        binding.editText23.setModifier(mainCharacterViewModel.athleticsModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText24)
        binding.editText24.setSkillText("Crossbow:")
        binding.editText24.setSkillValue(mainCharacterViewModel.crossbow.value)
        binding.editText24.setModifier(mainCharacterViewModel.crossbow.value)

        if (inCharCreation) allEditTexts.add(binding.editText25)
        binding.editText25.setSkillText("Sleight of Hand:")
        binding.editText25.setSkillValue(mainCharacterViewModel.sleightOfHand.value)
        binding.editText25.setModifier(mainCharacterViewModel.sleightOfHandModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText26)
        binding.editText26.setSkillText("Stealth:")
        binding.editText26.setSkillValue(mainCharacterViewModel.stealth.value)
        binding.editText26.setModifier(mainCharacterViewModel.stealthModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText27)
        binding.editText27.setSkillText("Physique:")
        binding.editText27.setSkillValue(mainCharacterViewModel.physique.value)
        binding.editText27.setModifier(mainCharacterViewModel.physiqueModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText28)
        binding.editText28.setSkillText("Endurance:")
        binding.editText28.setSkillValue(mainCharacterViewModel.endurance.value)
        binding.editText28.setModifier(mainCharacterViewModel.enduranceModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText29)
        binding.editText29.setSkillText("Charisma:")
        binding.editText29.setSkillValue(mainCharacterViewModel.charisma.value)
        binding.editText29.setModifier(mainCharacterViewModel.charismaModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText30)
        binding.editText30.setSkillText("Deceit:")
        binding.editText30.setSkillValue(mainCharacterViewModel.deceit.value)
        binding.editText30.setModifier(mainCharacterViewModel.deceitModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText31)
        binding.editText31.setSkillText("Fine Arts:")
        binding.editText31.setSkillValue(mainCharacterViewModel.fineArts.value)
        binding.editText31.setModifier(mainCharacterViewModel.fineArtsModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText32)
        binding.editText32.setSkillText("Gambling:")
        binding.editText32.setSkillValue(mainCharacterViewModel.gambling.value)
        binding.editText32.setModifier(mainCharacterViewModel.gamblingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText33)
        binding.editText33.setSkillText("Grooming and Style:")
        binding.editText33.setSkillValue(mainCharacterViewModel.groomingAndStyle.value)
        binding.editText33.setModifier(mainCharacterViewModel.groomingAndStyleModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText34)
        binding.editText34.setSkillText("Human Perception:")
        binding.editText34.setSkillValue(mainCharacterViewModel.humanPerception.value)
        binding.editText34.setModifier(mainCharacterViewModel.humanPerceptionModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText35)
        binding.editText35.setSkillText("Leadership:")
        binding.editText35.setSkillValue(mainCharacterViewModel.leadership.value)
        binding.editText35.setModifier(mainCharacterViewModel.leadershipModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText36)
        binding.editText36.setSkillText("Persuasion:")
        binding.editText36.setSkillValue(mainCharacterViewModel.persuasion.value)
        binding.editText36.setModifier(mainCharacterViewModel.persuasionModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText37)
        binding.editText37.setSkillText("Performance:")
        binding.editText37.setSkillValue(mainCharacterViewModel.performance.value)
        binding.editText37.setModifier(mainCharacterViewModel.performanceModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText38)
        binding.editText38.setSkillText("Seduction:")
        binding.editText38.setSkillValue(mainCharacterViewModel.seduction.value)
        binding.editText38.setModifier(mainCharacterViewModel.seductionModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText39)
        binding.editText39.setSkillText("Alchemy:")
        binding.editText39.setSkillValue(mainCharacterViewModel.alchemy.value)
        binding.editText39.setModifier(mainCharacterViewModel.alchemyModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText40)
        binding.editText40.setSkillText("Crafting:")
        binding.editText40.setSkillValue(mainCharacterViewModel.crafting.value)
        binding.editText40.setModifier(mainCharacterViewModel.craftingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText41)
        binding.editText41.setSkillText("Disguise:")
        binding.editText41.setSkillValue(mainCharacterViewModel.disguise.value)
        binding.editText41.setModifier(mainCharacterViewModel.disguiseModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText42)
        binding.editText42.setSkillText("First Aid:")
        binding.editText42.setSkillValue(mainCharacterViewModel.firstAid.value)
        binding.editText42.setModifier(mainCharacterViewModel.firstAidModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText43)
        binding.editText43.setSkillText("Forgery:")
        binding.editText43.setSkillValue(mainCharacterViewModel.forgery.value)
        binding.editText43.setModifier(mainCharacterViewModel.forgeryModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText44)
        binding.editText44.setSkillText("Pick Lock:")
        binding.editText44.setSkillValue(mainCharacterViewModel.pickLock.value)
        binding.editText44.setModifier(mainCharacterViewModel.pickLockModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText45)
        binding.editText45.setSkillText("Trap Crafting:")
        binding.editText45.setSkillValue(mainCharacterViewModel.trapCrafting.value)
        binding.editText45.setModifier(mainCharacterViewModel.trapCraftingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText46)
        binding.editText46.setSkillText("Courage:")
        binding.editText46.setSkillValue(mainCharacterViewModel.courage.value)
        binding.editText46.setModifier(mainCharacterViewModel.courageModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText47)
        binding.editText47.setSkillText("Hex Weaving:")
        binding.editText47.setSkillValue(mainCharacterViewModel.hexWeaving.value)
        binding.editText47.setModifier(mainCharacterViewModel.hexWeavingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText48)
        binding.editText48.setSkillText("Intimidation:")
        binding.editText48.setSkillValue(mainCharacterViewModel.intimidation.value)
        binding.editText48.setModifier(mainCharacterViewModel.intimidationModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText49)
        binding.editText49.setSkillText("Spell Casting:")
        binding.editText49.setSkillValue(mainCharacterViewModel.spellCasting.value)
        binding.editText49.setModifier(mainCharacterViewModel.spellCastingModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText50)
        binding.editText50.setSkillText("Resist Magic:")
        binding.editText50.setSkillValue(mainCharacterViewModel.resistMagic.value)
        binding.editText50.setModifier(mainCharacterViewModel.resistMagicModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText51)
        binding.editText51.setSkillText("Resist Coercion:")
        binding.editText51.setSkillValue(mainCharacterViewModel.resistCoercion.value)
        binding.editText51.setModifier(mainCharacterViewModel.resistCoercionModifier.value)

        if (inCharCreation) allEditTexts.add(binding.editText52)
        binding.editText52.setSkillText("Ritual Crafting:")
        binding.editText52.setSkillValue(mainCharacterViewModel.ritualCrafting.value)
        binding.editText52.setModifier(mainCharacterViewModel.ritualCraftingModifier.value)

    }

    private fun increaseButton() {
        if (activity?.currentFocus is EditText){
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null){
                (activity?.currentFocus as EditText).setText(mainCharacterViewModel.onSkillChange(skill.toString(),  true).toString())
            }
        }
    }

    private fun decreaseButton() {
        if (activity?.currentFocus is EditText){
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null){
                (activity?.currentFocus as EditText).setText(mainCharacterViewModel.onSkillChange(skill.toString(),  false).toString())
            }
        }
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