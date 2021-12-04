package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.core.presentation.MainCharacterViewModel
import com.example.thewitcherrpg.databinding.*
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.models.WeaponItem
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.models.ArmorSet
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.ArmorSetListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.EquipmentListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.WeaponListAdapter
import com.google.android.material.snackbar.Snackbar

class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private val mainCharacterViewModel: MainCharacterViewModel by activityViewModels()

    private lateinit var lightAdapter: EquipmentListAdapter
    private lateinit var mediumAdapter: EquipmentListAdapter
    private lateinit var heavyAdapter: EquipmentListAdapter

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

        binding.customTitle.setTitle("New Item")
        binding.customTitle.setTitleSize(20F)

        listAdaptersInit()

        binding.autoCompleteTextViewItemType.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val types =
                    TheWitcherTRPGApp.getContext()?.resources?.getStringArray(R.array.equipmentCategories)
                types?.get(position)?.let { item ->

                    if (item == "Weapon") {
                        listAdaptersInit(true)
                    } else listAdaptersInit(false)

                    if (item == "Head Armor") {
                        lightAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.head_light_armor_data))
                        mediumAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.head_medium_armor_data))
                        heavyAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.head_heavy_armor_data))
                    }
                    if (item == "Chest Armor") {
                        lightAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.chest_light_armor_data))
                        mediumAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.chest_medium_armor_data))
                        heavyAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.chest_heavy_armor_data))
                    }
                    if (item == "Leg Armor") {
                        lightAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.legs_light_armor_data))
                        mediumAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.legs_medium_armor_data))
                        heavyAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.legs_heavy_armor_data))
                    }
                    if (item == "Shield") {
                        lightAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.shields_light_data))
                        mediumAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.shields_medium_data))
                        heavyAdapter.setData(mainCharacterViewModel.getEquipmentList(R.array.shields_heavy_data))
                    }
                    if (item == "Armor Set") {
                        listAdaptersInit(weapons = false, armorSet = true)
                    }
                    if (item == "Miscellaneous") {
                        showCustomItemDialog()
                    }
                }
            }

        binding.rvLightEquipment.visibility = View.GONE
        binding.rvMediumEquipment.visibility = View.GONE
        binding.rvHeavyEquipment.visibility = View.GONE
        binding.rvBludgeons.visibility = View.GONE
        binding.rvPoleArms.visibility = View.GONE
        binding.rvStaves.visibility = View.GONE
        binding.rvThrownWeapons.visibility = View.GONE
        binding.rvBows.visibility = View.GONE
        binding.rvCrossbows.visibility = View.GONE
        binding.textViewLight.visibility = View.GONE
        binding.textViewMedium.visibility = View.GONE
        binding.textViewHeavy.visibility = View.GONE
        binding.textViewBludgeons.visibility = View.GONE
        binding.textViewPoleArms.visibility = View.GONE
        binding.textViewStaves.visibility = View.GONE
        binding.textViewThrownWeapons.visibility = View.GONE
        binding.textViewBows.visibility = View.GONE
        binding.textViewCrossbows.visibility = View.GONE

        binding.autoCompleteTextViewItemType.showDropDown()

        return view
    }

    private fun listAdaptersInit() {

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

    private fun listAdaptersInit(weapons: Boolean, armorSet: Boolean = false) {

        if (weapons) {
            val swordsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            swordsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.swords_data))
            binding.rvLightEquipment.adapter = swordsAdapter
            binding.textViewLight.text = "Swords"

            val smallBladesAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            smallBladesAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.small_blades_data))
            binding.rvMediumEquipment.adapter = smallBladesAdapter
            binding.textViewMedium.text = "Small Blades"


            val axesAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            axesAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.axes_data))
            binding.rvHeavyEquipment.adapter = axesAdapter
            binding.textViewHeavy.text = "Axes"

            val bludgeonsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            bludgeonsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.bludgeons_data))
            binding.rvBludgeons.adapter = bludgeonsAdapter
            binding.textViewBludgeons.text = "Bludgeons"

            val poleArmsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            poleArmsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.pole_arms_data))
            binding.rvPoleArms.adapter = poleArmsAdapter
            binding.textViewPoleArms.text = "Pole Arms"

            val stavesAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            stavesAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.staves_data))
            binding.rvStaves.adapter = stavesAdapter
            binding.textViewStaves.text = "Staves"

            val thrownWeaponsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            thrownWeaponsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.thrown_weapons_data))
            binding.rvThrownWeapons.adapter = thrownWeaponsAdapter
            binding.textViewThrownWeapons.text = "Thrown Weapons"

            val bowsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            bowsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.bows_data))
            binding.rvBows.adapter = bowsAdapter
            binding.textViewBows.text = "Bows"

            val crossbowsAdapter = WeaponListAdapter { item -> showWeaponDialog(item) }
            crossbowsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.crossbows_data))
            binding.rvCrossbows.adapter = crossbowsAdapter
            binding.textViewCrossbows.text = "Crossbows"

            binding.rvLightEquipment.visibility = View.VISIBLE
            binding.rvMediumEquipment.visibility = View.VISIBLE
            binding.rvHeavyEquipment.visibility = View.VISIBLE
            binding.rvBludgeons.visibility = View.VISIBLE
            binding.rvPoleArms.visibility = View.VISIBLE
            binding.rvStaves.visibility = View.VISIBLE
            binding.rvThrownWeapons.visibility = View.VISIBLE
            binding.rvBows.visibility = View.VISIBLE
            binding.rvCrossbows.visibility = View.VISIBLE
            binding.textViewLight.visibility = View.VISIBLE
            binding.textViewMedium.visibility = View.VISIBLE
            binding.textViewHeavy.visibility = View.VISIBLE
            binding.textViewBludgeons.visibility = View.VISIBLE
            binding.textViewPoleArms.visibility = View.VISIBLE
            binding.textViewStaves.visibility = View.VISIBLE
            binding.textViewThrownWeapons.visibility = View.VISIBLE
            binding.textViewBows.visibility = View.VISIBLE
            binding.textViewCrossbows.visibility = View.VISIBLE

        } else {
            if (!armorSet) {
                lightAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
                binding.rvLightEquipment.adapter = lightAdapter

                mediumAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
                binding.rvMediumEquipment.adapter = mediumAdapter

                heavyAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
                binding.rvHeavyEquipment.adapter = heavyAdapter
            } else {
                val lightArmorSetAdapter = ArmorSetListAdapter {
                    item -> showArmorSetDialog(item)
                }
                lightArmorSetAdapter.setData(mainCharacterViewModel.getArmorSetList(R.array.armor_set_light_data))
                binding.rvLightEquipment.adapter = lightArmorSetAdapter

                val mediumArmorSetAdapter = ArmorSetListAdapter {
                    item -> showArmorSetDialog(item)
                }
                mediumArmorSetAdapter.setData(mainCharacterViewModel.getArmorSetList(R.array.armor_set_medium_data))
                binding.rvMediumEquipment.adapter = mediumArmorSetAdapter

                val heavyArmorSetAdapter = ArmorSetListAdapter {
                    item -> showArmorSetDialog(item)
                }
                heavyArmorSetAdapter.setData(mainCharacterViewModel.getArmorSetList(R.array.armor_set_heavy_data))
                binding.rvHeavyEquipment.adapter = heavyArmorSetAdapter
            }

            binding.textViewLight.text = "Light"
            binding.textViewMedium.text = "Medium"
            binding.textViewHeavy.text = "Heavy"

            binding.rvLightEquipment.visibility = View.VISIBLE
            binding.rvMediumEquipment.visibility = View.VISIBLE
            binding.rvHeavyEquipment.visibility = View.VISIBLE
            binding.rvBludgeons.visibility = View.GONE
            binding.rvPoleArms.visibility = View.GONE
            binding.rvStaves.visibility = View.GONE
            binding.rvThrownWeapons.visibility = View.GONE
            binding.rvBows.visibility = View.GONE
            binding.rvCrossbows.visibility = View.GONE
            binding.textViewLight.visibility = View.VISIBLE
            binding.textViewMedium.visibility = View.VISIBLE
            binding.textViewHeavy.visibility = View.VISIBLE
            binding.textViewBludgeons.visibility = View.GONE
            binding.textViewPoleArms.visibility = View.GONE
            binding.textViewStaves.visibility = View.GONE
            binding.textViewThrownWeapons.visibility = View.GONE
            binding.textViewBows.visibility = View.GONE
            binding.textViewCrossbows.visibility = View.GONE
        }
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
                    effect = bind.etDescription.text.toString(),
                    equipmentType = EquipmentTypes.MISC_CUSTOM
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
}