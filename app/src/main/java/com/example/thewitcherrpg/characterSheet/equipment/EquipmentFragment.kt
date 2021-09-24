package com.example.thewitcherrpg.characterSheet.equipment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.characterSheet.SharedViewModel
import com.example.thewitcherrpg.databinding.*

class EquipmentFragment : Fragment() {
    private var _binding: FragmentEquipmentBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root
        
        binding.imageViewHead.setOnClickListener{
            Toast.makeText(context, "This is the head", Toast.LENGTH_SHORT).show()
        }

        binding.imageViewSword.setOnClickListener(){
            showWeaponDialog()
        }

        binding.buttonAddGear.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_equipmentFragment_to_addArmorFragment2)
        }

        return view
    }

    private fun showWeaponDialog() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val bind : CustomDialogWeaponBinding = CustomDialogWeaponBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        dialog.show()
    }


}