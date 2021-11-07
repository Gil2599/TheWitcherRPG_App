package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogCharArmorBinding
import com.example.thewitcherrpg.databinding.FragmentInventoryBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.LightEquipmentListAdapter

class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    private lateinit var lightAdapter: LightEquipmentListAdapter
    private lateinit var mediumAdapter: LightEquipmentListAdapter
    private lateinit var heavyAdapter: LightEquipmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view)
                .navigate(R.id.action_inventoryFragment_to_equipmentFragment)
        }
        callback.isEnabled = true

        onSpinnerInit()
        listAdaptersInit()

        return view
    }

    private fun onSpinnerInit() {

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.equipmentCategories,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAddEquipment.adapter = adapter
        }

        binding.spinnerAddEquipment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val selection =
                        binding.spinnerAddEquipment.getItemAtPosition(position).toString()

                    val lightArmorList = arrayListOf<EquipmentItem>()
                    val mediumArmorList = arrayListOf<EquipmentItem>()
                    val heavyArmorList = arrayListOf<EquipmentItem>()

                    when (selection) {
                        "Head Armor" -> {
                            for (item in mainCharacterViewModel.headEquipment.value) {
                                when (item.equipmentType) {
                                    EquipmentTypes.LIGHT_HEAD -> lightArmorList.add(item)
                                    EquipmentTypes.MEDIUM_HEAD -> mediumArmorList.add(item)
                                    EquipmentTypes.HEAVY_HEAD -> heavyArmorList.add(item)
                                }
                            }
                            lightAdapter.setData(lightArmorList)
                            mediumAdapter.setData(mediumArmorList)
                            heavyAdapter.setData(heavyArmorList)
                        }

                        "Chest Armor" -> {
                            for (item in mainCharacterViewModel.chestEquipment.value) {
                                when (item.equipmentType) {
                                    EquipmentTypes.LIGHT_CHEST -> lightArmorList.add(item)
                                    EquipmentTypes.MEDIUM_CHEST -> mediumArmorList.add(item)
                                    EquipmentTypes.HEAVY_CHEST -> heavyArmorList.add(item)
                                }
                            }
                            lightAdapter.setData(lightArmorList)
                            mediumAdapter.setData(mediumArmorList)
                            heavyAdapter.setData(heavyArmorList)
                        }
                    }


                    if (selection == "Leg Armor") {
                        mainCharacterViewModel.legEquipment.value.let { lightAdapter.setData(it) }
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // Nothing happens
                }
            }

    }

    private fun listAdaptersInit() {

        lightAdapter = LightEquipmentListAdapter { item -> showArmorDialog(item) }
        binding.rvLightEquipment.adapter = lightAdapter
        binding.rvLightEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLightEquipment.isNestedScrollingEnabled = false


        mediumAdapter = LightEquipmentListAdapter { item -> showArmorDialog(item) }
        binding.rvMediumEquipment.adapter = mediumAdapter
        binding.rvMediumEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMediumEquipment.isNestedScrollingEnabled = false

        heavyAdapter = LightEquipmentListAdapter { item -> showArmorDialog(item) }
        binding.rvHeavyEquipment.adapter = heavyAdapter
        binding.rvHeavyEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHeavyEquipment.isNestedScrollingEnabled = false
    }

    private fun showArmorDialog(armorItem: EquipmentItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogCharArmorBinding =
            CustomDialogCharArmorBinding.inflate(layoutInflater)

        val stoppingPower = "Stopping Power (SP): " + armorItem.stoppingPower
        val availability = "Availability: " + armorItem.availability
        val armorEnhancement = "Armor Enhancement: " + armorItem.armorEnhancement
        val effect = "Effect: " + armorItem.effect
        val encValue = "Encumbrance Value: " + armorItem.encumbranceValue
        val weight = "Weight: " + armorItem.weight
        val price = "Cost: " + armorItem.cost + " Crowns"

        bind.textViewTitle.text = armorItem.name
        bind.textViewSP.text = stoppingPower
        bind.textViewAvailability.text = availability
        bind.textViewAE.text = armorEnhancement
        bind.textViewEffect.text = effect
        bind.textViewEV.text = encValue
        bind.textViewWeight.text = weight
        bind.textViewCost.text = price
        bind.textCurrentSP.text = armorItem.currentStoppingPower.toString()


        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        bind.buttonRemove.setOnClickListener {

            mainCharacterViewModel.removeEquipment(armorItem)

            onSpinnerInit() //Refresh Adapters

            Toast.makeText(
                context,
                "${armorItem.name} added to ${mainCharacterViewModel.name.value}",
                Toast.LENGTH_SHORT
            ).show()

            dialog.dismiss()

        }

        bind.buttonEquipUnequip.setOnClickListener {

            mainCharacterViewModel.equipItem(armorItem)
            Toast.makeText(
                context,
                "${mainCharacterViewModel.name.value} has equipped ${armorItem.name}",
                Toast.LENGTH_SHORT
            ).show()

            onSpinnerInit() //Refresh Adapters

            dialog.dismiss()

        }

        bind.imageViewMinus.visibility = View.GONE
        bind.imageViewPlus.visibility = View.GONE

        dialog.setContentView(bind.root)

        dialog.show()
    }

}