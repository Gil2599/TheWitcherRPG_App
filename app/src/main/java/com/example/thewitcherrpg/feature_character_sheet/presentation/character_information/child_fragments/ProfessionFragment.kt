package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.Constants
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogHelpInfoBinding
import com.example.thewitcherrpg.databinding.FragmentProfessionBinding

class ProfessionFragment : Fragment() {
    private var _binding: FragmentProfessionBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    lateinit var race: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfessionBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = mainCharacterViewModel

        onInit()

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun onInit(){

        val skills = mainCharacterViewModel.getProfessionSkills()
        try {
            binding.textViewSkill1.text = skills[0]
            binding.textViewSkill2.text = skills[1]
            binding.textViewSkill3.text = skills[2]
            binding.textViewSkill4.text = skills[3]
            binding.textViewSkill5.text = skills[4]
            binding.textViewSkill6.text = skills[5]
            binding.textViewSkill7.text = skills[6]
            binding.textViewSkill8.text = skills[7]
            binding.textViewSkill9.text = skills[8]
            binding.textViewSkill10.text = skills[9]
        } catch (ex: IndexOutOfBoundsException){

        }

        val gear = mainCharacterViewModel.getProfessionGear()
        try {
            binding.textViewGear1.text = gear[0]
            binding.textViewGear2.text = gear[1]
            binding.textViewGear3.text = gear[2]
            binding.textViewGear4.text = gear[3]
            binding.textViewGear5.text = gear[4]
            binding.textViewGear6.text = gear[5]
            binding.textViewGear7.text = gear[6]
            binding.textViewGear8.text = gear[7]
            binding.textViewGear9.text = gear[8]
            binding.textViewGear10.text = gear[9]
        } catch (ex: IndexOutOfBoundsException){

        }

        when (mainCharacterViewModel.profession.value){
            Constants.Professions.MAGE -> binding.textSpecial.text = "Special: ${mainCharacterViewModel.getProfessionSpecial()}"
            Constants.Professions.NOBLE -> binding.textSpecial.text = "Starting Coin: ${mainCharacterViewModel.getProfessionSpecial()}"
            Constants.Professions.PEASANT -> binding.textSpecial.text = "Starting Coin: ${mainCharacterViewModel.getProfessionSpecial()}"
            Constants.Professions.WITCHER -> {
                binding.textViewGear.text = "Gear (Pick 2)"
                binding.textSpecial.text =
                    "Special: ${mainCharacterViewModel.getProfessionSpecial()}"
            }
            Constants.Professions.MERCHANT -> {
                binding.textViewGear.text = "Gear (Pick 3)"
                binding.textSpecial.text = "Cart: ${mainCharacterViewModel.getProfessionSpecial()}"
            }
            else -> binding.textSpecial.visibility = View.GONE
        }

        binding.textViewProfessionTitle.text = mainCharacterViewModel.profession.value.toString()

        binding.textDefiningSkill.setOnClickListener{
            showDialogInfo(mainCharacterViewModel.definingSkillInfo.value, mainCharacterViewModel.definingSkill.value)
        }

        binding.buttonHelp.setOnClickListener(){
            showDialogInfo(resources.getString(R.string.profession_help), "Your Profession")
        }
    }

    private fun showDialogInfo(text: String, title: String) {
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