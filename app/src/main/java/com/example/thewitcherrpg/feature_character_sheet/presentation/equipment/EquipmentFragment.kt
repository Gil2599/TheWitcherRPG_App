package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogCharArmorBinding
import com.example.thewitcherrpg.databinding.CustomDialogCharWeaponBinding
import com.example.thewitcherrpg.databinding.FragmentEquipmentBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EquipmentFragment : Fragment() {
    private var _binding: FragmentEquipmentBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.lifecycleOwner = this
        binding.mainViewModel = mainCharacterViewModel

        binding.customTitle.setTitle("Equipment")
        binding.customTitle.setTitleSize(20F)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Repeat when the lifecycle is STARTED, cancel when PAUSED
                launch {
                    mainCharacterViewModel.equippedHead.collectLatest {
                        binding.equippedRowHead.setItem(mainCharacterViewModel.equippedHead.value)
                    }
                }
                launch {
                    mainCharacterViewModel.equippedChest.collectLatest {
                        binding.equippedRowChest.setItem(mainCharacterViewModel.equippedChest.value)
                    }
                }
                launch {
                    mainCharacterViewModel.equippedLegs.collectLatest {
                        binding.equippedRowLegs.setItem(mainCharacterViewModel.equippedLegs.value)
                    }
                }
                launch {
                    mainCharacterViewModel.equippedWeapon.collectLatest {
                        binding.equippedRowWeapon.setItem(mainCharacterViewModel.equippedWeapon.value)
                    }
                }
                launch {
                    mainCharacterViewModel.equippedShield.collectLatest {
                        binding.equippedRowShield.setItem(mainCharacterViewModel.equippedShield.value)
                    }
                }
                launch {
                    mainCharacterViewModel.currentEnc.collectLatest { value ->
                        if (value > mainCharacterViewModel.encWithModifier.value) {
                            binding.textView53.setTextColor(
                                ContextCompat.getColor(
                                    TheWitcherTRPGApp.getContext()!!,
                                    R.color.light_red
                                )
                            )
                        } else {
                            binding.textView53.setTextColor(
                                ContextCompat.getColor(
                                    TheWitcherTRPGApp.getContext()!!,
                                    R.color.white
                                )
                            )
                        }
                    }
                }
            }
        }

        when (mainCharacterViewModel.equippedWeapon.value?.type) {
            WeaponTypes.SWORD -> binding.equippedRowWeapon.setIcon(R.drawable.ic_sword)
            WeaponTypes.SMALL_BLADE -> binding.equippedRowWeapon.setIcon(R.drawable.ic_dagger)
            WeaponTypes.AXE -> binding.equippedRowWeapon.setIcon(R.drawable.ic_axe)
            WeaponTypes.BLUDGEON -> binding.equippedRowWeapon.setIcon(R.drawable.ic_mallet)
            WeaponTypes.POLE_ARM -> binding.equippedRowWeapon.setIcon(R.drawable.ic_spears)
            WeaponTypes.STAFF -> binding.equippedRowWeapon.setIcon(R.drawable.ic_staff)
            WeaponTypes.THROWN_WEAPON -> binding.equippedRowWeapon.setIcon(R.drawable.ic_knife)
            WeaponTypes.BOW -> binding.equippedRowWeapon.setIcon(R.drawable.ic_bow)
            WeaponTypes.CROSSBOW -> binding.equippedRowWeapon.setIcon(R.drawable.ic_crossbow)

            else -> binding.equippedRowWeapon.setIcon(R.drawable.ic_sword)
        }
        binding.equippedRowWeapon.setOnClickListener {
            if (mainCharacterViewModel.equippedWeapon.value != null)
                showWeaponDialog()
            else {
                val action =
                    EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(3)
                Navigation.findNavController(view).navigate(action)
            }
        }

        binding.equippedRowShield.setIcon(R.drawable.ic_armor_shield)
        binding.equippedRowShield.setOnClickListener {
            if (mainCharacterViewModel.equippedShield.value != null)
                showArmorDialog(mainCharacterViewModel.equippedShield.value)
            else {
                val action =
                    EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(4)
                Navigation.findNavController(view).navigate(action)
            }
        }

        binding.equippedRowHead.setIcon(R.drawable.ic_armor_head)
        binding.equippedRowHead.setOnClickListener {
            if (mainCharacterViewModel.equippedHead.value != null)
                showArmorDialog(mainCharacterViewModel.equippedHead.value)
            else {
                val action =
                    EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(0)
                Navigation.findNavController(view).navigate(action)
            }
        }

        binding.equippedRowChest.setIcon(R.drawable.ic_armor_chest)
        binding.equippedRowChest.setOnClickListener {
            if (mainCharacterViewModel.equippedChest.value != null)
                showArmorDialog(mainCharacterViewModel.equippedChest.value)
            else {
                val action =
                    EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(1)
                Navigation.findNavController(view).navigate(action)
            }
        }

        binding.equippedRowLegs.setIcon(R.drawable.ic_armor_legs)
        binding.equippedRowLegs.setOnClickListener {
            if (mainCharacterViewModel.equippedLegs.value != null)
                showArmorDialog(mainCharacterViewModel.equippedLegs.value)
            else {
                val action =
                    EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(2)
                Navigation.findNavController(view).navigate(action)
            }
        }

        binding.addButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_equipmentFragment_to_addArmorFragment2)
        }
        binding.inventoryButton.setOnClickListener {
            val action = EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(-1)
            Navigation.findNavController(view).navigate(action)
        }

        return view
    }

    private fun showWeaponDialog() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogCharWeaponBinding =
            CustomDialogCharWeaponBinding.inflate(layoutInflater)

        val weaponItem = mainCharacterViewModel.equippedWeapon.value!!
        bind.weaponItem = weaponItem

        bind.customTitle.setTitle(weaponItem.name)
        bind.customTitle.setTitleSize(17F)

        bind.buttonRemove.visibility = View.GONE
        bind.buttonEquip.text = "Unequip"

        bind.buttonEquip.setOnClickListener {
            mainCharacterViewModel.unEquipWeapon(weaponItem)
            dialog.dismiss()
        }

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        bind.imageViewMinus.setOnClickListener {
            mainCharacterViewModel.onReliabilityChange(weaponItem, false)
            bind.textCurrentReliability.text = weaponItem.currentReliability.toString()
        }
        bind.imageViewPlus.setOnClickListener {
            mainCharacterViewModel.onReliabilityChange(weaponItem, true)
            bind.textCurrentReliability.text = weaponItem.currentReliability.toString()
        }


        dialog.setContentView(bind.root)
        dialog.show()
    }

    /*Dialog to display armor information, armor type used to distinguish between armor types and display the correct information
    * For example, if left arm is clicked, the armorType parameter would be LARM and the left arm stopping power of the chest piece
    * will be displayed.
    */
    private fun showArmorDialog(armorItem: EquipmentItem?) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogCharArmorBinding =
            CustomDialogCharArmorBinding.inflate(layoutInflater)

        //Check if there is an item equipped in this slot
        if (armorItem != null) {
            val stoppingPower = "Stopping Power (SP): " + armorItem.stoppingPower
            val availability = "Availability: " + armorItem.availability
            val armorEnhancement = "Armor Enhancement: " + armorItem.armorEnhancement
            val effect = "Effect: " + armorItem.effect
            val encumbValue = "Encumbrance Value: " + armorItem.encumbranceValue
            val weight = "Weight: " + armorItem.weight
            val price = "Cost: " + armorItem.cost + " Crowns"
            val type = armorItem.equipmentType

            bind.customTitle.setTitle(armorItem.name)
            bind.customTitle.setTitleSize(17F)
            bind.textViewSP.text = stoppingPower
            bind.textViewAvailability.text = availability
            bind.textViewAE.text = armorEnhancement
            bind.textViewEffect.text = effect
            bind.textViewEV.text = encumbValue
            bind.textViewWeight.text = weight
            bind.textViewCost.text = price

            when (armorItem.equipmentType) {
                EquipmentTypes.LIGHT_LEGS, EquipmentTypes.MEDIUM_LEGS, EquipmentTypes.HEAVY_LEGS -> {
                    bind.textViewCurrentSP.visibility = View.GONE
                    bind.textCurrentLeftSP.visibility = View.VISIBLE
                    bind.textCurrentRightSP.visibility = View.VISIBLE
                    bind.textCurrentLeftSP.text = armorItem.currentLLegSP.toString()
                    bind.textCurrentRightSP.text = armorItem.currentRLegSP.toString()
                    bind.textViewLeftSP.text = "Left Leg SP: "
                    bind.textViewRightSP.text = "Right Leg SP: "
                }

                EquipmentTypes.LIGHT_CHEST, EquipmentTypes.MEDIUM_CHEST, EquipmentTypes.HEAVY_CHEST -> {
                    bind.textCurrentLeftSP.visibility = View.VISIBLE
                    bind.textCurrentRightSP.visibility = View.VISIBLE
                    bind.textCurrentLeftSP.text = armorItem.currentLArmSP.toString()
                    bind.textCurrentRightSP.text = armorItem.currentRArmSP.toString()
                    bind.textCurrentSP.text = armorItem.currentStoppingPower.toString()
                }
                else -> {
                    bind.textCurrentSP.text = armorItem.currentStoppingPower.toString()
                    bind.textViewLeftSP.visibility = View.GONE
                    bind.textViewRightSP.visibility = View.GONE
                }
            }


            bind.buttonEquipUnequip.text = "Unequip"
            bind.buttonRemove.visibility = View.GONE

            bind.buttonCancel.setOnClickListener {
                dialog.dismiss()
            }

            bind.buttonEquipUnequip.setOnClickListener {

                mainCharacterViewModel.unEquipItem(armorItem)
                Toast.makeText(
                    context,
                    "${mainCharacterViewModel.name.value} has unequipped ${armorItem.name}",
                    Toast.LENGTH_SHORT
                ).show()

                dialog.dismiss()
            }

            bind.imageViewMinus.setOnClickListener {
                if (bind.textCurrentSP.text.toString().toInt() > 0) {
                    bind.textCurrentSP.text = mainCharacterViewModel.onItemSPChange(
                        armorItem,
                        increase = false,
                        armorItem.equipmentType
                    ).toString()
                }
            }

            bind.imageViewPlus.setOnClickListener {
                bind.textCurrentSP.text = mainCharacterViewModel.onItemSPChange(
                    armorItem,
                    increase = true,
                    armorItem.equipmentType
                ).toString()
            }

            bind.imageViewPlusLSP.setOnClickListener {
                when (armorItem.equipmentType) {
                    EquipmentTypes.LIGHT_LEGS,
                    EquipmentTypes.MEDIUM_LEGS,
                    EquipmentTypes.HEAVY_LEGS ->
                        bind.textCurrentLeftSP.text = mainCharacterViewModel.onItemSPChange(
                            armorItem,
                            increase = true,
                            EquipmentTypes.LLEG
                        ).toString()

                    EquipmentTypes.LIGHT_CHEST, EquipmentTypes.MEDIUM_CHEST, EquipmentTypes.HEAVY_CHEST ->
                        bind.textCurrentLeftSP.text = mainCharacterViewModel.onItemSPChange(
                            armorItem,
                            increase = true,
                            EquipmentTypes.LARM
                        ).toString()
                }
            }

            bind.imageViewMinusLSP.setOnClickListener {
                if (bind.textCurrentLeftSP.text.toString().toInt() > 0) {
                    when (armorItem.equipmentType) {
                        EquipmentTypes.LIGHT_LEGS,
                        EquipmentTypes.MEDIUM_LEGS,
                        EquipmentTypes.HEAVY_LEGS ->
                            bind.textCurrentLeftSP.text = mainCharacterViewModel.onItemSPChange(
                                armorItem,
                                increase = false,
                                EquipmentTypes.LLEG
                            ).toString()

                        EquipmentTypes.LIGHT_CHEST, EquipmentTypes.MEDIUM_CHEST, EquipmentTypes.HEAVY_CHEST ->
                            bind.textCurrentLeftSP.text = mainCharacterViewModel.onItemSPChange(
                                armorItem,
                                increase = false,
                                EquipmentTypes.LARM
                            ).toString()
                    }
                }
            }

            bind.imageViewPlusRSP.setOnClickListener {
                when (armorItem.equipmentType) {
                    EquipmentTypes.LIGHT_LEGS,
                    EquipmentTypes.MEDIUM_LEGS,
                    EquipmentTypes.HEAVY_LEGS ->
                        bind.textCurrentRightSP.text = mainCharacterViewModel.onItemSPChange(
                            armorItem,
                            increase = true,
                            EquipmentTypes.RLEG
                        ).toString()

                    EquipmentTypes.LIGHT_CHEST, EquipmentTypes.MEDIUM_CHEST, EquipmentTypes.HEAVY_CHEST ->
                        bind.textCurrentRightSP.text = mainCharacterViewModel.onItemSPChange(
                            armorItem,
                            increase = true,
                            EquipmentTypes.RARM
                        ).toString()
                }
            }

            bind.imageViewMinusRSP.setOnClickListener {
                if (bind.textCurrentRightSP.text.toString().toInt() > 0) {
                    when (armorItem.equipmentType) {
                        EquipmentTypes.LIGHT_LEGS,
                        EquipmentTypes.MEDIUM_LEGS,
                        EquipmentTypes.HEAVY_LEGS ->
                            bind.textCurrentRightSP.text = mainCharacterViewModel.onItemSPChange(
                                armorItem,
                                increase = false,
                                EquipmentTypes.RLEG
                            ).toString()

                        EquipmentTypes.LIGHT_CHEST, EquipmentTypes.MEDIUM_CHEST, EquipmentTypes.HEAVY_CHEST ->
                            bind.textCurrentRightSP.text = mainCharacterViewModel.onItemSPChange(
                                armorItem,
                                increase = false,
                                EquipmentTypes.RARM
                            ).toString()
                    }
                }
            }

            dialog.setContentView(bind.root)

            dialog.show()
        } else Toast.makeText(context, "No armor equipped in this slot.", Toast.LENGTH_SHORT).show()
    }


}