package com.example.thewitcherrpg.feature_character_creation.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCharCreationFirstBinding
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharCreationFirstFrag : Fragment() {
    private var _binding: FragmentCharCreationFirstBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    private lateinit var defSkill: String
    private lateinit var race: String
    private lateinit var gender: String
    private lateinit var professionString: String
    private lateinit var racePerks: String
    private lateinit var defSkillInfo: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharCreationFirstBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        onInit()

        binding.buttonToSecFrag.setOnClickListener{
            if (checkData()){
                Navigation.findNavController(view).navigate(R.id.action_charCreation_firstFrag_to_charCreation_secFrag)
            }
        }
        binding.customTitle.setTitle("General Information")
        binding.customTitle.setTitleSize(20F)

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun onInit(){

        mainCharacterViewModel.setInCharCreation(true)

        binding.autoCompleteTextViewRace.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val races = TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.races_array)
                races?.get(position)?.let {
                    mainCharacterViewModel.setRace(it)
                    race = it
                }
            }

        binding.autoCompleteTextViewProfession.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val races = TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.prof_array)
                races?.get(position)?.let {
                    mainCharacterViewModel.setProfession(it)

                    if (it == "Witcher"){
                        binding.autoCompleteTextViewRace.setText(it, false)
                        mainCharacterViewModel.setRace(it)
                        race = it
                    } else if (binding.autoCompleteTextViewRace.text.toString() == "Witcher"){
                        binding.autoCompleteTextViewRace.setText("Human", false)
                        mainCharacterViewModel.setRace("Human")
                        race = "Human"
                    }
                    professionString = it
                }
            }

        binding.autoCompleteTextViewGender.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val genders = TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.gender_array)
                genders?.get(position)?.let {
                    mainCharacterViewModel.setGender(it)
                    gender = it
                }
            }

        binding.textDefiningSkill.setOnClickListener {
            if (this::professionString.isInitialized) {
                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle(defSkill)
                alertDialogBuilder.setMessage(defSkillInfo)
                val alertDialog: AlertDialog = alertDialogBuilder.create()
                alertDialog.setCanceledOnTouchOutside(true)
                alertDialog.show()
            }
            else {
                Snackbar.make(binding.root, "Please select character profession",
                    Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.textRacePerks.setOnClickListener {
            if (this::race.isInitialized) {
                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle(race)
                alertDialogBuilder.setMessage(racePerks)
                val alertDialog: AlertDialog = alertDialogBuilder.create()
                alertDialog.setCanceledOnTouchOutside(true)
                alertDialog.show()
            }
            else{
                Snackbar.make(binding.root, "Please select character race",
                    Snackbar.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.definingSkill.collectLatest {
                        defSkill = it
                        binding.textDefiningSkill.text = "Defining Skill: $it"
                    }
                }
                launch {
                    mainCharacterViewModel.perks.collectLatest {
                        racePerks = it
                        binding.textRacePerks.text = "Perks: ${mainCharacterViewModel.race.value}"
                    }
                }
                launch {
                    mainCharacterViewModel.definingSkillInfo.collectLatest {
                        defSkillInfo = it
                    }
                }
                launch {
                    mainCharacterViewModel.race.collectLatest {
                        if (it == "Witcher"){
                            binding.autoCompleteTextViewProfession.setText(it, false)
                            mainCharacterViewModel.setProfession(it)
                            professionString = it
                        } else if (binding.autoCompleteTextViewProfession.text.toString() == "Witcher"){
                            binding.autoCompleteTextViewProfession.setText("Bard", false)
                            mainCharacterViewModel.setProfession("Bard")
                            professionString = "Bard"
                        }
                    }
                }
            }
        }

    }

    private fun checkData(): Boolean {

        if (binding.etCharName.text?.isEmpty() == true) {
            Snackbar.make(binding.root, "Character name cannot be empty",
                Snackbar.LENGTH_SHORT).show()
            return false
        }

        if(binding.etCharAge.text?.isEmpty() == true) {
            Snackbar.make(binding.root, "Character age cannot be empty",
                Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (!this::race.isInitialized){
            Snackbar.make(binding.root, "Please select character race",
                Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (!this::gender.isInitialized){
            Snackbar.make(binding.root, "Please select character gender",
                Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (!this::professionString.isInitialized){
            Snackbar.make(binding.root, "Please select character profession",
                Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.races_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.autoCompleteTextViewRace.setAdapter(adapter)
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.autoCompleteTextViewGender.setAdapter(adapter)
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.prof_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.autoCompleteTextViewProfession.setAdapter(adapter)
        }
    }
}
