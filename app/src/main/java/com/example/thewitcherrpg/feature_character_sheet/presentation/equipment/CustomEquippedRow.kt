package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.thewitcherrpg.R
import com.example.thewitcherrpg.TheWitcherTRPGApp
import com.example.thewitcherrpg.databinding.CustomEquippedRowBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.WeaponTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.models.WeaponItem
import android.util.TypedValue


class CustomEquippedRow constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var _binding: CustomEquippedRowBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomEquippedRowBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this
        )
    }

    fun setItem(item: EquipmentItem? = null) {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.textFillColor, value, true)
        val textColor = value.data

        context.theme.resolveAttribute(R.attr.colorOnError, value, true)
        val relic = value.data

        if (item != null) {
            binding.armorNameText.text = item.name
            binding.stoppingPowerText.text = "Stopping Power: " + item.stoppingPower.toString()
            binding.weightText.text = "Weight: " + item.weight.toString()

            if (item.isRelic) {
                binding.armorNameText.setTextColor(relic)
                binding.stoppingPowerText.setTextColor(relic)
                binding.weightText.setTextColor(relic)
                binding.imageView6.setBackgroundResource(R.drawable.relic_outline_bkg)
            } else {
                binding.armorNameText.setTextColor(
                    textColor
                )
                binding.stoppingPowerText.setTextColor(
                    textColor
                )
                binding.weightText.setTextColor(
                    textColor
                )
                binding.imageView6.setBackgroundResource(R.drawable.outline_bkg)
            }
        } else {
            binding.armorNameText.text = "No armor equipped."
            binding.stoppingPowerText.text = ""
            binding.weightText.text = ""
            binding.imageView6.setBackgroundResource(R.drawable.unfocused_outline_bkg)
            binding.armorNameText.setTextColor(
                textColor
            )
            binding.stoppingPowerText.setTextColor(
                textColor
            )
            binding.weightText.setTextColor(
                textColor
            )
        }
    }

    fun setItem(item: WeaponItem? = null) {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.textFillColor, value, true)
        val textColor = value.data

        context.theme.resolveAttribute(R.attr.colorOnError, value, true)
        val relic = value.data

        if (item != null) {
            binding.armorNameText.text = item.name
            if (item.type == WeaponTypes.AMULET) {
                binding.stoppingPowerText.text = "Effect: " + item.effect.toString()
                binding.weightText.text = "Cost: " + item.cost
            } else {
                binding.stoppingPowerText.text = "Reliability: " + item.reliability.toString()
                binding.weightText.text = "Damage: " + item.damage
            }

            if (item.isRelic) {
                binding.armorNameText.setTextColor(relic)
                binding.stoppingPowerText.setTextColor(relic)
                binding.weightText.setTextColor(relic)
                binding.imageView6.setBackgroundResource(R.drawable.relic_outline_bkg)
            } else {
                binding.armorNameText.setTextColor(
                    textColor
                )
                binding.stoppingPowerText.setTextColor(
                    textColor
                )
                binding.weightText.setTextColor(
                    textColor
                )
                binding.imageView6.setBackgroundResource(R.drawable.outline_bkg)
            }
            when (item.type) {
                WeaponTypes.SWORD -> binding.imageView6.setImageResource(R.drawable.ic_sword)
                WeaponTypes.SMALL_BLADE -> binding.imageView6.setImageResource(R.drawable.ic_dagger)
                WeaponTypes.AXE -> binding.imageView6.setImageResource(R.drawable.ic_axe)
                WeaponTypes.BLUDGEON -> binding.imageView6.setImageResource(R.drawable.ic_mallet)
                WeaponTypes.POLE_ARM -> binding.imageView6.setImageResource(R.drawable.ic_spears)
                WeaponTypes.STAFF -> binding.imageView6.setImageResource(R.drawable.ic_staff)
                WeaponTypes.THROWN_WEAPON -> binding.imageView6.setImageResource(R.drawable.ic_knife)
                WeaponTypes.BOW -> binding.imageView6.setImageResource(R.drawable.ic_bow)
                WeaponTypes.CROSSBOW -> binding.imageView6.setImageResource(R.drawable.ic_crossbow)
                WeaponTypes.AMULET -> binding.imageView6.setImageResource(R.drawable.ic_amulet)

            }
        } else {
            binding.armorNameText.text = "No weapon equipped."
            binding.stoppingPowerText.text = ""
            binding.weightText.text = ""
            binding.imageView6.setBackgroundResource(R.drawable.unfocused_outline_bkg)
            binding.armorNameText.setTextColor(
                textColor
            )
            binding.stoppingPowerText.setTextColor(
                textColor
            )
            binding.weightText.setTextColor(
                textColor
            )
        }
    }

    fun setIcon(icon: Int) {
        binding.imageView6.setImageResource(icon)
    }
}