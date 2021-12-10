package com.example.thewitcherrpg.feature_character_sheet.presentation.profession_skill_tree

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogEditStatsBinding
import com.example.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.example.thewitcherrpg.databinding.FragmentProfessionSkillTreeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfessionSkillTree : Fragment() {
    private var _binding: FragmentProfessionSkillTreeBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    private var focusedTextViews =  arrayOfNulls<TextView>(2)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profession_skill_tree, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        binding.arrow1.rotation = 130F
        binding.arrow2.rotation = 90F
        binding.arrow3.rotation = 50F
        binding.arrow4.rotation = 90F
        binding.arrow5.rotation = 90F
        binding.arrow6.rotation = 90F
        binding.arrow7.rotation = 90F
        binding.arrow8.rotation = 90F
        binding.arrow9.rotation = 90F

        getSkillTree()

        onInit()

        createObservers()

        binding.etIP.setRawInputType(0)
        binding.etIP.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDialogIP()
            }
        }
        binding.etIP.setOnClickListener {
            showDialogIP()
        }

        lifecycleScope.launch {
            mainCharacterViewModel.skillTreeInfoMode.collect { infoIsEnabled ->
                if (infoIsEnabled) {
                    showDialogDisclaimer()
                }
            }
        }

        return view
    }

    //Set the text views according to the selected profession
    private fun getSkillTree() {

        val tags = resources.getStringArray(R.array.profession_skill_trees)
        //Toast.makeText(context, sharedViewModel.definingSkill.value.toString(), Toast.LENGTH_SHORT).show()

        for (tag in tags) {
            val pair = tag.split(":").toTypedArray()
            val defSkill = pair[0]
            val allSkills = pair[1]

            if (mainCharacterViewModel.definingSkill.value == defSkill) {
                val skills = allSkills.split(",").toTypedArray()

                binding.firstSkillTitle.text = skills[0]
                binding.secondSkillTitle.text = skills[1]
                binding.thirdSkillTitle.text = skills[2]
                binding.textViewSkillA1.text = skills[3]
                binding.textViewSkillA2.text = skills[4]
                binding.textViewSkillA3.text = skills[5]
                binding.textViewSkillB1.text = skills[6]
                binding.textViewSkillB2.text = skills[7]
                binding.textViewSkillB3.text = skills[8]
                binding.textViewSkillC1.text = skills[9]
                binding.textViewSkillC2.text = skills[10]
                binding.textViewSkillC3.text = skills[11]

            }
        }

    }

    //Retrieve skill description
    private fun getSkillInfo(skill: String): String{

        var tags = resources.getStringArray(R.array.bard_skills_info)

        //Toast.makeText(context, sharedViewModel.profession.value.toString(), Toast.LENGTH_SHORT).show()
        when (mainCharacterViewModel.profession.value) {
            Constants.Professions.CRAFTSMAN -> tags = resources.getStringArray(R.array.craftsman_skills_info)
            Constants.Professions.CRIMINAL -> tags = resources.getStringArray(R.array.criminal_skills_info)
            Constants.Professions.DOCTOR -> tags = resources.getStringArray(R.array.doctor_skills_info)
            Constants.Professions.MAGE -> tags = resources.getStringArray(R.array.mage_skills_info)
            Constants.Professions.MAN_AT_ARMS -> tags = resources.getStringArray(R.array.man_at_arms_skills_info)
            Constants.Professions.MERCHANT -> tags = resources.getStringArray(R.array.merchant_skills_info)
            Constants.Professions.PRIEST -> tags = resources.getStringArray(R.array.priest_skills_info)
            Constants.Professions.WITCHER -> tags = resources.getStringArray(R.array.witcher_skills_info)
            Constants.Professions.NOBLE -> tags = resources.getStringArray(R.array.noble_skills_info)
        }

        for (tag in tags) {
            val pair = tag.split(":").toTypedArray()
            val skillName = pair[0]
            val skillInfo = pair[1]

            if (skill.replace(" ", "") == skillName.replace(" ", "")){
                return skillInfo
            }
        }
        return "Unknown"
    }

    //Set up all view logic relating to the skill tree and click listeners
    private fun onInit(){

        //Create Dialog for Defining Skill above tree
        binding.buttonDefiningSkill.setOnClickListener{
            showDialogSkillInfo(getDefSkillInfo(), mainCharacterViewModel.definingSkill.value)
        }

        binding.buttonHelp.setOnClickListener{
            showDialogDisclaimer()
        }

        //Create all of the button click listeners and long click listeners for skill info dialog
        binding.linearLayoutA1.setOnClickListener {
            focusedTextViews[0]?.visibility = View.INVISIBLE
            focusedTextViews[1]?.visibility = View.INVISIBLE

            binding.buttonA1Minus.visibility = View.VISIBLE
            focusedTextViews[0] = binding.buttonA1Minus

            binding.buttonA1Plus.visibility = View.VISIBLE
            focusedTextViews[1] = binding.buttonA1Plus
        }
        binding.linearLayoutA1.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillA1.text.toString()), binding.textViewSkillA1.text.toString())
            true
        }

        binding.linearLayoutA2.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillA2.text.toString()), binding.textViewSkillA2.text.toString())
            true
        }

        binding.linearLayoutA3.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillA3.text.toString()), binding.textViewSkillA3.text.toString())
            true
        }

        binding.linearLayoutB1.setOnClickListener {
            focusedTextViews[0]?.visibility = View.INVISIBLE
            focusedTextViews[1]?.visibility = View.INVISIBLE

            binding.buttonB1Minus.visibility = View.VISIBLE
            focusedTextViews[0] = binding.buttonB1Minus

            binding.buttonB1Plus.visibility = View.VISIBLE
            focusedTextViews[1] = binding.buttonB1Plus
        }
        binding.linearLayoutB1.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillB1.text.toString()), binding.textViewSkillB1.text.toString())
            true
        }
        binding.linearLayoutB2.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillB2.text.toString()), binding.textViewSkillB2.text.toString())
            true
        }
        binding.linearLayoutB3.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillB3.text.toString()), binding.textViewSkillB3.text.toString())
            true
        }

        binding.linearLayoutC1.setOnClickListener {
            focusedTextViews[0]?.visibility = View.INVISIBLE
            focusedTextViews[1]?.visibility = View.INVISIBLE

            binding.buttonC1Minus.visibility = View.VISIBLE
            focusedTextViews[0] = binding.buttonC1Minus

            binding.buttonC1Plus.visibility = View.VISIBLE
            focusedTextViews[1] = binding.buttonC1Plus
        }
        binding.linearLayoutC1.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillC1.text.toString()), binding.textViewSkillC1.text.toString())
            true
        }
        binding.linearLayoutC2.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillC2.text.toString()), binding.textViewSkillC2.text.toString())
            true
        }
        binding.linearLayoutC3.setOnLongClickListener{
            showDialogSkillInfo(getSkillInfo(binding.textViewSkillC3.text.toString()), binding.textViewSkillC3.text.toString())
            true
        }

        binding.buttonA1Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(1, false) }
        binding.buttonA1Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(1, true) }

        binding.buttonA2Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(2, false) }
        binding.buttonA2Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(2, true) }

        binding.buttonA3Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(3, false) }
        binding.buttonA3Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(3, true) }

        binding.buttonB1Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(4, false) }
        binding.buttonB1Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(4, true) }

        binding.buttonB2Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(5, false) }
        binding.buttonB2Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(5, true) }

        binding.buttonB3Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(6, false) }
        binding.buttonB3Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(6, true) }

        binding.buttonC1Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(7, false) }
        binding.buttonC1Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(7, true) }

        binding.buttonC2Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(8, false) }
        binding.buttonC2Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(8, true) }

        binding.buttonC3Minus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(9, false) }
        binding.buttonC3Plus.setOnClickListener{
            mainCharacterViewModel.onProfessionSkillChange(9, true) }

    }

    private fun getDefSkillInfo(): String {

        val tags = resources.getStringArray(R.array.defSkills_data_array)

        for (tag in tags) {
            val pair = tag.split(":").toTypedArray()
            val key = pair[0]
            val value = pair[1]

            if (mainCharacterViewModel.definingSkill.value == key) {
                return value
            }
        }
        return "?"
    }

    //Create observers for each skill in order to make next skills in branch editable when a value of 5 is reached
    private fun createObservers(){
        val value = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorOnPrimaryContainer, value, true)
        val purple = value.data

        context?.theme?.resolveAttribute(R.attr.colorOnSecondaryContainer, value, true)
        val green = value.data

        context?.theme?.resolveAttribute(R.attr.colorContainer, value, true)
        val red = value.data

        context?.theme?.resolveAttribute(R.attr.colorAccent, value, true)
        val disabled = value.data

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.professionSkillA1.collectLatest { value ->
                        if (value >= 5){
                            binding.linearLayoutA2.setOnClickListener {
                                focusedTextViews[0]?.visibility = View.INVISIBLE
                                focusedTextViews[1]?.visibility = View.INVISIBLE

                                binding.buttonA2Minus.visibility = View.VISIBLE
                                focusedTextViews[0] = binding.buttonA2Minus

                                binding.buttonA2Plus.visibility = View.VISIBLE
                                focusedTextViews[1] = binding.buttonA2Plus
                            }
                            binding.linearLayoutA2.backgroundTintList = ColorStateList.valueOf(purple)
                            binding.SkillA2.setTextColor(purple)
                            binding.textViewSkillA2.setTextColor(purple)
                            binding.arrow4.imageTintList = ColorStateList.valueOf(purple)
                        }
                        else{
                            binding.linearLayoutA2.setOnClickListener(null)
                            binding.linearLayoutA2.backgroundTintList = ColorStateList.valueOf(disabled)
                            binding.SkillA2.setTextColor(disabled)
                            binding.textViewSkillA2.setTextColor(disabled)
                            binding.arrow4.imageTintList = ColorStateList.valueOf(disabled)
                        }
                    }
                }
                launch {
                    mainCharacterViewModel.professionSkillA2.collectLatest { value ->
                        if (value >= 5){
                            binding.linearLayoutA3.setOnClickListener {
                                focusedTextViews[0]?.visibility = View.INVISIBLE
                                focusedTextViews[1]?.visibility = View.INVISIBLE

                                binding.buttonA3Minus.visibility = View.VISIBLE
                                focusedTextViews[0] = binding.buttonA3Minus

                                binding.buttonA3Plus.visibility = View.VISIBLE
                                focusedTextViews[1] = binding.buttonA3Plus
                            }
                            binding.linearLayoutA3.backgroundTintList = ColorStateList.valueOf(purple)
                            binding.SkillA3.setTextColor(purple)
                            binding.textViewSkillA3.setTextColor(purple)
                            binding.arrow5.imageTintList = ColorStateList.valueOf(purple)
                        }
                        else{
                            binding.linearLayoutA3.setOnClickListener(null)
                            binding.linearLayoutA3.backgroundTintList = ColorStateList.valueOf(disabled)
                            binding.SkillA3.setTextColor(disabled)
                            binding.textViewSkillA3.setTextColor(disabled)
                            binding.arrow5.imageTintList = ColorStateList.valueOf(disabled)
                        }
                    }
                }
                launch {
                    mainCharacterViewModel.professionSkillB1.collectLatest { value ->
                        if (value >= 5){
                            binding.linearLayoutB2.setOnClickListener{
                                focusedTextViews[0]?.visibility = View.INVISIBLE
                                focusedTextViews[1]?.visibility = View.INVISIBLE

                                binding.buttonB2Minus.visibility = View.VISIBLE
                                focusedTextViews[0] = binding.buttonB2Minus

                                binding.buttonB2Plus.visibility = View.VISIBLE
                                focusedTextViews[1] = binding.buttonB2Plus
                            }
                            binding.linearLayoutB2.backgroundTintList = ColorStateList.valueOf(green)
                            binding.SkillB2.setTextColor(green)
                            binding.textViewSkillB2.setTextColor(green)
                            binding.arrow6.imageTintList = ColorStateList.valueOf(green)
                        }
                        else{
                            binding.linearLayoutB2.setOnClickListener(null)
                            binding.linearLayoutB2.backgroundTintList = ColorStateList.valueOf(disabled)
                            binding.SkillB2.setTextColor(disabled)
                            binding.textViewSkillB2.setTextColor(disabled)
                            binding.arrow6.imageTintList = ColorStateList.valueOf(disabled)
                        }
                    }
                }
                launch {
                    mainCharacterViewModel.professionSkillB2.collectLatest { value ->
                        if (value >= 5){
                            binding.linearLayoutB3.setOnClickListener {
                                focusedTextViews[0]?.visibility = View.INVISIBLE
                                focusedTextViews[1]?.visibility = View.INVISIBLE

                                binding.buttonB3Minus.visibility = View.VISIBLE
                                focusedTextViews[0] = binding.buttonB3Minus

                                binding.buttonB3Plus.visibility = View.VISIBLE
                                focusedTextViews[1] = binding.buttonB3Plus
                            }
                            binding.linearLayoutB3.backgroundTintList = ColorStateList.valueOf(green)
                            binding.SkillB3.setTextColor(green)
                            binding.textViewSkillB3.setTextColor(green)
                            binding.arrow9.imageTintList = ColorStateList.valueOf(green)
                        }
                        else{
                            binding.linearLayoutB3.setOnClickListener(null)
                            binding.linearLayoutB3.backgroundTintList = ColorStateList.valueOf(disabled)
                            binding.SkillB3.setTextColor(disabled)
                            binding.textViewSkillB3.setTextColor(disabled)
                            binding.arrow9.imageTintList = ColorStateList.valueOf(disabled)
                        }
                    }
                }
                launch {
                    mainCharacterViewModel.professionSkillC1.collectLatest { value ->
                        if (value >= 5){
                            binding.linearLayoutC2.setOnClickListener{
                                focusedTextViews[0]?.visibility = View.INVISIBLE
                                focusedTextViews[1]?.visibility = View.INVISIBLE

                                binding.buttonC2Minus.visibility = View.VISIBLE
                                focusedTextViews[0] = binding.buttonC2Minus

                                binding.buttonC2Plus.visibility = View.VISIBLE
                                focusedTextViews[1] = binding.buttonC2Plus
                            }
                            binding.linearLayoutC2.backgroundTintList = ColorStateList.valueOf(red)
                            binding.SkillC2.setTextColor(red)
                            binding.textViewSkillC2.setTextColor(red)
                            binding.arrow8.imageTintList = ColorStateList.valueOf(red)
                        }
                        else{
                            binding.linearLayoutC2.setOnClickListener(null)
                            binding.linearLayoutC2.backgroundTintList = ColorStateList.valueOf(disabled)
                            binding.SkillC2.setTextColor(disabled)
                            binding.textViewSkillC2.setTextColor(disabled)
                            binding.arrow8.imageTintList = ColorStateList.valueOf(disabled)
                        }
                    }
                }
                launch {
                    mainCharacterViewModel.professionSkillC2.collectLatest { value ->
                        if (value >= 5){
                            binding.linearLayoutC3.setOnClickListener {
                                focusedTextViews[0]?.visibility = View.INVISIBLE
                                focusedTextViews[1]?.visibility = View.INVISIBLE

                                binding.buttonC3Minus.visibility = View.VISIBLE
                                focusedTextViews[0] = binding.buttonC3Minus

                                binding.buttonC3Plus.visibility = View.VISIBLE
                                focusedTextViews[1] = binding.buttonC3Plus
                            }
                            binding.linearLayoutC3.backgroundTintList = ColorStateList.valueOf(red)
                            binding.SkillC3.setTextColor(red)
                            binding.textViewSkillC3.setTextColor(red)
                            binding.arrow7.imageTintList = ColorStateList.valueOf(red)
                        }
                        else{
                            binding.linearLayoutC3.setOnClickListener(null)
                            binding.linearLayoutC3.backgroundTintList = ColorStateList.valueOf(disabled)
                            binding.SkillC3.setTextColor(disabled)
                            binding.textViewSkillC3.setTextColor(disabled)
                            binding.arrow7.imageTintList = ColorStateList.valueOf(disabled)
                        }
                    }
                }
            }
        }

    }

    private fun showDialogDisclaimer() {
        val dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogHelpInfoBinding = CustomDialogHelpInfoBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewInfo.text = resources.getString(R.string.profession_skill_tree_help)
        bind.textViewInfo.typeface = Typeface.DEFAULT

        bind.customTitle.setTitle("Profession Skill Tree")
        bind.customTitle.setTitleSize(18F)
        bind.checkBox.visibility = View.GONE

        bind.okButton.setOnClickListener {
            mainCharacterViewModel.saveSkillTreeInfoMode(false)
            dialog.dismiss()
        }
        dialog.show()
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

    private fun showDialogSkillInfo(text: String, title: String) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind: CustomDialogHelpInfoBinding = CustomDialogHelpInfoBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.textViewInfo.text = text
        bind.textViewInfo.typeface = Typeface.DEFAULT

        bind.customTitle.setTitle(title)
        bind.customTitle.setTitleSize(18F)
        bind.checkBox.visibility = View.GONE

        bind.okButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}