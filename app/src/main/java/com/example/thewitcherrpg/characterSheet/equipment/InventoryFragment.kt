package com.example.thewitcherrpg.characterSheet.equipment

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
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.equipment.listAdapters.LightEquipmentListAdapter
import com.example.thewitcherrpg.databinding.FragmentEquipmentBinding
import com.example.thewitcherrpg.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var lightAdapter: LightEquipmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val view = binding.root

        //Adds a callback to back button to return to previous fragment in nav graph instead of destroying activity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            Navigation.findNavController(view).navigate(R.id.action_inventoryFragment_to_equipmentFragment)
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
                    sharedViewModel.headEquipment.value?.let { lightAdapter.setData(it.toList()) }
                }
                else if (item == "Chest Armor"){
                    lightAdapter.setData(resources.getStringArray(R.array.chest_light_armor_data).toList())
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

        lightAdapter = LightEquipmentListAdapter(requireContext()){
                //item -> showArmorDialog(item)
        }

        binding.rvLightEquipment.adapter = lightAdapter
        binding.rvLightEquipment.layoutManager = LinearLayoutManager(requireContext())

        binding.rvLightEquipment.isNestedScrollingEnabled = false
    }

}