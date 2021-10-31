package com.example.thewitcherrpg.feature_character_creation.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharCreationFirstFrag : Fragment() {
    private var _binding: FragmentCharCreationFirstBinding? = null
    private val binding get() = _binding!!

    private val characterCreationViewModel: CharacterCreationViewModel by activityViewModels()

    lateinit var defSkill: String
    lateinit var race: String
    lateinit var racePerks: String
    lateinit var defSkillInfo: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharCreationFirstBinding.inflate(inflater, container, false)
        val view = binding.root

        onInit()

        binding.buttonToSecFrag.setOnClickListener{
            if (saveData()){
                Navigation.findNavController(view).navigate(R.id.action_charCreation_firstFrag_to_charCreation_secFrag)
            }
        }

        return view
    }

    @SuppressLint("SetTextI18n")
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
                race = binding.raceSpinner.selectedItem.toString()
                binding.textRacePerks.text = "Perks: $race"
                characterCreationViewModel.setRace(race)
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
                characterCreationViewModel.setProfession(binding.profSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Nothing happens
            }
        }

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                characterCreationViewModel.setGender(binding.genderSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing happens
            }
        }

        binding.etAge.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etAge.clearFocus()
            }
            false
        }
        binding.etAge.addTextChangedListener {
            characterCreationViewModel.setAge(binding.etAge.text.toString())
        }

        binding.etCharName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                binding.etCharName.clearFocus()
            }
            false
        }
        binding.etCharName.addTextChangedListener {
            characterCreationViewModel.setName(binding.etCharName.text.toString())
        }

        binding.textDefiningSkill.setOnClickListener {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(defSkill)
            alertDialogBuilder.setMessage(defSkillInfo)
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(true)
            alertDialog.show()
        }

        binding.textRacePerks.setOnClickListener {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(race)
            alertDialogBuilder.setMessage(racePerks)
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(true)
            alertDialog.show()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    characterCreationViewModel.definingSkill.collectLatest {
                        defSkill = it
                        binding.textDefiningSkill.text = "Defining Skill: $it"
                    }
                }
                launch {
                    characterCreationViewModel.perks.collectLatest {
                        racePerks = it
                    }
                }
                launch {
                    characterCreationViewModel.definingSkillInfo.collectLatest {
                        defSkillInfo = it
                    }
                }
            }
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

        /*sharedViewModel.setName(binding.etCharName.text.toString())
        sharedViewModel.setGender(binding.genderSpinner.selectedItem.toString())
        sharedViewModel.setRace(binding.raceSpinner.selectedItem.toString())
        sharedViewModel.setAge(binding.etAge.text.toString().toInt())
        sharedViewModel.setProfession(binding.profSpinner.selectedItem.toString())
        sharedViewModel.setDefiningSkill(defSkill)*/

        return true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
