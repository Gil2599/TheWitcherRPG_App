package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
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
import com.google.android.material.snackbar.Snackbar
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

        val value = TypedValue()
        context?.theme?.resolveAttribute(R.attr.textFillColor, value, true)
        val textColor = value.data

        context?.theme?.resolveAttribute(R.attr.colorContainer, value, true)
        val red = value.data

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
                    mainCharacterViewModel.equippedSecondHandShield.collectLatest {
                        if (it != null)
                            binding.equippedSecondHand.setItem(it)
                        else if (mainCharacterViewModel.equippedSecondHandWeapon.value == null)
                            binding.equippedSecondHand.setItem(mainCharacterViewModel.equippedSecondHandWeapon.value)
                    }
                }
                launch {
                    mainCharacterViewModel.equippedSecondHandWeapon.collectLatest {
                        if (it != null)
                            binding.equippedSecondHand.setItem(it)
                        else if (mainCharacterViewModel.equippedSecondHandShield.value == null)
                            binding.equippedSecondHand.setItem(mainCharacterViewModel.equippedSecondHandShield.value)
                    }
                }
                launch {
                    mainCharacterViewModel.currentEnc.collectLatest { value ->
                        if (value > mainCharacterViewModel.encWithModifier.value) {
                            binding.textView53.setTextColor(red)
                        } else {
                            binding.textView53.setTextColor(textColor)
                        }
                    }
                }
            }
        }

        if (mainCharacterViewModel.equippedWeapon.value == null) {
            binding.equippedRowWeapon.setIcon(R.drawable.ic_sword)
        }

        binding.equippedRowWeapon.setOnClickListener {
            if (mainCharacterViewModel.equippedWeapon.value != null)
                showWeaponDialog(secondHand = false)
            else {
                val action =
                    EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(3)
                Navigation.findNavController(view).navigate(action)
            }
        }

        binding.equippedSecondHand.setIcon(R.drawable.ic_armor_shield)
        binding.equippedSecondHand.setOnClickListener {
            when {
                mainCharacterViewModel.equippedSecondHandShield.value != null -> showArmorDialog(
                    mainCharacterViewModel.equippedSecondHandShield.value
                )
                mainCharacterViewModel.equippedSecondHandWeapon.value != null -> showWeaponDialog(
                    secondHand = true
                )
                else -> {
                    val action =
                        EquipmentFragmentDirections.actionEquipmentFragmentToInventoryFragment(4)
                    Navigation.findNavController(view).navigate(action)
                }
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

    private fun showWeaponDialog(secondHand: Boolean) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogCharWeaponBinding =
            CustomDialogCharWeaponBinding.inflate(layoutInflater)

        val weaponItem = if (secondHand) mainCharacterViewModel.equippedSecondHandWeapon.value!! else mainCharacterViewModel.equippedWeapon.value!!

        bind.weaponItem = weaponItem

        bind.customTitle.setTitle(weaponItem.name)
        bind.customTitle.setTitleSize(17F)

        bind.buttonRemove.visibility = View.GONE
        bind.buttonEquip.text = "Unequip"

        bind.buttonEquip.setOnClickListener {
            mainCharacterViewModel.unEquipWeapon(weaponItem)
            Snackbar.make(
                binding.root, "${mainCharacterViewModel.name.value} has unequipped ${weaponItem.name}",
                Snackbar.LENGTH_SHORT
            ).show()
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

        if (weaponItem.type == WeaponTypes.AMULET){
            with(bind){
                textViewType.visibility = View.GONE
                textViewWA.visibility = View.GONE
                textViewAvailability.visibility = View.GONE
                textViewDamage.visibility = View.GONE
                textViewReliability.visibility = View.GONE
                textViewHands.visibility = View.GONE
                textViewRNG.visibility = View.GONE
                textViewEN.visibility = View.GONE
                textViewCurrentReliability.visibility = View.GONE
                textCurrentReliability.visibility = View.GONE
                imageViewMinus.visibility = View.GONE
                imageViewPlus.visibility = View.GONE
            }
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

        bind.equipmentItem = armorItem

        //Check if there is an item equipped in this slot
        if (armorItem != null) {

            bind.customTitle.setTitle(armorItem.name)
            bind.customTitle.setTitleSize(17F)

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
                Snackbar.make(
                    binding.root, "${mainCharacterViewModel.name.value} has unequipped ${armorItem.name}",
                    Snackbar.LENGTH_SHORT
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