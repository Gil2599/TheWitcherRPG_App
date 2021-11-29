package com.example.thewitcherrpg.feature_character_creation.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.FragmentCharCreationFirstBinding
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.CharFragment
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
    private var professionString: String = ""
    private lateinit var racePerks: String
    private lateinit var defSkillInfo: String

    companion object {
        private const val IN_EDIT_MODE = "InMode"

        fun newInstance(inEditMode: Boolean): CharCreationFirstFrag {
            val fragment = CharCreationFirstFrag()

            val bundle = Bundle().apply {
                putBoolean(IN_EDIT_MODE, inEditMode)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharCreationFirstBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        val inEditMode = arguments?.getBoolean(IN_EDIT_MODE)

        if (inEditMode == null) onInit(false)
        else onInit(true)

        binding.buttonToSecFrag.setOnClickListener {
            if (inEditMode == null) {
                if (checkData(false)) {
                    mainCharacterViewModel.name.value = binding.etCharName.text.toString()
                    mainCharacterViewModel.age.value = binding.etCharAge.text.toString()
                    mainCharacterViewModel.setGender(gender)

                    Navigation.findNavController(view)
                        .navigate(R.id.action_charCreation_firstFrag_to_charCreation_secFrag)
                }

            } else if (checkData(true)) {
                mainCharacterViewModel.onSaveEdit(
                    name = binding.etCharName.text.toString(),
                    age = binding.etCharAge.text.toString(),
                    gender = binding.autoCompleteTextViewGender.text.toString()
                )
                Toast.makeText(requireContext(), "Character Updated", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView3, CharFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit()
            }
        }
        binding.buttonCancel.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, CharFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit()
        }
        
        binding.customTitle.setTitle("General Information")
        binding.customTitle.setTitleSize(20F)

        if (inEditMode != null) {
            binding.etCharName.setText(mainCharacterViewModel.name.value)
            binding.etCharAge.setText(mainCharacterViewModel.age.value)
            binding.autoCompleteTextViewGender.setText(mainCharacterViewModel.gender.value, false)
            binding.autoCompleteTextViewRace.setText(mainCharacterViewModel.race.value, false)
            binding.autoCompleteTextViewProfession.setText(
                mainCharacterViewModel.profession.value.toString(),
                false
            )

            binding.autoCompleteTextViewRace.isEnabled = false
            binding.textInputLayout.isEnabled = false
            binding.textInputLayout3.isEnabled = false
            binding.autoCompleteTextViewProfession.isEnabled = false
            binding.buttonToSecFrag.text = "Save"
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun onInit(inEditMode: Boolean) {

        if (!inEditMode) binding.buttonCancel.visibility = View.GONE

        binding.autoCompleteTextViewRace.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val races =
                    TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.races_array)
                races?.get(position)?.let {
                    mainCharacterViewModel.setRace(it)
                    race = it
                }
            }

        binding.autoCompleteTextViewProfession.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val races =
                    TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.prof_array)
                races?.get(position)?.let {
                    mainCharacterViewModel.setProfession(it)

                    if (it == "Witcher") {
                        binding.autoCompleteTextViewRace.setText(it, false)
                        mainCharacterViewModel.setRace(it)
                        race = it
                    } else if (binding.autoCompleteTextViewRace.text.toString() == "Witcher") {
                        binding.autoCompleteTextViewRace.setText("Human", false)
                        mainCharacterViewModel.setRace("Human")
                        race = "Human"
                    }
                    professionString = it
                }
            }

        binding.autoCompleteTextViewGender.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                val genders =
                    TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.gender_array)
                genders?.get(position)?.let {
                    gender = it
                }
            }

        binding.textDefiningSkill.setOnClickListener {
            if (!inEditMode) {
                if (professionString.isNotEmpty()) {
                    val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                    alertDialogBuilder.setTitle(defSkill)
                    alertDialogBuilder.setMessage(defSkillInfo)
                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.setCanceledOnTouchOutside(true)
                    alertDialog.show()
                } else {
                    Snackbar.make(
                        binding.root, "Please select character profession",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {

                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle(mainCharacterViewModel.definingSkill.value)
                alertDialogBuilder.setMessage(mainCharacterViewModel.definingSkillInfo.value)
                val alertDialog: AlertDialog = alertDialogBuilder.create()
                alertDialog.setCanceledOnTouchOutside(true)
                alertDialog.show()

            }
        }

        binding.textRacePerks.setOnClickListener {
            if (!inEditMode) {
                if (this::race.isInitialized) {
                    val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                    alertDialogBuilder.setTitle(race)
                    alertDialogBuilder.setMessage(racePerks)
                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.setCanceledOnTouchOutside(true)
                    alertDialog.show()
                } else {
                    Snackbar.make(
                        binding.root, "Please select character race",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle(mainCharacterViewModel.race.value)
                alertDialogBuilder.setMessage(mainCharacterViewModel.racePerks.value)
                val alertDialog: AlertDialog = alertDialogBuilder.create()
                alertDialog.setCanceledOnTouchOutside(true)
                alertDialog.show()
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
                    mainCharacterViewModel.racePerks.collectLatest {
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
                        if (it == "Witcher") {
                            binding.autoCompleteTextViewProfession.setText(it, false)
                            mainCharacterViewModel.setProfession(it)
                            professionString = it
                        } else if (binding.autoCompleteTextViewProfession.text.toString() == "Witcher") {
                            binding.autoCompleteTextViewProfession.setText("Bard", false)
                            mainCharacterViewModel.setProfession("Bard")
                            professionString = "Bard"
                        }
                    }
                }
            }
        }
    }

    private fun checkData(inEditMode: Boolean): Boolean {

        if (binding.etCharName.text?.isEmpty() == true) {
            Snackbar.make(
                binding.root, "Character name cannot be empty",
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }

        if (binding.etCharAge.text?.isEmpty() == true) {
            Snackbar.make(
                binding.root, "Character age cannot be empty",
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }

        if (!inEditMode) {

            if (!this::race.isInitialized) {
                Snackbar.make(
                    binding.root, "Please select character race",
                    Snackbar.LENGTH_SHORT
                ).show()
                return false
            }

            if (!this::gender.isInitialized) {
                Snackbar.make(
                    binding.root, "Please select character gender.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return false
            }

            if (professionString.isEmpty()) {
                Snackbar.make(
                    binding.root, "Please select character profession.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return false
            }
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
