package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.CustomDialogCharArmorBinding
import com.example.thewitcherrpg.databinding.CustomDialogCharWeaponBinding
import com.example.thewitcherrpg.databinding.FragmentInventoryBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.models.WeaponItem
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.EquipmentListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.WeaponListAdapter


class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()
    private val selectionArg: InventoryFragmentArgs by navArgs()

    private lateinit var lightAdapter: EquipmentListAdapter
    private lateinit var mediumAdapter: EquipmentListAdapter
    private lateinit var heavyAdapter: EquipmentListAdapter

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

        binding.customTitle.setTitle("Inventory")
        binding.customTitle.setTitleSize(20F)

        binding.autoCompleteTextViewItemType.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
                updateRVs(binding.autoCompleteTextViewItemType.text.toString())
            }
        listAdaptersSetup()

        binding.autoCompleteTextViewItemType.setText("Head Armor", false)
        updateRVs(binding.autoCompleteTextViewItemType.text.toString())


        return view
    }

    private fun listAdaptersSetup() {

        binding.rvLightEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLightEquipment.isNestedScrollingEnabled = false

        binding.rvMediumEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMediumEquipment.isNestedScrollingEnabled = false

        binding.rvHeavyEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHeavyEquipment.isNestedScrollingEnabled = false

        binding.rvBludgeons.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBludgeons.isNestedScrollingEnabled = false

        binding.rvPoleArms.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPoleArms.isNestedScrollingEnabled = false

        binding.rvStaves.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStaves.isNestedScrollingEnabled = false

        binding.rvThrownWeapons.layoutManager = LinearLayoutManager(requireContext())
        binding.rvThrownWeapons.isNestedScrollingEnabled = false

        binding.rvBows.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBows.isNestedScrollingEnabled = false

        binding.rvCrossbows.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCrossbows.isNestedScrollingEnabled = false
    }

    private fun listAdaptersInit(weapons: Boolean, customItem: Boolean = false) {
        if (weapons) {
            val swords = arrayListOf<WeaponItem>()
            val smallBlades = arrayListOf<WeaponItem>()
            val axes = arrayListOf<WeaponItem>()
            val bludgeons = arrayListOf<WeaponItem>()
            val poleArms = arrayListOf<WeaponItem>()
            val staves = arrayListOf<WeaponItem>()
            val thrownWeapons = arrayListOf<WeaponItem>()
            val bows = arrayListOf<WeaponItem>()
            val crossbows = arrayListOf<WeaponItem>()

            for (item in mainCharacterViewModel.weaponEquipment.value) {
                when (item.type) {
                    WeaponTypes.SWORD -> swords.add(item)
                    WeaponTypes.SMALL_BLADE -> smallBlades.add(item)
                    WeaponTypes.AXE -> axes.add(item)
                    WeaponTypes.BLUDGEON -> bludgeons.add(item)
                    WeaponTypes.POLE_ARM -> poleArms.add(item)
                    WeaponTypes.STAFF -> staves.add(item)
                    WeaponTypes.THROWN_WEAPON -> thrownWeapons.add(item)
                    WeaponTypes.BOW -> bows.add(item)
                    WeaponTypes.CROSSBOW -> crossbows.add(item)
                }
            }

            val swordsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            swordsAdapter.setData(swords)
            binding.rvLightEquipment.adapter = swordsAdapter
            binding.textViewLight.text = "Swords"

            val smallBladesAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            smallBladesAdapter.setData(smallBlades)
            binding.rvMediumEquipment.adapter = smallBladesAdapter
            binding.textViewMedium.text = "Small Blades"


            val axesAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            axesAdapter.setData(axes)
            binding.rvHeavyEquipment.adapter = axesAdapter
            binding.textViewHeavy.text = "Axes"

            val bludgeonsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            bludgeonsAdapter.setData(bludgeons)
            binding.rvBludgeons.adapter = bludgeonsAdapter
            binding.textViewBludgeons.text = "Bludgeons"

            val poleArmsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            poleArmsAdapter.setData(poleArms)
            binding.rvPoleArms.adapter = poleArmsAdapter
            binding.textViewPoleArms.text = "Pole Arms"

            val stavesAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            stavesAdapter.setData(staves)
            binding.rvStaves.adapter = stavesAdapter
            binding.textViewStaves.text = "Staves"

            val thrownWeaponsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            thrownWeaponsAdapter.setData(thrownWeapons)
            binding.rvThrownWeapons.adapter = thrownWeaponsAdapter
            binding.textViewThrownWeapons.text = "Thrown Weapons"

            val bowsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            bowsAdapter.setData(bows)
            binding.rvBows.adapter = bowsAdapter
            binding.textViewBows.text = "Bows"

            val crossbowsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            crossbowsAdapter.setData(crossbows)
            binding.rvCrossbows.adapter = crossbowsAdapter
            binding.textViewCrossbows.text = "Crossbows"

            if (bludgeons.isNotEmpty()) {
                binding.rvBludgeons.visibility = View.VISIBLE
                binding.textViewBludgeons.visibility = View.VISIBLE
            } else {
                binding.rvBludgeons.visibility = View.GONE
                binding.textViewBludgeons.visibility = View.GONE
            }
            if (poleArms.isNotEmpty()) {
                binding.rvPoleArms.visibility = View.VISIBLE
                binding.textViewPoleArms.visibility = View.VISIBLE
            } else {
                binding.rvPoleArms.visibility = View.GONE
                binding.textViewPoleArms.visibility = View.GONE
            }
            if (staves.isNotEmpty()) {
                binding.rvStaves.visibility = View.VISIBLE
                binding.textViewStaves.visibility = View.VISIBLE
            } else {
                binding.rvStaves.visibility = View.GONE
                binding.textViewStaves.visibility = View.GONE
            }
            if (thrownWeapons.isNotEmpty()) {
                binding.rvThrownWeapons.visibility = View.VISIBLE
                binding.textViewThrownWeapons.visibility = View.VISIBLE
            } else {
                binding.rvThrownWeapons.visibility = View.GONE
                binding.textViewThrownWeapons.visibility = View.GONE
            }
            if (bows.isNotEmpty()) {
                binding.rvBows.visibility = View.VISIBLE
                binding.textViewBows.visibility = View.VISIBLE
            } else {
                binding.rvBows.visibility = View.GONE
                binding.textViewBows.visibility = View.GONE
            }
            if (crossbows.isNotEmpty()) {
                binding.rvCrossbows.visibility = View.VISIBLE
                binding.textViewCrossbows.visibility = View.VISIBLE
            } else {
                binding.rvCrossbows.visibility = View.GONE
                binding.textViewCrossbows.visibility = View.GONE
            }
            if (swords.isNotEmpty()) {
                binding.rvLightEquipment.visibility = View.VISIBLE
                binding.textViewLight.visibility = View.VISIBLE
            } else {
                binding.rvLightEquipment.visibility = View.GONE
                binding.textViewLight.visibility = View.GONE
            }
            if (smallBlades.isNotEmpty()) {
                binding.rvMediumEquipment.visibility = View.VISIBLE
                binding.textViewMedium.visibility = View.VISIBLE
            } else {
                binding.rvMediumEquipment.visibility = View.GONE
                binding.textViewMedium.visibility = View.GONE
            }
            if (axes.isNotEmpty()) {
                binding.rvHeavyEquipment.visibility = View.VISIBLE
                binding.textViewHeavy.visibility = View.VISIBLE
            } else {
                binding.rvHeavyEquipment.visibility = View.GONE
                binding.textViewHeavy.visibility = View.GONE
            }

            if (swords.size == 0 &&
                smallBlades.size == 0 &&
                axes.size == 0 &&
                bludgeons.size == 0 &&
                poleArms.size == 0 &&
                staves.size == 0 &&
                thrownWeapons.size == 0 &&
                bows.size == 0 &&
                crossbows.size == 0) {
                binding.textViewNoEquipment.visibility = View.VISIBLE
                binding.textViewAddEquipment.visibility = View.VISIBLE
            } else {
                binding.textViewNoEquipment.visibility = View.INVISIBLE
                binding.textViewAddEquipment.visibility = View.INVISIBLE
            }

        } else {
            if (!customItem) {
                lightAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
                binding.rvLightEquipment.adapter = lightAdapter

                mediumAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
                binding.rvMediumEquipment.adapter = mediumAdapter

                heavyAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
                binding.rvHeavyEquipment.adapter = heavyAdapter

                binding.textViewLight.text = "Light"
                binding.textViewMedium.text = "Medium"
                binding.textViewHeavy.text = "Heavy"
                binding.textViewHeavy.visibility = View.VISIBLE
                binding.rvMediumEquipment.visibility = View.VISIBLE
                binding.rvHeavyEquipment.visibility = View.VISIBLE
            } else {
                binding.textViewLight.text = "Custom Items"
                binding.textViewLight.visibility = View.VISIBLE
                binding.textViewMedium.visibility = View.GONE
                binding.textViewHeavy.visibility = View.GONE
            }

            binding.rvLightEquipment.visibility = View.VISIBLE
            binding.rvBludgeons.visibility = View.GONE
            binding.rvPoleArms.visibility = View.GONE
            binding.rvStaves.visibility = View.GONE
            binding.rvThrownWeapons.visibility = View.GONE
            binding.rvBows.visibility = View.GONE
            binding.rvCrossbows.visibility = View.GONE
            binding.textViewBludgeons.visibility = View.GONE
            binding.textViewPoleArms.visibility = View.GONE
            binding.textViewStaves.visibility = View.GONE
            binding.textViewThrownWeapons.visibility = View.GONE
            binding.textViewBows.visibility = View.GONE
            binding.textViewCrossbows.visibility = View.GONE
        }
    }

    private fun showArmorDialog(item: EquipmentItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogCharArmorBinding =
            CustomDialogCharArmorBinding.inflate(layoutInflater)

        bind.equipmentItem = item

        bind.customTitle.setTitle(item.name)
        bind.customTitle.setTitleSize(17F)

        bind.imageViewMinus.visibility = View.GONE
        bind.imageViewPlus.visibility = View.GONE
        bind.imageViewMinusLSP.visibility = View.GONE
        bind.imageViewMinusRSP.visibility = View.GONE
        bind.imageViewPlusLSP.visibility = View.GONE
        bind.imageViewPlusRSP.visibility = View.GONE

        when (item.equipmentType) {
            EquipmentTypes.LIGHT_LEGS, EquipmentTypes.MEDIUM_LEGS, EquipmentTypes.HEAVY_LEGS -> {
                bind.textViewCurrentSP.visibility = View.GONE
                bind.textCurrentLeftSP.visibility = View.VISIBLE
                bind.textCurrentRightSP.visibility = View.VISIBLE
                bind.textCurrentLeftSP.text = item.currentLLegSP.toString()
                bind.textCurrentRightSP.text = item.currentRLegSP.toString()
                bind.textViewLeftSP.text = "Left Leg SP: "
                bind.textViewRightSP.text = "Right Leg SP: "
            }

            EquipmentTypes.LIGHT_CHEST, EquipmentTypes.MEDIUM_CHEST, EquipmentTypes.HEAVY_CHEST -> {
                bind.textCurrentLeftSP.visibility = View.VISIBLE
                bind.textCurrentRightSP.visibility = View.VISIBLE
                bind.textCurrentLeftSP.text = item.currentLArmSP.toString()
                bind.textCurrentRightSP.text = item.currentRArmSP.toString()
                bind.textCurrentSP.text = item.currentStoppingPower.toString()
            }

            EquipmentTypes.MISC_CUSTOM -> {
                bind.textViewEV.visibility = View.GONE
                bind.textViewSP.visibility = View.GONE
                bind.textViewAvailability.visibility = View.GONE
                bind.textViewAE.visibility = View.GONE
                bind.textViewLeftSP.visibility = View.GONE
                bind.textViewRightSP.visibility = View.GONE

                bind.imageViewMinus.visibility = View.VISIBLE
                bind.imageViewPlus.visibility = View.VISIBLE
                bind.textCurrentSP.text = item.quantity.toString()
                bind.textViewCurrentSP.text = "Quantity: "
                bind.buttonEquipUnequip.visibility = View.GONE
                bind.textViewEffect.text = "Description: " + item.effect
            }
            else -> {
                bind.textCurrentSP.text = item.currentStoppingPower.toString()
                bind.textViewLeftSP.visibility = View.GONE
                bind.textViewRightSP.visibility = View.GONE
            }
        }


        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        bind.buttonRemove.setOnClickListener {
            mainCharacterViewModel.removeEquipment(item)
            dialog.dismiss()
            updateRVs(binding.autoCompleteTextViewItemType.text.toString())
        }

        bind.buttonEquipUnequip.setOnClickListener {
            mainCharacterViewModel.equipItem(item)
            Navigation.findNavController(binding.root)
                .navigateUp()
            dialog.dismiss()
        }

        bind.imageViewMinus.setOnClickListener {
            if (bind.textCurrentSP.text.toString().toInt() > 0) {
                bind.textCurrentSP.text = mainCharacterViewModel.onItemSPChange(
                    item,
                    increase = false,
                    EquipmentTypes.MISC_CUSTOM
                ).toString()
            }
        }

        bind.imageViewPlus.setOnClickListener {
            mainCharacterViewModel.onItemSPChange(item, increase = true, EquipmentTypes.MISC_CUSTOM)
            bind.textCurrentSP.text = item.quantity.toString()
        }

        dialog.setContentView(bind.root)

        dialog.show()
    }

    private fun showWeaponDialog(weaponItem: WeaponItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogCharWeaponBinding =
            CustomDialogCharWeaponBinding.inflate(layoutInflater)

        bind.weaponItem = weaponItem

        bind.customTitle.setTitle(weaponItem.name)
        bind.customTitle.setTitleSize(17F)

        bind.imageViewPlus.visibility = View.GONE
        bind.imageViewMinus.visibility = View.GONE

        bind.buttonEquip.setOnClickListener {
            mainCharacterViewModel.equipWeapon(weaponItem)
            Navigation.findNavController(binding.root)
                .navigateUp()
            dialog.dismiss()
        }

        bind.buttonRemove.setOnClickListener {
            mainCharacterViewModel.removeWeapon(weaponItem)
            updateRVs(binding.autoCompleteTextViewItemType.text.toString())
            dialog.dismiss()
        }

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(bind.root)
        dialog.show()

    }

    override fun onResume() {
        super.onResume()

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.inventoryCategories,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.autoCompleteTextViewItemType.setAdapter(adapter)
        }
    }

    private fun updateRVs(position: String) {
        val lightArmorList = arrayListOf<EquipmentItem>()
        val mediumArmorList = arrayListOf<EquipmentItem>()
        val heavyArmorList = arrayListOf<EquipmentItem>()

        if (position == "Weapon") {
            listAdaptersInit(true)
            return
        }

        when (position) {
            "Head Armor" -> {
                for (item in mainCharacterViewModel.headEquipment.value) {
                    when (item.equipmentType) {
                        EquipmentTypes.LIGHT_HEAD -> lightArmorList.add(item)
                        EquipmentTypes.MEDIUM_HEAD -> mediumArmorList.add(item)
                        EquipmentTypes.HEAVY_HEAD -> heavyArmorList.add(item)
                    }
                }
                listAdaptersInit(false)
                lightAdapter.setData(lightArmorList)
                mediumAdapter.setData(mediumArmorList)
                heavyAdapter.setData(heavyArmorList)
            }

            "Chest Armor" -> {
                listAdaptersInit(false)
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

            "Leg Armor" -> {
                listAdaptersInit(false)
                for (item in mainCharacterViewModel.legEquipment.value) {
                    when (item.equipmentType) {
                        EquipmentTypes.LIGHT_LEGS -> lightArmorList.add(item)
                        EquipmentTypes.MEDIUM_LEGS -> mediumArmorList.add(item)
                        EquipmentTypes.HEAVY_LEGS -> heavyArmorList.add(item)
                    }
                }
                lightAdapter.setData(lightArmorList)
                mediumAdapter.setData(mediumArmorList)
                heavyAdapter.setData(heavyArmorList)
            }

            "Shield" -> {
                listAdaptersInit(false)
                for (item in mainCharacterViewModel.shieldEquipment.value) {
                    when (item.equipmentType) {
                        EquipmentTypes.LIGHT_SHIELD -> lightArmorList.add(item)
                        EquipmentTypes.MEDIUM_SHIELD -> mediumArmorList.add(item)
                        EquipmentTypes.HEAVY_SHIELD -> heavyArmorList.add(item)
                    }
                }
                lightAdapter.setData(lightArmorList)
                mediumAdapter.setData(mediumArmorList)
                heavyAdapter.setData(heavyArmorList)
            }
            "Miscellaneous" -> {
                listAdaptersInit(false)
                for (item in mainCharacterViewModel.miscEquipment.value) {
                    lightArmorList.add(item)
                }
                listAdaptersInit(weapons = false, customItem = true)
                lightAdapter.setData(lightArmorList)
            }
        }
        if (lightArmorList.size == 0) binding.textViewLight.visibility = View.INVISIBLE
        else binding.textViewLight.visibility = View.VISIBLE
        if (mediumArmorList.size == 0) binding.textViewMedium.visibility = View.INVISIBLE
        else binding.textViewMedium.visibility = View.VISIBLE
        if (heavyArmorList.size == 0) binding.textViewHeavy.visibility = View.INVISIBLE
        else binding.textViewHeavy.visibility = View.VISIBLE

        if (lightArmorList.size == 0 && mediumArmorList.size == 0 && heavyArmorList.size == 0) {
            binding.textViewNoEquipment.visibility = View.VISIBLE
            binding.textViewAddEquipment.visibility = View.VISIBLE
        } else {
            binding.textViewNoEquipment.visibility = View.INVISIBLE
            binding.textViewAddEquipment.visibility = View.INVISIBLE
        }
    }
}
