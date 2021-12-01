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

    private var focusedView: EditText? = null
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
        binding.editText1.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText1.getEditText()
            } else binding.editText1.hideModifier()
        }

        if (inCharCreation) allEditTexts.add(binding.editText2)
        binding.editText2.setSkillText("Business:")
        binding.editText2.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText2.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText3)
        binding.editText3.setSkillText("Deduction:")
        binding.editText3.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText3.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText4)
        binding.editText4.setSkillText("Education:")
        binding.editText4.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText4.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText5)
        binding.editText5.setSkillText("Common Speech:")
        binding.editText5.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText5.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText6)
        binding.editText6.setSkillText("Elder Speech:")
        binding.editText6.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText6.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText7)
        binding.editText7.setSkillText("Dwarven:")
        binding.editText7.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText7.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText8)
        binding.editText8.setSkillText("Monster Lore:")
        binding.editText8.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText8.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText9)
        binding.editText9.setSkillText("Social Etiquette:")
        binding.editText9.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText9.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText10)
        binding.editText10.setSkillText("Streetwise:")
        binding.editText10.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText10.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText11)
        binding.editText11.setSkillText("Tactics:")
        binding.editText11.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText11.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText12)
        binding.editText12.setSkillText("Teaching:")
        binding.editText12.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText12.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText13)
        binding.editText13.setSkillText("Wilderness Survival:")
        binding.editText13.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText13.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText14)
        binding.editText14.setSkillText("Brawling:")
        binding.editText14.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText14.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText15)
        binding.editText15.setSkillText("Dodge/Escape:")
        binding.editText15.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText15.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText16)
        binding.editText16.setSkillText("Melee:")
        binding.editText16.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText16.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText17)
        binding.editText17.setSkillText("Riding:")
        binding.editText17.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText17.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText18)
        binding.editText18.setSkillText("Sailing:")
        binding.editText18.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText18.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText19)
        binding.editText19.setSkillText("Small Blades:")
        binding.editText19.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText19.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText20)
        binding.editText20.setSkillText("Staff/Spear:")
        binding.editText20.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText20.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText21)
        binding.editText21.setSkillText("Swordsmanship:")
        binding.editText21.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText21.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText22)
        binding.editText22.setSkillText("Archery:")
        binding.editText22.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText22.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText23)
        binding.editText23.setSkillText("Athletics:")
        binding.editText23.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText23.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText24)
        binding.editText24.setSkillText("Crossbow:")
        binding.editText24.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText24.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText25)
        binding.editText25.setSkillText("Sleight of Hand:")
        binding.editText25.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText25.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText26)
        binding.editText26.setSkillText("Stealth:")
        binding.editText26.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText26.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText27)
        binding.editText27.setSkillText("Physique:")
        binding.editText27.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText27.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText28)
        binding.editText28.setSkillText("Endurance:")
        binding.editText28.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText28.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText29)
        binding.editText29.setSkillText("Charisma:")
        binding.editText29.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText29.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText30)
        binding.editText30.setSkillText("Deceit:")
        binding.editText30.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText30.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText31)
        binding.editText31.setSkillText("Fine Arts:")
        binding.editText31.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText31.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText32)
        binding.editText32.setSkillText("Gambling:")
        binding.editText32.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText32.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText33)
        binding.editText33.setSkillText("Grooming and Style:")
        binding.editText33.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText33.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText34)
        binding.editText34.setSkillText("Human Perception:")
        binding.editText34.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText34.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText35)
        binding.editText35.setSkillText("Leadership:")
        binding.editText35.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText35.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText36)
        binding.editText36.setSkillText("Persuasion:")
        binding.editText36.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText36.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText37)
        binding.editText37.setSkillText("Performance:")
        binding.editText37.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText37.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText38)
        binding.editText38.setSkillText("Seduction:")
        binding.editText38.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText38.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText39)
        binding.editText39.setSkillText("Alchemy:")
        binding.editText39.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText39.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText40)
        binding.editText40.setSkillText("Crafting:")
        binding.editText40.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText40.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText41)
        binding.editText40.setSkillText("Disguise:")
        binding.editText40.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText40.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText42)
        binding.editText42.setSkillText("First Aid:")
        binding.editText42.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText42.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText43)
        binding.editText43.setSkillText("Forgery:")
        binding.editText43.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText43.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText44)
        binding.editText44.setSkillText("Pick Lock:")
        binding.editText44.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText44.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText45)
        binding.editText45.setSkillText("Trap Crafting:")
        binding.editText45.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText45.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText46)
        binding.editText46.setSkillText("Courage:")
        binding.editText46.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText46.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText47)
        binding.editText47.setSkillText("Hex Weaving:")
        binding.editText47.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText47.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText48)
        binding.editText48.setSkillText("Intimidation:")
        binding.editText48.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText48.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText49)
        binding.editText49.setSkillText("Spell Casting:")
        binding.editText49.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText49.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText50)
        binding.editText50.setSkillText("Resist Magic:")
        binding.editText50.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText50.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText51)
        binding.editText51.setSkillText("Resist Coercion:")
        binding.editText51.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText51.getEditText()
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText52)
        binding.editText52.setSkillText("Resist Crafting:")
        binding.editText52.getEditText().onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText52.getEditText()
            }
        }
    }

    private fun increaseButton() {
        //Toast.makeText(requireContext(), test.toString(), Toast.LENGTH_SHORT).show()
        if (focusedView != null) {
            //val skill = focusedView!!.tag.toString()
            focusedView!!.setText("2")
            //binding.editText5.setSkillText("0")
        }
    }

    private fun decreaseButton() {

        if (focusedView != null) {
            val skill = focusedView!!.tag.toString()
            mainCharacterViewModel.onSkillChange(skill, false)
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