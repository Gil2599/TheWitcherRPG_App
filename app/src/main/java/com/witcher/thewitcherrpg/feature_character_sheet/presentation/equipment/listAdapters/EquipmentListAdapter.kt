package com.witcher.thewitcherrpg.feature_character_sheet.presentation.equipment.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.witcher.thewitcherrpg.R
import com.witcher.thewitcherrpg.TheWitcherTRPGApp
import com.witcher.thewitcherrpg.databinding.ArmorRowBinding
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.EquipmentItem
import com.witcher.thewitcherrpg.feature_character_sheet.domain.item_types.EquipmentTypes
import com.witcher.thewitcherrpg.feature_character_sheet.domain.models.ArmorSet

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

                    if (item.isRelic) {
                        armorNameText.setTextColor(
                            ContextCompat.getColor(
                                TheWitcherTRPGApp.getContext()!!,
                                R.color.relic
                            )
                        )
                        costText.setTextColor(
                            ContextCompat.getColor(
                                TheWitcherTRPGApp.getContext()!!,
                                R.color.relic
                            )
                        )
                        stoppingPowerText.setTextColor(
                            ContextCompat.getColor(
                                TheWitcherTRPGApp.getContext()!!,
                                R.color.relic
                            )
                        )
                    }

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
        armorList = itemList
        notifyDataSetChanged()
    }
}