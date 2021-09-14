package com.example.thewitcherrpg.characterSheet

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentProfessionSkillTreeBinding


class ProfessionSkillTree : Fragment() {
    private var _bindind: FragmentProfessionSkillTreeBinding? = null
    private val binding get() = _bindind!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var focusedTextViews =  arrayOfNulls<TextView>(2)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindind = DataBindingUtil.inflate(inflater, R.layout.fragment_profession_skill_tree, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

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


        return view
    }

    private fun getSkillTree() {

        val tags = resources.getStringArray(R.array.profession_skill_trees)
        Toast.makeText(context, sharedViewModel.definingSkill.value.toString(), Toast.LENGTH_SHORT).show()

        for (tag in tags) {
            val pair = tag.split(":").toTypedArray()
            val defSkill = pair[0]
            val allSkills = pair[1]

            if (sharedViewModel.definingSkill.value.toString() == defSkill) {
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

    private fun onInit(){

        binding.buttonDefiningSkill.setOnClickListener() {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

            alertDialogBuilder.setTitle(sharedViewModel.definingSkill.value.toString())

            // set dialog message
            alertDialogBuilder.setMessage(getDefSkillInfo())

            // create alert dialog
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(true)

            // show it
            alertDialog.show()

        }

        if (sharedViewModel.professionSkillA1.value!! < 5){
            binding.linearLayoutA2.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)

            if (sharedViewModel.professionSkillA2.value!! < 5){
                binding.linearLayoutA3.background = ContextCompat.getDrawable(requireContext(), R.drawable.unfocused_outline_bkg)

            }
        }

        binding.linearLayoutA1.setOnClickListener {
            focusedTextViews[0]?.visibility = View.INVISIBLE
            focusedTextViews[1]?.visibility = View.INVISIBLE

            binding.buttonA1Minus.visibility = View.VISIBLE
            focusedTextViews[0] = binding.buttonA1Minus

            binding.buttonA1Plus.visibility = View.VISIBLE
            focusedTextViews[1] = binding.buttonA1Plus
        }

        binding.linearLayoutA1.setOnLongClickListener(){
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

            alertDialogBuilder.setTitle("Info Here")

            // set dialog message
            alertDialogBuilder.setMessage("Placeholder Information Here")

            // create alert dialog
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(true)

            // show it
            alertDialog.show()

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

        binding.linearLayoutC1.setOnClickListener {
            focusedTextViews[0]?.visibility = View.INVISIBLE
            focusedTextViews[1]?.visibility = View.INVISIBLE

            binding.buttonC1Minus.visibility = View.VISIBLE
            focusedTextViews[0] = binding.buttonC1Minus

            binding.buttonC1Plus.visibility = View.VISIBLE
            focusedTextViews[1] = binding.buttonC1Plus
        }

    }

    private fun getDefSkillInfo(): String {

        val tags = resources.getStringArray(R.array.defSkills_data_array)

        for (tag in tags) {
            val pair = tag.split(":").toTypedArray()
            val key = pair[0]
            val value = pair[1]

            if (sharedViewModel.definingSkill.value.toString() == key) {
                return value
            }
        }
        return "?"
    }


}