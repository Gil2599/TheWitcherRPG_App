package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentSkillsBinding
import com.example.thewitcherrpg.feature_character_creation.presentation.CharacterCreationViewModel
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel


class SkillsFragment : Fragment() {
    private var _binding: FragmentSkillsBinding? = null
    private val binding get() = _binding!!

    private var focusedView: EditText? = null

    //private val sharedViewModel: SharedViewModel by activityViewModels()
    private val characterCreationViewModel: CharacterCreationViewModel by activityViewModels()

    //private lateinit var sharedViewModel: SharedViewModel
    //private var characterCreationViewModel: CharacterCreationViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skills, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = characterCreationViewModel

        skillsInit()

        binding.editIP.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                binding.editIP.clearFocus()
            }
            false
        }

        binding.buttonPlus.setOnClickListener(){
            increaseButton()
        }

        binding.buttonMinus.setOnClickListener(){
            decreaseButton()
        }

        return view
    }

    private fun increaseButton() {

        if (focusedView != null) {
            val skill = focusedView!!.tag.toString()
            characterCreationViewModel!!.onSkillChange(skill, true)
        }
    }

    private fun decreaseButton(){

            if (focusedView != null) {
                val skill = focusedView!!.tag.toString()
                characterCreationViewModel!!.onSkillChange(skill, false)
            }
    }

    private fun skillsInit(){

        //Initialize all EditTexts
        binding.editIP.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            //if (!hasFocus) { sharedViewModel.setIP(binding.editIP.text.toString().toInt()) }
        }

        binding.editText1.setRawInputType(0)
        binding.editText1.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText1 } }

        binding.editText2.setRawInputType(0)
        binding.editText2.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText2 } }

        binding.editText3.setRawInputType(0)
        binding.editText3.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText3 } }

        binding.editText4.setRawInputType(0)
        binding.editText4.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText4 } }

        binding.editText5.setRawInputType(0)
        binding.editText5.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText5 } }

        binding.editText6.setRawInputType(0)
        binding.editText6.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText6 } }

        binding.editText7.setRawInputType(0)
        binding.editText7.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText7 } }

        binding.editText8.setRawInputType(0)
        binding.editText8.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText8 } }

        binding.editText9.setRawInputType(0)
        binding.editText9.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText9 } }

        binding.editText10.setRawInputType(0)
        binding.editText10.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText10 } }

        binding.editText11.setRawInputType(0)
        binding.editText11.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText11 } }

        binding.editText12.setRawInputType(0)
        binding.editText12.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText12 } }

        binding.editText13.setRawInputType(0)
        binding.editText13.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText13 } }

        binding.editText14.setRawInputType(0)
        binding.editText14.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText14 } }

        binding.editText15.setRawInputType(0)
        binding.editText15.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText15 } }

        binding.editText16.setRawInputType(0)
        binding.editText16.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText16 } }

        binding.editText17.setRawInputType(0)
        binding.editText17.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText17 } }

        binding.editText18.setRawInputType(0)
        binding.editText18.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText18 } }

        binding.editText19.setRawInputType(0)
        binding.editText19.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText19 } }

        binding.editText20.setRawInputType(0)
        binding.editText20.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText20 } }

        binding.editText21.setRawInputType(0)
        binding.editText21.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText21 } }

        binding.editText22.setRawInputType(0)
        binding.editText22.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText22 } }

        binding.editText23.setRawInputType(0)
        binding.editText23.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText23 } }

        binding.editText24.setRawInputType(0)
        binding.editText24.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText24 } }

        binding.editText25.setRawInputType(0)
        binding.editText25.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText25 } }

        binding.editText26.setRawInputType(0)
        binding.editText26.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText26 } }

        binding.editText27.setRawInputType(0)
        binding.editText27.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText27 } }

        binding.editText28.setRawInputType(0)
        binding.editText28.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText28 } }

        binding.editText29.setRawInputType(0)
        binding.editText29.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText29 } }

        binding.editText30.setRawInputType(0)
        binding.editText30.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText30 } }

        binding.editText31.setRawInputType(0)
        binding.editText31.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText31 } }

        binding.editText32.setRawInputType(0)
        binding.editText32.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText32 } }

        binding.editText33.setRawInputType(0)
        binding.editText33.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText33 } }

        binding.editText34.setRawInputType(0)
        binding.editText34.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText34 } }

        binding.editText35.setRawInputType(0)
        binding.editText35.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText35 } }

        binding.editText36.setRawInputType(0)
        binding.editText36.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText36 } }

        binding.editText37.setRawInputType(0)
        binding.editText37.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText37 } }

        binding.editText38.setRawInputType(0)
        binding.editText38.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText38 } }

        binding.editText39.setRawInputType(0)
        binding.editText39.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText39 } }

        binding.editText40.setRawInputType(0)
        binding.editText40.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText40 } }

        binding.editText41.setRawInputType(0)
        binding.editText41.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText41 } }

        binding.editText42.setRawInputType(0)
        binding.editText42.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText42 } }

        binding.editText43.setRawInputType(0)
        binding.editText43.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText43 } }

        binding.editText44.setRawInputType(0)
        binding.editText44.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText44 } }

        binding.editText45.setRawInputType(0)
        binding.editText45.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText45 } }

        binding.editText46.setRawInputType(0)
        binding.editText46.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText46 } }

        binding.editText47.setRawInputType(0)
        binding.editText47.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText47 } }

        binding.editText48.setRawInputType(0)
        binding.editText48.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText48 } }

        binding.editText49.setRawInputType(0)
        binding.editText49.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText49 } }

        binding.editText50.setRawInputType(0)
        binding.editText50.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText50 } }

        binding.editText51.setRawInputType(0)
        binding.editText51.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText51 } }

        binding.editText52.setRawInputType(0)
        binding.editText52.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { focusedView = binding.editText52 } }

    }

    override fun onPause() {
        super.onPause()
        focusedView = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




