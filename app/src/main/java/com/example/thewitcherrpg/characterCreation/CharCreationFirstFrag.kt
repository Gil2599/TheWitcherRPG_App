package com.example.thewitcherrpg.characterCreation

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCharCreationFirstBinding
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.thewitcherrpg.characterSheet.SharedViewModel


class CharCreationFirstFrag : Fragment() {
    private var _bindind: FragmentCharCreationFirstBinding? = null
    private val binding get() = _bindind!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    lateinit var defSkill: String
    lateinit var race: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bindind = FragmentCharCreationFirstBinding.inflate(inflater, container, false)
        val view = binding.root

        onInit()

        binding.buttonToSecFrag.setOnClickListener(){
            if (saveData()){
                Navigation.findNavController(view).navigate(R.id.action_charCreation_firstFrag_to_charCreation_secFrag)
            }
        }

        return view
    }


    fun getDefSkill(profession: String): String {

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

    private fun getRacePerksInfo(definingSkill: String): String {

        val tags = resources.getStringArray(R.array.races_data_array)

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

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.genderSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.races_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.raceSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.prof_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.profSpinner.adapter = adapter
        }

        binding.raceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                race = binding.raceSpinner.getItemAtPosition(position).toString()

                val racePerks = "Perks: $race"
                binding.textRacePerks.text = racePerks
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Nothing happens
            }
        }

        binding.profSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                defSkill = getDefSkill(binding.profSpinner.getItemAtPosition(position).toString())

                val definingSkill = "Defining Skill: $defSkill"
                binding.textDefiningSkill.text = definingSkill
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Nothing happens
            }
        }

        binding.etAge.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                binding.etAge.clearFocus()
            }
            false
        }

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

        binding.textRacePerks.setOnClickListener() {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

            alertDialogBuilder.setTitle(race)

            // set dialog message
            alertDialogBuilder.setMessage(getRacePerksInfo(race))

            // create alert dialog
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(true)

            // show it
            alertDialog.show()

            // After some action
            //alertDialog.dismiss()
        }

    }

    private fun saveData(): Boolean {

        if (binding.etCharName.text.isEmpty()) {
            Toast.makeText(context, "Character name cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        if(binding.etAge.text.isEmpty()) {
            Toast.makeText(context, "Character age cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        sharedViewModel.setName(binding.etCharName.text.toString())
        sharedViewModel.setGender(binding.genderSpinner.selectedItem.toString())
        sharedViewModel.setRace(binding.raceSpinner.selectedItem.toString())
        sharedViewModel.setAge(binding.etAge.text.toString().toInt())
        sharedViewModel.setProfession(binding.profSpinner.selectedItem.toString())
        sharedViewModel.setDefiningSkill(defSkill)

        return true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null
    }
}
