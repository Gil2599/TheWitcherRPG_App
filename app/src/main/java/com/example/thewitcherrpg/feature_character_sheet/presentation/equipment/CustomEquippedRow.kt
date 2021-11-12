package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.databinding.CustomEquippedRowBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_models.EquipmentItem

class CustomEquippedRow constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var _binding: CustomEquippedRowBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomEquippedRowBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater, this)
    }

    fun setItem(item: EquipmentItem? = null){
        if (item != null) {
            binding.armorNameText.text = item.name
            binding.stoppingPowerText.text = "Stopping Power: " + item.stoppingPower.toString()
            binding.weightText.text = "Weight: " + item.weight.toString()
            binding.imageView6.setBackgroundResource(R.drawable.outline_bkg)
            }
        else {
            binding.armorNameText.text = "No armor equipped."
            binding.stoppingPowerText.text = ""
            binding.weightText.text = ""
            binding.imageView6.setBackgroundResource(R.drawable.unfocused_outline_bkg)
        }
    }


    fun setIcon(icon: Int){
        binding.imageView6.setImageResource(icon)
    }
}