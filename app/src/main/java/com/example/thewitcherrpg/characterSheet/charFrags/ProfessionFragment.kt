package com.example.thewitcherrpg.characterSheet.charFrags

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.databinding.FragmentProfessionBinding

class ProfessionFragment : Fragment() {
    private var _binding: FragmentProfessionBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    lateinit var defSkill: String
    lateinit var race: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfessionBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        onInit()

        return view
    }

    private fun getDefSkill(profession: String): String {

        val tags = resources.getStringArray(R.array.professions_defSkills_array)
        for (tag in tags) {
            val pair = tag.split(":").toTypedArray()
            val key = pair[0]
            val value = pair[1]

            if (profession == key) {
                return value
            }
        }
        return "?"
    }

    private fun getDefSkillInfo(definingSkill: String): String {

        val tags = resources.getStringArray(R.array.defSkills_data_array)

        for (tag in tags) {
            val pair = tag.split(":").toTypedArray()
            val key = pair[0]
            val value = pair[1]

            if (definingSkill == key) {
                return value
            }
        }
        return "?"
    }

    private fun onInit(){

        defSkill = getDefSkill(sharedViewModel.profession.value.toString())

        val definingSkill = "Defining Skill: $defSkill"
        binding.textDefiningSkill.text = definingSkill

        binding.textDefiningSkill.setOnClickListener() {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

            alertDialogBuilder.setTitle(defSkill)

            // set dialog message
            alertDialogBuilder.setMessage(getDefSkillInfo(defSkill))

            // create alert dialog
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(true)

            // show it
            alertDialog.show()

            // After some action
            //alertDialog.dismiss()
        }

        binding.buttonHelp.setOnClickListener(){
            showDialogHelp()
        }
    }

    private fun showDialogHelp() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.custom_dialog_help_info)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        //dialog.textViewInfo.text = resources.getString(R.string.profession_help)

        //dialog.textViewTitle.text = "Your Profession"
        //textview.setText(Html.fromHtml(resources.getString(R.string.text)));

        dialog.show()
    }

}