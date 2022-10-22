package com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.CustomDialogCharArmorBinding
import com.witcher.thewitcherrpg.databinding.CustomDialogCharWeaponBinding
import com.witcher.thewitcherrpg.databinding.FragmentInventoryBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.*
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.EquipmentListAdapter
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.properties.Delegates


class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()
    private val selectionArg: InventoryFragmentArgs by navArgs()

    private var darkModeEnabled by Delegates.notNull<Boolean>()
    private lateinit var equipmentAdapter: EquipmentListAdapter
    private var itemList = arrayListOf<Any>()

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

        lifecycleScope.launch {
            launch {
                mainCharacterViewModel.isDarkModeEnabled.collect { value ->
                    darkModeEnabled = value
                }
            }
        }

        equipmentAdapter = EquipmentListAdapter ({ equipment ->
            when (equipment) {
                is EquipmentItem -> {
                    showArmorDialog(equipment)
                }
                is WeaponItem -> {
                    showWeaponDialog(equipment)
                }
            }
        }, darkModeEnabled)
        binding.inventoryRv.adapter = equipmentAdapter
        binding.inventoryRv.layoutManager = LinearLayoutManager(requireContext())

        binding.autoCompleteTextViewItemType.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
                //updateRVs(binding.autoCompleteTextViewItemType.text.toString())
                updateListData()
            }
        listAdaptersInit(AddItemFragment.ItemType.HEAD_ARMOR)

        var selection = "Head Armor"
        when (selectionArg.spinnerSelection) {
            1 -> selection = "Chest Armor"
            2 -> selection = "Leg Armor"
            3 -> selection = "Weapon"
            4 -> selection = "Shield/Accessory"
        }

        binding.autoCompleteTextViewItemType.setText(selection, false)
        updateListData()

        return view
    }

    private fun listAdaptersInit(itemType: AddItemFragment.ItemType) {
        val newItemList = arrayListOf<Equipment>()

        when (itemType) {
            AddItemFragment.ItemType.WEAPON -> {
                newItemList.addAll(mainCharacterViewModel.weaponEquipment.value)
            }
            AddItemFragment.ItemType.HEAD_ARMOR -> {
                newItemList.addAll(mainCharacterViewModel.headEquipment.value)
            }
            AddItemFragment.ItemType.CHEST_ARMOR -> {
                newItemList.addAll(mainCharacterViewModel.chestEquipment.value)
            }
            AddItemFragment.ItemType.LEG_ARMOR -> {
                newItemList.addAll(mainCharacterViewModel.legEquipment.value)
            }
            AddItemFragment.ItemType.SHIELD_ACCESSORY -> {
                newItemList.addAll(mainCharacterViewModel.accessoryEquipment.value)
                newItemList.addAll(mainCharacterViewModel.weaponEquipment.value.filter { it.type == WeaponTypes.AMULET })
            }
            AddItemFragment.ItemType.CUSTOM_ITEM -> {
                newItemList.addAll(mainCharacterViewModel.miscEquipment.value)
            }
            else -> {}
        }
        addHeadersToList(newItemList)
    }

    private fun updateListData(){
        when (binding.autoCompleteTextViewItemType.text.toString()) {
            "Head Armor" -> {
                listAdaptersInit(AddItemFragment.ItemType.HEAD_ARMOR)
            }
            "Chest Armor" -> {
                listAdaptersInit(AddItemFragment.ItemType.CHEST_ARMOR)
            }
            "Leg Armor" -> {
                listAdaptersInit(AddItemFragment.ItemType.LEG_ARMOR)
            }
            "Weapon" -> {
                listAdaptersInit(AddItemFragment.ItemType.WEAPON)
            }
            "Shield/Accessory" -> {
                listAdaptersInit(AddItemFragment.ItemType.SHIELD_ACCESSORY)
            }
            "Miscellaneous" -> {
                listAdaptersInit(AddItemFragment.ItemType.CUSTOM_ITEM)
            }
        }
    }

    private fun addHeadersToList(equipmentList: ArrayList<Equipment>) {
        val tempItemList = arrayListOf<Any>()
        var smallBladesHeaderAdded = false
        var swordsHeaderAdded = false
        var axesHeaderAdded = false
        var bludgeonsHeaderAdded = false
        var poleArmsHeaderAdded = false
        var stavesHeaderAdded = false
        var thrownWeaponsHeaderAdded = false
        var bowsHeaderAdded = false
        var lightHeaderAdded = false
        var mediumHeaderAdded = false
        var heavyHeaderAdded = false
        var crossbowsHeaderAdded = false
        var amuletHeaderAdded = false
        var customHeaderAdded = false

        if (equipmentList.size > 0) {
            binding.textViewNoEquipment.visibility = View.GONE
            binding.textViewAddEquipment.visibility = View.GONE
            if (equipmentList[0] is EquipmentItem) {
                try {
                    (equipmentList as ArrayList<EquipmentItem>).sortBy { it.equipmentType }
                } catch (e: Exception) {}
            } else if (equipmentList[0] is WeaponItem) {
                (equipmentList as ArrayList<WeaponItem>).sortBy { it.type }
            }
        } else {
            binding.textViewNoEquipment.visibility = View.VISIBLE
            binding.textViewAddEquipment.visibility = View.VISIBLE
        }

        for (item in equipmentList) {
            when (item) {
                is WeaponItem -> {
                    if (item.type == WeaponTypes.SWORD){
                        if (!swordsHeaderAdded) {
                            tempItemList.add(ListHeader("Swords"))
                            swordsHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.SMALL_BLADE){
                        if (!smallBladesHeaderAdded) {
                            tempItemList.add(ListHeader("Small Blades"))
                            smallBladesHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.AXE){
                        if (!axesHeaderAdded) {
                            tempItemList.add(ListHeader("Axes"))
                            axesHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.BLUDGEON){
                        if (!bludgeonsHeaderAdded) {
                            tempItemList.add(ListHeader("Bludgeons"))
                            bludgeonsHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.POLE_ARM){
                        if (!poleArmsHeaderAdded) {
                            tempItemList.add(ListHeader("Pole Arms"))
                            poleArmsHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.STAFF){
                        if (!stavesHeaderAdded) {
                            tempItemList.add(ListHeader("Staves"))
                            stavesHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.THROWN_WEAPON){
                        if (!thrownWeaponsHeaderAdded) {
                            tempItemList.add(ListHeader("Thrown Weapons"))
                            thrownWeaponsHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.BOW){
                        if (!bowsHeaderAdded) {
                            tempItemList.add(ListHeader("Bows"))
                            bowsHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.CROSSBOW){
                        if (!crossbowsHeaderAdded) {
                            tempItemList.add(ListHeader("Crossbows"))
                            crossbowsHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    if (item.type == WeaponTypes.AMULET){
                        if (!amuletHeaderAdded) {
                            tempItemList.add(ListHeader("Amulets"))
                            amuletHeaderAdded = true
                        }
                        tempItemList.add(item)
                        continue
                    }
                    //tempItemList.add(item)
                }
                is EquipmentItem -> {
                    if (!lightHeaderAdded && (item.equipmentType == EquipmentTypes.LIGHT_HEAD ||
                                item.equipmentType == EquipmentTypes.LIGHT_CHEST ||
                                item.equipmentType == EquipmentTypes.LIGHT_LEGS ||
                                item.equipmentType == EquipmentTypes.LIGHT_SHIELD)) {
                        tempItemList.add(ListHeader("Light"))
                        tempItemList.add(item)
                        lightHeaderAdded = true
                        continue
                    }
                    if (!mediumHeaderAdded && (item.equipmentType == EquipmentTypes.MEDIUM_HEAD ||
                                item.equipmentType == EquipmentTypes.MEDIUM_CHEST ||
                                item.equipmentType == EquipmentTypes.MEDIUM_LEGS ||
                                item.equipmentType == EquipmentTypes.MEDIUM_SHIELD)) {
                        tempItemList.add(ListHeader("Medium"))
                        tempItemList.add(item)
                        mediumHeaderAdded = true
                        continue
                    }
                    if (!heavyHeaderAdded && (item.equipmentType == EquipmentTypes.HEAVY_HEAD ||
                                item.equipmentType == EquipmentTypes.HEAVY_CHEST ||
                                item.equipmentType == EquipmentTypes.HEAVY_LEGS ||
                                item.equipmentType == EquipmentTypes.HEAVY_SHIELD)) {
                        tempItemList.add(ListHeader("Heavy"))
                        tempItemList.add(item)
                        heavyHeaderAdded = true
                        continue
                    }
                    if (!customHeaderAdded && (item.equipmentType == EquipmentTypes.MISC_CUSTOM)) {
                        tempItemList.add(ListHeader("Miscellaneous Items"))
                        tempItemList.add(item)
                        customHeaderAdded = true
                        continue
                    }
                    tempItemList.add(item)
                }
            }
        }
        itemList = tempItemList
        setAdapterData()
    }

    private fun setAdapterData() {
        equipmentAdapter.setData(itemList)
        binding.inventoryRv.scheduleLayoutAnimation()
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
            updateListData()
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
            updateListData()
            dialog.dismiss()
        }

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
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
            }
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
}
