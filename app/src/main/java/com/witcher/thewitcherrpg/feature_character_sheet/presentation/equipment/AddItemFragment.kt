package com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.TheWitcherTRPGApp
import com.witcher.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.witcher.thewitcherrpg.databinding.*
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.WeaponItem
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.ArmorSet
import com.google.android.material.snackbar.Snackbar
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.ListHeader
import com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.EquipmentListAdapter
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()
    private var itemList = arrayListOf<Any>()
    private var darkModeEnabled by Delegates.notNull<Boolean>()
    private lateinit var equipmentAdapter: EquipmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view)
                .navigate(R.id.action_addArmorFragment2_to_equipmentFragment)
        }
        callback.isEnabled = true

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
                is ArmorSet -> {
                    showArmorSetDialog(equipment)
                }
                is WeaponItem -> {
                    showWeaponDialog(equipment)
                }
            }
        }, darkModeEnabled)

        binding.customTitle.setTitle("New Item")
        binding.customTitle.setTitleSize(20F)

        binding.equipmentRv.adapter = equipmentAdapter
        binding.equipmentRv.layoutManager = LinearLayoutManager(requireContext())

        listAdaptersInit(ItemType.HEAD_ARMOR)
        setAdapterData()

        binding.autoCompleteTextViewItemType.setText("Head Armor")
        binding.autoCompleteTextViewItemType.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val types =
                    TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.equipmentCategories)
                types?.get(position)?.let { item ->

                    when (item) {
                        "Weapon" -> {
                            listAdaptersInit(ItemType.WEAPON)
                            setAdapterData()
                        }
                        "Head Armor" -> {
                            listAdaptersInit(ItemType.HEAD_ARMOR)
                            setAdapterData()
                        }
                        "Chest Armor" -> {
                            listAdaptersInit(ItemType.CHEST_ARMOR)
                            setAdapterData()
                        }
                        "Leg Armor" -> {
                            listAdaptersInit(ItemType.LEG_ARMOR)
                            setAdapterData()
                        }
                        "Shield/Accessory" -> {
                            listAdaptersInit(ItemType.SHIELD_ACCESSORY)
                            setAdapterData()
                        }
                        "Armor Set" -> {
                            listAdaptersInit(ItemType.ARMOR_SET)
                            setAdapterData()
                        }
                        "Miscellaneous" -> showCustomItemDialog()
                    }
                }
            }

        //binding.autoCompleteTextViewItemType.showDropDown()

        return view
    }

    private fun listAdaptersInit(itemType: ItemType) {
        val newItemList = arrayListOf<Any>()

        when (itemType) {
            ItemType.WEAPON -> {
                newItemList.add(ListHeader("Swords"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.swords_data))

                newItemList.add(ListHeader("Small Blades"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.small_blades_data))

                newItemList.add(ListHeader("Axes"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.axes_data))

                newItemList.add(ListHeader("Bludgeons"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.bludgeons_data))

                newItemList.add(ListHeader("Pole Arms"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.pole_arms_data))

                newItemList.add(ListHeader("Staves"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.staves_data))

                newItemList.add(ListHeader("Thrown Weapons"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.thrown_weapons_data))

                newItemList.add(ListHeader("Bows"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.bows_data))

                newItemList.add(ListHeader("Crossbows"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.crossbows_data))
            }
            ItemType.HEAD_ARMOR -> {
                newItemList.add(ListHeader("Light"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.head_light_armor_data))

                newItemList.add(ListHeader("Medium"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.head_medium_armor_data))

                newItemList.add(ListHeader("Heavy"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.head_heavy_armor_data))
            }
            ItemType.CHEST_ARMOR -> {
                newItemList.add(ListHeader("Light"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.chest_light_armor_data))

                newItemList.add(ListHeader("Medium"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.chest_medium_armor_data))

                newItemList.add(ListHeader("Heavy"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.chest_heavy_armor_data))
            }
            ItemType.LEG_ARMOR -> {
                newItemList.add(ListHeader("Light"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.legs_light_armor_data))

                newItemList.add(ListHeader("Medium"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.legs_medium_armor_data))

                newItemList.add(ListHeader("Heavy"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.legs_heavy_armor_data))
            }
            ItemType.SHIELD_ACCESSORY -> {
                newItemList.add(ListHeader("Light"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.shields_light_data))

                newItemList.add(ListHeader("Medium"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.shields_medium_data))

                newItemList.add(ListHeader("Heavy"))
                newItemList.addAll(mainCharacterViewModel.getEquipmentList(R.array.shields_heavy_data))

                newItemList.add(ListHeader("Amulets"))
                newItemList.addAll(mainCharacterViewModel.getWeaponList(R.array.amulets_data))
            }
            ItemType.ARMOR_SET -> {
                newItemList.add(ListHeader("Light"))
                newItemList.addAll(mainCharacterViewModel.getArmorSetList(R.array.armor_set_light_data))

                newItemList.add(ListHeader("Medium"))
                newItemList.addAll(mainCharacterViewModel.getArmorSetList(R.array.armor_set_medium_data))

                newItemList.add(ListHeader("Heavy"))
                newItemList.addAll(mainCharacterViewModel.getArmorSetList(R.array.armor_set_heavy_data))
            }
            else -> {}
        }
        itemList = newItemList
    }

    private fun setAdapterData() {
        equipmentAdapter.setData(itemList)
        binding.equipmentRv.scheduleLayoutAnimation()
    }

    private fun showArmorDialog(armorItem: EquipmentItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogAddArmorBinding = CustomDialogAddArmorBinding.inflate(layoutInflater)

        bind.equipmentItem = armorItem

        bind.customTitle.setTitle(armorItem.name)
        bind.customTitle.setTitleSize(17F)

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        bind.buttonAdd.setOnClickListener {

            mainCharacterViewModel.addEquipmentItem(armorItem)
            Snackbar.make(
                binding.root, "Item added to ${mainCharacterViewModel.name.value}",
                Snackbar.LENGTH_SHORT,
            ).show()

            dialog.dismiss()
        }

        bind.buttonBuy.setOnClickListener {

            if (mainCharacterViewModel.buyItem(armorItem)) {
                Snackbar.make(
                    binding.root, "${armorItem.name} purchased.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    binding.root, "Not enough crowns.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()

        }

        dialog.setContentView(bind.root)

        dialog.show()
    }

    private fun showWeaponDialog(weaponItem: WeaponItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogAddWeaponBinding =
            CustomDialogAddWeaponBinding.inflate(layoutInflater)

        bind.weaponItem = weaponItem

        bind.customTitle.setTitle(weaponItem.name)
        bind.customTitle.setTitleSize(17F)

        bind.buttonAdd.setOnClickListener {
            mainCharacterViewModel.addWeaponItem(weaponItem)
            Snackbar.make(
                binding.root, "Item added to ${mainCharacterViewModel.name.value}",
                Snackbar.LENGTH_SHORT,
            ).show()
            dialog.dismiss()

        }

        bind.buttonBuy.setOnClickListener {

            if (mainCharacterViewModel.buyWeapon(weaponItem)) {
                Snackbar.make(
                    binding.root, "${weaponItem.name} purchased.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    binding.root, "Not enough crowns.",
                    Snackbar.LENGTH_SHORT
                ).show()
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
                }
            }
            dialog.dismiss()
            dialog.dismiss()
        }

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(bind.root)
        dialog.show()

    }

    private fun showCustomItemDialog() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val bind: CustomDialogAddCustomItemBinding =
            CustomDialogAddCustomItemBinding.inflate(layoutInflater)

        bind.customTitle.setTitle("Custom Item")
        bind.customTitle.setTitleSize(17F)

        bind.buttonAddItem.setOnClickListener {
            if (bind.etName.text.toString().isNotBlank() &&
                bind.etQuantity.text.toString().isNotBlank() &&
                bind.etWeight.text.toString().isNotBlank() &&
                bind.etCost.text.toString().isNotBlank() &&
                bind.etDescription.text.toString().isNotBlank()
            ) {
                val customItem = EquipmentItem(
                    name = bind.etName.text.toString(),
                    quantity = bind.etQuantity.text.toString().toInt(),
                    weight = bind.etWeight.text.toString().toFloat(),
                    cost = bind.etCost.text.toString().toInt(),
                    effect = "",
                    equipmentType = EquipmentTypes.MISC_CUSTOM,
                    equipmentNote = bind.etDescription.text.toString()
                )
                mainCharacterViewModel.addEquipmentItem(customItem)
                Snackbar.make(
                    binding.root, "${customItem.name} added to inventory.",
                    Snackbar.LENGTH_SHORT,
                ).show()
                dialog.dismiss()
            } else {
                Snackbar.make(
                    bind.root, "Please fill out every field.",
                    Snackbar.LENGTH_SHORT,
                ).show()
            }
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
            R.array.equipmentCategories,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.autoCompleteTextViewItemType.setAdapter(adapter)
        }
    }

    private fun showArmorSetDialog(armorItem: ArmorSet) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogAddArmorBinding = CustomDialogAddArmorBinding.inflate(layoutInflater)

        bind.equipmentItem = null
        bind.armorSetItem = armorItem

        bind.customTitle.setTitle(armorItem.name)
        bind.customTitle.setTitleSize(17F)

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        bind.buttonAdd.setOnClickListener {

            mainCharacterViewModel.addArmorSet(armorItem)
            Snackbar.make(
                binding.root, "Armor Set items individually added.",
                Snackbar.LENGTH_SHORT,
            ).show()
            dialog.dismiss()
        }

        bind.buttonBuy.setOnClickListener {

            if (mainCharacterViewModel.buyArmorSet(armorItem)) {
                Snackbar.make(
                    binding.root, "${armorItem.name} purchased. Armor Set items individually added.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    binding.root, "Not enough crowns.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()

        }

        dialog.setContentView(bind.root)

        dialog.show()
    }

    enum class ItemType {
        HEAD_ARMOR,
        CHEST_ARMOR,
        LEG_ARMOR,
        WEAPON,
        SHIELD_ACCESSORY,
        ARMOR_SET,
        CUSTOM_ITEM
    }
}