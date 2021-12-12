package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogEditStatsBinding
import com.example.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.example.thewitcherrpg.databinding.FragmentSkillsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SkillsFragment : Fragment() {
    private var _binding: FragmentSkillsBinding? = null
    private val binding get() = _binding!!

    private val allEditTexts = arrayListOf<CustomSkillRow>()
    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_skills, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        onSkillsInit()

        binding.etIP.setRawInputType(0)
        binding.etIP.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDialogIP()
            }
        }
        binding.etIP.setOnClickListener {
            showDialogIP()
        }

        binding.buttonHelp.setOnClickListener {
            showDialogDisclaimer()
        }

        binding.buttonPlus.setOnClickListener {
            increaseButton()
        }

        binding.buttonMinus.setOnClickListener {
            decreaseButton()
        }


            lifecycleScope.launch {
                mainCharacterViewModel.skillsInfoMode.collect { infoIsEnabled ->
                    if (infoIsEnabled) {
                        showDialogDisclaimer()
                    }
                }
            }


        return view
    }

    private fun onSkillsInit() {

        val inCharCreation = mainCharacterViewModel.inCharacterCreation.value
        //Initialize all EditTexts
        if (inCharCreation) allEditTexts.add(binding.editText1)
        binding.editText1.setSkillText("Awareness:")

        if (inCharCreation) allEditTexts.add(binding.editText2)
        binding.editText2.setSkillText("Business:")

        if (inCharCreation) allEditTexts.add(binding.editText3)
        binding.editText3.setSkillText("Deduction:")

        if (inCharCreation) allEditTexts.add(binding.editText4)
        binding.editText4.setSkillText("Education:")

        if (inCharCreation) allEditTexts.add(binding.editText5)
        binding.editText5.setSkillText("Common Speech:")

        if (inCharCreation) allEditTexts.add(binding.editText6)
        binding.editText6.setSkillText("Elder Speech:")

        if (inCharCreation) allEditTexts.add(binding.editText7)
        binding.editText7.setSkillText("Dwarven:")

        if (inCharCreation) allEditTexts.add(binding.editText8)
        binding.editText8.setSkillText("Monster Lore:")

        if (inCharCreation) allEditTexts.add(binding.editText9)
        binding.editText9.setSkillText("Social Etiquette:")

        if (inCharCreation) allEditTexts.add(binding.editText10)
        binding.editText10.setSkillText("Streetwise:")

        if (inCharCreation) allEditTexts.add(binding.editText11)
        binding.editText11.setSkillText("Tactics:")

        if (inCharCreation) allEditTexts.add(binding.editText12)
        binding.editText12.setSkillText("Teaching:")

        if (inCharCreation) allEditTexts.add(binding.editText13)
        binding.editText13.setSkillText("Wilderness Survival:")

        if (inCharCreation) allEditTexts.add(binding.editText14)
        binding.editText14.setSkillText("Brawling:")

        if (inCharCreation) allEditTexts.add(binding.editText15)
        binding.editText15.setSkillText("Dodge/Escape:")

        if (inCharCreation) allEditTexts.add(binding.editText16)
        binding.editText16.setSkillText("Melee:")

        if (inCharCreation) allEditTexts.add(binding.editText17)
        binding.editText17.setSkillText("Riding:")

        if (inCharCreation) allEditTexts.add(binding.editText18)
        binding.editText18.setSkillText("Sailing:")

        if (inCharCreation) allEditTexts.add(binding.editText19)
        binding.editText19.setSkillText("Small Blades:")

        if (inCharCreation) allEditTexts.add(binding.editText20)
        binding.editText20.setSkillText("Staff/Spear:")

        if (inCharCreation) allEditTexts.add(binding.editText21)
        binding.editText21.setSkillText("Swordsmanship:")

        if (inCharCreation) allEditTexts.add(binding.editText22)
        binding.editText22.setSkillText("Archery:")

        if (inCharCreation) allEditTexts.add(binding.editText23)
        binding.editText23.setSkillText("Athletics:")

        if (inCharCreation) allEditTexts.add(binding.editText24)
        binding.editText24.setSkillText("Crossbow:")

        if (inCharCreation) allEditTexts.add(binding.editText25)
        binding.editText25.setSkillText("Sleight of Hand:")

        if (inCharCreation) allEditTexts.add(binding.editText26)
        binding.editText26.setSkillText("Stealth:")

        if (inCharCreation) allEditTexts.add(binding.editText27)
        binding.editText27.setSkillText("Physique:")

        if (inCharCreation) allEditTexts.add(binding.editText28)
        binding.editText28.setSkillText("Endurance:")

        if (inCharCreation) allEditTexts.add(binding.editText29)
        binding.editText29.setSkillText("Charisma:")

        if (inCharCreation) allEditTexts.add(binding.editText30)
        binding.editText30.setSkillText("Deceit:")

        if (inCharCreation) allEditTexts.add(binding.editText31)
        binding.editText31.setSkillText("Fine Arts:")

        if (inCharCreation) allEditTexts.add(binding.editText32)
        binding.editText32.setSkillText("Gambling:")

        if (inCharCreation) allEditTexts.add(binding.editText33)
        binding.editText33.setSkillText("Grooming and Style:")

        if (inCharCreation) allEditTexts.add(binding.editText34)
        binding.editText34.setSkillText("Human Perception:")

        if (inCharCreation) allEditTexts.add(binding.editText35)
        binding.editText35.setSkillText("Leadership:")

        if (inCharCreation) allEditTexts.add(binding.editText36)
        binding.editText36.setSkillText("Persuasion:")

        if (inCharCreation) allEditTexts.add(binding.editText37)
        binding.editText37.setSkillText("Performance:")

        if (inCharCreation) allEditTexts.add(binding.editText38)
        binding.editText38.setSkillText("Seduction:")

        if (inCharCreation) allEditTexts.add(binding.editText39)
        binding.editText39.setSkillText("Alchemy:")

        if (inCharCreation) allEditTexts.add(binding.editText40)
        binding.editText40.setSkillText("Crafting:")

        if (inCharCreation) allEditTexts.add(binding.editText41)
        binding.editText41.setSkillText("Disguise:")

        if (inCharCreation) allEditTexts.add(binding.editText42)
        binding.editText42.setSkillText("First Aid:")

        if (inCharCreation) allEditTexts.add(binding.editText43)
        binding.editText43.setSkillText("Forgery:")

        if (inCharCreation) allEditTexts.add(binding.editText44)
        binding.editText44.setSkillText("Pick Lock:")

        if (inCharCreation) allEditTexts.add(binding.editText45)
        binding.editText45.setSkillText("Trap Crafting:")

        if (inCharCreation) allEditTexts.add(binding.editText46)
        binding.editText46.setSkillText("Courage:")

        if (inCharCreation) allEditTexts.add(binding.editText47)
        binding.editText47.setSkillText("Hex Weaving:")

        if (inCharCreation) allEditTexts.add(binding.editText48)
        binding.editText48.setSkillText("Intimidation:")

        if (inCharCreation) allEditTexts.add(binding.editText49)
        binding.editText49.setSkillText("Spell Casting:")

        if (inCharCreation) allEditTexts.add(binding.editText50)
        binding.editText50.setSkillText("Resist Magic:")

        if (inCharCreation) allEditTexts.add(binding.editText51)
        binding.editText51.setSkillText("Resist Coercion:")

        if (inCharCreation) allEditTexts.add(binding.editText52)
        binding.editText52.setSkillText("Ritual Crafting:")

        if (inCharCreation) {
            val indexArray = mainCharacterViewModel.getProfessionIndices()
            for (index in 0 until allEditTexts.size) {
                if (index !in indexArray) allEditTexts[index].setInCharCreationMode(professionSkill = false)
                else allEditTexts[index].setInCharCreationMode(professionSkill = true)
            }
        }

        when (mainCharacterViewModel.profession.value){
            Constants.Professions.BARD -> {
                binding.etBusking.visibility = View.VISIBLE
                binding.etBusking.setSkillText("Busking:")
                binding.etBusking.setDefSkillColor()
            }
            Constants.Professions.CRIMINAL -> {
                binding.etPracticedParanoia.visibility = View.VISIBLE
                binding.etPracticedParanoia.setSkillText("Practiced Paranoia:")
                binding.etPracticedParanoia.setDefSkillColor()
            }
            Constants.Professions.CRAFTSMAN -> {
                binding.etPatchJob.visibility = View.VISIBLE
                binding.etPatchJob.setSkillText("Patch Job:")
                binding.etPatchJob.setDefSkillColor()
            }
            Constants.Professions.DOCTOR -> {
                binding.etHealingHands.visibility = View.VISIBLE
                binding.etHealingHands.setSkillText("Healing Hands:")
                binding.etHealingHands.setDefSkillColor()
            }
            Constants.Professions.MAGE -> {
                binding.etMagicTraining.visibility = View.VISIBLE
                binding.etMagicTraining.setSkillText("Magic Training:")
                binding.etMagicTraining.setDefSkillColor()
            }
            Constants.Professions.MAN_AT_ARMS -> {
                binding.etToughAsNails.visibility = View.VISIBLE
                binding.etToughAsNails.setSkillText("Tough As Nails:")
                binding.etToughAsNails.setDefSkillColor()
            }
            Constants.Professions.PRIEST -> {
                binding.etInitiateOfTheGods.visibility = View.VISIBLE
                binding.etInitiateOfTheGods.setSkillText("Initiate of The Gods:")
                binding.etInitiateOfTheGods.setDefSkillColor()
            }
            Constants.Professions.WITCHER -> {
                binding.etWitcherTraining.visibility = View.VISIBLE
                binding.etWitcherTraining.setSkillText("Witcher Training:")
                binding.etWitcherTraining.setDefSkillColor()
            }
            Constants.Professions.MERCHANT -> {
                binding.etWellTraveled.visibility = View.VISIBLE
                binding.etWellTraveled.setSkillText("Well Traveled:")
                binding.etWellTraveled.setDefSkillColor()
            }
            Constants.Professions.NOBLE -> {
                binding.etNotoriety.visibility = View.VISIBLE
                binding.etNotoriety.setSkillText("Notoriety:")
                binding.etNotoriety.setDefSkillColor()
            }
        }
    }

    private fun increaseButton() {
        if (activity?.currentFocus is EditText) {
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null) {
                val row = getCustomRow(skill.toString())
                if (row.editModifier) mainCharacterViewModel.onSkillModifierChange(
                    skill.toString(),
                    true
                )
                else mainCharacterViewModel.onSkillChange(
                    skill.toString(),
                    true
                )
            }
        }
    }

    private fun decreaseButton() {
        if (activity?.currentFocus is EditText) {
            val skill = (activity?.currentFocus as EditText).tag
            if (skill != null) {
                val row = getCustomRow(skill.toString())
                if (row.editModifier) mainCharacterViewModel.onSkillModifierChange(
                    skill.toString(),
                    false
                )
                else mainCharacterViewModel.onSkillChange(
                    skill.toString(),
                    false
                )
            }
        }
    }

    private fun getCustomRow(tag: String): CustomSkillRow {
        when (tag) {
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

        bind.textViewCurrent.text = getString(R.string.current_ip)

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

    private fun showDialogDisclaimer() {
        val dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogHelpInfoBinding = CustomDialogHelpInfoBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewInfo.text = resources.getString(R.string.characterSkills_info)
        bind.textViewInfo.typeface = Typeface.DEFAULT

        bind.customTitle.setTitle("Character Skills")
        bind.customTitle.setTitleSize(18F)
        bind.checkBox.visibility = View.GONE

        bind.okButton.setOnClickListener {
            dialog.dismiss()
        }
        mainCharacterViewModel.saveSkillsInfoMode(false)
        dialog.show()
    }
}