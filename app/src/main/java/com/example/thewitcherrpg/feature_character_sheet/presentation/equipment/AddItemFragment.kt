package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.feature_character_sheet.SharedViewModel
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.HeavyEquipmentListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.LightEquipmentListAdapter
import com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters.MediumEquipmentListAdapter
import com.example.thewitcherrpg.databinding.CustomDialogAddArmorBinding
import com.example.thewitcherrpg.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var lightAdapter: LightEquipmentListAdapter
    private lateinit var mediumAdapter: MediumEquipmentListAdapter
    private lateinit var heavyAdapter: HeavyEquipmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view).navigate(R.id.action_addArmorFragment2_to_equipmentFragment)
        }
        callback.isEnabled = true

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.equipmentCategories,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAddEquipment.adapter = adapter
        }


        binding.spinnerAddEquipment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val item = binding.spinnerAddEquipment.getItemAtPosition(position).toString()

                if(item == "Head Armor"){
                    lightAdapter.setData(resources.getStringArray(R.array.head_light_armor_data).toList())
                    mediumAdapter.setData(resources.getStringArray(R.array.head_medium_armor_data).toList())
                    heavyAdapter.setData(resources.getStringArray(R.array.head_heavy_armor_data).toList())
                }
                if (item == "Chest Armor"){
                    lightAdapter.setData(resources.getStringArray(R.array.chest_light_armor_data).toList())
                    mediumAdapter.setData(resources.getStringArray(R.array.chest_medium_armor_data).toList())
                    heavyAdapter.setData(resources.getStringArray(R.array.chest_heavy_armor_data).toList())
                }
                if (item == "Leg Armor"){
                    lightAdapter.setData(resources.getStringArray(R.array.legs_light_armor_data).toList())
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Nothing happens
            }
        }

        listAdaptersInit()

        return view
    }

    private fun listAdaptersInit(){

        lightAdapter = LightEquipmentListAdapter(requireContext(), {item -> showArmorDialog(item)}){}
        binding.rvLightEquipment.adapter = lightAdapter
        binding.rvLightEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLightEquipment.isNestedScrollingEnabled = false

        mediumAdapter = MediumEquipmentListAdapter(requireContext(), {item -> showArmorDialog(item)}){}
        binding.rvMediumEquipment.adapter = mediumAdapter
        binding.rvMediumEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMediumEquipment.isNestedScrollingEnabled = false

        heavyAdapter = HeavyEquipmentListAdapter(requireContext(), {item -> showArmorDialog(item)}){}
        binding.rvHeavyEquipment.adapter = heavyAdapter
        binding.rvHeavyEquipment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHeavyEquipment.isNestedScrollingEnabled = false

    }

    private fun showArmorDialog(armorItem: String){
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind : CustomDialogAddArmorBinding = CustomDialogAddArmorBinding.inflate(layoutInflater)

        val pair = armorItem.split(":").toTypedArray()
        val armorName = pair[0]
        val stoppingPower = "Stopping Power: " + pair[1]
        val availability = "Availability: " + pair[2]
        val armorEnhancement = "Armor Enhancement: " + pair[3]
        val effect = "Effect: " + pair[4]
        val encumbValue = "Encumbrance Value: " + pair[5]
        val weight = "Weight: " + pair[6]
        val price = "Cost: " + pair[7] + " Crowns"

        bind.textViewTitle.text = armorName
        bind.textViewSP.text = stoppingPower
        bind.textViewAvailability.text = availability
        bind.textViewAE.text = armorEnhancement
        bind.textViewEffect.text = effect
        bind.textViewEV.text = encumbValue
        bind.textViewWeight.text = weight
        bind.textViewCost.text = price

        bind.buttonCancel.setOnClickListener{
            dialog.dismiss()
        }

        bind.buttonAdd.setOnClickListener{

            sharedViewModel.addArmor(armorItem)
            Toast.makeText(context, "Item added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        bind.buttonBuy.setOnClickListener{

            if (sharedViewModel.buyArmor(armorItem)){
                Toast.makeText(context, "${sharedViewModel.name.value} purchased $armorName", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(context, "${sharedViewModel.name.value} does not have enough crowns to purchase $armorName", Toast.LENGTH_SHORT).show()

            dialog.dismiss()

        }

        dialog.setContentView(bind.root)

        dialog.show()
    }

}