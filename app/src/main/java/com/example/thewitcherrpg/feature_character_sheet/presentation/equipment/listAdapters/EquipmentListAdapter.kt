package com.example.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thewitcherrpg.databinding.ArmorRowBinding
import com.example.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.example.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.example.thewitcherrpg.feature_character_sheet.domain.models.ArmorSet

class EquipmentListAdapter(
    val itemClickString: (EquipmentItem) -> Unit
) : RecyclerView.Adapter<EquipmentListAdapter.ViewHolder>() {

    private var armorList = emptyList<EquipmentItem>()
    private lateinit var currentItem: EquipmentItem

    inner class ViewHolder(private val binding: ArmorRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EquipmentItem, position: Int) {

            if (item.equipmentType != EquipmentTypes.MISC_CUSTOM) {
                val weight = "Cost: " + item.cost.toString()
                val stoppingPower = "Stopping Power: " + item.stoppingPower.toString()

                with(binding) {
                    armorNameText.text = item.name
                    costText.text = weight
                    stoppingPowerText.text = stoppingPower

                    rowLayout.setOnClickListener {
                        itemClickString(armorList[position])

                    }
                }
            } else {
                val weight = "Quantity: " + item.quantity.toString()
                val stoppingPower = "Total Cost: " + item.totalCost.toString()

                with(binding) {
                    armorNameText.text = item.name
                    costText.text = weight
                    stoppingPowerText.text = stoppingPower

                    rowLayout.setOnClickListener {
                        itemClickString(armorList[position])

                    }
                }
            }
        }

        fun bind(item: ArmorSet, position: Int) {

            val weight = "Cost: " + item.cost.toString()
            val stoppingPower = "Stopping Power: " + item.stoppingPower.toString()

            with(binding) {
                armorNameText.text = item.name
                costText.text = weight
                stoppingPowerText.text = stoppingPower

                rowLayout.setOnClickListener {
                    itemClickString(armorList[position])

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ArmorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentItem = armorList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return armorList.size
    }

    fun setData(itemList: ArrayList<EquipmentItem>) {
        this.armorList = itemList
        notifyDataSetChanged()
    }
}