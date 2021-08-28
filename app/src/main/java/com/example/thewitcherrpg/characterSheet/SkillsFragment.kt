package com.example.thewitcherrpg.characterSheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.thewitcherrpg.databinding.FragmentSkillsBinding
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.R


class SkillsFragment : Fragment() {
    private var _bindind: FragmentSkillsBinding? = null
    private val binding get() = _bindind!!

    private var focusedView: EditText? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bindind = DataBindingUtil.inflate(inflater, R.layout.fragment_skills, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        //iP = if (!inCharCreation) sharedViewModel.iP.value.toString().toInt() else 44 //Random IP value, Determined by the GM

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

        //professionSkills()

        return view
    }

    private fun increaseButton() {

        if (focusedView != null) {

            when (focusedView){
                binding.editText1 -> sharedViewModel.increaseSkill(1)
                binding.editText2 -> sharedViewModel.increaseSkill(2)
                binding.editText3 -> sharedViewModel.increaseSkill(3)
                binding.editText4 -> sharedViewModel.increaseSkill(4)
                binding.editText5 -> sharedViewModel.increaseSkill(5)
                binding.editText6 -> sharedViewModel.increaseSkill(6)
                binding.editText7 -> sharedViewModel.increaseSkill(7)
                binding.editText8 -> sharedViewModel.increaseSkill(8)
                binding.editText9 -> sharedViewModel.increaseSkill(9)
                binding.editText10 -> sharedViewModel.increaseSkill(10)
                binding.editText11 -> sharedViewModel.increaseSkill(11)
                binding.editText12 -> sharedViewModel.increaseSkill(12)
                binding.editText13 -> sharedViewModel.increaseSkill(13)
                binding.editText14 -> sharedViewModel.increaseSkill(14)
                binding.editText15 -> sharedViewModel.increaseSkill(15)
                binding.editText16 -> sharedViewModel.increaseSkill(16)
                binding.editText17 -> sharedViewModel.increaseSkill(17)
                binding.editText18 -> sharedViewModel.increaseSkill(18)
                binding.editText19 -> sharedViewModel.increaseSkill(19)
                binding.editText20 -> sharedViewModel.increaseSkill(20)
                binding.editText21 -> sharedViewModel.increaseSkill(21)
                binding.editText22 -> sharedViewModel.increaseSkill(22)
                binding.editText23 -> sharedViewModel.increaseSkill(23)
                binding.editText24 -> sharedViewModel.increaseSkill(24)
                binding.editText25 -> sharedViewModel.increaseSkill(25)
                binding.editText26 -> sharedViewModel.increaseSkill(26)
                binding.editText27 -> sharedViewModel.increaseSkill(27)
                binding.editText28 -> sharedViewModel.increaseSkill(28)
                binding.editText29 -> sharedViewModel.increaseSkill(29)
                binding.editText30 -> sharedViewModel.increaseSkill(30)
                binding.editText31 -> sharedViewModel.increaseSkill(31)
                binding.editText32 -> sharedViewModel.increaseSkill(32)
                binding.editText33 -> sharedViewModel.increaseSkill(33)
                binding.editText34 -> sharedViewModel.increaseSkill(34)
                binding.editText35 -> sharedViewModel.increaseSkill(35)
                binding.editText36 -> sharedViewModel.increaseSkill(36)
                binding.editText37 -> sharedViewModel.increaseSkill(37)
                binding.editText38 -> sharedViewModel.increaseSkill(38)
                binding.editText39 -> sharedViewModel.increaseSkill(39)
                binding.editText40 -> sharedViewModel.increaseSkill(40)
                binding.editText41 -> sharedViewModel.increaseSkill(41)
                binding.editText42 -> sharedViewModel.increaseSkill(42)
                binding.editText43 -> sharedViewModel.increaseSkill(43)
                binding.editText44 -> sharedViewModel.increaseSkill(44)
                binding.editText45 -> sharedViewModel.increaseSkill(45)
                binding.editText46 -> sharedViewModel.increaseSkill(46)
                binding.editText47 -> sharedViewModel.increaseSkill(47)
                binding.editText48 -> sharedViewModel.increaseSkill(48)
                binding.editText49 -> sharedViewModel.increaseSkill(49)
                binding.editText50 -> sharedViewModel.increaseSkill(50)
                binding.editText51 -> sharedViewModel.increaseSkill(51)
                binding.editText52 -> sharedViewModel.increaseSkill(52)

            }
        }
    }

    private fun decreaseButton(){

        if (focusedView != null) {

            when (focusedView){
                binding.editText1 -> sharedViewModel.decreaseSkill(1)
                binding.editText2 -> sharedViewModel.decreaseSkill(2)
                binding.editText3 -> sharedViewModel.decreaseSkill(3)
                binding.editText4 -> sharedViewModel.decreaseSkill(4)
                binding.editText5 -> sharedViewModel.decreaseSkill(5)
                binding.editText6 -> sharedViewModel.decreaseSkill(6)
                binding.editText7 -> sharedViewModel.decreaseSkill(7)
                binding.editText8 -> sharedViewModel.decreaseSkill(8)
                binding.editText9 -> sharedViewModel.decreaseSkill(9)
                binding.editText10 -> sharedViewModel.decreaseSkill(10)
                binding.editText11 -> sharedViewModel.decreaseSkill(11)
                binding.editText12 -> sharedViewModel.decreaseSkill(12)
                binding.editText13 -> sharedViewModel.decreaseSkill(13)
                binding.editText14 -> sharedViewModel.decreaseSkill(14)
                binding.editText15 -> sharedViewModel.decreaseSkill(15)
                binding.editText16 -> sharedViewModel.decreaseSkill(16)
                binding.editText17 -> sharedViewModel.decreaseSkill(17)
                binding.editText18 -> sharedViewModel.decreaseSkill(18)
                binding.editText19 -> sharedViewModel.decreaseSkill(19)
                binding.editText20 -> sharedViewModel.decreaseSkill(20)
                binding.editText21 -> sharedViewModel.decreaseSkill(21)
                binding.editText22 -> sharedViewModel.decreaseSkill(22)
                binding.editText23 -> sharedViewModel.decreaseSkill(23)
                binding.editText24 -> sharedViewModel.decreaseSkill(24)
                binding.editText25 -> sharedViewModel.decreaseSkill(25)
                binding.editText26 -> sharedViewModel.decreaseSkill(26)
                binding.editText27 -> sharedViewModel.decreaseSkill(27)
                binding.editText28 -> sharedViewModel.decreaseSkill(28)
                binding.editText29 -> sharedViewModel.decreaseSkill(29)
                binding.editText30 -> sharedViewModel.decreaseSkill(30)
                binding.editText31 -> sharedViewModel.decreaseSkill(31)
                binding.editText32 -> sharedViewModel.decreaseSkill(32)
                binding.editText33 -> sharedViewModel.decreaseSkill(33)
                binding.editText34 -> sharedViewModel.decreaseSkill(34)
                binding.editText35 -> sharedViewModel.decreaseSkill(35)
                binding.editText36 -> sharedViewModel.decreaseSkill(36)
                binding.editText37 -> sharedViewModel.decreaseSkill(37)
                binding.editText38 -> sharedViewModel.decreaseSkill(38)
                binding.editText39 -> sharedViewModel.decreaseSkill(39)
                binding.editText40 -> sharedViewModel.decreaseSkill(40)
                binding.editText41 -> sharedViewModel.decreaseSkill(41)
                binding.editText42 -> sharedViewModel.decreaseSkill(42)
                binding.editText43 -> sharedViewModel.decreaseSkill(43)
                binding.editText44 -> sharedViewModel.decreaseSkill(44)
                binding.editText45 -> sharedViewModel.decreaseSkill(45)
                binding.editText46 -> sharedViewModel.decreaseSkill(46)
                binding.editText47 -> sharedViewModel.decreaseSkill(47)
                binding.editText48 -> sharedViewModel.decreaseSkill(48)
                binding.editText49 -> sharedViewModel.decreaseSkill(49)
                binding.editText50 -> sharedViewModel.decreaseSkill(50)
                binding.editText51 -> sharedViewModel.decreaseSkill(51)
                binding.editText52 -> sharedViewModel.decreaseSkill(52)

            }
        }
    }

    private fun skillsInit(){

        //Initialize all EditTexts
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
        _bindind = null
    }
}




