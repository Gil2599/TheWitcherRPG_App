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
import com.example.thewitcherrpg.databinding.CustomDialogAddArmorBinding
import com.example.thewitcherrpg.databinding.FragmentAddItemBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.EquipmentListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.WeaponListAdapter

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

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.equipmentCategories,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAddEquipment.adapter = adapter
        }

        listAdaptersInit()

        binding.spinnerAddEquipment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = binding.spinnerAddEquipment.getItemAtPosition(position).toString()

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
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // Nothing happens
                }
            }

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

    private fun listAdaptersInit(weapons: Boolean) {

        if (weapons) {
            val swordsAdapter = WeaponListAdapter {}
            swordsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.swords_data))
            binding.rvLightEquipment.adapter = swordsAdapter
            binding.textViewLight.text = "Swords"

            val smallBladesAdapter = WeaponListAdapter {}
            smallBladesAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.small_blades_data))
            binding.rvMediumEquipment.adapter = smallBladesAdapter
            binding.textViewMedium.text = "Small Blades"


            val axesAdapter = WeaponListAdapter {}
            axesAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.axes_data))
            binding.rvHeavyEquipment.adapter = axesAdapter
            binding.textViewHeavy.text = "Axes"

            val bludgeonsAdapter = WeaponListAdapter {}
            bludgeonsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.bludgeons_data))
            binding.rvBludgeons.adapter = bludgeonsAdapter
            binding.textViewBludgeons.text = "Bludgeons"

            val poleArmsAdapter = WeaponListAdapter {}
            poleArmsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.pole_arms_data))
            binding.rvPoleArms.adapter = poleArmsAdapter
            binding.textViewPoleArms.text = "Pole Arms"

            val stavesAdapter = WeaponListAdapter {}
            stavesAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.staves_data))
            binding.rvStaves.adapter = stavesAdapter
            binding.textViewStaves.text = "Staves"

            val thrownWeaponsAdapter = WeaponListAdapter {}
            thrownWeaponsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.thrown_weapons_data))
            binding.rvThrownWeapons.adapter = thrownWeaponsAdapter
            binding.textViewThrownWeapons.text = "Thrown Weapons"

            val bowsAdapter = WeaponListAdapter {}
            bowsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.bows_data))
            binding.rvBows.adapter = bowsAdapter
            binding.textViewBows.text = "Bows"

            val crossbowsAdapter = WeaponListAdapter {}
            crossbowsAdapter.setData(mainCharacterViewModel.getWeaponList(R.array.crossbows_data))
            binding.rvCrossbows.adapter = crossbowsAdapter
            binding.textViewCrossbows.text = "Crossbows"

            binding.rvBludgeons.visibility = View.VISIBLE
            binding.rvPoleArms.visibility = View.VISIBLE
            binding.rvStaves.visibility = View.VISIBLE
            binding.rvThrownWeapons.visibility = View.VISIBLE
            binding.rvBows.visibility = View.VISIBLE
            binding.rvCrossbows.visibility = View.VISIBLE
            binding.textViewBludgeons.visibility = View.VISIBLE
            binding.textViewPoleArms.visibility = View.VISIBLE
            binding.textViewStaves.visibility = View.VISIBLE
            binding.textViewThrownWeapons.visibility = View.VISIBLE
            binding.textViewBows.visibility = View.VISIBLE
            binding.textViewCrossbows.visibility = View.VISIBLE
        } else {
            lightAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
            binding.rvLightEquipment.adapter = lightAdapter

            mediumAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
            binding.rvMediumEquipment.adapter = mediumAdapter

            heavyAdapter = EquipmentListAdapter { item -> showArmorDialog(item) }
            binding.rvHeavyEquipment.adapter = heavyAdapter

            binding.textViewLight.text = "Light"
            binding.textViewMedium.text = "Medium"
            binding.textViewHeavy.text = "Heavy"

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

    private fun showArmorDialog(armorItem: EquipmentItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind: CustomDialogAddArmorBinding = CustomDialogAddArmorBinding.inflate(layoutInflater)

        val stoppingPower = "Stopping Power: " + armorItem.stoppingPower
        val availability = "Availability: " + armorItem.availability
        val armorEnhancement = "Armor Enhancement: " + armorItem.armorEnhancement
        val effect = "Effect: " + armorItem.effect
        val encValue = "Encumbrance Value: " + armorItem.encumbranceValue
        val weight = "Weight: " + armorItem.weight
        val price = "Cost: " + armorItem.cost + " Crowns"

        bind.customTitle.setTitle(armorItem.name)
        bind.customTitle.setTitleSize(17F)
        bind.textViewSP.text = stoppingPower
        bind.textViewAvailability.text = availability
        bind.textViewAE.text = armorEnhancement
        bind.textViewEffect.text = effect
        bind.textViewEV.text = encValue
        bind.textViewWeight.text = weight
        bind.textViewCost.text = price

        bind.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        bind.buttonAdd.setOnClickListener {

            mainCharacterViewModel.addEquipmentItem(armorItem)
            Toast.makeText(
                context,
                "Item added to ${mainCharacterViewModel.name.value}",
                Toast.LENGTH_SHORT
            ).show()

            dialog.dismiss()
        }

        bind.buttonBuy.setOnClickListener {

            //if (sharedViewModel.buyArmor(armorItem)){
            // Toast.makeText(context, "${sharedViewModel.name.value} purchased $armorName", Toast.LENGTH_SHORT).show()
            //}
            //else Toast.makeText(context, "${sharedViewModel.name.value} does not have enough crowns to purchase $armorName", Toast.LENGTH_SHORT).show()

            dialog.dismiss()

        }

        dialog.setContentView(bind.root)

        dialog.show()
    }

}