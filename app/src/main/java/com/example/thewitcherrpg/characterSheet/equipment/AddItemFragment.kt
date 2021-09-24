package com.example.thewitcherrpg.characterSheet.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.equipment.listAdapters.LightEquipmentListAdapter
import com.example.thewitcherrpg.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var lightAdapter: LightEquipmentListAdapter
    private lateinit var enum: LightEquipmentListAdapter.ArmorType

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

        listAdaptersInit()

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
                }
                else if (item == "Chest Armor"){
                    lightAdapter.setData(resources.getStringArray(R.array.chest_light_armor_data).toList())
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Nothing happens
            }
        }


        return view
    }

    private fun listAdaptersInit(){
        //val noviceAdapter = NoviceSpellListAdapter(requireContext()){
          //      spell -> showSpellDialog(spell, SpellAddFragment.SpellLevel.NOVICE) //Determines which list this spell goes under
        //}
        lightAdapter = LightEquipmentListAdapter(requireContext()){
        }

        lightAdapter.setAddSpell(true) //Sets add spell state to true to show all spells

        binding.rvLightEquipment.adapter = lightAdapter
        binding.rvLightEquipment.layoutManager = LinearLayoutManager(requireContext())

        binding.rvLightEquipment.isNestedScrollingEnabled = false
    }

}