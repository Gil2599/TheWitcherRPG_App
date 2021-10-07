package com.example.thewitcherrpg.characterSheet.equipment

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
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.characterSheet.equipment.listAdapters.LightEquipmentListAdapter
import com.example.thewitcherrpg.databinding.CustomDialogAddArmorBinding
import com.example.thewitcherrpg.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var lightAdapter: LightEquipmentListAdapter

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
            item -> showArmorDialog(item)
        }

        binding.rvLightEquipment.adapter = lightAdapter
        binding.rvLightEquipment.layoutManager = LinearLayoutManager(requireContext())

        binding.rvLightEquipment.isNestedScrollingEnabled = false
    }

    private fun showArmorDialog(armorItem: String){
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bind : CustomDialogAddArmorBinding = CustomDialogAddArmorBinding.inflate(layoutInflater)

        bind.buttonCancel.setOnClickListener{
            dialog.dismiss()
        }

        bind.buttonAdd.setOnClickListener{

            if (sharedViewModel.addArmor(armorItem)){
                Toast.makeText(context, "Item added to ${sharedViewModel.name.value}", Toast.LENGTH_SHORT).show()

            } else Toast.makeText(context, "${sharedViewModel.name.value} already has this item.", Toast.LENGTH_SHORT).show()

            //Toast.makeText(context, sharedViewModel.headEquipment.value.toString(), Toast.LENGTH_SHORT).show()

        }

        dialog.setContentView(bind.root)

        dialog.show()
    }

}