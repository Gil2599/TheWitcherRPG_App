package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogAddCustomItemBinding
import com.example.thewitcherrpg.databinding.CustomDialogAddLifeEventBinding
import com.example.thewitcherrpg.databinding.FragmentCharacterBackgroundBinding
import com.example.thewitcherrpg.databinding.FragmentPersonalInfoBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.models.LifeEvent
import com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.child_fragments.adapters.LifeEventsListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.WeaponListAdapter

class CharacterBackgroundFragment : Fragment() {
    private var _binding: FragmentCharacterBackgroundBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBackgroundBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.customTitle.setTitle("Background Events")
        binding.customTitle.setTitleSize(20F)

        binding.addButton.setOnClickListener{
            showAddLifeEventDialog()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        onRVInit()

        return view
    }

    private fun onRVInit(){
        val lifeEventAdapter = LifeEventsListAdapter {
            Toast.makeText(requireContext(), mainCharacterViewModel.lifeEvents.value.toString(), Toast.LENGTH_SHORT).show()
        }
        lifeEventAdapter.setData(mainCharacterViewModel.lifeEvents.value)
        binding.recyclerView.adapter = lifeEventAdapter
    }

    private fun showAddLifeEventDialog() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogAddLifeEventBinding =
            CustomDialogAddLifeEventBinding.inflate(layoutInflater)

        bind.customTitle.setTitle("Life Event")
        bind.customTitle.setTitleSize(17F)

        bind.buttonAddItem.setOnClickListener {
            val lifeEvent = LifeEvent(
                name = bind.etName.text.toString(),
                age = bind.etAge.text.toString().toInt(),
                description = bind.etDescription.text.toString()
            )
            mainCharacterViewModel.addLifeEvent(lifeEvent)
            Toast.makeText(requireContext(), mainCharacterViewModel.lifeEvents.value.toString(), Toast.LENGTH_SHORT).show()
            onRVInit()
            dialog.dismiss()
        }

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(bind.root)
        dialog.show()

    }
}
