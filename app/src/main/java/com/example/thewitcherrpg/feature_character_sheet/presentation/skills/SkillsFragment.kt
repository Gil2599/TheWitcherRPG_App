package com.example.thewitcherrpg.feature_character_sheet.presentation.skills

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.databinding.FragmentSkillsBinding
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogEditStatsBinding
import java.util.EnumSet.range


class SkillsFragment : Fragment() {
    private var _binding: FragmentSkillsBinding? = null
    private val binding get() = _binding!!

    private var focusedView: EditText? = null
    private val allEditTexts = arrayListOf<EditText>()

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skills, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        skillsInit()

        lifecycleScope.launchWhenCreated {
            if (mainCharacterViewModel.inCharacterCreation.value)
                charCreation()
        }

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

    private fun increaseButton() {

        if (focusedView != null) {
            val skill = focusedView!!.tag.toString()
            mainCharacterViewModel.onSkillChange(skill, true)
        }
    }

    private fun decreaseButton() {

        if (focusedView != null) {
            val skill = focusedView!!.tag.toString()
            mainCharacterViewModel.onSkillChange(skill, false)
        }
    }

    private fun skillsInit() {

        val inCharCreation = mainCharacterViewModel.inCharacterCreation.value

        //Initialize all EditTexts
        if (inCharCreation) allEditTexts.add(binding.editText1)
        binding.editText1.setRawInputType(0)
        binding.editText1.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText1
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText2)
        binding.editText2.setRawInputType(0)
        binding.editText2.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText2
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText3)
        binding.editText3.setRawInputType(0)
        binding.editText3.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText3
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText4)
        binding.editText4.setRawInputType(0)
        binding.editText4.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText4
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText5)
        binding.editText5.setRawInputType(0)
        binding.editText5.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText5
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText6)
        binding.editText6.setRawInputType(0)
        binding.editText6.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText6
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText7)
        binding.editText7.setRawInputType(0)
        binding.editText7.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText7
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText8)
        binding.editText8.setRawInputType(0)
        binding.editText8.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText8
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText9)
        binding.editText9.setRawInputType(0)
        binding.editText9.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText9
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText10)
        binding.editText10.setRawInputType(0)
        binding.editText10.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText10
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText11)
        binding.editText11.setRawInputType(0)
        binding.editText11.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText11
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText12)
        binding.editText12.setRawInputType(0)
        binding.editText12.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText12
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText13)
        binding.editText13.setRawInputType(0)
        binding.editText13.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText13
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText14)
        binding.editText14.setRawInputType(0)
        binding.editText14.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText14
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText15)
        binding.editText15.setRawInputType(0)
        binding.editText15.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText15
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText16)
        binding.editText16.setRawInputType(0)
        binding.editText16.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText16
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText17)
        binding.editText17.setRawInputType(0)
        binding.editText17.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText17
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText18)
        binding.editText18.setRawInputType(0)
        binding.editText18.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText18
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText19)
        binding.editText19.setRawInputType(0)
        binding.editText19.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText19
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText20)
        binding.editText20.setRawInputType(0)
        binding.editText20.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText20
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText21)
        binding.editText21.setRawInputType(0)
        binding.editText21.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText21
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText22)
        binding.editText22.setRawInputType(0)
        binding.editText22.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText22
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText23)
        binding.editText23.setRawInputType(0)
        binding.editText23.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText23
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText24)
        binding.editText24.setRawInputType(0)
        binding.editText24.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText24
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText25)
        binding.editText25.setRawInputType(0)
        binding.editText25.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText25
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText26)
        binding.editText26.setRawInputType(0)
        binding.editText26.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText26
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText27)
        binding.editText27.setRawInputType(0)
        binding.editText27.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText27
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText28)
        binding.editText28.setRawInputType(0)
        binding.editText28.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText28
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText29)
        binding.editText29.setRawInputType(0)
        binding.editText29.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText29
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText30)
        binding.editText30.setRawInputType(0)
        binding.editText30.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText30
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText31)
        binding.editText31.setRawInputType(0)
        binding.editText31.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText31
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText32)
        binding.editText32.setRawInputType(0)
        binding.editText32.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText32
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText33)
        binding.editText33.setRawInputType(0)
        binding.editText33.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText33
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText34)
        binding.editText34.setRawInputType(0)
        binding.editText34.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText34
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText35)
        binding.editText35.setRawInputType(0)
        binding.editText35.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText35
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText36)
        binding.editText36.setRawInputType(0)
        binding.editText36.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText36
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText37)
        binding.editText37.setRawInputType(0)
        binding.editText37.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText37
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText38)
        binding.editText38.setRawInputType(0)
        binding.editText38.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText38
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText39)
        binding.editText39.setRawInputType(0)
        binding.editText39.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText39
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText40)
        binding.editText40.setRawInputType(0)
        binding.editText40.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText40
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText41)
        binding.editText41.setRawInputType(0)
        binding.editText41.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText41
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText42)
        binding.editText42.setRawInputType(0)
        binding.editText42.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText42
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText43)
        binding.editText43.setRawInputType(0)
        binding.editText43.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText43
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText44)
        binding.editText44.setRawInputType(0)
        binding.editText44.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText44
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText45)
        binding.editText45.setRawInputType(0)
        binding.editText45.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText45
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText46)
        binding.editText46.setRawInputType(0)
        binding.editText46.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText46
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText47)
        binding.editText47.setRawInputType(0)
        binding.editText47.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText47
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText48)
        binding.editText48.setRawInputType(0)
        binding.editText48.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText48
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText49)
        binding.editText49.setRawInputType(0)
        binding.editText49.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText49
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText50)
        binding.editText50.setRawInputType(0)
        binding.editText50.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText50
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText51)
        binding.editText51.setRawInputType(0)
        binding.editText51.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText51
            }
        }

        if (inCharCreation) allEditTexts.add(binding.editText52)
        binding.editText52.setRawInputType(0)
        binding.editText52.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = binding.editText52
            }
        }

    }

    private fun charCreation() {
        val indexArray = mainCharacterViewModel.getProfessionIndices()

        for (index in 0 until allEditTexts.size) {
            if (index !in indexArray) {
                allEditTexts[index].isFocusable = false
                allEditTexts[index].setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.blueish_gray,
                        null
                    )
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        focusedView?.clearFocus()
        focusedView = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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




